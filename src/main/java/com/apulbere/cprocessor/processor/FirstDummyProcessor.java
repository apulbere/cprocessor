package com.apulbere.cprocessor.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("firstDummyProcessor")
public class FirstDummyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Yes, I am");
    }
}
