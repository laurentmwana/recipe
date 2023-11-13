package com.example.recipe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";

    private static final int DB_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // création de la table event
        String createTableEvent = "CREATE TABLE IF NOT EXISTS actions(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "start_time TIME NOT NULL," +
                "end_time TIME," +
                "amount_daily_recipe FLOAT," +
                "amount_daily_expense FLOAT," +
                "state TINYINT NOT NULL DEFAULT 0," +
                "created_at DATETIME" +
                ");";

        sqLiteDatabase.execSQL(createTableEvent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String dropTablActions = "DROP TABLE IF EXISTS actions";

        sqLiteDatabase.execSQL(dropTablActions);
    }
}
