package com.mesh.training;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
@RestController
public class BackEndApplication {

	@Value("${app.version}")
	private String appVersion;

	@Value("${app.instance-id}")
	private String appInstanceId;

	@Value("${app.flag.throw.err}")
	private boolean isThrowException;

	@Value("${app.flag.cause.delay}")
	private long sleepMillis;

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@GetMapping("/")
	public String getAppStatus() {
		return  "Back-End Service (" +appVersion+ ") - [" +appInstanceId+ "] is running!";
	}	



	@GetMapping("/greet") 
	public String greet( @RequestHeader Map<String, String> headers) throws InterruptedException {
				headers.forEach((key, value) -> {
					System.out.println(String.format("Header '%s' = %s", key, value));
			});  
		if (isThrowException) {
			System.err.println("Something went wrong - Unable to process request");
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Back End Service ["+appInstanceId+"] Not Available!!!");
		}
		if (sleepMillis > 0 ) {
			Thread.sleep(sleepMillis);
		}
		System.out.println("Fetching Greetings...");	
		return  "Greetings 🙏 - From Back-End Service ("+appVersion+") 😎";
	}
	
	@GetMapping("/fault") 
	public String setFault(@RequestParam boolean throwErr, @RequestParam long delay ) throws InterruptedException {
		this.isThrowException=throwErr;
		this.sleepMillis=delay;
		return  "Injected Faults Via Code!!!!";
	}
}
