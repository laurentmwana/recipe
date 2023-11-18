package com.example.recipe.controller;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Picker;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ShowActionActivity;
import com.example.recipe.views.UpdateActionActivity;

public class UpdateActionController {

    private UpdateActionActivity context;

    private ActionRepository repository;

    private TextView mTextViewStartTime,  mTextViewEndTime;

    private EditText mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense;

    private Button mButtonSave;

    private Action action;


    public UpdateActionController(UpdateActionActivity context) throws NotFoundException {
        this.context = context;
        this.repository = new ActionRepository(context);

        this.action = getAction();
    }

    public void handle() throws NotFoundException {

        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_end_time);
        mEditTextAmountDailyExpense = (EditText) context.findViewById(R.id.edit_text_amount_daily_expense);
        mEditTextAmountDailyRecipe = (EditText) context.findViewById(R.id.edit_text_amount_daily_recipe);
        mButtonSave = (Button) context.findViewById(R.id.button_save);

        addListeners();
    }

    private void addListeners() {
        hydrateData();
        mTextViewStartTime.setOnClickListener((view -> Picker.onTime(mTextViewStartTime)));
        mTextViewEndTime.setOnClickListener((view -> Picker.onTime(mTextViewEndTime)));
        mButtonSave.setOnClickListener(this::onSave);
    }

    private void onSave(View view) {
        Validator validator = (new Validator())
            .required(mEditTextAmountDailyExpense, mEditTextAmountDailyRecipe)
            .positive(mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense)
            .isTime(mTextViewStartTime).isTime(mTextViewEndTime);

        if (validator.isValid()) {
            Action hydrate = hydrate();
            String message = (repository.update(hydrate))
                    ? "votre action a été modifié."
                    : "nous n'avons pas pu modifié l'action";

            Flash.ok(context, message, (dialogInterface, i) -> {
                Redirect.route(context, ShowActionActivity.class, "id", String.valueOf(action.getId()));
            });
        }
    }

    public Action getAction() throws NotFoundException {
        String id = Shared.getId(context);
        if (null == id) {
            throw new NullPointerException("Une erreur est survenue, merci de réessayer.");
        }
        return repository.find(id);
    }

    private Action hydrate() {
        action.setStartTime(mTextViewStartTime.getText().toString());
        action.setEndTime(mTextViewEndTime.getText().toString());
        action.setAmountDailyExpense(Float.parseFloat(mEditTextAmountDailyExpense.getText().toString()));
        action.setAmountDailyRecipe(Float.parseFloat(mEditTextAmountDailyRecipe.getText().toString()));
        return action;
    }

    private void hydrateData() {
        mTextViewStartTime.setText(action.getStartTime());
        mTextViewEndTime.setText(action.getEndTime());
        mEditTextAmountDailyExpense.setText(String.valueOf(action.getAmountDailyExpense()));
        mEditTextAmountDailyRecipe.setText(String.valueOf(action.getAmountDailyRecipe()));
    }
}
