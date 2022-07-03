package com.example.onboarding.datastore;

import com.example.onboarding.configuration.DocumentsRequired;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigurationManager {
    private final DocumentsRequired documentsRequired;
    private Set<String> countries = new HashSet<>();
    public ConfigurationManager(DocumentsRequired documentsRequired){
        this.documentsRequired = documentsRequired;
        init();
    }

    private void init(){
        countries =  documentsRequired.getCountryToDocumentsRequired().keySet();
    }

    public boolean isSupportedCountry(String country){
        return countries.contains(country);
    }

    public List<String> getRequiredDocuments(String country){
        return documentsRequired.getCountryToDocumentsRequired().get(country);
    }
}
