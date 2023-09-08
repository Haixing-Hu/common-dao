////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.dao;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.dao.testbed.AppDao;
import ltd.qubit.commons.dao.testbed.AttachmentDao;
import ltd.qubit.commons.dao.testbed.CategoryDao;
import ltd.qubit.commons.dao.testbed.CityDao;
import ltd.qubit.commons.dao.testbed.CountryDao;
import ltd.qubit.commons.dao.testbed.CredentialDao;
import ltd.qubit.commons.dao.testbed.DeviceDao;
import ltd.qubit.commons.dao.testbed.DistrictDao;
import ltd.qubit.commons.dao.testbed.OrganizationDao;
import ltd.qubit.commons.dao.testbed.PayloadDao;
import ltd.qubit.commons.dao.testbed.PersonDao;
import ltd.qubit.commons.dao.testbed.ProvinceDao;
import ltd.qubit.commons.dao.testbed.SourceDao;
import ltd.qubit.commons.dao.testbed.StreetDao;
import ltd.qubit.commons.dao.testbed.UploadDao;
import ltd.qubit.commons.dao.testbed.UserDao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.random.RandomBeanGenerator;
import ltd.qubit.commons.test.dao.DaoTestGeneratorRegistry;
import ltd.qubit.commons.util.clock.MockClock;
import ltd.qubit.model.commons.App;
import ltd.qubit.model.commons.Category;
import ltd.qubit.model.commons.Credential;
import ltd.qubit.model.commons.Payload;
import ltd.qubit.model.commons.Source;
import ltd.qubit.model.contact.City;
import ltd.qubit.model.contact.Country;
import ltd.qubit.model.contact.District;
import ltd.qubit.model.contact.Province;
import ltd.qubit.model.contact.Street;
import ltd.qubit.model.device.Device;
import ltd.qubit.model.organization.Organization;
import ltd.qubit.model.person.Person;
import ltd.qubit.model.person.User;
import ltd.qubit.model.system.Host;
import ltd.qubit.model.system.Setting;
import ltd.qubit.model.upload.Attachment;
import ltd.qubit.model.upload.Upload;

/**
 * 对DAO进行集成测试的类的基类。
 *
 * @author 胡海星
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration({"classpath:test-context.xml"})
@Transactional
public class DaoTestBase {

  protected static final int TEST_LOOPS = 50;

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected final DaoTestGeneratorRegistry registry = new DaoTestGeneratorRegistry();

  protected final RandomBeanGenerator generator = registry.getRandom();

  protected boolean daoRegistered = false;

  @Autowired
  protected MockClock clock;

  protected ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  protected HostDao hostDao;

  @Autowired
  protected SettingDao settingDao;

  @Autowired
  protected CountryDao countryDao;

  @Autowired
  protected ProvinceDao provinceDao;

  @Autowired
  protected CityDao cityDao;

  @Autowired
  protected DistrictDao districtDao;

  @Autowired
  protected StreetDao streetDao;

  @Autowired
  protected PayloadDao payloadDao;

  @Autowired
  protected CategoryDao categoryDao;

  @Autowired
  protected UploadDao uploadDao;

  @Autowired
  protected AttachmentDao attachmentDao;

  @Autowired
  protected CredentialDao credentialDao;

  @Autowired
  protected OrganizationDao organizationDao;

  @Autowired
  protected AppDao appDao;

  @Autowired
  protected SourceDao sourceDao;

  @Autowired
  protected UserDao userDao;

  @Autowired
  protected PersonDao personDao;

  @Autowired
  protected DeviceDao deviceDao;

  @BeforeEach
  public void beforeEach() throws Exception {
    clock.reset();
    clock.add(1, TimeUnit.SECONDS, true);  // 每次调用 MockClock 都增加 1 秒
    TimeZone.setDefault(DateUtils.UTC);
    if (! daoRegistered) {
      initDaoRegistry();
    }
  }

  private synchronized void initDaoRegistry() {
    if (! daoRegistered) {
      registry.register(Host.class, hostDao);
      registry.register(Setting.class, settingDao);
      registry.register(Country.class, countryDao);
      registry.register(Province.class, provinceDao);
      registry.register(City.class, cityDao);
      registry.register(District.class, districtDao);
      registry.register(Street.class, streetDao);
      registry.register(Payload.class, payloadDao);
      registry.register(Category.class, categoryDao);
      registry.register(Upload.class, uploadDao);
      registry.register(Attachment.class, attachmentDao);
      registry.register(Credential.class, credentialDao);
      registry.register(Organization.class, organizationDao);
      registry.register(App.class, appDao);
      registry.register(Source.class, sourceDao);
      registry.register(User.class, userDao);
      registry.register(Person.class, personDao);
      registry.register(Device.class, deviceDao);
      daoRegistered = true;
    }
  }

  @AfterEach
  public void afterEach() throws Exception {
    //  empty
  }
}
