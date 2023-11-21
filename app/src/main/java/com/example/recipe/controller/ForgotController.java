package com.example.recipe.controller;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.auth.ForgotPasswordActivity;
import com.example.recipe.views.auth.LoginActivity;

public class ForgotController {

    private ForgotPasswordActivity context;

    private EditText mEditTextPin, mEditTextPassword;

    private Button mButtonForgot;

    public ForgotController(ForgotPasswordActivity context) {

        this.context = context;

        Authentificator.isconnected(context);
    }

    public void handle() {

        mEditTextPassword = (EditText) context.findViewById(R.id.edit_text_password);
        mEditTextPin = (EditText) context.findViewById(R.id.edit_text_pin);

        mButtonForgot = (Button) context.findViewById(R.id.button_forgot);

        addListeners();

    }

    private void addListeners() {
        mButtonForgot.setOnClickListener(this::onForgot);
    }

    private void onForgot(View view) {
        Validator validator = new Validator();

        validator.required(mEditTextPassword, mEditTextPin);
        validator.regex(mEditTextPin, "(^[0-9]+$)");
        validator.between(mEditTextPin, 4, 4);

        if (validator.isValid()) {

            String pin = mEditTextPin.getText().toString();
            String password = mEditTextPassword.getText().toString();

            if (Authentificator.checkPin(context, pin)){
                Authentificator.setPassword(context, password);
                Flash.ok(context, "votre mot de passe bien été modifié", (dialogInterface, i) ->  {
                    Redirect.route(context, LoginActivity.class);
                });
            } else {
                Flash.modal(context, "votre code pin est incorrect.");
            }
        }
    }
}