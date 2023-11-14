package com.example.recipe.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Redirect {


    /**
     * Permet de faire une redirection
     *
     * @param start
     * @param end
     */
    public static void route(Context start, Class end) {
        Intent intent = new Intent(start, end);
        start.startActivity(intent);
    }

    /**
     * Permet de faire une redirection
     *
     * @param start
     * @param end
     */
    public static void route(Context start, Class end, String key, String value) {
        Intent intent = new Intent(start, end);
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        intent.putExtras(bundle);
        start.startActivity(intent);
    }
}
