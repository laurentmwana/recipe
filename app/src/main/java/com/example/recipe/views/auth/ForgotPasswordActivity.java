package com.example.recipe.views.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.AuthRegisterController;
import com.example.recipe.controller.ForgotController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.ExpenseActivity;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ForgotPasswordActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = ForgotPasswordActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);
            init();
        } catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Items.auth(instance, item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Redirect.route(instance, LoginActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Redirect.route(instance, LoginActivity.class);
        return true;
    }

    private void init() {
        CustomActionBar.backed("Retrouvez votre compte", instance);
        (new ForgotController(instance)).handle();
    }
}