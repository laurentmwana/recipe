package com.example.recipe.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.auth.LoginActivity;
import com.example.recipe.views.auth.ProfileActivity;

import java.util.Objects;

public abstract class Session {

    private static String SESSION = "session_auth";

    public static void check(AppCompatActivity context) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_MULTI_PROCESS);
        String state = sh.getString("state", null);

        if (!"connected".equals(state)) {
            Redirect.route(context, LoginActivity.class);
        }
    }

    public static void isconnected(AppCompatActivity context) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_MULTI_PROCESS);
        String state = sh.getString("state", null);
        if ("connected".equals(state)) {
            Redirect.route(context, ActionActivity.class);
        }
    }

    public static void isRegister(AppCompatActivity context) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        String state = sh.getString("state", null);
        if (!(state == null)) {
            Redirect.route(context, LoginActivity.class);
        }
    }


    public static void set(AppCompatActivity context, String fullname, String username, String password, String pin) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        // Ouverture en mode écriture ou éditable
        SharedPreferences.Editor ed = sh.edit();

        // Ecriture des variables dans les SharePreferences
        ed.putString("state", "connected");
        ed.putString("full_name", fullname);
        ed.putString("username", username);
        ed.putString("password", Hasher.generate(password));
        ed.putString("pin", pin);
        ed.putString("created_at", Moment.at());
        ed.commit();
    }

    public static boolean checkIdentified(AppCompatActivity context, String username, String password) {
        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        String u = sh.getString("username", null);
        String p = sh.getString("password", null);

        if (!Objects.equals(u, username)) {
            return false;
        }

        boolean okPassword = Hasher.check(password, p);

        if (!okPassword) {
            return false;
        }

        SharedPreferences.Editor ed = sh.edit();
        // Ecriture des variables dans les SharePreferences
        ed.putString("state", "connected");

        ed.commit();

        return true;
    }

    public static void setPassword(AppCompatActivity context, String password) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        // Ouverture en mode écriture ou éditable
        SharedPreferences.Editor ed = sh.edit();

        // Ecriture des variables dans les SharePreferences
        ed.putString("password", Hasher.generate(password));

        ed.commit();

    }

    public static boolean checkPin(AppCompatActivity context, String pin) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        String p = sh.getString("pin", null);
        return Objects.equals(p, pin);
    }

    public static SharedPreferences get(AppCompatActivity context) {
        return context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
    }

    public static void setInfo(ProfileActivity context, String fullname, String username, String pin) {

        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        // Ouverture en mode écriture ou éditable
        SharedPreferences.Editor ed = sh.edit();

        // Ecriture des variables dans les SharePreferences
        ed.putString("full_name", fullname);
        ed.putString("username", username);
        ed.putString("pin", pin);
        ed.commit();
    }


    public static void logout(ActionActivity start) {
        SharedPreferences sh = start.getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        // Ouverture en mode écriture ou éditable
        SharedPreferences.Editor ed = sh.edit();

        // Ecriture des variables dans les SharePreferences
        ed.putString("state", "disconnect");

        ed.commit();
    }



    public static void initialize(AppCompatActivity context) {
        SharedPreferences sh = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sh.edit();

        // Ecriture des variables dans les SharePreferences
        ed.putString("state", null);
        ed.putString("full_name", null);
        ed.putString("username", null);
        ed.putString("password", null);
        ed.putString("pin", null);
        ed.putString("created_at", null);

        ed.commit();
    }
}
