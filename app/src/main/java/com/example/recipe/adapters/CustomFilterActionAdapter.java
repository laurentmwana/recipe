package com.example.recipe.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.controller.FilterController;
import com.example.recipe.controller.ListingActionController;
import com.example.recipe.helper.Helper;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.views.ShowActionActivity;

import java.util.ArrayList;

public class CustomFilterActionAdapter extends  RecyclerView.Adapter<CustomFilterActionAdapter.ViewHolder> {

    private FilterController controller;
    private ArrayList<Action> actions;

    public CustomFilterActionAdapter(FilterController controller, ArrayList<Action> actions) {
        this.controller = controller;

        this.actions = actions;
    }

    @NonNull
    @Override
    public CustomFilterActionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_filter_action, parent, false);
        return  new CustomFilterActionAdapter.ViewHolder(view, controller);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomFilterActionAdapter.ViewHolder holder, int position) {

        Action action = actions.get(position);
        holder.mTextViewStartTime.setText(Helper.preffix("De", action.getStartTime()));
        holder.mTextViewEndTime.setText(Helper.preffix("à", action.getEndTime()));
        holder.mTextViewAmountDailyExpense.setText(Helper.suffix(action.getAmountDailyExpense(), "Fc"));
        holder.mTextViewAmountDailyRecipe.setText(Helper.suffix(action.getAmountDailyRecipe(), "Fc"));
        holder.mButtonEye.setTag(action);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewStartTime, mTextViewEndTime;

        public TextView mTextViewAmountDailyRecipe, mTextViewAmountDailyExpense;

        public Button mButtonEye;

        private View view;

        private FilterController controller;

        public ViewHolder(@NonNull View view, FilterController controller) {
            super(view);

            this.view = view;
            this.controller = controller;

            init();
        }

        private void init() {

            // initialisation

            mTextViewStartTime = (TextView) view.findViewById(R.id.text_view_start_time);
            mTextViewEndTime = (TextView) view.findViewById(R.id.text_view_end_time);

            mTextViewAmountDailyExpense = (TextView) view.findViewById(R.id.text_view_amount_daily_expense);
            mTextViewAmountDailyRecipe = (TextView) view.findViewById(R.id.text_view_amount_daily_recipe);

            mButtonEye = (Button) view.findViewById(R.id.button_eye);

            addListeners();

        }

        private void addListeners() {
            mButtonEye.setOnClickListener(v -> {
                Action action = (Action) v.getTag();
                Redirect.route(view.getContext(), ShowActionActivity.class, "id", String.valueOf(action.getId()));
            });
        }

    }
}
