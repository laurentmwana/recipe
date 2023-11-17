package com.example.recipe.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Shared {

    public static void setId(AppCompatActivity context, int id) {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", id);
        editor.apply();
    }

    public static String getId(AppCompatActivity context) {
        return context.getIntent().getExtras().getString("id");
    }
}
