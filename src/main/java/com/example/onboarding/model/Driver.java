package com.example.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Driver {
    private String id;
    private String name;
    private String ph;
    private String country;
    private String city;
}
