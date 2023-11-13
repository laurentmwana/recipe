package com.example.recipe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AppDatabase {

    private static AppDatabase INSTANCE = null;

    public SQLiteDatabase writable;

    public SQLiteDatabase readable;

    public Database database;

    private Context context;

    public static AppDatabase getDatabaseInstance(Context context) {

        if (null == INSTANCE) {
            INSTANCE = new AppDatabase(context);
        }
        return INSTANCE;
    }

    private AppDatabase(Context context) {

        this.database = new Database(context);

        this.writable = database.getWritableDatabase();
        this.readable = database.getReadableDatabase();
    }

    public void close() {
        this.database.close();
    }
}
