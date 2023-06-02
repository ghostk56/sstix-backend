package com.sstixbackend.util;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.message.AuthException;

import org.springframework.stereotype.Service;

import com.sstixbackend.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JWTUtil {
	private static final long EXPIRATION_TIME = 1 * 60 * 1000;
	/**
	 * JWT SECRET KEY
	 */
	private static final String SECRET = "sstix sunny";

	/**
	 * 簽發JWT
	 */
	public String generateToken(Users user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("userName", user.getUserName());

		return Jwts.builder().setClaims(claims).setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	/**
	 * 驗證JWT
	 */
	public Integer validateToken(String token) throws AuthException {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			Claims claims = claimsJws.getBody();
			return claims.get("id", Integer.class);
		} catch (SignatureException e) {
			throw new AuthException("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			throw new AuthException("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			throw new AuthException("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new AuthException("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new AuthException("JWT token compact of handler are invalid");
		}
	}
}
