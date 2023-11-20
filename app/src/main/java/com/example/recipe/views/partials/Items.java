package com.example.recipe.views.partials;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe.R;
import com.example.recipe.controller.LoginController;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Session;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.AllCommentsActivity;
import com.example.recipe.views.ExpenseActivity;
import com.example.recipe.views.FilterActivity;
import com.example.recipe.views.NewActionActivity;
import com.example.recipe.views.NewCommentActivity;
import com.example.recipe.views.RapportActivity;
import com.example.recipe.views.ShowActionActivity;
import com.example.recipe.views.ShowExpenseActivity;
import com.example.recipe.views.auth.ForgotPasswordActivity;
import com.example.recipe.views.auth.LoginActivity;
import com.example.recipe.views.auth.ProfileActivity;
import com.example.recipe.views.auth.RegisterActivity;

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
            } else if (id == R.id.item_action_rapport_stat) {
                Redirect.route(start, RapportActivity.class);
            } else if (id == R.id.item_profile) {
                Redirect.route(start, ProfileActivity.class);
            } else if (id == R.id.item_logout) {
                Session.logout(start);
                Redirect.route(start, LoginActivity.class);
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
                Redirect.route(context, AllCommentsActivity.class, "id", identified);
            }
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }

    public static void auths(AppCompatActivity context, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.item_register) {
                Redirect.route(context, RegisterActivity.class);
            } else if (id == R.id.item_forgot) {
                Redirect.route(context, ForgotPasswordActivity.class);
            }
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }

    public static void auth(AppCompatActivity context, MenuItem menuItem) {
        try {
            int id = menuItem.getItemId();
            if (id == R.id.item_login)
                Redirect.route(context, LoginActivity.class);
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }
}
