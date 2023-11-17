package com.example.recipe.controller;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.NewActionActivity;

public class NewActionController {

    private final NewActionActivity context;

    private TextView mTextViewStartTime;

    private Button mButtonSave;

    private ActionRepository repository;

    public NewActionController(NewActionActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() {

        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mButtonSave = (Button) context.findViewById(R.id.button_save);

        // on vérifie
        Action action = repository.findState();
        if (null != action) {
            mTextViewStartTime.setText(action.getStartTime());
        }

        addListeners();
    }

    private void addListeners() {
        mTextViewStartTime.setOnClickListener(this::onCustomPicker);
        mButtonSave.setOnClickListener(this::onSave);
    }

    private void onCustomPicker(View view) {
        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), (timePicker, hours, minute) -> {
            String time = hours + ":" + minute;
            mTextViewStartTime.setText(time);
        }, 12, 30, false);
        dialog.show();
    }

    private void onSave(View view) {

        Validator validator = new Validator();

        validator.isTime(mTextViewStartTime);

        if (validator.hasError()) {
            Action action = repository.findState();

            if (null != action) {
                action.setStartTime(mTextViewStartTime.getText().toString());
                repository.startedUpdate(action);
            } else {
                repository.startedAdd(mTextViewStartTime.getText().toString());
            }

            Flash.modal(context, "Action a été mis à jour.");

            Redirect.route(context, ActionActivity.class);

        }
    }

    private void created(Context context, boolean created) {
        if (created) {
            Flash.modal(context, "l'heure du début a bien été enregistrée");
        } else {
            Flash.modal(context, "nous n'avons pas pu enregistré l'heure de début.");
        }
    }

    private void updated(Context context, boolean updated) {
        if (updated) {
            Flash.modal(context, "l'heure du début a bien été modifié");
        } else {
            Flash.modal(context, "nous n'avons pas pu modifié l'heure de début.");
        }
    }

}
