package com.apulbere.routes;

import com.apulbere.cprocessor.routes.CustomerConsumer;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ToDynamicDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
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

    @Before
    public void init() throws Exception {
        camelContext.getRouteDefinitions().get(0).adviceWith(context,
            new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    weaveByType(ToDynamicDefinition.class).replace().to("mock:toRedis");
                }
        });
    }

    @Test
    public void notWorkingTest() throws Exception {
        MockEndpoint mockToRedis = getMockEndpoint("mock:toRedis");
        mockToRedis.expectedMessageCount(2);

        producerTemplate.sendBody("8f9ed0f5-1440-4754-954a-4ef4da1a2093");

        mockToRedis.assertIsSatisfied();
    }


}
