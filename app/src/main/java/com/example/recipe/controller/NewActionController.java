package com.example.recipe.controller;

import android.widget.Button;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Picker;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.NewActionActivity;
import com.example.recipe.views.ShowActionActivity;

public class NewActionController {

    private final NewActionActivity context;

    private TextView mTextViewStartTime;

    private Button mButtonSave;

    private ActionRepository repository;

    public NewActionController(NewActionActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() throws NotFoundException {

        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mButtonSave = (Button) context.findViewById(R.id.button_save);

        addListeners();
    }

    private void addListeners() {
        Action action = getAction();
        if (null != action) {
            mTextViewStartTime.setText(action.getStartTime());
        }
        mTextViewStartTime.setOnClickListener((view -> Picker.onTime(mTextViewStartTime)));
        mButtonSave.setOnClickListener((view -> {
            Validator validator = new Validator();

            validator.isTime(mTextViewStartTime);

            if (validator.isValid()) {
                // on récupère l'action
                String v = mTextViewStartTime.getText().toString();
                if (null == action) {
                    onNew(repository.startedAdd(v));
                } else {
                    action.setStartTime(v);
                    onUpdate(action);
                }
            } else {
                Flash.modal(context, String.join("\n\n", validator.errors));
            }
        }));
    }

    private void onNew(boolean c) {
        String message = c ? "Action ajoutée avec succès." : "Nous n'avons pas pu effetcuer cette action.";
        Flash.ok(context, message, (dialogInterface, i) -> {
            Redirect.route(context, ActionActivity.class);
        });
    }

    private void onUpdate(Action action) {
        String message = repository.startedUpdate(action) ?
                "l'action #" + action.getId()  + " a bien été modifié."
                : "Nous n'avons pas pu effetcuer cette action.";
        Flash.ok(context, message, (dialogInterface, i) -> {
            Redirect.route(context, ShowActionActivity.class, "id", String.valueOf(action.getId()));
        });
    }

    public Action getAction() {
        return  repository.findState();
    }
}
