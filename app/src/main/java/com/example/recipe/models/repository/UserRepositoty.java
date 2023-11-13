package com.example.recipe.models.repository;

import android.content.Context;
import android.database.Cursor;

import com.example.expense.database.AppDatabase;
import com.example.expense.models.QueryBuilder;
import com.example.expense.models.query.Insert;

public class UserRepositoty {

    public static final String TABLE = "users";

    private final AppDatabase database;

    private String[] columns = new String[]{"id", "name", "email", "username"};

    public UserRepositoty(Context c) {

        this.database = AppDatabase.getDatabaseInstance(c.getApplicationContext());
    }

    public boolean create(String fullName, String email, String username, String password) {
        return true;
    }

    public Cursor findBy(String key, String value) {

        String sql = (new QueryBuilder())
                .from(TABLE, null)
                .where(key + " = ?")
                .limit(1)
                .getSql();


        Cursor c = database.writable.rawQuery(sql, new String[]{"lobi123"});

        c.moveToFirst();

        return c;

    }

}
