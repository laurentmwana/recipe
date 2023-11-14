package com.example.recipe.controller;

import android.app.AlertDialog;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.adapters.ListingActionAdapter;
import com.example.recipe.helper.Flash;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ActionActivity;

import java.util.ArrayList;

public class ListingActionController {

    private ActionActivity context;

    private ActionRepository repository;

    public ListingActionController(ActionActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() {

        RecyclerView mRecyclerViewContainer = (RecyclerView) context.findViewById(R.id.recyclerview_action_container);

        // on commence par recupère toutes les actions effectuée
        ArrayList<Action> actions = repository.findAll();

        // on affiche les résultats dans le view
        mRecyclerViewContainer.setAdapter( new ListingActionAdapter(this, actions));
    }

    public void onEye(View view) {
        Action action = (Action) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("Action #" + action.getId());

        builder.setNeutralButton("Fermer", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        String message = getEye();

        builder.setMessage(message);

        builder.show();
    }

    public void onDelete(View view) {
        Action action = (Action) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("Supprimer l'action #" + action.getId());

        builder.setNegativeButton("Non", (dialogInterface, i) -> {

        });

        builder.setPositiveButton("Oui", (dialogInterface, i) -> {

        });

        builder.show();
    }

    private String getEye() {
        return "De %s à %s\n\n Recette journalière %s\n\n " +
                "Dépense journalière %s\n\n Date de création %s";
    }

}
