package com.example.moviesmatch.validation;


public class CountryAbbreviation {
    public String getCountryAbbreviation(String fullCountryName){
        switch (fullCountryName){
            case "Canada":
                return "CAN";
            default:
                return fullCountryName;
        }
    }
}
