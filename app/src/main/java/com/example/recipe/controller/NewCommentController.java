package com.example.recipe.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Shared;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.validator.Validator;
import com.example.recipe.views.NewCommentActivity;

public class NewCommentController {

    private final Action action;
    
    private final NewCommentActivity context;

    private final ActionRepository repository;
    
    private EditText mEditTextComment;
    
    private Button mButtonSave;

    public NewCommentController(NewCommentActivity context) throws NotFoundException {

        this.context = context;

        this.repository = new ActionRepository(context);
        
        this.action = getAction();
    }

    public void handle()  {
        mEditTextComment = (EditText) context.findViewById(R.id.edit_text_new_comment);
        mButtonSave = (Button) context.findViewById(R.id.button_save);
        
        addListeners();
    }

    private void addListeners() {
        mButtonSave.setOnClickListener(this::onSubmit);
    }

    private void onSubmit(View view) {

        Validator validator = (new Validator())
                .between(mEditTextComment, 5, 500).required(mEditTextComment);

        if (validator.isValid()) {

        }
    }

    public Action getAction() throws NotFoundException {
        return  repository.find(Shared.getId(context));
    }
}
