package com.example.recipe.views.partials;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public abstract class ActionBar {

    /**
     * Permet de modifier le titre de l'action bar
     * @param title
     * @param context
     */
    public static void set(String title, AppCompatActivity context) {
        // on modifie le titre de l'activité
        Objects.requireNonNull(context.getSupportActionBar()).setTitle(title);
    }
}
