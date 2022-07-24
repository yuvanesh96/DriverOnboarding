package com.example.onboarding.controller;

import com.example.onboarding.datastore.CabManager;
import com.example.onboarding.datastore.DriverManager;
import com.example.onboarding.exception.InvalidDriverIdException;
import com.example.onboarding.model.Cab;
import com.example.onboarding.model.response.GenericResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@RestController
public class CabController {
    DriverManager driverManager;
    CabManager cabManager;
    @RequestMapping(value ="/v1/register/cab",method = RequestMethod.POST)
    public ResponseEntity registerCab(@NonNull String driverId, @NonNull String cabId,
                                      @NonNull String regNumber, @NonNull String model) {
        if (driverManager.getDriver(driverId) == null) {
            return new ResponseEntity<>(new GenericResponse(
                    InvalidDriverIdException.class.toGenericString()),
                    HttpStatus.OK);
        }

        // TODO: checks to figure out if the cab is already registered with us.

        cabManager.createCab(new Cab(cabId, driverId, regNumber, model));
        driverManager.associateCab(driverId, cabId);
        return new ResponseEntity<>(new GenericResponse(),HttpStatus.OK);
    }

    // TODO: To handle requests related to cab like trip,location  etc.

}
