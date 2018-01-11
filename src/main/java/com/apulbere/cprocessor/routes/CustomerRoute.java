package com.apulbere.cprocessor.routes;

import com.apulbere.cprocessor.model.Customer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.component.redis.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomerRoute extends RouteBuilder {

    private JacksonDataFormat dataFormat = new ListJacksonDataFormat(Customer.class);

    @Autowired
    @Qualifier("firstDummyProcessor")
    private Processor firstDummyProcessor;
    @Autowired
    @Qualifier("secondDummyProcessor")
    private Processor secondDummyProcessor;

    @Override
    public void configure() throws Exception {
        from("direct:customerToRedis")
            .log(LoggingLevel.INFO, "Inserting customers into Redis for upload id: ${header.uploadId}")
            .pollEnrich().simple("file:in?fileName=${header.uploadId}.customer.json&delete=true")
            .unmarshal(dataFormat)
            .process(firstDummyProcessor)
            .split(body())
                .setHeader(RedisConstants.KEY, constant("customerLookup"))
                .setHeader(RedisConstants.VALUE, simple("${body.name}"))
                .process(secondDummyProcessor)
                .toD("spring-redis://{{redis.host}}:{{redis.port}}?command=SADD&redisTemplate=#customTemplate")
                .log(LoggingLevel.INFO, "${header.CamelRedis.Value} was inserted into Redis")
        .end();
    }
}
