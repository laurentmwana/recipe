package com.example.recipe.models;

import java.util.ArrayList;
import java.util.Arrays;

public class QueryBuilder {

    private String table = null;

    private ArrayList<String> fields = new ArrayList<String>();

    private String where = null;

    private ArrayList<String> andWhere = new ArrayList<String>();

    private ArrayList<String> orWhere = new ArrayList<String>();

    private ArrayList<String> orders = new ArrayList<String>();

    private String limit = null;


    public QueryBuilder from(String table, String alias) {

        this.table = null != alias ?  table + " " + alias : table;

        return this;
    }

    public QueryBuilder select(String... columns) {

        this.fields.addAll(Arrays.asList(columns));

        return this;
    }

    public QueryBuilder where(String condition) {

        this.where = condition;

        return this;
    }

    public QueryBuilder andWhere(String... conditions) {

        this.andWhere.addAll(Arrays.asList(conditions));

        return this;
    }

    public QueryBuilder orWhere(String... conditions) {

        this.orWhere.addAll(Arrays.asList(conditions));

        return this;
    }


    public QueryBuilder orderBy(String field, String direction) {

        orders.add(field + " " + direction);

        return this;
    }

    public QueryBuilder limit(int limit) {

        this.limit = String.valueOf(limit);

        return this;
    }

    public String getSql() {
        ArrayList<String> l = new ArrayList<String>();

        l.add("SELECT");

        if (fields.isEmpty()) {
            l.add("*");
        } else {
            l.add(String.join(", ", fields));
        }

        l.add("FROM");

        l.add(table);

        if (where != null || andWhere.isEmpty() || orWhere.isEmpty()) {
            String s = "";
            if (where != null) {
                s = where;
            } else if (!andWhere.isEmpty()) {
                s +=  " AND (" + String.join(") AND (", andWhere) + ")";
            } else {
                s +=  " OR (" + String.join(") OR (", orWhere) + ")";
            }
            l.add("WHERE");
            l.add(s);
        }

        if (!orders.isEmpty()) {
            l.add("ORDER BY");
            l.add(String.join(", ", orders));
        }

        if (limit != null) {
            l.add("LIMIT");
            l.add(limit);
        }

        return String.join(" ", l);
    }


}
