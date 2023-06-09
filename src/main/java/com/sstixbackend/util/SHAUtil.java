package com.sstixbackend.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class SHAUtil {
	/**
	 * SHA-256
	 */
	public static final String KEY_SHA = "SHA-512";
	
	private static final String SECRET = "sstix sunny";

	public String getResult(String inputStr) {
		BigInteger sha = null;
		String str = inputStr + SECRET;
		byte[] inputData = str.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
			messageDigest.update(inputData);
			sha = new BigInteger(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sha.toString(32);
	}
}
