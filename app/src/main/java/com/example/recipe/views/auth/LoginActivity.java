package com.example.recipe.views.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.LoginController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class LoginActivity extends AppCompatActivity {

    private LoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = LoginActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            init();
        } catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Items.auths(instance, item);

        return true;
    }

    @Override
    public void onBackPressed() {}

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }

    private void init() {
        CustomActionBar.backed("Authentification", instance);

        (new LoginController(instance)).handle();
    }
}