package com.abhi.sms_spam_checker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.abhi.sms_spam_checker.model.User;

import java.util.ArrayList;


public class UserStore {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public UserStore(Context context) {
        this.context = context;
    }

    public UserStore open() throws SQLException {
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


    public void insertUser(User user) {
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USER_USER_ID, "1");
            contentValues.put(DBHelper.USER_EMAIL, user.getEmail());
            contentValues.put(DBHelper.MOBILE, user.getMobile());
//            contentValues.put(DBHelper.IMAGE, user.getImage());
            contentValues.put(DBHelper.ACTIVE, user.isStatus());
//            contentValues.put(DBHelper.ONLINE, user.isOnline());
//            contentValues.put(DBHelper.BLOCK, user.isBlock());
//            contentValues.put(DBHelper.REGISTERD, ComLib.getDate(user.getRegisteredAt()));
            contentValues.put(DBHelper.DOCUMENTID, user.getUserDocumentId());
            contentValues.put(DBHelper.NAME, user.getFullName());

            database.insert(DBHelper.TBL_USER, null, contentValues);

            database.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
        }
    }


    public ArrayList<User> getUser(){
        String selectQuery = "SELECT  * FROM " + DBHelper.TBL_USER;
        Cursor cursor = database.rawQuery(selectQuery,null);
        ArrayList<User> users = new ArrayList<>();

        database.beginTransaction();
        try{
            if(cursor.moveToFirst()){
                do{
                    User user = new User();
                    user.setEmail(cursor.getString(1));
                    user.setMobile(cursor.getString(2));
                    user.setFullName(cursor.getString(11));
                    user.setUserDocumentId(cursor.getString(10));

                    System.out.println("cursor.getString(1) " + cursor.getString(1));
                    System.out.println("cursor.getString(2) " + cursor.getString(2));
                    System.out.println("cursor.getString(11) " + cursor.getString(11));
                    System.out.println("cursor.getString(10) " + cursor.getString(10));

                    users.add(user);
                }while (cursor.moveToNext());
            }
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }

        cursor.close();
        return users;
    }

}
