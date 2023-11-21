package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.CompleteActioncontroller;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Shared;
import com.example.recipe.views.partials.CustomActionBar;

public class CompleteActivity extends AppCompatActivity {

    private AppCompatActivity instance;

    protected void onCreate(Bundle savedInstanceState) {
        instance = CompleteActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complete);
            init();
        }catch (Exception e) {
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
        Redirect.route(instance, ShowActionActivity.class, "id", Shared.getId(instance));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Redirect.route(instance, ShowActionActivity.class, "id", Shared.getId(instance));
        return true;
    }

    private void init() throws NotFoundException {

        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed("Finalisation", instance);

        // initialise le controller
        (new CompleteActioncontroller(this)).handle();
    }
}