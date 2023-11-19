package com.example.recipe.models.entity;

public class Comment {

    private int id;

    private String message;

    private String created_at;

    private String updated_at;

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

    public String getUpdatedAt() {
        return updated_at;
    }

    public Comment setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;

        return this;
    }
}
