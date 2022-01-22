package com.inicu.postgres.service;

import org.springframework.stereotype.Repository;

@Repository
public interface PasswordService {
	
	String encryptPassword(String passwordStr);
	String decryptPassword(String encryptedPassword);

}
