package com.cegeka.switchfully.security.external.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.cegeka.switchfully.security.external.authentication.ExternalAuthenticaton.externalAuthenticaton;
import static com.google.common.collect.Lists.newArrayList;

@Service
public class FakeAuthenticationService {

    private final PasswordEncoder passwordEncoder;

    public FakeAuthenticationService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private List<ExternalAuthenticaton> externalAuthenticatons = newArrayList(
            externalAuthenticaton().withUsername("ZWANETTA")
                    .withPassword("$2a$04$ogITLuIWMy5MeLPC6oLGT.AWOOTks94rZr47uk7obr9ChHR9h6OKa")
                    .withRoles(newArrayList("CIVILIAN")),
            externalAuthenticaton()
                    .withUsername("JMILLER")
                    .withPassword("$2a$04$w3MC6VpdKA8SpKd6tTk/beELHyXlEcY37uEQ5KLI1Ltv5Bv6edCiy")
                    .withRoles(newArrayList("PRIVATE")),
            externalAuthenticaton()
                    .withUsername("UNCLE")
                    .withPassword("$2a$04$aj/MmIEkn0n1xAaUmH0D4e7nDcvt31Me..qe8zQAokYawRd5XXFuq")
                    .withRoles(newArrayList("HUMAN_RELATIONSHIPS")),
            externalAuthenticaton()
                    .withUsername("GENNY")
                    .withPassword("$2a$04$EJD8pQH/02lMISqILWN.ZOL1y3me3FZgiFvkCmbXkd0bL0EFaVYnq")
                    .withRoles(newArrayList("GENERAL"))
    );

    public Optional<ExternalAuthenticaton> getUser(String username, String password) {
        return externalAuthenticatons.stream()
                .filter(externalAuthenticaton -> externalAuthenticaton.getUsername().equals(username))
                .filter(externalAuthenticaton -> passwordEncoder.matches(password, externalAuthenticaton.getPassword()))
                .findFirst();
    }
}
