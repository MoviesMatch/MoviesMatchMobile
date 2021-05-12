package com.example.moviesmatch.validation;

public class Calculator {

    public String rating(String rate) {
        return String.valueOf(Double.valueOf(rate) / 10)+" /10";
    }

    public String runTime(String time) {
        int hours = 0;
        double minutes = Double.valueOf(time);
        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }
        return String.valueOf(hours) + "h " + String.valueOf(minutes) + "mn";
    }
}
