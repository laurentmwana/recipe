package com.example.recipe.controller;

import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ExpenseActivity;

import java.util.ArrayList;

public class ExpenseController {

    private ActionRepository repository;

    private ExpenseActivity context;

    private ArrayList<Action> actions;

    /**
     * @param context
     */
    public ExpenseController(ExpenseActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);

    }

    public void handle() {

    }

    private void data() {

    }
}
