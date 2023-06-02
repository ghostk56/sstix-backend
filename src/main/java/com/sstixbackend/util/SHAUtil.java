package com.sstixbackend.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class SHAUtil {
	/**
	 * SHA-256
	 */
	public static final String KEY_SHA = "SHA-256";

	public String getResult(String inputStr) {
		BigInteger sha = null;
		byte[] inputData = inputStr.getBytes();
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
