package com.example.onboarding.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "docs")
@Setter
@Getter
@ToString
public class DocumentsRequired {
    private Map<String, List<String>> countryToDocumentsRequired;
}