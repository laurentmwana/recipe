package com.example.recipe.validator;

public class RuleValidator {

    private String data;

    public RuleValidator(String data) {
        this.data = data;
    }

    public boolean regex(String regex) {
        return data.matches(regex);
    }

    public boolean required() {
        return data.isEmpty();
    }

    public boolean min(int min) {
        return data.length() < min;
    }

    public boolean max(int max) {
        return data.length() > max;
    }

    public boolean between(int min, int max) {
        int len = data.length();
        return len < min || len > max;
    }

    public boolean positive() {
        boolean isPositive = false;
        try {
            int positive = Integer.parseInt(data);
            isPositive = positive <= 0;
        }catch (Exception e) {
            isPositive = false;
        }
        return  isPositive;
    }
}
