package com.example.recipe.controller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;

import java.util.Objects;

public class NewActionController {

    private final Context context;

    private ActionRepository repository;

    public NewActionController(Context context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void init() {
        // on affiche le modal
        Dialog dialog = new Dialog(context);

        dialog.setTitle("Add Action");
        dialog.setContentView(R.layout.new_action_modal);

        // on recupère les champs
        EditText mEditTextStartTime = (EditText) dialog.findViewById(R.id.edit_text_start_time);
        Button mButtonSave =  (Button) dialog.findViewById(R.id.button_save);

        mButtonSave.setOnClickListener(view -> {
            Validator validator = new Validator();
            // on fait la validation
            validator.required(mEditTextStartTime);
            validator.regex(mEditTextStartTime, "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
            if (validator.hasError()) {
                // on vérifie s'il existe une action en cours d'execution
                try {

                    Action action = repository.findState();
                    if (null == action) {
                        created(dialog.getContext(), repository.startedAdd(mEditTextStartTime.getText().toString()));
                    } else {
                        action.setStartTime(mEditTextStartTime.getText().toString());
                        updated(dialog.getContext(), repository.startedUpdate(action));
                    }
                    dialog.dismiss();

                }catch (Exception e) {
                    Flash.modal(dialog.getContext(), e.getMessage());
                }
            }
        });
        dialog.show();
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
