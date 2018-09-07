package com.cegeka.switchfully.security.external.criminalrecord;

import org.springframework.stereotype.Component;

@Component
public class CriminalRecordService {

    public CriminalRecord getCriminalRecord(String username){
        if(username.equals("MOB") || username.equals("CRIMI")){
            return CriminalRecord.recordForCriminal();
        }
        return CriminalRecord.cleanRecord();
    }

    public boolean hasNoCriminalRecord(String username){
       return getCriminalRecord(username).getOffenses().isEmpty();
    }
}
