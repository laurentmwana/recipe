package com.example.recipe.models.entity;

public class Action {

    private int id;

    private String startTime;

    private String endTime;

    private Float amountDailyRecipe;

    private Float amountDailyExpense;

    private String created_at;

    private String updated_at;

    public int getId() {
        return id;
    }

    public Action setId(int id) {
        this.id = id;

        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Action setStartTime(String startTime) {
        this.startTime = startTime;

        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Action setEndTime(String endTime) {
        this.endTime = endTime;

        return this;
    }

    public Float getAmountDailyRecipe() {
        return amountDailyRecipe;
    }

    public Action setAmountDailyRecipe(Float amountDailyRecipe) {
        this.amountDailyRecipe = amountDailyRecipe;

        return this;
    }

    public Float getAmountDailyExpense() {
        return amountDailyExpense;
    }

    public Action setAmountDailyExpense(Float amountDailyExpense) {
        this.amountDailyExpense = amountDailyExpense;

        return this;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public Action setCreatedAt(String created_at) {
        this.created_at = created_at;

        return this;
    }
    public String getUpdatedAt() {
        return updated_at;
    }

    public Action setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;

        return this;
    }
}
