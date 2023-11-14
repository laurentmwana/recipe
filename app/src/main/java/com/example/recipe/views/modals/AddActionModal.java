package com.example.recipe.views.modals;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.recipe.R;

public class AddActionModal {

    private Context context;

    public  AddActionModal(Context context) {

        this.context = context;
    }

    public Dialog show() {
        Dialog dialog = new Dialog(context);

        dialog.setTitle("Action");

        dialog.setContentView(R.layout.new_action_modal);

        dialog.setCancelable(true);

        // on affiche le modal
        dialog.show();

        return dialog;
    }
}
