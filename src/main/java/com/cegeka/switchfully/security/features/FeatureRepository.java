package com.cegeka.switchfully.security.features;

import java.util.Collection;

public interface FeatureRepository {

    Collection<Feature> getFeaturesForRoles(String role);
}
