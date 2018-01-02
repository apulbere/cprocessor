package com.apulbere.routes;

import com.apulbere.cprocessor.routes.CustomerConsumer;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = CustomerConsumer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableAutoConfiguration
public class CustomerConsumerTest extends CamelTestSupport {

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = "direct:customerToRedis")
    private ProducerTemplate producerTemplate;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return camelContext;
    }

    @Test
    public void notWorkingTest() throws Exception {
        producerTemplate.sendBody("8f9ed0f5-1440-4754-954a-4ef4da1a2093");
    }


}
