package com.example.onboarding.service;

import com.example.onboarding.datastore.ConfigurationManager;
import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.exception.CabNotRegisteredException;
import com.example.onboarding.exception.InvalidDriverIdException;
import com.example.onboarding.exception.UnsupportedDocumentException;
import com.example.onboarding.exception.VerificationPendingException;
import com.example.onboarding.factory.DriverFactory;
import com.example.onboarding.model.Driver;
import com.example.onboarding.validator.ValidationRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Service
public class SignupService {
    @Autowired
    DriverManager driverManager;

    @Autowired
    ConfigurationManager configurationManager;

    @Autowired
    ValidationRunner validationRunner;

    @Autowired
    DocumentVerificationService documentVerificationService;


    public Driver createNewDriver(@NonNull String name, @NonNull String ph, @NonNull String country, String city) throws RuntimeException {
        final Driver driver = DriverFactory.getInstance().createDriver(name, ph, country, city);
        /* To avoid multiple if-else */
        validationRunner.validateDriverParams(driver);
        driverManager.addDriver(driver);
        documentVerificationService.requestVerification(driver);
        return driver;
    }

    public void updateVerifiedDocument(@NonNull String driverId, String doc) {
        final Driver driver = driverManager.getDriver(driverId);
        if (driver == null)
            throw new InvalidDriverIdException();

        final var docsToVerify = configurationManager.getRequiredDocuments(driver.getCountry());

        if (!docsToVerify.contains(doc))
            throw new UnsupportedDocumentException();
        System.out.println("Triggering Notification to " + driverId + " to " +
                "notify" +
                " " + doc + " has been verified");
        driverManager.updateDocumentsVerified(driverId, doc);
    }

    public void updateReady(@NonNull String driverId, final boolean ready) {
        final Driver driver = driverManager.getDriver(driverId);
        if (driver == null)
            throw new InvalidDriverIdException();

        if (!driverManager.isDocumentVerificationComplete(driverId))
            throw new VerificationPendingException();

        if (driverManager.getCabOwned(driverId) == null)
            throw new CabNotRegisteredException();

        driverManager.updateReadyStatus(driverId, ready);
    }

    public List<String> getDocumentListToUpload(@NonNull String driverId) {
        final Driver driver = driverManager.getDriver(driverId);
        if (driver == null)
            throw new InvalidDriverIdException();

        return configurationManager.getRequiredDocuments(driver.getCountry());
    }
}
