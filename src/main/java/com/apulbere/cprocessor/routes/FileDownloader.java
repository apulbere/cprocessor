package com.apulbere.cprocessor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileDownloader extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:fileDownloader")
            .log(LoggingLevel.INFO, "Downloading file for upload id: ${body}")
            .toD("dropbox://get?{{dropbox.uri}}&remotePath=/${body}")
            .to("file://in?fileName=customer.json&autoCreate=true")
        .end();
    }
}
