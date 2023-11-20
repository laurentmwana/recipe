package com.example.recipe.controller;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.adapters.CustomActionsAdapter;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.ShowActionActivity;

import java.util.ArrayList;

public class ListingActionController {

    private ActionActivity context;

    private ActionRepository repository;

    private RecyclerView mRecyclerViewContainer;

    private TextView mTextViewCountActions;

    private CustomActionsAdapter adapter;

    public ListingActionController(ActionActivity context) {

        this.context = context;

        Authentificator.check(context);

        this.repository = new ActionRepository(context);

    }

    public void handle() {

        mRecyclerViewContainer = (RecyclerView) context.findViewById(R.id.recyclerview_action_container);
        mTextViewCountActions = (TextView) context.findViewById(R.id.text_view_count_action);


        // on commence par recupère toutes les actions effectuée
        ArrayList<Action> actions = repository.findAll();

        showCount(actions);

        // adapters
        adapter = new CustomActionsAdapter(this, actions);

        // on affiche les résultats dans le view
        mRecyclerViewContainer.setAdapter(adapter);

        addListeners();
    }

    private void showCount(ArrayList<Action> actions) {
        int len = actions.size();
        String label = len < 1 ? "action" : "actions";
        String message = String.format("Il y a %s %s dans la base de données", String.valueOf(len), label);
        mTextViewCountActions.setText(message);
    }

    private void addListeners() {
        mRecyclerViewContainer.setOnClickListener(view -> {
            Flash.modal(view.getContext(), "oui");
        });
    }

    public void onOption(View view) {
        Action action = (Action) view.getTag();
        Redirect.route(context, ShowActionActivity.class, "id", String.valueOf(action.getId()));
    }
}
