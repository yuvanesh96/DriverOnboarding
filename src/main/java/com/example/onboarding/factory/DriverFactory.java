package com.example.onboarding.factory;

import com.example.onboarding.model.Driver;
import lombok.NonNull;

import java.util.UUID;
public class DriverFactory {
    private static DriverFactory instance = null;
    private DriverFactory() {

    }

    public static DriverFactory getInstance() {
        if( instance == null)
            instance = new DriverFactory();
        return instance;
    }

    public Driver createDriver(@NonNull String name,@NonNull String ph,@NonNull String country, String city) {
        final UUID uuid = UUID.randomUUID();
        final String id = uuid.toString();
        return new Driver(id,name,ph,country,city);
    }
}
