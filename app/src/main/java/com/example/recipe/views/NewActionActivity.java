package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.NewActionController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.views.partials.CustomActionBar;

public class NewActionActivity extends AppCompatActivity {

    private NewActionActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = NewActionActivity.this;
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
        NewActionController controller = new NewActionController(instance);

        Action action = controller.getAction();

        String title = (null != action) ? "Editer l'action #" + action.getId() : "Ajouter une action.";

        CustomActionBar.backed(title, instance);

        controller.handle();
    }

}