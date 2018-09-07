package com.cegeka.switchfully.security.features;

import org.springframework.security.core.GrantedAuthority;

public enum Feature implements GrantedAuthority {
    DEPLOY_ARMY_INFO,
    JOIN_ARMY,
    PROMOTE_PRIVATE,
    DISCHARGE_SOLDIER,
    LAUNCH_NUKES;

    @Override
    public String getAuthority() {
        return name();
    }
}
