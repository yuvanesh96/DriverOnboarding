package com.example.onboarding.validator.driver;

import com.example.onboarding.datastore.ConfigurationManager;
import com.example.onboarding.exception.CountryNotSupportedException;
import com.example.onboarding.model.Driver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultDriverCountryValidator implements DriverParamsValidator {
    private ConfigurationManager configurationManager;

    @Override
    public boolean isValid(Driver driver) throws CountryNotSupportedException {
        if(!configurationManager.isSupportedCountry(driver.getCountry()))
            throw  new CountryNotSupportedException();
        return true;
    }
}
