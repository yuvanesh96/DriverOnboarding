package com.example.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Cab {
    private String id;
    private String driverId;
    private String regNumber;
    private String model;
    // TODO: Add more fields like location, trip and availability etc.
}