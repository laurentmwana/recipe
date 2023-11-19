package com.example.recipe.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.controller.ExpenseController;
import com.example.recipe.controller.FilterController;
import com.example.recipe.helper.Helper;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.views.ShowActionActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomExpenseActionAdapter extends  RecyclerView.Adapter<CustomExpenseActionAdapter.ViewHolder> {

    private ExpenseController controller;
    private ArrayList<Action> actions;

    public CustomExpenseActionAdapter(ExpenseController controller, ArrayList<Action> actions) {
        this.controller = controller;
        this.actions = actions;
    }

    @NonNull
    @Override
    public CustomExpenseActionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_expense_action, parent, false);
        return  new CustomExpenseActionAdapter.ViewHolder(view, controller);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomExpenseActionAdapter.ViewHolder holder, int position) {

        Action action = actions.get(position);
        holder.mTextViewAmountDailyExpense.setText(Helper.suffix(action.getAmountDailyExpense(), "Fc"));
        holder.mButtonEye.setTag(action);
        holder.mButtonComment.setTag(action);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView mTextViewAmountDailyExpense;

        public Button mButtonEye, mButtonComment;

        private View view;

        private ExpenseController controller;

        public ViewHolder(@NonNull View view, ExpenseController controller) {
            super(view);

            this.view = view;
            this.controller = controller;

            init();
        }

        private void init() {

            // initialisation

            mTextViewAmountDailyExpense = (TextView) view.findViewById(R.id.text_view_amount_daily_expense);

            mButtonEye = (Button) view.findViewById(R.id.button_eye);
            mButtonComment = (Button) view.findViewById(R.id.button_comment);

            addListeners();

        }

        private void addListeners() {
            mButtonEye.setOnClickListener(controller::onEye);
            mButtonComment.setOnClickListener(controller::onComment);
        }

    }
}
