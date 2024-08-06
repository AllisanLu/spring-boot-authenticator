package com.example.authenticator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.authenticator.data.Customer;
import com.example.authenticator.data.Token;
import com.example.authenticator.data.TokenRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountAPI {
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@GetMapping("/token")
	public boolean validateToken(@PathVariable("token") String token) {
		Token tokenObj = tokenRepo.findFirstByToken(token);
		
		if (tokenObj != null) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/token")
	public Optional<Token> newToken(@PathVariable("account") Customer customer) {
		// make api call to Customer api 
		String uri = "https://localhost:8080/api/customers/validate";
		RestTemplate restTemplate = new RestTemplate();
		Customer[] result = restTemplate.getForObject(uri,  Customer[].class);
		
		//check if customer is there
		for (Customer r : result) {
			if (r.getName().equals(customer.getName()) 
					&& r.getPassword().equals(customer.getPassword())) {
				// generate token
				
			}
		}
		return Optional.empty();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> newAccount(@PathVariable("account") Customer newCustomer) {
		// push new customer to customer api
		String uri = "https://localhost:8080/api/customers/";
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Customer> request = new HttpEntity<>(newCustomer);
		Customer result = restTemplate.postForObject(uri, request, Customer.class);
		
		return ResponseEntity.ok().build();
	}
	
}
