package com.cegeka.switchfully.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String CIVILIAN_ROLE = "CIVILIAN";
    private static final String PRIVATE_ROLE = "PRIVATE";
    private static final String HUMAN_RELATIONSHIPS_ROLE = "HUMAN_RELATIONSHIPS";
    private static final String GENERAL_ROLE = "GENERAL";

    private final AuthenticationEntryPoint authEntryPoint;

    public SecurityConfig(AuthenticationEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/armies").hasAnyRole(CIVILIAN_ROLE)
                .antMatchers(HttpMethod.POST, "/armies/promote/**", "/armies/discharge/**").hasRole(HUMAN_RELATIONSHIPS_ROLE)
                .antMatchers(HttpMethod.GET, "/armies/nuke").hasRole(GENERAL_ROLE)
                .antMatchers(HttpMethod.GET, "/armies/{\\d+}").hasAnyRole(PRIVATE_ROLE, GENERAL_ROLE)
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
//                Password = WORST
                .withUser("ZWANETTA").password(("{MD5}{MAARTEN}a0b5a6ba469e5fc37559be6a2396830d")).roles(CIVILIAN_ROLE)
                .and()
//                Password = THANKS
                .withUser("JMILLER").password(("{MD5}{NIELS}d037465e3ea09dd346e1a7271a111c77")).roles(PRIVATE_ROLE)
                .and()
//                Password = SAM
                .withUser("UNCLE").password(("{MD5}{ANDRE}212acec2581451c651fb0ee15db1ad59")).roles(HUMAN_RELATIONSHIPS_ROLE)
                .and()
//                PASSWORD = RALLY
                .withUser("GENNY").password(("{MD5}{FRIENDS4LIFE}1bf699ee7fe9e40b317197702b6dc44f")).roles(GENERAL_ROLE);
    }

}
