package com.example.recipe.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Redirect;
import com.example.recipe.helper.Authentificator;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.models.repository.CommentRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.NewCommentActivity;
import com.example.recipe.views.ShowExpenseActivity;

public class NewCommentController {

    private final Action action;
    
    private final NewCommentActivity context;

    private final ActionRepository actionRepository;

    private EditText mEditTextComment;
    
    private Button mButtonSave;

    private CommentRepository repository;

    public NewCommentController(NewCommentActivity context) throws NotFoundException {

        this.context = context;

        this.actionRepository = new ActionRepository(context);
        
        this.action = getAction();

        this.repository = new CommentRepository(context);
    }

    public void handle()  {
        mEditTextComment = (EditText) context.findViewById(R.id.edit_text_new_comment);
        mButtonSave = (Button) context.findViewById(R.id.button_save);

        Authentificator.check(context);

        addListeners();
    }

    private void addListeners() {
        mButtonSave.setOnClickListener(this::onSubmit);
    }

    private void onSubmit(View view) {

        Validator validator = (new Validator())
                .between(mEditTextComment, 5, 500).required(mEditTextComment);

        if (validator.isValid()) {
            boolean created = repository.add(mEditTextComment.getText().toString(), action.getId());
            Flash.ok(context, "Commentaire ajouté", (dialogInterface, i) -> {
                Redirect.route(context, ShowExpenseActivity.class, "id", String.valueOf(action.getId()));
            });
        }
    }

    public Action getAction() throws NotFoundException {
        return  actionRepository.find(Shared.getId(context));
    }
}
