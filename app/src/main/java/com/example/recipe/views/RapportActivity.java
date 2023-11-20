package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.RapportController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;

public class RapportActivity extends AppCompatActivity {

    private RapportActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        instance = RapportActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rapport);
            init();
        }catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }
    }

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
        CustomActionBar.backed("Rapport statistique", instance);
        (new RapportController(instance)).handle();
    }
}