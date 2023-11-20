package com.example.recipe.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Picker;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.CompleteActivity;
import com.example.recipe.views.ShowActionActivity;

public class CompleteActioncontroller {

    private CompleteActivity context;

    private TextView mTextViewStartTime,  mTextViewEndTime;

    private EditText mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense;

    private Button mButtonSave;

    private ActionRepository repository;

    private Action action;

    public CompleteActioncontroller(CompleteActivity context) throws NotFoundException {

        this.context = context;

        Authentificator.check(context);


        this.repository = new ActionRepository(context);

        this.action = getAction();
    }

    private Action getAction() throws NotFoundException {
        return repository.find(Shared.getId(context));
    }

    public void handle() throws NotFoundException {

        // initialisation
        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_end_time);

        mEditTextAmountDailyExpense = (EditText) context.findViewById(R.id.edit_text_amount_daily_expense);
        mEditTextAmountDailyRecipe = (EditText) context.findViewById(R.id.edit_text_amount_daily_recipe);

        mButtonSave = (Button) context.findViewById(R.id.button_save);

        addListeners();
    }

    private void addListeners() {

        mTextViewStartTime.setText(action.getStartTime());
        mTextViewStartTime.setOnClickListener((view -> Picker.onTime(mTextViewStartTime)));
        mTextViewEndTime.setOnClickListener((view -> Picker.onTime(mTextViewEndTime)));
        mButtonSave.setOnClickListener(this::onSave);
    }

    private void onSave(View view) {
        Validator validator = new Validator();

        validator.required(mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense);

        validator.isTime(mTextViewStartTime).isTime(mTextViewEndTime);

        validator.positive(mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense);

        if (validator.isValid())  {
            Action hydrate = hydrate();
            boolean updated = repository.update(hydrate);
            String message = updated ? "votre action a été finalisé." : "nous n'avons pas pu finalisé l'action";
            Flash.ok(context, message, (dialogInterface, i) -> {
                Redirect.route(context, ShowActionActivity.class, "id", String.valueOf(action.getId()));
            });

        } else {
            Flash.modal(context, String.join("\n\n", validator.errors));
        }
    }
    private Action hydrate() {
        action.setStartTime(mTextViewStartTime.getText().toString());
        action.setEndTime(mTextViewEndTime.getText().toString());
        action.setAmountDailyExpense(Float.parseFloat(mEditTextAmountDailyExpense.getText().toString()));
        action.setAmountDailyRecipe(Float.parseFloat(mEditTextAmountDailyRecipe.getText().toString()));
        action.setState(1);
        return action;
    }

}
