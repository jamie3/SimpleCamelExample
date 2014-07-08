package com.whatever;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

	public static void main(String[] args) throws Exception {
		
		CamelContext camel = new DefaultCamelContext();
		camel.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		
		RouteBuilder inputRoute = new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				from("activemq:queue:flightfollow")
				.convertBodyTo(String.class)
				.bean(new ReportGenerator(), "onFlightFollow")
				.to("activemq:queue:processedFlightFollow");
				
				from("activemq:queue:textmessage")
				.convertBodyTo(String.class)
				.bean(new ReportGenerator(), "onTextMessage");
			}
		};
		
		camel.addRoutes(inputRoute);
		camel.start();
		
		Object obj = new Object();
		synchronized (obj) {
			obj.wait();
		}
	}
}
