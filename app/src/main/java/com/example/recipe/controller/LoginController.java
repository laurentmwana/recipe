package com.example.recipe.controller;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.auth.LoginActivity;

public class LoginController {

    private LoginActivity context;

    private EditText mEditTextPassword, mEditTextUsername;

    private Button mButtonLogin;

    public LoginController(LoginActivity context) {

        this.context = context;

        Authentificator.isconnected(context);

    }

    public static void logout(ActionActivity c) {
        Authentificator.logout(c);
    }

    public void handle() {

        mEditTextUsername = (EditText) context.findViewById(R.id.edit_text_username);
        mEditTextPassword = (EditText) context.findViewById(R.id.edit_text_password);

        mButtonLogin = (Button) context.findViewById(R.id.button_login);

        addListeners();
    }

    private void addListeners() {
        mButtonLogin.setOnClickListener(this::onLogin);
    }

    private void onLogin(View view) {

        Validator validator = new Validator();

        validator.required(mEditTextPassword, mEditTextUsername);

        if (validator.isValid()) {

            String u = mEditTextUsername.getText().toString();
            String pass = mEditTextPassword.getText().toString();

            boolean okPassport = Authentificator.checkIdentified(context, u, pass);

            if (okPassport) {
                Flash.ok(context, "Vous êtes connecté " + u, (dialogInterface, i) -> {
                    Redirect.route(context, ActionActivity.class);
                });
            } else {
                Flash.modal(context, "Nom d'utilisateur ou mot de passe incorrect.");
            }
        }
    }

}