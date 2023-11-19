package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.adapters.CustomActionsAdapter;
import com.example.recipe.controller.ExpenseController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;

public class ExpenseActivity extends AppCompatActivity {

    private ExpenseActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = ExpenseActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_expense);
            init();
        } catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Redirect.route(instance, ActionActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Redirect.route(instance, ActionActivity.class);
        return true;
    }

    private void init() {
        CustomActionBar.backed("Dépenses", instance);
        (new ExpenseController(instance)).handle();
    }
}