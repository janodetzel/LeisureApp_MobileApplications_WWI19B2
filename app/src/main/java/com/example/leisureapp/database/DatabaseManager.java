package com.example.leisureapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.leisureapp.fragments.HomeFragment;
import com.example.leisureapp.models.ItemModel;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = DatabaseManager.class.getSimpleName();

    public DatabaseManager(Context context) {
        super(context, "leisure.db", null, 1);

        // DELETE CURRENT DATABASE
        //context.deleteDatabase("leisure.db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createFavorites = "CREATE TABLE favorites (favorites_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "activity_key TEXT NOT NULL, title TEXT NOT NULL, " +
                    "type TEXT NOT NULL, participants INTEGER NOT NULL, " +
                    "price DOUBLE NOT NULL, accessibility DOUBLE NOT NULL, img_url TEXT NOT NULL)";

            String createTmp = "CREATE TABLE tmp (tmp_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "activity_key TEXT NOT NULL, title TEXT NOT NULL, " +
                    "type TEXT NOT NULL, participants INTEGER NOT NULL, " +
                    "price DOUBLE NOT NULL, accessibility DOUBLE NOT NULL, img_url TEXT NOT NULL)";

            db.execSQL(createFavorites);
            db.execSQL(createTmp);
        } catch (SQLException ex) {
            Log.e(TAG, "SQL-Error: " + ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // NOT NEEDED
    }

    public ItemModel[] getFavorites() {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM favorites", null);

            int resultCount = cursor.getCount();
            if (resultCount == 0) {
                Log.d(TAG, "No entries in database.");
                return new ItemModel[]{};
            } else {
                ItemModel[] results = new ItemModel[resultCount];
                int counter = 0;

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String key = cursor.getString(1);
                    String title = cursor.getString(2);
                    String type = cursor.getString(3);
                    int persons = cursor.getInt(4);
                    double price = cursor.getDouble(5);
                    double accessibility = cursor.getDouble(6);
                    String imgUrl = cursor.getString(7);

                    ItemModel item = new ItemModel(title, type, persons, price, "", key, accessibility, imgUrl);
                    results[counter] = item;
                    counter++;
                }

                cursor.close();
                return results;
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
            return null;
        }
    }

    public void insertFavorite(ItemModel item, Context context) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT activity_key FROM favorites WHERE activity_key = ?", new String[] {item.getKey()});
            if (cursor.getCount() > 0) {
                // DUPLICATE ITEM IN DATABASE
                Toast.makeText(context, "Already saved in Favorites.", Toast.LENGTH_SHORT).show();
            } else {
                // INSERT IN DATABASE
                // Set values for insert
                ContentValues values = new ContentValues();
                values.put("title", item.getActivity());
                values.put("type", item.getType());
                values.put("participants", item.getParticipants());
                values.put("price", item.getPrice());
                values.put("activity_key", item.getKey());
                values.put("accessibility", item.getAccessibility());
                if (item.getImgURL() == null) {
                    values.put("img_url", "NOT IMPLEMENTED");
                } else {
                    values.put("img_url", item.getImgURL());
                }

                // Insert new item in favorites
                db.insertOrThrow("favorites", null, values);
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
        }
    }

    public void clearFavorites() {
        try {
            // Delete all entries in favorites
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM favorites");
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
        }
    }

    public void removeFavorite(String id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM favorites WHERE activity_key = " + id);
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
        }
    }

    public ItemModel[] getTmp() {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM tmp ORDER BY tmp_id DESC LIMIT 3", null);

            int resultCount = cursor.getCount();
            if (resultCount == 0) {
                Log.d(TAG, "No entries in database.");
                return new ItemModel[]{};
            } else {
                ItemModel[] results = new ItemModel[resultCount];
                int counter = 0;

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String key = cursor.getString(1);
                    String title = cursor.getString(2);
                    String type = cursor.getString(3);
                    int persons = cursor.getInt(4);
                    double price = cursor.getDouble(5);
                    double accessibility = cursor.getDouble(6);
                    String imgUrl = cursor.getString(7);

                    ItemModel item = new ItemModel(title, type, persons, price, "", key, accessibility, imgUrl);
                    results[counter] = item;
                    counter++;
                }

                cursor.close();
                return results;
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
            return null;
        }
    }

    public void insertTmp(ItemModel item) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            // Set values for insert
            ContentValues values = new ContentValues();
            values.put("title", item.getActivity());
            values.put("type", item.getType());
            values.put("participants", item.getParticipants());
            values.put("price", item.getPrice());
            values.put("activity_key", item.getKey());
            values.put("accessibility", item.getAccessibility());
            if (item.getImgURL() == null) {
                values.put("img_url", "NOT IMPLEMENTED");
            } else {
                values.put("img_url", item.getImgURL());
            }

            // Insert new item in favorites
            db.insertOrThrow("tmp", null, values);
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
        }
    }

    public void clearTmp() {
        try {
            // Delete all entries in tmp
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM tmp");
        } catch (SQLException e) {
            Log.e(TAG, "SQL-Error: " + e.getMessage());
        }
    }


}
