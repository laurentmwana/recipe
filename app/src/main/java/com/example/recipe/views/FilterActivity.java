package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.FilterController;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.partials.ActionBar;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_filter);
            init();
        }catch (Exception e) {
            Flash.modal(FilterActivity.this, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    private void init() {
        // activer le bouton "retour vers la page précédente"
        ActionBar.backed(this, "Filtrer par");

        // initialise le controller
        (new FilterController(this)).handle();
    }
}