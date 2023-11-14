package com.example.recipe.views.partials;

import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.controller.NewActionController;
import com.example.recipe.helper.Flash;
import com.example.recipe.views.ActionActivity;

public abstract class Items {

    public static void actions(ActionActivity start, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.item_action_home_add) {
                (new NewActionController(start)).init();
            }
        } catch (Exception e) {
            Flash.modal(start, e.getMessage());
        }
    }
}
