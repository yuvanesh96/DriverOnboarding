package com.example.onboarding.model.response;

import com.example.onboarding.model.Driver;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DriversDetailResponse {
    private List<Driver> drivers;
}
