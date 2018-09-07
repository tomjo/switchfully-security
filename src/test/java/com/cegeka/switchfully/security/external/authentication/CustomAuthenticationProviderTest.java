package com.cegeka.switchfully.security.external.authentication;

import com.cegeka.switchfully.security.CustomAuthenticationProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomAuthenticationProviderTest {

    private CustomAuthenticationProvider authProvider;

    @Before
    public void setUp() throws Exception {
        authProvider = new CustomAuthenticationProvider(new FakeAuthenticationService());
    }

    @Test
    public void authenticate() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("JMILLER", "THANKS");

        Authentication result = authProvider.authenticate(authenticationToken);

        assertThat(result.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsExactly("PRIVATE");
    }

    @Test
    public void authenticate_wrongCredentials_throws() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("JMILLER", "FOO");

        assertThatThrownBy(() -> authProvider.authenticate(authenticationToken))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    public void authenticate_unexistingUser_throws() {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("BOB", "FOO");

        assertThatThrownBy(() -> authProvider.authenticate(authenticationToken))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }
}