package com.example.recipe.models.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.recipe.database.AppDatabase;
import com.example.recipe.helper.Moment;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.entity.Comment;
import com.example.recipe.models.query.Delete;
import com.example.recipe.models.query.Insert;

import java.util.ArrayList;

public class CommentRepository {

    public static final String TABLE = "comments";

    private final AppDatabase database;
    private Context context;

    private String[] columns = new String[]{"c.id", "c.comment", "c.action_id", "c.created_at"};

    public CommentRepository(Context context) {

        this.database = AppDatabase.getDatabaseInstance(context.getApplicationContext());
        this.context = context;
    }

    public boolean add(String comment, int actionId) {

        ContentValues values = new ContentValues();
        values.put("comment", comment);
        values.put("action_id", actionId);
        values.put("created_at", Moment.at());

        return (new Insert(database)).from(TABLE).values(values).save();
    }

    public int count(int actionId) {
        Cursor cs = database.writable
                .rawQuery("SELECT COUNT(id) AS ade FROM comments WHERE action_id = ?",
                        new String[]{String.valueOf(actionId)});

        cs.moveToFirst();

        return cs.getInt(0);
    }

    public  ArrayList<Comment> findAll(int actionId) {
        Cursor cs = database.writable
                .rawQuery("SELECT * FROM comments WHERE action_id = ?",
                        new String[]{String.valueOf(actionId)});

        ArrayList<Comment> cm = new ArrayList<Comment>();

        // on fait l'hydratation
        // pour avoir les données sous forme d'objets
        while (cs.moveToNext()) {
            cm.add(getHydrate(cs));
        }
        return cm;

    }

    private Comment getHydrate(Cursor cs) {
        return  (new Comment())
                .setId(cs.getInt(0))
                .setMessage(cs.getString(1))
                .setCreatedAt(cs.getString(2))
                .setActionId(cs.getInt(3));
    }

    public boolean delete(int id) {
        return (new Delete(database))
                .from(TABLE, null)
                .where("id = ?")
                .args(String.valueOf(id))
                .save();
    }
}
