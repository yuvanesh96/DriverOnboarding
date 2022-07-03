package com.example.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class DocumentMetadata {
    private String driverId;
    private String id;
    private String type;
    private String url;
}

