package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.ListingActionController;
import com.example.recipe.controller.NewActionController;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.partials.ActionBar;
import com.example.recipe.views.partials.Items;

public class NewActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_action);
            init();
        }catch (Exception e) {
            Flash.modal(NewActionActivity.this, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    private void init() {
        // initialise le controller
        (new NewActionController(this)).handle();

        ActionBar.backed(this, "Ajouter une action");
    }
}