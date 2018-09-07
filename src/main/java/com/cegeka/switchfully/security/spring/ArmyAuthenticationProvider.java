package com.cegeka.switchfully.security.spring;

import com.cegeka.switchfully.security.external.authentication.ExternalAuthenticaton;
import com.cegeka.switchfully.security.external.authentication.FakeAuthenticationService;
import com.cegeka.switchfully.security.features.Feature;
import com.cegeka.switchfully.security.features.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ArmyAuthenticationProvider implements AuthenticationProvider {

    private final FakeAuthenticationService fakeAuthenticationService;
    private final FeatureRepository featureRepository;

    @Autowired
    public ArmyAuthenticationProvider(FakeAuthenticationService fakeAuthenticationService, FeatureRepository featureRepository) {
        this.fakeAuthenticationService = fakeAuthenticationService;
        this.featureRepository = featureRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ExternalAuthenticaton user = fakeAuthenticationService.getUser(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        if(user != null){
            return new ArmyAuthentication(user.getUsername(), user.getPassword(), getFeaturesFromRoles(user));
        }
        throw new BadCredentialsException("BAD BAD NOT GOOD");
    }

    private List<Feature> getFeaturesFromRoles(ExternalAuthenticaton user) {
        return user.getRoles().stream()
                .map(featureRepository::getFeaturesForRoles)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
