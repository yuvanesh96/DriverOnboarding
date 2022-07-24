package com.example.onboarding.controller;

import com.example.onboarding.model.Driver;
import com.example.onboarding.model.response.DocumentListResponse;
import com.example.onboarding.model.response.DriversDetailResponse;
import com.example.onboarding.model.response.GenericResponse;
import com.example.onboarding.model.response.RegisterDriverResponse;
import com.example.onboarding.model.response.UpdateVerifiedDocumentResponse;
import com.example.onboarding.service.SignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@RestController
public class DriverSignupController {

    @Autowired
    private SignupService signupService;

    @RequestMapping(value = "/v1/register/driver", method = RequestMethod.POST)
    public ResponseEntity registerDriver(String name, String ph, String country,
                                         String city) {
        Driver driver = null;
        try {
            driver = signupService.createNewDriver(name, ph, country, city);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new GenericResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new RegisterDriverResponse(driver.getId()),HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/update/docs-verified", method =
            RequestMethod.POST)
    public ResponseEntity updateVerifiedDocument(@NonNull String driverId, String doc) {
        try {
            signupService.updateVerifiedDocument(driverId, doc);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new GenericResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
                new UpdateVerifiedDocumentResponse(driverId,doc,
                        UpdateVerifiedDocumentResponse.DefaultMsg),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/update/ready", method = RequestMethod.POST)
    public ResponseEntity updateReady(@NonNull String driverId, final boolean ready) {
        try{
            signupService.updateReady(driverId, ready);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(new GenericResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse(),HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/register/documents", method =
            RequestMethod.GET)
    public ResponseEntity getDocumentListToUpload(@NonNull String driverId) {
        List<String> list;
        try{
            list = signupService.getDocumentListToUpload(driverId);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(new GenericResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DocumentListResponse(list),HttpStatus.OK);
    }

    // Just for testing only.
    @RequestMapping(value = "/v1/list/drivers", method =
            RequestMethod.GET)
    public ResponseEntity getDriverDetails() {
        List<Driver> list;
        try{
            list = signupService.getDrivers();
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(new GenericResponse(e.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new DriversDetailResponse(list),HttpStatus.OK);
    }
}
