package com.example.recipe.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.controller.ListingActionController;
import com.example.recipe.helper.Helper;
import com.example.recipe.models.entity.Action;

import java.util.ArrayList;

public class ListingActionAdapter extends  RecyclerView.Adapter<ListingActionAdapter.ViewHolder> {

    private ListingActionController controller;
    private ArrayList<Action> actions;

    public ListingActionAdapter(ListingActionController controller, ArrayList<Action> actions) {
        this.controller = controller;

        this.actions = actions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_all_action, parent, false);
        return  new ViewHolder(view, controller);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Action action = actions.get(position);
        if (action.isState()) {
            holder.mTextViewState.setText("Finalisée");
        }
        holder.mTextViewStartTime.setText(Helper.preffix("De", action.getStartTime()));
        holder.mTextViewEndTime.setText(Helper.preffix("à", action.getEndTime()));
        holder.mTextViewAmountDailyExpense.setText(Helper.suffix(action.getAmountDailyRecipe(), "Fc"));
        holder.mTextViewAmountDailyRecipe.setText(Helper.suffix(action.getAmountDailyExpense(), "Fc"));
        holder.mButtonOption.setTag(action);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewStartTime, mTextViewEndTime, mTextViewState;

        public TextView mTextViewAmountDailyRecipe, mTextViewAmountDailyExpense;

        public Button mButtonOption;


        private View view;

        private ListingActionController controller;

        public ViewHolder(@NonNull View view, ListingActionController controller) {
            super(view);

            this.view = view;
            this.controller = controller;

            init();
        }

        private void init() {

            // initialisation
            mTextViewState = (TextView) view.findViewById(R.id.text_view_state);

            mTextViewStartTime = (TextView) view.findViewById(R.id.text_view_starttime);
            mTextViewEndTime = (TextView) view.findViewById(R.id.text_view_endtime);

            mTextViewAmountDailyExpense = (TextView) view.findViewById(R.id.text_view_amount_daily_expense);
            mTextViewAmountDailyRecipe = (TextView) view.findViewById(R.id.text_view_amount_daily_recipe);

            mButtonOption = (Button) view.findViewById(R.id.button_more);

            addListners();

        }
        private void addListners() {
            mButtonOption.setOnClickListener(controller::onOption);
        }

    }
}
