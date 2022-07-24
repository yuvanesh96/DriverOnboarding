package com.example.onboarding.datastore;

import com.example.onboarding.model.Cab;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CabManager {
    ConcurrentHashMap<String,Cab> cabs = new ConcurrentHashMap<>();

    public void createCab(final Cab cab){
        cabs.put(cab.getId(),cab);
    }

    public Cab getCab(String cabId){
        return cabs.get(cabId);
    }
}
