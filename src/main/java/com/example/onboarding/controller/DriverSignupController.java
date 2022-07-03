package com.example.onboarding.controller;

import com.example.onboarding.service.SignupService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DriverSignupController {

    private SignupService signupService;

    @RequestMapping(value = "/register/driver", method = RequestMethod.POST)
    public String registerDriver(String name, String ph, String country, String city) {
        final var driver = signupService.createNewDriver(name, ph, country, city);
        return driver.getId();
    }

    public void updateVerifiedDocument(@NonNull String driverId, String doc) {
        signupService.updateVerifiedDocument(driverId, doc);
    }

    public void updateReady(@NonNull String driverId, final boolean ready) {
        signupService.updateReady(driverId, ready);
    }

    public List<String> getDocumentListToUpload(@NonNull String driverId) {
        return signupService.getDocumentListToUpload(driverId);
    }

}
