package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.ShowActionController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class ShowActionActivity extends AppCompatActivity {

    private ShowActionActivity instance;

    public ShowActionController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = ShowActionActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_action);
            init();
        } catch (NotFoundException e) {
            Flash.modal(instance, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Items.showAction(instance, item);
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

    private void init() throws NotFoundException {

        // initialise le controller
        controller = new ShowActionController(instance);

        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed("Action #" + controller.getAction().getId(), instance);

        controller.handle();
    }
}