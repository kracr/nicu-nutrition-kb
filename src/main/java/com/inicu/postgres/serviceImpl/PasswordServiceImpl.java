package com.inicu.postgres.serviceImpl;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Repository;

import com.inicu.postgres.service.PasswordService;
import com.inicu.postgres.utility.BasicConstants;

@Repository
public class PasswordServiceImpl implements PasswordService{

	@Override
	public String encryptPassword(String passwordStr) {
		try{
			String text = passwordStr;
			String key = BasicConstants.AES_KEY; // 128 bit key

			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
//			System.out.println("ENCRYPTED :" +new String(encrypted));
			String pass = Base64.getEncoder().encodeToString(encrypted);
			return pass;

		}catch(Exception e){
//			System.out.println("Could not encrypt Password");
			return null;
		}
	}

	@Override
	public String decryptPassword(String encryptedPassword) {

		try{
			String key = BasicConstants.AES_KEY;

			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] passbyte = Base64.getDecoder().decode(encryptedPassword);
			String decrypted = new String(cipher.doFinal(passbyte));
//			System.out.println("DECRYPTED :"+decrypted);
			return decrypted;

		}catch(Exception e){
//			System.out.println("Could not decrypt password");
			return null;
		}
	}

}
