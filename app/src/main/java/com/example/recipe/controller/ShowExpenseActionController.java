package com.example.recipe.controller;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Helper;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.models.repository.CommentRepository;
import com.example.recipe.views.NewCommentActivity;
import com.example.recipe.views.ShowExpenseActivity;

public class ShowExpenseActionController {

    private final Action action;
    private ShowExpenseActivity context;

    private ActionRepository repository;

    private TextView mTextViewStartTime, mTextViewEndTime, mTextViewCountComment;

    private TextView mTextViewAmountDailyRecipe, mTextViewAmountDailyExpense;

    private TextView mTextViewCreatedAt, mTextViewUpdatedAt;

    private Button mButtonNewComment;
    private CommentRepository commentRepository;


    public ShowExpenseActionController(ShowExpenseActivity context) throws NotFoundException {

        this.context = context;

        Authentificator.check(context);

        this.repository = new ActionRepository(context);
        this.commentRepository = new CommentRepository(context);

        this.action = getAction();
    }

    public void handle() throws NotFoundException {

        // initialisation
        mTextViewStartTime = (TextView) context.findViewById(R.id.text_view_start_time);
        mTextViewEndTime = (TextView) context.findViewById(R.id.text_view_end_time);
        mTextViewAmountDailyExpense = (TextView) context.findViewById(R.id.text_view_amount_daily_expense);
        mTextViewAmountDailyRecipe = (TextView) context.findViewById(R.id.text_view_amount_daily_recipe);
        mTextViewCountComment = (TextView) context.findViewById(R.id.text_view_count_comment);
        mTextViewCreatedAt = (TextView) context.findViewById(R.id.text_view_created_at);
        mTextViewUpdatedAt = (TextView) context.findViewById(R.id.text_view_updated_at);

        mButtonNewComment = (Button) context.findViewById(R.id.button_add_comment);

        show();

        addListeners();

    }

    private void show() {
        int comments = commentRepository.count(action.getId());
        String c = "Il y a " + comments + " commentaire(s)";
        mTextViewCountComment.setText(c) ;
        mTextViewStartTime.setText(action.getStartTime());
        mTextViewEndTime.setText(action.getEndTime());
        mTextViewAmountDailyRecipe.setText(Helper.suffix(action.getAmountDailyRecipe(), "Fc"));
        mTextViewAmountDailyExpense.setText(Helper.suffix(action.getAmountDailyExpense(), "Fc"));
        mTextViewCreatedAt.setText(action.getCreatedAt());
        mTextViewUpdatedAt.setText(action.getUpdatedAt());
    }

    public Action getAction() throws NotFoundException {
        return  repository.find(Shared.getId(context));
    }

    private void addListeners() {
        mButtonNewComment.setOnClickListener(this::onComment);
    }

    private void onComment(View view) {
        Redirect.route(context, NewCommentActivity.class, "id", String.valueOf(action.getId()));
    }

}
