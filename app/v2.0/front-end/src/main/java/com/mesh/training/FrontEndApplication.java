package com.mesh.training;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestHeader;

@SpringBootApplication
@RestController
public class FrontEndApplication {

	@Value("${backend.service.url}")
	private String backendServiceUrl;

	@Autowired 
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(FrontEndApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}	

	@GetMapping("/") 
	public String greet(@RequestHeader(name = "opt-in", required = false) String optIn, 
						@RequestHeader(name = "user-agent", required = false) String userAgent,
						@RequestHeader(name = "user", required = false) String userStr) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("opt-in",optIn);
		headers.add("user-agent",userAgent);
		headers.add("user",userStr);
		HttpEntity<String> entity = new HttpEntity<>("", headers);
		ResponseEntity<String> response = restTemplate.exchange(backendServiceUrl+"/greet", HttpMethod.GET, entity, String.class);
		String greetingsTxt = response.getBody();
		return  "Front-End Service -> " + greetingsTxt;
	}	

	@GetMapping("/status") 
	public String getServiceStatus() {
		String backendServiceStatus = restTemplate.getForObject(backendServiceUrl+"/", String.class);
		return  "Front-End Service is Running !! -> " + backendServiceStatus;
	}
}
