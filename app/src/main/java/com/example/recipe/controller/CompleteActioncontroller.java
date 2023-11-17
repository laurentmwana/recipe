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
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.CompleteActivity;

public class CompleteActioncontroller {

    private CompleteActivity context;

    private TextView mTextViewStartTime,  mTextViewEndTime;

    private EditText mEditTextAmountDailyRecipe, mEditTextAmountDailyExpense;

    private Button mButtonSave;

    private ActionRepository repository;

    private Action action;

    public CompleteActioncontroller(CompleteActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() throws NotFoundException {
        int id = Integer.parseInt(Shared.getId(context));
        if (id < 1) {
            throw new NotFoundException("nous n'avons trouvé aucune action qui a l'id #" + id);
        }
        action = repository.find(String.valueOf(id));
        if (null == action) {
            throw new NotFoundException("nous n'avons trouvé aucune action qui a l'id #" + id);
        }

        // initialisation
        init();

        addListeners();
    }

    private void addListeners() {
        mTextViewStartTime.setOnClickListener((view -> Picker.onTime(mTextViewStartTime)));
        mTextViewEndTime.setOnClickListener((view -> Picker.onTime(mTextViewEndTime)));
        mButtonSave.setOnClickListener(this::onSave);
    }

    private void onSave(View view) {
        Validator validator = new Validator();

        validator.isTime(mTextViewEndTime).isTime(mTextViewEndTime);

        validator.required(mEditTextAmountDailyExpense, mEditTextAmountDailyRecipe);

        validator.positive(mEditTextAmountDailyExpense, mEditTextAmountDailyExpense);

        if (validator.hasError())  {
            hydrate();
            String message = (repository.update(action))
                    ? "votre action a été finalisé."
                    : "nous n'avons pas pu finalisé l'action";

            Redirect.route(context, ShowActionController.class, "id", String.valueOf(action.getId()));
        } else {
            Flash.modal(context, String.join("\n\n", validator.errors));
        }
    }

    private void hydrate() {
        action.setStartTime(mTextViewStartTime.getText().toString());
        action.setEndTime(mTextViewEndTime.getText().toString());
        action.setAmountDailyExpense(Float.parseFloat(mEditTextAmountDailyExpense.getText().toString()));
        action.setAmountDailyRecipe(Float.parseFloat(mEditTextAmountDailyRecipe.getText().toString()));
        action.setState(1);
    }

    private void init() {
        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_end_time);

        mEditTextAmountDailyExpense = (EditText) context.findViewById(R.id.edit_text_amount_daily_expense);
        mEditTextAmountDailyRecipe = (EditText) context.findViewById(R.id.edit_text_amount_daily_recipe);

        mButtonSave = (Button) context.findViewById(R.id.button_save);
    }
}
