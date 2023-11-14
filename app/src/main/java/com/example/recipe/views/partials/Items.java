package com.example.recipe.views.partials;

import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.views.HomeActionActivity;
import com.example.recipe.views.modals.AddActionModal;

public abstract class Items {

    public static void actions(HomeActionActivity start, MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.item_action_home_add) {
            // on active le modal
            (new AddActionModal(start)).show();
        }
    }
}
