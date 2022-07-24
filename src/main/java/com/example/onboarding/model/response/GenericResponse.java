package com.example.onboarding.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GenericResponse {
    private final static String DEFAULT_MSG = "Request Succeeded";
    private String message;

    public GenericResponse(){
        message = DEFAULT_MSG;
    }
}
