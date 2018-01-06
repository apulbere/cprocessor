package com.apulbere.cprocessor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileDownloadRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:fileDownloader")
            .log(LoggingLevel.INFO, "Downloading file for upload id: ${header.uploadId}")
            .toD("dropbox://get?{{dropbox.uri}}&remotePath=/${header.uploadId}/customer.json")
            .to("file://in?fileName=${header.uploadId}.customer.json&autoCreate=true")
        .end();
    }
}
