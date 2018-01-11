package com.apulbere.routes;

import com.apulbere.TestConfig;
import com.apulbere.cprocessor.processor.FirstDummyProcessor;
import com.apulbere.cprocessor.processor.SecondDummyProcessor;
import com.apulbere.cprocessor.routes.CustomerRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = { TestConfig.class,
                            CustomerRoute.class,
                            FirstDummyProcessor.class,
                            SecondDummyProcessor.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableAutoConfiguration
@ActiveProfiles("useMocks")
public class CustomerRouteTest extends CamelTestSupport {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "direct:customerToRedis")
    private ProducerTemplate producerTemplate;

    @Autowired
    private SetOperations<String, String> setOperations;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return camelContext;
    }

    @Before
    public void init() throws Exception {
        Files.copy(getClass().getResourceAsStream("/8f9ed0f5-1440-4754-954a-4ef4da1a2093.customer.json"),
                Paths.get("in/8f9ed0f5-1440-4754-954a-4ef4da1a2093.customer.json"));

    }

    @Test
    public void routeIsProcessingTheUpload() throws Exception {
        producerTemplate.sendBodyAndHeader(null, "uploadId", "8f9ed0f5-1440-4754-954a-4ef4da1a2093");

        verify(setOperations, times(1)).add("customerLookup", "Konoba Ltd");
        verify(setOperations, times(1)).add("customerLookup", "Obel Inc");
    }
}
