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
                "state INT(1) NOT NULL DEFAULT 0," +
                "created_at DATETIME NOT NULL," +
                "updated_at DATETIME" +
                ");";

        String createTableComments = "CREATE TABLE IF NOT EXISTS comments(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "comment TEXT NOT NULL," +
                "action_id INTEGER NOT NULL," +
                "created_at DATETIME NOT NULL," +
                "FOREIGN KEY (`action_id`) REFERENCES `actions` (`id`) ON UPDATE CASCADE ON DELETE CASCADE" +
                ");";

        sqLiteDatabase.execSQL(createTableEvent);
        sqLiteDatabase.execSQL(createTableComments);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String dropTablActions = "DROP TABLE IF EXISTS actions";
        String dropTableComments = "DROP TABLE IF EXISTS comments";

        sqLiteDatabase.execSQL(dropTablActions);
        sqLiteDatabase.execSQL(dropTableComments);
    }
}
