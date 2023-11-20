package com.example.recipe.controller;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;
import com.example.recipe.adapters.CustomCommentAdapter;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Session;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.entity.Comment;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.models.repository.CommentRepository;
import com.example.recipe.views.AllCommentsActivity;

import java.util.ArrayList;

public class AllCommentController  {

    private AllCommentsActivity context;

    private CommentRepository repository;

    private RecyclerView mRecyclerViewComments;

    private TextView mTextViewCountComment;

    private ActionRepository actionRepository;

    public AllCommentController(AllCommentsActivity context) {

        this.context = context;

        Session.check(context);

        this.actionRepository = new ActionRepository(context);
        this.repository = new CommentRepository(context);
    }


    public void onEye(View view) {
        Comment c = (Comment) view.getTag();
        Flash.modal(context, c.getMessage());
    }

    public void onDelete(View view) {
        Comment c = (Comment) view.getTag();
        repository.delete(c.getId());

        Flash.ok(context, "commentaire supprimé", (dialogInterface, i) -> context.recreate());
    }

    public void handle() throws NotFoundException {

        mRecyclerViewComments = (RecyclerView) context.findViewById(R.id.recyclerview_comment_container);
        mTextViewCountComment = (TextView) context.findViewById(R.id.text_view_count_comment);

        try {
            Action action = getAction();
            ArrayList<Comment> comments = repository.findAll(action.getId());
            showCount(comments);
            mRecyclerViewComments.setAdapter(new CustomCommentAdapter(this, comments));
        } catch (Exception e) {
            Flash.modal(context, e.getMessage());
        }
    }

    public Action getAction() throws NotFoundException {
        return actionRepository.find(Shared.getId(context));
    }

    private void showCount(ArrayList<Comment> cs) {
        String message = "Il y a " + cs.size() + " commentaire(s)";
        mTextViewCountComment.setText(message);
    }
}
