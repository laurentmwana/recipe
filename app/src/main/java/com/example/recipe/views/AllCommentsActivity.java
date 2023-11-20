package com.example.recipe.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.recipe.R;
import com.example.recipe.controller.AllCommentController;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.models.entity.Action;
import com.example.recipe.views.partials.CustomActionBar;

public class AllCommentsActivity extends AppCompatActivity {

    private AllCommentsActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = AllCommentsActivity.this;

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_comments);
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

    private void init() throws NotFoundException {
        AllCommentController controller = new AllCommentController(instance);

        Action action = controller.getAction();

        CustomActionBar.backed("Commentaire(s) de l'action #" + action.getId(), instance);

        controller.handle();


    }
}