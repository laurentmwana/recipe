package com.example.recipe.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.adapters.CustomFilterActionAdapter;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Picker;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.FilterActivity;

import java.util.ArrayList;
import java.util.Objects;

public class FilterController {

    private EditText mEditTextDate;

    private FilterActivity context;

    private TextView mTextViewResults;

    private RecyclerView mRecyclerViewActions;

    private RadioGroup mRadioGroupFilters;

    private ActionRepository repository;

    public FilterController(FilterActivity context) {

        this.context = context;

        Authentificator.check(context);


        this.repository = new ActionRepository(context);
    }

    public void handle() {

        mRecyclerViewActions = (RecyclerView) context.findViewById(R.id.recyclerview_filter_action_container);
        mTextViewResults = (TextView) context.findViewById(R.id.text_view_filter_results);
        mEditTextDate = (EditText) context.findViewById(R.id.edit_text_filter_date);
        mRadioGroupFilters = (RadioGroup) context.findViewById(R.id.radio_group_filter);

        try {
            ArrayList<Action> actions = repository.findAll();
            reload(actions);
            showCountResult(actions);
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
        addListeners();
    }

    private void showCountResult(ArrayList<Action> actions) {
        int len = actions.size();
        String label = len < 2 ? "action" : "actions";
        String message = String.format("Il y a %s %s", String.valueOf(len), label);
        mTextViewResults.setText(message);
    }

    private void addListeners() {

        mEditTextDate.setOnClickListener(this::onDate);
        mEditTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String v = charSequence.toString();
                if (!v.isEmpty()) {
                    try {
                        ArrayList<Action> actions = repository.findByDate("%" + v + "%");
                        reload(actions);
                        showCountResult(actions);
                    } catch (Exception e) {
                        Flash.modal(context, e.getMessage());
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mRadioGroupFilters.setOnCheckedChangeListener(this::onRadioGroup);
    }

    private void onRadioGroup(RadioGroup radioGroup, int id) {
        RadioButton radio = radioGroup.findViewById(id);
        String tag = (String) radio.getTag();
        String v = mEditTextDate.getText().toString();
        if (Objects.equals(tag, "filter_biggest_expense")) {
            ArrayList<Action> actions = repository.biggest(v, true);
            showCountResult(actions);
            reload(actions);
        } else if (Objects.equals(tag, "filter_biggest_recipe")) {
            ArrayList<Action> actions = repository.biggest(v, false);
            showCountResult(actions);
            reload(actions);
        } else if (Objects.equals(tag, "filter_smallest_recipe")) {
            ArrayList<Action> actions = repository.smallest(v, false);
            showCountResult(actions);
            reload(actions);
        } else if (Objects.equals(tag, "filter_smallest_expense")) {
            ArrayList<Action> actions = repository.smallest(v, true);
            showCountResult(actions);
            reload(actions);
        }
    }

    private void onDate(View view) {
        Picker.onDate((EditText) view);
    }


    private void reload(ArrayList<Action> actions) {
        CustomFilterActionAdapter adapter = new CustomFilterActionAdapter(this, actions);
        mRecyclerViewActions.setAdapter(adapter);
    }
}
