package com.example.recipe.views.partials;

import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.FilterActivity;
import com.example.recipe.views.NewActionActivity;

public abstract class Items {

    public static void actions(ActionActivity start, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.item_action_add) {
                Redirect.route(start, NewActionActivity.class);
            } else if (id == R.id.item_action_filter) {
                Redirect.route(start, FilterActivity.class);
            }
        } catch (Exception e) {
            Flash.modal(start, e.getMessage());
        }
    }
}
