package com.example.recipe.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.controller.AllCommentController;
import com.example.recipe.controller.FilterController;
import com.example.recipe.helper.Helper;
import com.example.recipe.helper.Redirect;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.entity.Comment;
import com.example.recipe.views.ShowActionActivity;

import java.util.ArrayList;

public class CustomCommentAdapter extends  RecyclerView.Adapter<CustomCommentAdapter.ViewHolder> {

    private AllCommentController controller;
    private ArrayList<Comment> comments;

    public CustomCommentAdapter(AllCommentController controller, ArrayList<Comment> comments) {
        this.controller = controller;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CustomCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_comments, parent, false);
        return  new CustomCommentAdapter.ViewHolder(view, controller);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.mTextViewMessage.setText(comment.getMessage());
        holder.mButtonEye.setTag(comment);
        holder.mButtonDelete.setTag(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewMessage, mTextViewCreatedAt;

        public Button mButtonDelete, mButtonEye;


        private View view;

        private AllCommentController controller;

        public ViewHolder(@NonNull View view, AllCommentController controller) {
            super(view);

            this.view = view;
            this.controller = controller;

            init();
        }

        private void init() {

            // initialisation

            mTextViewMessage = (TextView) view.findViewById(R.id.text_view_message);
            mTextViewCreatedAt = (TextView) view.findViewById(R.id.text_view_created_at);

            mButtonEye = (Button) view.findViewById(R.id.button_eye);
            mButtonDelete = (Button) view.findViewById(R.id.button_delete);

            addListeners();

        }

        private void addListeners() {
            mButtonEye.setOnClickListener(controller::onEye);
            mButtonDelete.setOnClickListener(controller::onDelete);
        }

    }
}
