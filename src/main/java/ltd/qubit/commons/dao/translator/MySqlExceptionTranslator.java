////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao.translator;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import ltd.qubit.commons.error.DuplicateKeyException;
import ltd.qubit.commons.error.FieldTooLongException;
import ltd.qubit.commons.error.FieldValueOutOfRangeException;
import ltd.qubit.commons.error.ForeignKeyConstraintFailException;
import ltd.qubit.commons.error.InvalidFieldValueCharacterException;
import ltd.qubit.commons.error.NullFieldException;
import ltd.qubit.commons.sql.DaoOperation;
import ltd.qubit.commons.util.codec.HexCodec;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The exception translator for the MySQL database.
 *
 * @author Haixing Hu
 */
public class MySqlExceptionTranslator implements PersistenceExceptionTranslator {

  private static final Pattern COLUMN_CANNOT_NULL =
      Pattern.compile("Column '(.+)' cannot be null");

  private static final Pattern NULL_NOT_ALLOWED =
      Pattern.compile("NULL not allowed for column \"([^ ]+)\"");

  private static final Pattern NO_DEFAULT_VALUE =
      Pattern.compile("Field '(.+)' doesn't have a default value");

  private static final Pattern DUPLICATED_KEY =
      Pattern.compile("Duplicate entry '(.+)' for key '(.+)'");

  private static final Pattern UNIQUE_INDEX =
      Pattern.compile("Unique index or primary key violation: \"[^ ]+ ON "
          + "PUBLIC\\.[^(]+\\(([^)]+)\\) VALUES \\('(.+)', [0-9]+\\)\"");

  private static final Pattern DATA_TOO_LONG = Pattern
      .compile("Data too long for column '(.+)'");

  private static final Pattern VALUE_TOO_LONG =
      Pattern.compile("Value too long for column \"([^ ]+).*\"");

  private static final Pattern FOREIGN_KEY_FAILED =
      Pattern.compile("Cannot ([a-z]+) or update a [a-z]+ row: "
          + "a foreign key constraint fails "
          + "\\(`.+`\\.`.+`, CONSTRAINT `.+` FOREIGN KEY \\(`(.+)`\\) "
          + "REFERENCES `(.+)` \\(`(.+)`\\)[ A-Za-z]*\\)");

  private static final Pattern INTEGRITY_VIOLATION =
      Pattern.compile("Referential integrity constraint violation: "
          + "\"[a-zA-Z0-9_]+: [^ ]+ FOREIGN KEY\\(([^)]+)\\) REFERENCES "
          + "[^.]+.([^(]+)\\(([^)]+)\\) \\([^)]+\\)\"; "
          + "SQL statement:[\\s]+([a-zA-Z]+)");

  private static final Pattern OUT_OF_RANGE =
      Pattern.compile("Out of range value for column '([^']+)' at row");

  private static final Pattern INVALID_CHARACTER =
      Pattern.compile("Incorrect string value: '([^']+)' for column '([^']+)'");

  private static final String ENDING_ELLIPSIS = "...";

  private static final Logger LOGGER = LoggerFactory.getLogger(MySqlExceptionTranslator.class);

  //  private static final Pattern NUMBER_OUT_Of_RNAGE =
  //      Pattern.compile("Numeric value out of range: \"([^\"]+)\"");

  private final SQLExceptionTranslator fallbackTranslator;

  /**
   * Creates a new {@code MySqlExceptionTranslator} instance.
   *
   * @param dataSource
   *          the data source to use to find metadata and establish which error
   *          codes are usable.
   */
  public MySqlExceptionTranslator(final DataSource dataSource) {
    fallbackTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
  }

  @Override
  public DataAccessException translateExceptionIfPossible(final RuntimeException e) {
    if (e instanceof PersistenceException) {
      RuntimeException ex = e;
      // Batch exceptions come inside another PersistenceException
      // recursion has a risk of infinite loop so better make another if
      if (ex.getCause() instanceof PersistenceException) {
        ex = (PersistenceException) ex.getCause();
      }
      if (ex.getCause() instanceof final SQLException cause) {
        final DataAccessException result = translateMySqlError(cause);
        if (result != null) {
          return result;
        } else {
          return fallbackTranslator.translate(ex.getMessage() + "\n", null, cause);
        }
      }
      return new MyBatisSystemException(ex);
    } else {
      return null;
    }
  }

  public static DataAccessException translateMySqlError(final Throwable cause) {
    DataAccessException ex = translateNullField(cause);
    if (ex != null) {
      return ex;
    }
    ex = translateDuplicatedKey(cause);
    if (ex != null) {
      return ex;
    }
    ex = translateDataTooLong(cause);
    if (ex != null) {
      return ex;
    }
    ex = translateForeignKeyFail(cause);
    if (ex != null) {
      return ex;
    }
    ex = translateOutOfRange(cause);
    if (ex != null) {
      return ex;
    }
    ex = translateInvalidCharacter(cause);
    return ex;
  }

  public static DataAccessException translateNullField(final Throwable cause) {
    // match MySQL database exceptions
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for NullFieldException: {}", message);
    final Matcher cannotNull = COLUMN_CANNOT_NULL.matcher(message);
    if (cannotNull.find()) {
      final String field = cannotNull.group(1).toLowerCase();
      return new NullFieldException(field);
    } else {
      // field no default value
      final Matcher noDefaultValue = NO_DEFAULT_VALUE.matcher(message);
      if (noDefaultValue.find()) {
        final String field = noDefaultValue.group(1).toLowerCase();
        return new NullFieldException(field);
      } else {  // match H2 database exceptions
        final Matcher nullNotAllowed = NULL_NOT_ALLOWED.matcher(message);
        if (nullNotAllowed.find()) {
          final String field = nullNotAllowed.group(1).toLowerCase();
          return new NullFieldException(field);
        }
        return null;
      }
    }
  }

  public static DataAccessException translateDuplicatedKey(final Throwable cause) {
    // match MySQL database exceptions
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for DuplicateKeyException: {}", message);
    final Matcher duplicatedKey = DUPLICATED_KEY.matcher(message);
    if (duplicatedKey.find()) {
      final String key = getLastField(duplicatedKey.group(2).toLowerCase());
      final String value = duplicatedKey.group(1);
      return new DuplicateKeyException(key, value);
    } else {  // match H2 database exceptions
      final Matcher uniqueIndex = UNIQUE_INDEX.matcher(message);
      if (uniqueIndex.find()) {
        final String key = uniqueIndex.group(1).toLowerCase();
        final String value = uniqueIndex.group(2);
        return new DuplicateKeyException(key, value);
      }
      return null;
    }
  }

  public static DataAccessException translateDataTooLong(final Throwable cause) {
    // match MySQL database exceptions
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for FieldTooLongException: {}", message);
    final Matcher dataTooLong = DATA_TOO_LONG.matcher(message);
    if (dataTooLong.find()) {
      final String field = dataTooLong.group(1).toLowerCase();
      return new FieldTooLongException(field);
    } else {  // match H2 database exceptions
      final Matcher valueTooLong = VALUE_TOO_LONG.matcher(message);
      if (valueTooLong.find()) {
        final String field = valueTooLong.group(1).toLowerCase();
        return new FieldTooLongException(field);
      }
      return null;
    }
  }

  public static DataAccessException translateForeignKeyFail(
      final Throwable cause) {
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for "
        + "ForeignKeyConstraintFailException: {}", message);
    // match MySQL database exceptions
    final Matcher foreignKeyFailed = FOREIGN_KEY_FAILED.matcher(message);
    if (foreignKeyFailed.find()) {
      final DaoOperation operation;
      final String op = foreignKeyFailed.group(1);
      if (op.equalsIgnoreCase("add")) {
        operation = DaoOperation.ADD_OR_UPDATE;
      } else if (op.equalsIgnoreCase("delete")) {
        operation = DaoOperation.DELETE;
      } else {
        operation = DaoOperation.UNKNOWN;
      }
      final String field = foreignKeyFailed.group(2).toLowerCase();
      final String referenceEntry = foreignKeyFailed.group(3).toLowerCase();
      final String referenceField = foreignKeyFailed.group(4).toLowerCase();
      return new ForeignKeyConstraintFailException(operation, field, referenceEntry,
          referenceField);
    } else {
      // match h2 database exceptions
      final Matcher integrityViolation = INTEGRITY_VIOLATION.matcher(message);
      if (integrityViolation.find()) {
        final DaoOperation operation;
        final String op = integrityViolation.group(4);
        if (op.equalsIgnoreCase("insert")) {
          operation = DaoOperation.ADD_OR_UPDATE;
        } else if (op.equalsIgnoreCase("update")) {
          operation = DaoOperation.ADD_OR_UPDATE;
        } else if (op.equalsIgnoreCase("delete")) {
          operation = DaoOperation.DELETE;
        } else {
          operation = DaoOperation.UNKNOWN;
        }
        final String field = integrityViolation.group(1).toLowerCase();
        final String referenceEntry = integrityViolation.group(2).toLowerCase();
        final String referenceField = integrityViolation.group(3).toLowerCase();
        return new ForeignKeyConstraintFailException(operation, field, referenceEntry,
            referenceField);
      }
      return null;
    }
  }

  public static DataAccessException translateOutOfRange(final Throwable cause) {
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for "
        + "FieldValueOutOfRangeException: {}", message);
    final Matcher outOfRange = OUT_OF_RANGE.matcher(message);
    if (outOfRange.find()) {
      final String field = outOfRange.group(1);
      return new FieldValueOutOfRangeException(field);
    }
    return null;
  }

  private static String getLastField(final String fieldPath) {
    final int pos = fieldPath.lastIndexOf('.');
    if (pos < 0) {
      return fieldPath;
    } else {
      return fieldPath.substring(pos + 1);
    }
  }

  public static DataAccessException translateInvalidCharacter(final Throwable cause) {
    final String message = cause.getMessage();
    LOGGER.debug("Try to translate the MySQL error message for "
        + "InvalidFieldValueCharacterException: {}", message);
    final Matcher invalidCharacter = INVALID_CHARACTER.matcher(message);
    if (invalidCharacter.find()) {
      final String value = translateUtf8Hex(invalidCharacter.group(1));
      final String field = invalidCharacter.group(2);
      return new InvalidFieldValueCharacterException(field, value);
    }
    return null;
  }

  private static String translateUtf8Hex(final String hexString) {
    final boolean ellipsis;
    final String str;
    if (hexString.endsWith(ENDING_ELLIPSIS)) {
      ellipsis = true;
      str = hexString.substring(0, hexString.length() - ENDING_ELLIPSIS.length());
    } else {
      ellipsis = false;
      str = hexString;
    }
    final HexCodec codec = new HexCodec();
    codec.setShowRadix(false);
    codec.setPrefix("\\x");
    final byte[] bytes = codec.decode(str);
    final String result = new String(bytes, UTF_8);
    if (ellipsis) {
      return result + ENDING_ELLIPSIS;
    } else {
      return result;
    }
  }
}
