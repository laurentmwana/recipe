package com.example.recipe.controller;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.ShowActionActivity;
import com.example.recipe.views.UpdateActionActivity;

import org.w3c.dom.UserDataHandler;

import java.util.concurrent.CompletionStage;

import javax.security.auth.callback.CallbackHandler;

public class UpdateActionController {

    private UpdateActionActivity context;

    private ActionRepository repository;

    private TextView mTextViewStartTime;

    private Button mButtonSave;

    public UpdateActionController(UpdateActionActivity context) {
        this.context = context;
        this.repository = new ActionRepository(context);
    }

    public void handle() throws NotFoundException {

        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mButtonSave = (Button) context.findViewById(R.id.button_save);

        addListeners();
    }

    private void addListeners() throws NotFoundException {
        Action action = getAction();
        mTextViewStartTime.setText(action.getStartTime());
        mTextViewStartTime.setOnClickListener(this::onCustomPicker);
        mButtonSave.setOnClickListener(view -> {
            Validator validator = new Validator();

            validator.isTime(mTextViewStartTime);

            if (validator.hasError()) {
                action.setStartTime(mTextViewStartTime.getText().toString());
                boolean updated = repository.startedUpdate(action);
                Flash.modal(context, "Votre action a bien été modifié");
                Redirect.route(context, ShowActionActivity.class, "id", String.valueOf(action.getId()));
            }
        });
    }

    private void onCustomPicker(View view) {
        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), (timePicker, hours, minute) -> {
            String time = hours + ":" + minute;
            mTextViewStartTime.setText(time);
        }, 12, 30, true);
        dialog.show();
    }


    public Action getAction() throws NotFoundException {
        String id = Shared.getId(context);
        if (null == id) {
            throw new NullPointerException("Une erreur est survenue, merci de réessayer.");
        }
        return repository.find(id);
    }
}
