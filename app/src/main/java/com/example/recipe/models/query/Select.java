package com.example.recipe.models.query;

import android.database.Cursor;

import com.example.expense.database.AppDatabase;
import java.util.ArrayList;
import java.util.Arrays;

public class Select {


    private AppDatabase database;

    private String[] columns = {};

    private final ArrayList<String> andWhere = new ArrayList<String>();

    private final ArrayList<String> orWhere = new ArrayList<String>();

    private String where = null;

    private String[] args = null;

    private final ArrayList<String> orderBy = new ArrayList<String>();

    private String limit = null;

    public Select(AppDatabase database) {

        this.database = database;
    }

    private String from = null;

    /**
     * Nom de la table
     * @param table
     * @param alias
     * @return
     */
    public Select from(String table, String alias) {
        if (null != alias) {
            this.from = table + " " + alias;
        } else {
            this.from = table;
        }
        return this;
    }

    public Select select(String[] cs) {

        this.columns = cs;

        return this;
    }


    public Select orderBy(String field, String direction) {

        this.orderBy.add(field + " " + direction);

        return this;
    }

    public Select where(String condition) {

        this.where = condition;

        return this;
    }

    public Select andWhere(String... conditions) {

        this.andWhere.addAll(Arrays.asList(conditions));

        return this;
    }

    public Select orWhere(String... conditions) {

        this.orWhere.addAll(Arrays.asList(conditions));

        return this;
    }

    public Select limit(int l) {

        this.limit = String.valueOf(l);

        return this;
    }

    public Select params(String... params) {

        this.args = params;

        return this;
    }

    public Cursor execute() {

        String selection = toWhere();
        String orders = toOrderBy();
        String[] params = args;

        return database.writable
                .query(from, columns, selection, params, null, null, orders, limit);
    }


    private String toOrderBy() {

        if (!orderBy.isEmpty()) {
            return String.join(", ", orderBy);
        }

        return null;
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

