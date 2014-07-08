package com.whatever;

public class ReportGenerator {

	public String onTextMessage(String text) {
		return "test message";
	}
	
	// on every flight follow generate report
	public String onFlightFollow(String ff) {
		
		System.out.println(ff);
		return "flight follow 123";
	}
}
