package com.example.recipe.controller;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Session;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.auth.LoginActivity;
import com.example.recipe.views.auth.ProfileActivity;
import com.example.recipe.views.auth.RegisterActivity;

public class ProfileController {

    private ProfileActivity context;


    private EditText mEditTextFullName, mEditTextUsername;

    private EditText mEditTextPassword, mEditTextPin;

    private Button mButtonInfo, mButtonPassword, mButtonDelete;

    private ActionRepository repository;

    public ProfileController(ProfileActivity context) {

        this.context = context;

        Session.check(context);

        this.repository = new ActionRepository(context);
    }

    public void handle() {


        mEditTextFullName = (EditText) context.findViewById(R.id.edit_text_fullname);
        mEditTextUsername = (EditText) context.findViewById(R.id.edit_text_username);
        mEditTextPin = (EditText) context.findViewById(R.id.edit_text_pin);
        mEditTextPassword = (EditText) context.findViewById(R.id.edit_text_password);

        mButtonInfo = (Button) context.findViewById(R.id.button_info);
        mButtonPassword = (Button) context.findViewById(R.id.button_password);
        mButtonDelete = (Button) context.findViewById(R.id.button_delete);

        hydrate();

        addListeners();

    }

    private void hydrate() {
        SharedPreferences session = Session.get(context);
        mEditTextPin.setText(session.getString("pin", null));
        mEditTextFullName.setText(session.getString("full_name", null));
        mEditTextUsername.setText(session.getString("username", null));
    }

    private void addListeners() {

        mButtonDelete.setOnClickListener(this::onDelete);
        mButtonInfo.setOnClickListener(this::onInfo);
        mButtonPassword.setOnClickListener(this::onPassword);
    }

    private void onDelete(View view) {

        DialogInterface.OnClickListener onYes = (dialogInterface, i) -> {
            Session.initialize(context);
            repository.deleteAll();
            Redirect.route(context, LoginActivity.class);
        };

        DialogInterface.OnClickListener onNo = (dialogInterface, i) -> {
            dialogInterface.dismiss();
        };

        Flash.modal(context, "Voulez-vous vraiment effectuer cette action ?", onYes, onNo);
    }

    private void onInfo(View view) {

        Validator validator = new Validator();

        validator.regex(mEditTextPin, "(^[0-9]+$)")
                .regex(mEditTextUsername, "(^[0-9a-zA-Z_]+$)");
        validator.required(mEditTextFullName, mEditTextUsername, mEditTextPin);
        validator.between(mEditTextFullName, 3, 30);
        validator.between(mEditTextUsername, 5, 10);

        if (validator.isValid()) {
            Session.setInfo(context,
                    mEditTextFullName.getText().toString(),
                    mEditTextUsername.getText().toString(),
                    mEditTextPin.getText().toString()
                    );
            Flash.ok(context, "votre compte a été mis à jour.", (dialogInterface, i) -> {
                context.recreate();
            });
        }
    }

    private void onPassword(View view) {
        Validator validator = new Validator();

        validator.required(mEditTextPassword);
        validator.regex(mEditTextPassword, "(^[0-9A-Za-z_]+$)");
        validator.between(mEditTextUsername, 5, 100);

        if (validator.isValid()) {
            Session.setPassword(context, mEditTextPassword.getText().toString());
            Flash.ok(context, "votre mot de passe a été mis à jour.", (dialogInterface, i) -> {
                context.recreate();
            });
        }
    }
}