package com.example.recipe.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.ShowActionController;
import com.example.recipe.controller.ShowExpenseActionController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.partials.CustomActionBar;
import com.example.recipe.views.partials.Items;

public class ShowExpenseActivity extends AppCompatActivity {

    private ShowExpenseActivity instance;

    public ShowExpenseActionController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = ShowExpenseActivity.this;
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_expense);
            init();
        } catch (Exception e) {
            Flash.modal(instance, e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Items.showExpense(instance, item);
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
        controller = new ShowExpenseActionController(instance);

        // activer le bouton "retour vers la page précédente"
        CustomActionBar.backed("Dépense de l'action #" + controller.getAction().getId(), instance);

        controller.handle();
    }
}