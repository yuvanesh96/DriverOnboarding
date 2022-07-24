package com.example.onboarding.validator.driver;

import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.exception.PhoneNumberAlreadyExistsException;
import com.example.onboarding.model.Driver;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DefaultDriverPhoneValidator implements DriverParamsValidator {

    DriverManager driverManager;

    @Override
    public boolean isValid(final Driver driver) throws PhoneNumberAlreadyExistsException {
        if(driverManager.isPhoneNumberAlreadyExists(driver.getPh()))
            throw new PhoneNumberAlreadyExistsException();
        return true;
    }
}
