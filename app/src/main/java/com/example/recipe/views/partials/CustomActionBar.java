package com.example.recipe.views.partials;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public abstract class CustomActionBar {

    /**
     * Permet de modifier le titre de l'action bar
     * @param title
     * @param context
     */
    public static void set(String title, AppCompatActivity context) {
        // on modifie le titre de l'activité
        context.getSupportActionBar().setTitle(title);
    }

    /**
     * Permet de modifier le titre de l'action bar
     *
     * @param context
     * @param title
     */
    public static void backed(String title, AppCompatActivity context) {
        ActionBar actionBar = context.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(title);
    }
}
