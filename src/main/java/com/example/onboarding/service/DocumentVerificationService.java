package com.example.onboarding.service;

import com.example.onboarding.datastore.ConfigurationManager;
import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.model.Driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

@Component
public class DocumentVerificationService implements AsyncService {
    @Getter(lazy = true)
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private DriverManager driverManager;
    private final ConfigurationManager configurationManager;

    public DocumentVerificationService(DriverManager driverManager, ConfigurationManager configurationManager) {
        this.driverManager = driverManager;
        this.configurationManager = configurationManager;
    }

    @Override
    public void stop() {
        getService().shutdownNow();
    }

    public void requestVerification(final Driver driver) {
        getService().execute(() -> {
                final var docsToVerify =
                        configurationManager.getRequiredDocuments(driver.getCountry());
                docsToVerify.forEach(doc -> System.out.println("Triggering request to verify "+doc+
                        " " +
                        "......."));
        });
    }

}
