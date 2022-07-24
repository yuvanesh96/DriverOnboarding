package com.example.onboarding.datastore;

import com.example.onboarding.model.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.NonNull;

/* Inmemory DB for storing Driver information */
@Repository
public class DriverManager {
    ConcurrentHashMap<String, Driver> drivers = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> phDirectory = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Set<String>> verifiedDocuments = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Boolean> driverReadyStatus = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, String> ownsCab = new ConcurrentHashMap<>();
    ConfigurationManager configurationManager;

    public DriverManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public void addDriver(final Driver driver) {
        drivers.put(driver.getId(), driver);
        phDirectory.put(driver.getPh(), driver.getId());
        verifiedDocuments.put(driver.getId(), new HashSet<>());
        driverReadyStatus.put(driver.getId(), false);
    }

    public void updateDocumentsVerified(@NonNull String driverId, @NonNull String document) {
        verifiedDocuments.get(driverId).add(document);
    }

    public boolean isPhoneNumberAlreadyExists(@NonNull String ph) {
        return phDirectory.containsKey(ph);
    }

    public Driver getDriver(@NonNull String driverId) {
        return drivers.get(driverId);
    }

    public boolean isDocumentVerificationComplete(@NonNull String driverId) {
        final Driver driver = drivers.get(driverId);
        final var toVerify = configurationManager.getRequiredDocuments(driver.getCountry());
        final var verified = verifiedDocuments.get(driverId);

        if (verified == null)
            return false;

        return verified.containsAll(toVerify);
    }

    public void updateReadyStatus(@NonNull String driverId, final boolean ready) {
        driverReadyStatus.put(driverId, ready);
    }

    public void associateCab(@NonNull String driverId, @NonNull String cabId) {
        ownsCab.put(driverId, cabId);
    }

    public String getCabOwned(@NonNull String driverId) {
        return ownsCab.get(driverId);
    }

    public List<Driver> getDrivers(){
        return new ArrayList<>(drivers.values());
    }
}
