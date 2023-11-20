package com.example.recipe.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.ProfileController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.partials.CustomActionBar;

public class ProfileActivity extends AppCompatActivity {

    private ProfileActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        instance = ProfileActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
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

        CustomActionBar.backed("Mon profil", instance);

        (new ProfileController(instance)).handle();
    }



}