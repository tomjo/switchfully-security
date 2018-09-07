package com.cegeka.switchfully.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String CIVILIAN_ROLE = "CIVILIAN";
    public static final String PRIVATE_ROLE = "PRIVATE";
    public static final String HUMAN_RELATIONSHIPS_ROLE = "HUMAN_RELATIONSHIPS";
    public static final String GENERAL_ROLE = "GENERAL";

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
                .authenticationEntryPoint(authEntryPoint);
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
//                Password = BOB
                .withUser("NONKEL").password(("{MD5}{6aD4DY6xly+2FGd6iVoIhEbMMkyDDojXIQp/7PUQU9A=}ca44d56ff82918cde866d19a8398ff8b")).roles(PRIVATE_ROLE, HUMAN_RELATIONSHIPS_ROLE)
                .and()
//                PASSWORD = RALLY
                .withUser("GENNY").password(("{MD5}{FRIENDS4LIFE}1bf699ee7fe9e40b317197702b6dc44f")).roles(GENERAL_ROLE);
    }

}
