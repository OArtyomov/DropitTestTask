package com.dropit.core;

import com.dropit.DeliveryApplication;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
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

import static org.junit.rules.ExpectedException.none;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {DeliveryApplication.class},
		value = {"spring.jmx.enabled=true"}
)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ContextConfiguration()
@ActiveProfiles("componentTest")
public abstract class AbstractBaseIT {

	@Autowired
	protected MockMvc mockMvc;

	@Value("${local.server.port}")
	protected int serverPort;

	@Autowired
	protected DeliveryRepository deliveryRepository;

	@Autowired
	protected PackageRepository packageRepository;

	@Rule
	public ExpectedException expectedException = none();


	@Before
	public void beforeEachTest() {
		packageRepository.deleteAllInBatch();
		deliveryRepository.deleteAllInBatch();
	}


}