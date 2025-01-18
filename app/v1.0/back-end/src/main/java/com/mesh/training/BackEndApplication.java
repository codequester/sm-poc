package com.mesh.training;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
@RestController
public class BackEndApplication {

	
	@Value("${app.version}")
	private String appVersion;

	@Value("${app.instance-id}")
	private String appInstanceId;

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
    	return new TimedAspect(registry);
	}		

	@GetMapping("/")
	public String getAppStatus() {
		return  "Back-End Service (" +appVersion+ ") - [" +appInstanceId+ "] is running!";
	}	

	@GetMapping("/greet") 
	@Timed(value = "backend-end-greeted.time", description = "Time taken to perform the greeting by the Back End Service")
	public String greet(
		@RequestHeader Map<String, String> headers) {
			headers.forEach((key, value) -> {
				System.out.println(String.format("Header '%s' = %s", key, value));
			});
		System.out.println("Fetching Greetings...");	
		return  "Greetings :) - From Back-End Service ("+appVersion+")";
	}	
}
