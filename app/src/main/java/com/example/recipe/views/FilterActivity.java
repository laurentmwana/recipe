package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.FilterController;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class FilterActivity extends AppCompatActivity {

    private FilterActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = FilterActivity.this;
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
        getMenuInflater().inflate(R.menu.menu_action_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Items.filter(instance, item);
        return true;
    }

    private void init() {
        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed("Filtrer par", instance);

        // initialise le controller
        (new FilterController(this)).handle();
    }
}