package com.example.onboarding.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UpdateVerifiedDocumentResponse {
    public static final String DefaultMsg = "Verification Result Updated";

    private String driverId;
    private String document;
    private String message;
}
