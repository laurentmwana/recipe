package com.example.recipe.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public abstract class Flash {

    /**
     * Permet d'afficher un toast dans l'écran
     *
     * @param c
     * @param message
     */
    public static void modal(Context c, String message) {
        (new AlertDialog.Builder(c))
                .setTitle("Message")
                .setMessage(message)
                .setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }


}
