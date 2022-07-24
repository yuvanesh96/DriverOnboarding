package com.example.onboarding;

import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.service.SignupService;
import com.example.onboarding.validator.ValidationRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OnboardingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(OnboardingApplication.class, args);
    }

}
