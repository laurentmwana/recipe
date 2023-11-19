package com.example.recipe.controller;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.adapters.CustomExpenseActionAdapter;
import com.example.recipe.adapters.CustomFilterActionAdapter;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ExpenseActivity;
import com.example.recipe.views.ShowExpenseActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExpenseController {

    private ActionRepository repository;

    private ExpenseActivity context;

    private RecyclerView mRecyclerViewExpense;

    private TextView mTextViewExpenses;


    /**
     * @param context
     */
    public ExpenseController(ExpenseActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() {
        mRecyclerViewExpense = (RecyclerView) context.findViewById(R.id.recyclerview_expense_action_container);
        mTextViewExpenses = (TextView) context.findViewById(R.id.text_view_expenses);
        showCountExpense();
        ArrayList<Action> expenses = repository.expenses();
        reload(expenses);
    }

    private void showCountExpense() {
        Float expenses = repository.countExpense();
        String message = "Vous avez dépensé " + expenses + " FC";
        mTextViewExpenses.setText(message);
    }

    private void reload(ArrayList<Action> actions) {
        CustomExpenseActionAdapter adapter = new CustomExpenseActionAdapter(this, actions);
        mRecyclerViewExpense.setAdapter(adapter);
    }

    public void onEye(View view) {
        Action action = (Action) view.getTag();
        Redirect.route(context, ShowExpenseActivity.class, "id", String.valueOf(action.getId()));
    }

    public void onComment(View view) {
        Action action = (Action) view.getTag();
        Redirect.route(context, ShowExpenseActivity.class, "id", String.valueOf(action.getId()));
    }
}
