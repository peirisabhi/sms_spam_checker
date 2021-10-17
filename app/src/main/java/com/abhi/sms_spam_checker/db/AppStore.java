package com.abhi.sms_spam_checker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AppStore {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public AppStore(Context context) {
        this.context = context;
    }

    public AppStore open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void close() {
        dbHelper.close();
    }

    public void drop() {
        dbHelper.dropTblUser(database);
    }


    public void insertNew() {
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USER_USER_ID, "1");
            contentValues.put(DBHelper.NAME, "aaa");

            database.insert(DBHelper.TBL_APP, null, contentValues);

            database.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public int getCount() {
        String selectQuery = "SELECT  * FROM " + DBHelper.TBL_APP;
        Cursor cursor = database.rawQuery(selectQuery, null);

        int count = 0;

        database.beginTransaction();
        try {
            if (cursor.moveToFirst()) {
                do {
                    count++;
                } while (cursor.moveToNext());

                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
        }

        cursor.close();
        return count;
    }

//
}
