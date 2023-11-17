package com.example.recipe.controller;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private RecyclerView mRecyclerViewContainer;

    private ListingActionAdapter adapter;

    public ListingActionController(ActionActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() {

        mRecyclerViewContainer = (RecyclerView) context.findViewById(R.id.recyclerview_action_container);

        // on commence par recupère toutes les actions effectuée
        ArrayList<Action> actions = repository.findAll();

        // adapters
        adapter = new ListingActionAdapter(this, actions);

        // on affiche les résultats dans le view
        mRecyclerViewContainer.setAdapter(adapter);
    }


    public void onEye(@NonNull View view) {

        Action action = (Action) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("Action #" + action.getId());

        builder.setNeutralButton("Fermer", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        String message = String.format(getEye(), action.getStartTime(),
                action.getEndTime(),
                action.getAmountDailyRecipe(),
                action.getAmountDailyExpense(),
                action.getCreatedAt());

        builder.setMessage(message);

        builder.show();
    }

    public void onDelete(@NonNull View view) {

        Action action = (Action) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("Suppression");

        builder.setMessage("Voulez-vous vraiment supprimer cette action ?");

        builder.setNegativeButton("Non", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setPositiveButton("Oui", (dialogInterface, i) -> {

            boolean state = repository.delete(action);
            if (state) {
                handle();
                Flash.modal(view.getContext(), "Action supprimée avec succès.");
            } else {
                Flash.modal(view.getContext(), "nous n'avons pas pu supprimé l'action #" + action.getId());
            }
        });

        builder.show();
    }

    private String getEye() {
        return "De %s à %s\n\n Recette journalière : %s\n\n " +
                "Dépense journalière : %s\n\n Date de création : %s";
    }

}
