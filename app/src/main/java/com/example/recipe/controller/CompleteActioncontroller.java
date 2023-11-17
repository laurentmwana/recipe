package com.example.recipe.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.CompleteActivity;

public class CompleteActioncontroller {

    private CompleteActivity context;

    private TextView mTextViewStartTime;

    private TextView mTextViewEndTime;

    private EditText mEditTextAmountDailyRecipe;

    private EditText mEditTextAmountDailyExpense;

    private Button mButtonSave;

    private ActionRepository repository;

    public CompleteActioncontroller(CompleteActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() throws NotFoundException {
        int id = Integer.parseInt(Shared.getId(context));
        if (id < 1) {
            throw new NotFoundException("nous n'avons trouvé aucune action qui a l'id #" + id);
        }
        Action action = repository.find(String.valueOf(id));
        if (null == action) {
            throw new NotFoundException("nous n'avons trouvé aucune action qui a l'id #" + id);
        }

        // initialisation
        init();

        addListeners();
    }

    private void addListeners() {
        mButtonSave.setOnClickListener(this::onSave);
    }

    private void onSave(View view) {

    }

    private void init() {
        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_starttime);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_endtime);

        mEditTextAmountDailyExpense = (EditText) context.findViewById(R.id.edit_text_amount_daily_expense);
        mEditTextAmountDailyRecipe = (EditText) context.findViewById(R.id.edit_text_amount_daily_recipe);

        mButtonSave = (Button) context.findViewById(R.id.button_save);
    }
}
