package com.example.leisureapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.leisureapp.activities.MainActivity;

public class DatabaseManager extends SQLiteOpenHelper  {
    private static final String TAG = DatabaseManager.class.getSimpleName();

    public SQLiteStatement _statementInsertFavorite;

    public DatabaseManager(Context context) {
        super(context, "leisure.db", null, 1);

        SQLiteDatabase db = getReadableDatabase();
        _statementInsertFavorite = db.compileStatement("INSERT INTO favorites (api_key) VALUES (?)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE favorites (favorites_id INTEGER PRIMARY KEY AUTOINCREMENT, api_key TEXT NOT NULL)");
            db.execSQL("CREATE INDEX favorites_index ON favorites(api_key)");
        } catch (SQLException ex) {
            Log.e(TAG, "Exception beim Anlegen von DB-Schema aufgetreten: " + ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // NOT NEEDED
    }
}