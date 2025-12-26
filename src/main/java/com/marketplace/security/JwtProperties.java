package com.marketplace.security;
import org.springframework.stereotype.Component;
@Component
public class JwtProperties {


	  public final String SECRET = "marketplace-secret-key-change-later";
	  public final long EXPIRATION_TIME= 1000 * 60 * 60;

}
