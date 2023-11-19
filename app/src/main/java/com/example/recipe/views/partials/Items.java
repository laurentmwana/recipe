package com.example.recipe.views.partials;

import android.view.MenuItem;

import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.CommentsActivity;
import com.example.recipe.views.ExpenseActivity;
import com.example.recipe.views.FilterActivity;
import com.example.recipe.views.NewActionActivity;
import com.example.recipe.views.NewCommentActivity;
import com.example.recipe.views.ShowActionActivity;
import com.example.recipe.views.ShowExpenseActivity;

public abstract class Items {

    public static void actions(ActionActivity start, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.item_action_add) {
                Redirect.route(start, NewActionActivity.class);
            } else if (id == R.id.item_action_filter) {
                Redirect.route(start, FilterActivity.class);
            } else if (id == R.id.item_action_expense) {
                Redirect.route(start, ExpenseActivity.class);
            }
        } catch (Exception e) {
            Flash.modal(start, e.getMessage());
        }
    }

    public static void showAction(ShowActionActivity context, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.show_edit) {
               context.controller.OnRedirectEdit();
            } else if (id == R.id.show_delete) {
                context.controller.onDelete();
            } else if (id == R.id.show_back_home) {
                Redirect.route(context, ActionActivity.class);
            }
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }

    public static void filter(FilterActivity context, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.filter_reset) {
                Redirect.route(context, FilterActivity.class);
            }
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }

    public static void showExpense(ShowExpenseActivity context, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            String identified = String.valueOf(context.controller.getAction().getId());
            if (id == R.id.comment_add) {
                Redirect.route(context, NewCommentActivity.class, "id", identified);
            } else if (id == R.id.comment_show) {
                Redirect.route(context, CommentsActivity.class, "id", identified);
            }
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }
}
