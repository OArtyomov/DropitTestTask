package com.dropit.core;

import com.dropit.DeliveryApplication;
import com.dropit.config.IntegrationTestConfigurationInitializer;
import com.dropit.dao.AddressRepository;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.CreateAddressDTO;
import com.dropit.model.AddressEntity;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.junit.rules.ExpectedException.none;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {DeliveryApplication.class},
		value = {"spring.jmx.enabled=true"}
)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ContextConfiguration(initializers = {IntegrationTestConfigurationInitializer.class})
@ActiveProfiles("componentTest")
public abstract class AbstractBaseIT {

	@Autowired
	protected MockMvc mockMvc;

	@Value("${local.server.port}")
	protected int serverPort;

	@Resource
	protected ObjectMapper objectMapper;

	@Resource
	protected DeliveryRepository deliveryRepository;

	@Resource
	protected PackageRepository packageRepository;

	@Resource
	protected AddressRepository addressRepository;

	@Rule
	public ExpectedException expectedException = none();


	@Before
	public void beforeEachTest() {
		packageRepository.deleteAllInBatch();
		deliveryRepository.deleteAllInBatch();
		addressRepository.deleteAllInBatch();
	}

	protected PackageEntity buildPackage(String tag, DeliveryEntity deliveryEntity) {
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setTag(tag);
		packageEntity.setDelivery(deliveryEntity);
		return packageEntity;
	}

	protected DeliveryEntity buildEntity(String deliveryName, AddressEntity addressEntity) {
		DeliveryEntity deliveryEntity = new DeliveryEntity();
		deliveryEntity.setName(deliveryName);
		deliveryEntity.setAddress(addressEntity);
		return deliveryEntity;
	}

	protected CreateAddressDTO createAddress(String addressAsOneLine) {
		CreateAddressDTO result = new CreateAddressDTO();
		result.setAddressLine(addressAsOneLine);
		return result;
	}

}