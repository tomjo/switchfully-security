package com.cegeka.switchfully.security.features;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.cegeka.switchfully.security.external.authentication.FakeAuthenticationService.*;
import static com.cegeka.switchfully.security.features.Feature.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Repository
public class InMemoryFeatureRepository implements FeatureRepository {

    private final Map<String, Collection<Feature>> featuresPerRole;

    public InMemoryFeatureRepository(){
        this.featuresPerRole = new HashMap<>();
        this.featuresPerRole.put(CIVILIAN, asList(JOIN_ARMY));
        this.featuresPerRole.put(PRIVATE, asList(DEPLOY_ARMY_INFO));
        this.featuresPerRole.put(HUMAN_RELATIONSHIPS, asList(PROMOTE_PRIVATE, DISCHARGE_SOLDIER));
        this.featuresPerRole.put(GENERAL, asList(LAUNCH_NUKES, DEPLOY_ARMY_INFO));

    }
    @Override
    public Collection<Feature> getFeaturesForRoles(String role) {
        return featuresPerRole.getOrDefault(role, emptyList());
    }
}
