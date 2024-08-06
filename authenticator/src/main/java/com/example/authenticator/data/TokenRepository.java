package com.example.authenticator.data;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long>{
	public Token findFirstByToken(String token);
}
