package com.example.recipe.models.query;

import android.content.ContentValues;

import com.example.expense.database.AppDatabase;

public class Insert {

    private String table = null;

    private ContentValues values = null;

    private AppDatabase database;

    public Insert(AppDatabase database) {

        this.database = database;
    }

    public Insert from(String table, String alias) {

        this.table = table + " " + alias;

        return this;
    }

    public Insert from(String table) {
        this.table = table;

        return this;
    }

    public Insert values(ContentValues values) {

        this.values = values;

        return this;
    }

    public boolean save() {

        Long state = database.writable.insert(table, null, values);

        database.close();

        return !(state == -1);
    }

}
