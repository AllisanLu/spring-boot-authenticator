package com.example.authenticator.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.authenticator.data.Customer;
import com.example.authenticator.data.Token;
import com.example.authenticator.security.JWTHelper;

@RestController
@RequestMapping("/account")
public class AccountAPI {
	
	private static Token appUserToken;
	
	@GetMapping("/token")
	public String getAll() {
		return "jwt-fake-token-asdfasdfasfa".toString();
	}
	
	@PostMapping("/token")
	public ResponseEntity<?> newToken(@RequestBody Customer customer) {		
		//check if customer is there
		if (checkPassword(customer.getName(), customer.getPassword())) { 
				// generate token
			return ResponseEntity.ok(createToken(customer.getName()));
		}
		
		return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> newAccount(@RequestBody Customer newCustomer) {
		// push new customer to customer api
		String uri = "http://localhost:8080/api/customers";
		RestTemplate restTemplate = new RestTemplate();
		
		//add header with a token 
		HttpHeaders headers = new HttpHeaders();
		String token = getAppUserToken().getToken();
		headers.add("Authorization", "Bearer " + token);
		
		HttpEntity<Customer> request = new HttpEntity<>(newCustomer, headers);
		ResponseEntity<Customer> result = restTemplate.exchange(uri, HttpMethod.POST, request, Customer.class);
		
		return result;
	}	
	
	// Not api end points, helper methods
	public static boolean checkPassword(String user, String password) {
		// make api call to Customer api 
		
		String uri = "http://localhost:8080/api/customers/byname/" + user;
		
		HttpHeaders headers = new HttpHeaders();
		Token token = getAppUserToken();
		headers.add("Authorization", "Bearer " + token.getToken());
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Customer> result = restTemplate.exchange(uri, HttpMethod.GET, request, Customer.class);
		
		if (result.getBody() != null && result.getBody().getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public static Token getAppUserToken() {
		if(appUserToken == null || appUserToken.getToken() == null || appUserToken.getToken().length() == 0) {
			appUserToken = createToken("ApiClientApp");
		}
		return appUserToken;
	}
	
    private static Token createToken(String username) {
    	String scopes = "com.webage.data.apis";
    	// special case for application user
    	if( username.equalsIgnoreCase("ApiClientApp")) {
    		scopes = "com.webage.auth.apis";
    	}
    	String token_string = JWTHelper.createToken(scopes);
    	
    	return new Token(token_string);
    }
	
}
