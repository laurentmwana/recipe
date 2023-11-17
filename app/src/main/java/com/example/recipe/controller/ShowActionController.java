package com.example.recipe.controller;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.CompleteActivity;
import com.example.recipe.views.NewActionActivity;
import com.example.recipe.views.ShowActionActivity;
import com.example.recipe.views.UpdateActionActivity;

public class ShowActionController {

    private ShowActionActivity context;

    private ActionRepository repository;

    private Button mButtonComplete;


    public ShowActionController(ShowActionActivity context) throws NotFoundException {

        this.context = context;

        this.repository = new ActionRepository(context);

    }

    public void handle() throws NotFoundException {

        // initialisation
        mButtonComplete = (Button) context.findViewById(R.id.button_complete);


        addListeners();

    }


    public void OnRedirectEdit() throws NotFoundException {

        Action action = getAction();

        if (action.isState()) {
            Redirect.route(context, CompleteActivity.class, "id", String.valueOf(action.getId()));
        } else {
            Redirect.route(context, NewActionActivity.class, "id", String.valueOf(action.getId()));
        }
    }

    public void onDelete() throws NotFoundException {

        Action action = (Action) getAction();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Suppression");

        builder.setMessage("Voulez-vous vraiment supprimer cette action ?");

        builder.setNegativeButton("Non", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setPositiveButton("Oui", (dialogInterface, i) -> {
            boolean deleted = repository.delete(action);

            String message = deleted
                    ? "Action supprimée avec succès."
                    : "Nous n'avons pas pu effectuer cette action";

            Flash.modal(context, message);

            Redirect.route(context, ActionActivity.class);
        });

        builder.show();
    }


    public Action getAction() throws NotFoundException {
        return  repository.find(Shared.getId(context));
    }

    private void addListeners() {
        mButtonComplete.setOnClickListener(view -> {
            try {
                onComplete(view);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onComplete(View view) throws NotFoundException {
        Action action = getAction();
        Redirect.route(context, CompleteActivity.class, "id", String.valueOf(action.getId()));
    }

}
