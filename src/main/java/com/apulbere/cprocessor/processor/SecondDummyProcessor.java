package com.apulbere.cprocessor.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("secondDummyProcessor")
public class SecondDummyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("I am too");
    }
}
