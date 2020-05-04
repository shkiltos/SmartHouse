package com.anton.smarthouse.config;


import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/", "/login", "/api/health").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String email = (String) map.get("email");
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                user = new UserEntity((String) map.get("name"), (String) map.get("email"), (String) map.get("picture"));
            }
            user.setLastVisit(LocalDateTime.now());
            return userRepository.save(user);
        };
    }

}
