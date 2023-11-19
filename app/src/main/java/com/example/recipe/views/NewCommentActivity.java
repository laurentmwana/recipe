package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.NewCommentController;
import com.example.recipe.controller.ShowExpenseActionController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class NewCommentActivity extends AppCompatActivity {

    private NewCommentActivity instance;

    private NewCommentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = NewCommentActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_comment);
            init();
        } catch (NotFoundException e) {
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
        Redirect.route(instance, ExpenseActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Redirect.route(instance, ExpenseActivity.class);
        return true;
    }

    private void init() throws NotFoundException {

        // initialise le controller
        controller = new NewCommentController(instance);

        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed(
                "Ajout d'un commentaire pour l'action #" + controller.getAction().getId(), instance);

        controller.handle();
    }
}