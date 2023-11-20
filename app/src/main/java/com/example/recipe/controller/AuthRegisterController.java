package com.example.recipe.controller;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Session;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.auth.RegisterActivity;

public class AuthRegisterController {

    private RegisterActivity context;


    private EditText mEditTextFullName, mEditTextUsername;

    private EditText mEditTextPassword, mEditTextPin;

    private Button mButtonRegister;

    public AuthRegisterController(RegisterActivity context) {

        this.context = context;

        Session.isRegister(context);
    }

    public void handle() {

        mEditTextFullName = (EditText) context.findViewById(R.id.edit_text_fullname);
        mEditTextUsername = (EditText) context.findViewById(R.id.edit_text_username);
        mEditTextPin = (EditText) context.findViewById(R.id.edit_text_pin);
        mEditTextPassword = (EditText) context.findViewById(R.id.edit_text_password);

        mButtonRegister = (Button) context.findViewById(R.id.button_register);

        addListeners();

    }

    private void addListeners() {
        mButtonRegister.setOnClickListener(this::onRegister);
    }

    private void onRegister(View view) {

        Validator validator = new Validator();

        if (validator.isValid()) {

            Session.set(context,
                    mEditTextFullName.getText().toString(),
                    mEditTextUsername.getText().toString(),
                    mEditTextPassword.getText().toString(),
                    mEditTextPin.getText().toString()
                    );

            Flash.ok(context, "votre compte a bien été créér.", (dialogInterface, i) -> {
                Redirect.route(context, ActionActivity.class);
            });
        }
    }
}