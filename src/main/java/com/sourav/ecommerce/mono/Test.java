package com.sourav.ecommerce.mono;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
public class Test {
	
	    public static void main(String[] args) {
	        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encoded = passwordEncoder.encode("sourav123");
	        System.out.println(encoded);
	    }

}
