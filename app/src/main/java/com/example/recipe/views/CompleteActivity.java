package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.CompleteActioncontroller;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.partials.CustomActionBar;

public class CompleteActivity extends AppCompatActivity {

    private AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            instance = CompleteActivity.this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_complete);
        }catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    private void init() throws NotFoundException {

        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed("Finalisation", instance);

        // initialise le controller
        (new CompleteActioncontroller(this)).handle();
    }
}