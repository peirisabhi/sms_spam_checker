package com.abhi.sms_spam_checker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "spam.db";
    private static final int DB_VERSION = 1;

    public static final String TBL_USER = "user";
    public static final String TBL_APP = "app";
    public static final String TBL_WORDS = "spam_words";



    public static final String USER_USER_ID = "user_id";
    public static final String USER_EMAIL = "email";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String IMAGE = "image";
    public static final String LAT = "lat";
    public static final String LAN = "lan";
    public static final String ACTIVE = "isActive";
    public static final String ONLINE = "isOnline";
    public static final String BLOCK = "isBlock";
    public static final String REGISTERD = "registerdAt";
    public static final String DOCUMENTID = "documentId";


    public static final String SPAM_WORD = "word";


    private static final String USER_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_USER +
            "(" + USER_USER_ID + " TEXT NOT NULL, "
            + USER_EMAIL + " TEXT, "
            + MOBILE + " TEXT, "
            + IMAGE + " TEXT, "
            + LAT + " REAL, "
            + LAN + " REAL, "
            + ACTIVE + " INTEGER, "
            + ONLINE + " INTEGER, "
            + BLOCK + " INTEGER, "
            + REGISTERD + " TEXT, "
            + DOCUMENTID + " TEXT,"
            + NAME + " TEXT);";


    private static final String APP_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_APP +
            "(" + USER_USER_ID + " TEXT NOT NULL, "
            + NAME + " TEXT);";


    private static final String SPAM_WORD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_WORDS +
            "(" + SPAM_WORD + " TEXT NOT NULL);";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE_TABLE);
        db.execSQL(APP_CREATE_TABLE);
        db.execSQL(SPAM_WORD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_APP);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WORDS);
        onCreate(db);
    }

    public void dropTblUser(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        onCreate(db);
    }
}
