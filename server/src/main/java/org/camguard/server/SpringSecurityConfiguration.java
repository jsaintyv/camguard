package org.camguard.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {    	
        auth.authenticationProvider(new CustomAuthenticationProvider());
                                    
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http        		
            .requestCache()                	
            .and()
            .authorizeRequests()            
            .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(ROLE_ADMIN)
            .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(ROLE_ADMIN)                
            .and()
            .httpBasic()
            .authenticationEntryPoint(authEntryPoint);                      
                
    } 
    
    
    public static class CustomAuthenticationProvider implements AuthenticationProvider {
    	long lastAuth = System.currentTimeMillis();
    	Random random = new Random();
    	
        @Override
        public Authentication authenticate(Authentication authentication)  {
            String name = authentication.getName();
            Object credentials = authentication.getCredentials();
            
            if (!(credentials instanceof String)) {
            	throw new BadCredentialsException("Not supported credential");
            }
            String password = credentials.toString();
            ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
            
            randomResponseDelay();
            
            if (!name.equals(configuration.login)
            		|| !password.equals(configuration.password)) {
                throw new BadCredentialsException("Authentication failed for " + name);
            }

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
            Authentication auth = new
                    UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
            return auth;
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        }

        private void randomResponseDelay() {
        	
    		synchronized (random) {
    			if((System.currentTimeMillis() - lastAuth) > 60000) {
    				lastAuth = System.currentTimeMillis();
    				return;
    			}
    			
                try {
    				Thread.sleep(5 + random.nextInt(3) * 1000);
    			} catch (InterruptedException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}            
                lastAuth = System.currentTimeMillis();
    		}
    	}
    }


    
}
