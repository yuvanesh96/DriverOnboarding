package com.example.onboarding.validator;

import com.example.onboarding.model.Driver;
import com.example.onboarding.validator.driver.DriverParamsValidator;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Component
public class ValidationRunner {
    List<DriverParamsValidator> driverParamsValidators;

    public boolean validateDriverParams(final Driver driver) {
        driverParamsValidators.forEach(it -> {
            it.isValid(driver);
        });
        return true;
    }

}
