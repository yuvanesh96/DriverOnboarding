package com.example.onboarding;

import com.example.onboarding.configuration.DocumentsRequired;
import com.example.onboarding.controller.CabController;
import com.example.onboarding.controller.DriverSignupController;
import com.example.onboarding.datastore.CabManager;
import com.example.onboarding.datastore.ConfigurationManager;
import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.model.Driver;
import com.example.onboarding.service.DocumentVerificationService;
import com.example.onboarding.service.SignupService;
import com.example.onboarding.validator.ValidationRunner;
import com.example.onboarding.validator.driver.DefaultDriverCountryValidator;
import com.example.onboarding.validator.driver.DefaultDriverPhoneValidator;
import com.example.onboarding.validator.driver.DriverParamsValidator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnboardingApplicationTests {
    // Controllers
    DriverSignupController driverSignupController;
    CabController cabController;
    // Mangers
    ConfigurationManager configurationManager;
    DriverManager driverManager;
    CabManager cabManager;
    // Service
    SignupService signupService;
    DocumentVerificationService documentVerificationService;
    // Validators
    ValidationRunner validationRunner;
    @Autowired
    private DocumentsRequired documentsRequired;

    @BeforeEach
    void setup() {
        configurationManager = new ConfigurationManager(documentsRequired);
        driverManager = new DriverManager(configurationManager);
        cabManager = new CabManager();

        // Validators
        final var validatorList = new ArrayList<DriverParamsValidator>();
        validatorList.add(new DefaultDriverCountryValidator(configurationManager));
        validatorList.add(new DefaultDriverPhoneValidator(driverManager));
        validationRunner = new ValidationRunner(validatorList);
        //Service
        documentVerificationService =
                new DocumentVerificationService(driverManager,configurationManager);
        signupService = new SignupService(driverManager, configurationManager
                , validationRunner,documentVerificationService);
        driverSignupController = new DriverSignupController(signupService);
        cabController = new CabController(driverManager,cabManager);
    }

    @Test
    void run() {

        System.out.println("\n<<<<<<<<<< Starting our Test >>>>>>>>>\n");
        final var id1 = driverSignupController.registerDriver("abc",
                "9444494444", "India", "Blr");
        cabController.registerCab(id1,"cab1","KA12345","SwiftDzire2020");

        final Driver driver1 = driverManager.getDriver(id1);

        final var id2 = driverSignupController.registerDriver("xyz",
                "9876598765", "India", "Blr");
        final Driver driver2 = driverManager.getDriver(id2);
        System.out.println(driver2);



        try {
            Thread.sleep(500);

            driverSignupController.updateVerifiedDocument(driver1.getId(),"Aadhaar");
            driverSignupController.updateVerifiedDocument(driver1.getId(),
                    "INR-PAN");
            driverSignupController.updateVerifiedDocument(driver1.getId(),
                    "INR-License");
            driverSignupController.updateVerifiedDocument(driver1.getId(),
                    "INR-RC");

            driverSignupController.updateReady(driver1.getId(),true);
        }
        catch (Exception e){
            System.err.println("Exception :" + e);
        }
    }

    @AfterEach
    void tearDown(){
        documentVerificationService.stop();
    }
}
