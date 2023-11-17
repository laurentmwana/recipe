package com.example.recipe.views.partials;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe.R;
import com.example.recipe.views.FilterActivity;

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

    /**
     * Permet de modifier le titre de l'action bar
     *
     * @param context
     * @param title
     */
    public static void backed(AppCompatActivity context, String title) {
        androidx.appcompat.app.ActionBar actionBar = context.getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeActionContentDescription("Retour");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }
}
