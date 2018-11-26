package com.android.livedatasample.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class FavoriteDbHelper extends SQLiteOpenHelper {
    public FavoriteDbHelper(Context context) {
        super(context, "fav", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE fav ( name  TEXT NOT NULL PRIMARY KEY, color TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fav");
        onCreate(db);
    }
}
