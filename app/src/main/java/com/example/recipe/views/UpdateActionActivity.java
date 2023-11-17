package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.UpdateActionController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.views.partials.CustomActionBar;

public class UpdateActionActivity extends AppCompatActivity {

    private UpdateActionActivity instance;

    private Action action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = UpdateActionActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_action);
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
        Redirect.route(instance, ShowActionActivity.class, "id", String.valueOf(action.getId()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Redirect.route(instance, ShowActionActivity.class, "id", String.valueOf(action.getId()));
        return true;
    }

    private void init() throws NotFoundException {
        // initialise le controller
        UpdateActionController controller = new UpdateActionController(instance);

        action = controller.getAction();
        // on modifie le titre de l'action bar
        String message = action.isState() ? "Editer"  : "Complèter";
        CustomActionBar.backed(message + " l'action  #" + action.getId(), instance);

        controller.handle();
    }
}