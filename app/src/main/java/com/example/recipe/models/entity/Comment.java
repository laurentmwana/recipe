package com.example.recipe.models.entity;

public class Comment {

    private int id;

    private String message;

    private String created_at;

    private int action_id;

    public int getId() {
        return id;
    }

    public Comment setId(int id) {
        this.id = id;

        return this;
    }

    public String getMessage() {
        return message;
    }

    public Comment setMessage(String message) {
        this.message = message;

        return this;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public Comment setCreatedAt(String created_at) {
        this.created_at = created_at;

        return this;

    }

    public int getActionId() {
        return action_id;
    }

    public Comment setActionId(int action_id) {
        this.action_id = action_id;

        return this;
    }
}
