package com.example.recipe.controller;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.recipe.R;
import com.example.recipe.adapters.ListingActionAdapter;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.ActionActivity;
import com.example.recipe.views.ShowActionActivity;

import java.util.ArrayList;

public class ListingActionController {

    private ActionActivity context;

    private ActionRepository repository;

    private RecyclerView mRecyclerViewContainer;

    private ListingActionAdapter adapter;

    public ListingActionController(ActionActivity context) {

        this.context = context;

        this.repository = new ActionRepository(context);
    }

    public void handle() {

        mRecyclerViewContainer = (RecyclerView) context.findViewById(R.id.recyclerview_action_container);

        // on commence par recupère toutes les actions effectuée
        ArrayList<Action> actions = repository.findAll();

        // adapters
        adapter = new ListingActionAdapter(this, actions);

        // on affiche les résultats dans le view
        mRecyclerViewContainer.setAdapter(adapter);

        addListeners();
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
