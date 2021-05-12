package com.example.moviesmatch.validation;


public class CountryAbbreviation {
    public String getCountryAbbreviation(String fullCountryName){
        switch (fullCountryName){
            case "Canada":
                return "CA";
            default:
                return fullCountryName;
        }
    }

    public String getCountryAbbreviationReverse (String shortCountryName){
        switch (shortCountryName){
            case "CA":
                return "Canada";
            default:
                return shortCountryName;
        }
    }
}
