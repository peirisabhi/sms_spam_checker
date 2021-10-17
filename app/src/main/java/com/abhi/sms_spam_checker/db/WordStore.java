package com.abhi.sms_spam_checker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.abhi.sms_spam_checker.model.SpamWord;
import com.abhi.sms_spam_checker.model.User;

import java.util.ArrayList;

public class WordStore {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public WordStore(Context context) {
        this.context = context;
    }

    public WordStore open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void insertSpamWord(SpamWord spamWord) {
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.SPAM_WORD, spamWord.getWord());


            database.insert(DBHelper.TBL_WORDS, null, contentValues);

            database.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
        }
    }



    public ArrayList<SpamWord> getSpamWords(){
        String selectQuery = "SELECT  * FROM " + DBHelper.TBL_WORDS;
        Cursor cursor = database.rawQuery(selectQuery,null);
        ArrayList<SpamWord> spamWords = new ArrayList<>();

        database.beginTransaction();
        try{
            if(cursor.moveToFirst()){
                do{
                    SpamWord spamWord = new SpamWord();
//                    spamWord.setId(cursor.getInt(1));
                    spamWord.setWord(cursor.getString(1));
//                    spamWord.setStatus(cursor.get(3));


                    spamWords.add(spamWord);
                }while (cursor.moveToNext());
            }
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }

        cursor.close();
        return spamWords;
    }
}
