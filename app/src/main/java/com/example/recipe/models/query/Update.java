package com.example.recipe.models.query;

import android.content.ContentValues;

import com.example.recipe.database.AppDatabase;
import java.util.ArrayList;
import java.util.Arrays;

public class Update {


    private AppDatabase database;

    private ContentValues values;

    private final ArrayList<String> andWhere = new ArrayList<String>();

    private final ArrayList<String> orWhere = new ArrayList<String>();

    private String where = null;

    private String[] args = null;


    public Update(AppDatabase database) {

        this.database = database;
    }

    private String from = null;

    /**
     * Nom de la table
     * @param table
     * @param alias
     * @return
     */
    public Update from(String table, String alias) {
        if (null != alias) {
            this.from = table + " " + alias;
        } else {
            this.from = table;
        }
        return this;
    }

    public Update values(ContentValues value) {

        this.values = value;

        return this;
    }

    public Update where(String condition) {

        this.where = condition;

        return this;
    }

    public Update andWhere(String... conditions) {

        this.andWhere.addAll(Arrays.asList(conditions));

        return this;
    }

    public Update orWhere(String... conditions) {

        this.orWhere.addAll(Arrays.asList(conditions));

        return this;
    }

    public Update args(String... valueOf) {

        this.args = valueOf;

        return this;
    }


    public Update columns(String... columns) {
        
        
        return this;
    }

    public boolean save() {

        String selection = toWhere();

        int state =  database.writable
                .update(from, values, selection, args);

        return !(state == -1);
    }

    private String toWhere() {

        if (null != where || !andWhere.isEmpty() || !orWhere.isEmpty()) {

            String w = "";

            if (null != where && andWhere.isEmpty() && orWhere.isEmpty()) {
                w = "(" + where + ")";
            } else if (null == where && !andWhere.isEmpty() && orWhere.isEmpty()) {
                w += " AND (" + String.join(") AND (", andWhere) + ")";
            } else {
                w += " OR (" + String.join(") OR (", andWhere) + ")";
            }
            return w;
        }

        return null;
    }

}

