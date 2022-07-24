package com.example.onboarding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.onboarding.configuration.DocumentsRequired;
import com.example.onboarding.controller.CabController;
import com.example.onboarding.controller.DriverSignupController;
import com.example.onboarding.datastore.CabManager;
import com.example.onboarding.datastore.ConfigurationManager;
import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.exception.InvalidDriverIdException;
import com.example.onboarding.exception.PhoneNumberAlreadyExistsException;
import com.example.onboarding.exception.VerificationPendingException;
import com.example.onboarding.model.Driver;
import com.example.onboarding.model.response.GenericResponse;
import com.example.onboarding.model.response.RegisterDriverResponse;
import com.example.onboarding.service.DocumentVerificationService;
import com.example.onboarding.service.SignupService;
import com.example.onboarding.validator.ValidationRunner;
import com.example.onboarding.validator.driver.DefaultDriverCountryValidator;
import com.example.onboarding.validator.driver.DefaultDriverPhoneValidator;
import com.example.onboarding.validator.driver.DriverParamsValidator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        documentVerificationService = new DocumentVerificationService(driverManager, configurationManager);
        signupService = new SignupService(driverManager, configurationManager, validationRunner, documentVerificationService);
        driverSignupController = new DriverSignupController(signupService);
        cabController = new CabController(driverManager, cabManager);
    }

    @AfterEach
    void tearDown() {
        documentVerificationService.stop();
    }

    @Test
    void test_all_success_scenario() throws InterruptedException {
        final var response = driverSignupController.registerDriver("abc",
                "9444494444", "India", "Blr");
        String id1 = ((RegisterDriverResponse)response.getBody()).getDriverId();
        cabController.registerCab(id1, "cab1", "KA-AA-2345", "Swift-Dzire" +
                "-2020");
        final Driver driver = driverManager.getDriver(id1);
        System.out.println("Driver Details : " + driver);

        Thread.sleep(5000);

        assertEquals(HttpStatus.OK,driverSignupController
                .updateVerifiedDocument(driver.getId(),"Aadhaar").getStatusCode());
        assertEquals(HttpStatus.OK,driverSignupController
                .updateVerifiedDocument(driver.getId(), "INR-License").getStatusCode());
        assertEquals(HttpStatus.OK,driverSignupController
                .updateVerifiedDocument(driver.getId(), "INR-RC").getStatusCode());

        assertEquals(HttpStatus.OK,driverSignupController
                .updateReady(driver.getId(), true).getStatusCode());
    }

    @Test
    void test_duplicate_phone_number(){
        ResponseEntity response;
        // driver 1
        response = driverSignupController.registerDriver("abc",
                "9444494444",
                "India", "Blr");
        assert(response.getStatusCode() == HttpStatus.OK);
        // driver 2
        response = driverSignupController.registerDriver("abc",
                "9444494444",
                "India", "Blr");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        String actual = ((GenericResponse)response.getBody()).getMessage();
        assertEquals(actual,
                PhoneNumberAlreadyExistsException.class.getName());
    }

    @Test
    void test_invalid_updates(){
        ResponseEntity response;
        // driver 1
        response = driverSignupController.registerDriver("abc",
                "9444494444",
                "India", "Blr");
        assert(response.getStatusCode() == HttpStatus.OK);
        String id1 = ((RegisterDriverResponse)response.getBody()).getDriverId();

        String actual = "";

        // Updating status before verification.
        response = driverSignupController.updateReady(id1,true);
        actual = ((GenericResponse)response.getBody()).getMessage();
        assertEquals(actual,VerificationPendingException.class.getName());


    }


}
