package com.apulbere.cprocessor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rabbitmq://{{amqp.uri}}")
            .log(LoggingLevel.INFO, "Receiving upload id: ${body}")
            .setHeader("uploadId", body())
            .multicast()
                .to("direct:fileDownloader", "direct:customerToRedis")
        .end();
    }
}
