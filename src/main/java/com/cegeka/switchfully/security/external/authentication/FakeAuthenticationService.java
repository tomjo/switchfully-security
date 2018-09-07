package com.cegeka.switchfully.security.external.authentication;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class FakeAuthenticationService {

    public static final String CIVILIAN = "CIVILIAN";
    public static final String PRIVATE = "PRIVATE";
    public static final String HUMAN_RELATIONSHIPS = "HUMAN_RELATIONSHIPS";
    public static final String GENERAL = "GENERAL";
    private List<ExternalAuthenticaton> externalAuthenticatons = newArrayList(
            ExternalAuthenticaton.externalAuthenticaton().withUsername("ZWANETTA").withPassword("WORST").withRoles(newArrayList(CIVILIAN)),
            ExternalAuthenticaton.externalAuthenticaton().withUsername("JMILLER").withPassword("THANKS").withRoles(newArrayList(PRIVATE)),
            ExternalAuthenticaton.externalAuthenticaton().withUsername("UNCLE").withPassword("SAM").withRoles(newArrayList(HUMAN_RELATIONSHIPS)),
            ExternalAuthenticaton.externalAuthenticaton().withUsername("GENNY").withPassword("RALLY").withRoles(newArrayList(GENERAL))
    );

    public ExternalAuthenticaton getUser(String username, String password) {
        return externalAuthenticatons.stream()
                .filter(externalAuthenticaton -> externalAuthenticaton.getUsername().equals(username))
                .filter(externalAuthenticaton -> externalAuthenticaton.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
