package com.example.recipe.controller;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Helper;
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

    private final Action action;
    private ShowActionActivity context;

    private ActionRepository repository;

    private TextView mTextViewStartTime, mTextViewEndTime, mTextViewState;

    private TextView mTextViewAmountDailyRecipe, mTextViewAmountDailyExpense;

    private TextView mTextViewCreatedAt, mTextViewUpdatedAt;

    private Button mButtonComplete;


    public ShowActionController(ShowActionActivity context) throws NotFoundException {

        this.context = context;

        this.repository = new ActionRepository(context);

        this.action = getAction();
    }

    public void handle() throws NotFoundException {

        // initialisation
        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_end_time);
        mTextViewAmountDailyExpense = (TextView) context.findViewById(R.id.text_view_amount_daily_expense);
        mTextViewAmountDailyRecipe = (TextView) context.findViewById(R.id.text_view_amount_daily_recipe);
        mTextViewState = (TextView) context.findViewById(R.id.text_view_state);
        mTextViewCreatedAt = (TextView) context.findViewById(R.id.text_view_created_at);
        mTextViewUpdatedAt = (TextView) context.findViewById(R.id.text_view_updated_at);

        mButtonComplete = (Button) context.findViewById(R.id.button_complete);

        show();

        addListeners();

    }

    private void show() {
        mTextViewState.setText(action.isState() ? "Finalisée" : "Début") ;
        mTextViewStartTime.setText(action.getStartTime());
        mTextViewEndTime.setText(action.getEndTime());
        mTextViewAmountDailyRecipe.setText(Helper.suffix(action.getAmountDailyRecipe(), "Fc"));
        mTextViewAmountDailyExpense.setText(Helper.suffix(action.getAmountDailyExpense(), "Fc"));
        mTextViewCreatedAt.setText(action.getCreatedAt());
        mTextViewUpdatedAt.setText(action.getUpdatedAt());
    }


    public void OnRedirectEdit() {

        if (action.isState()) {
            Redirect.route(context, UpdateActionActivity.class, "id", String.valueOf(action.getId()));
        } else {
            Redirect.route(context, NewActionActivity.class, "id", String.valueOf(action.getId()));
        }
    }

    public void onDelete() {

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
       if (!action.isState()) {
           mButtonComplete.setVisibility(View.VISIBLE);
           mButtonComplete.setOnClickListener(view -> {
               try {
                   onComplete(view);
               } catch (NotFoundException e) {
                   throw new RuntimeException(e);
               }
           });
       }
    }

    private void onComplete(View view) throws NotFoundException {
        Redirect.route(context, CompleteActivity.class, "id", String.valueOf(action.getId()));
    }

}
