package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.ListingActionController;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.partials.ActionBar;
import com.example.recipe.views.partials.Items;

public class ActionActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        ActionBar.set("Actions", ActionActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            init();
        }catch (Exception e) {
            Flash.modal(ActionActivity.this, e.getMessage());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Items.actions(ActionActivity.this, item);

        return true;
    }

    private void init() {
        // initialise le controller
        (new ListingActionController(this)).handle();
    }
}