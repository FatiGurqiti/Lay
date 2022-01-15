package com.example.fibuv2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "LayDB";
    private static final String TABLE_ACCOUNTS = "account";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST = "FirstTime";
    private static final String KEY_LOGGED_IN = "LoggedIn";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance

       // context.deleteDatabase("LayDB"); // deletes database
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_FIRST + " INTEGER ,"
                + KEY_LOGGED_IN + " INTEGER " + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);


    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new account
    public void addaccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, account.getIsFirstTime()); // account Name
        values.put(KEY_LOGGED_IN, account.getIsLoggedIn()); // account Phone

        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single account
    Account getaccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID,
                        KEY_FIRST, KEY_LOGGED_IN }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Account account = new Account(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        // return account
        return account;
    }

    // code to get all accounts in a list view
    public List<Account> getAllaccounts() {
        List<Account> accountList = new ArrayList<Account>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setIsFirstTime(Integer.parseInt(cursor.getString(1)));
                account.setIsLoggedIn (Integer.parseInt(cursor.getString(2)));
                // Adding account to list
                accountList.add(account);
            } while (cursor.moveToNext());
        }

        // return account list
        return accountList;
    }

    // code to update the single account
    public int updateaccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, account.getIsFirstTime());
        values.put(KEY_LOGGED_IN, account.getIsLoggedIn());

        // updating row
        return db.update(TABLE_ACCOUNTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(account.getId()) });
    }

    // Deleting single account
    public void deleteaccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, KEY_ID + " = ?",
                new String[] { String.valueOf(account.getId()) });
        db.close();
    }

    // Getting accounts Count
    public int getaccountsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }
    // Getting if it's the users first time
    public boolean getIsFirtsTime() {
        String countQuery = "SELECT FirstTime FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        Log.d("FirstTimeResult", String.valueOf(cursor.moveToFirst()));

        if(String.valueOf(cursor.moveToFirst()).equals("true"))
        {
            return true;
        }
        else
            return false;

    }

    // Getting if the user logged in
    public int getIsLoggedIn() {
        String countQuery = "SELECT LoggedIn FROM " + TABLE_ACCOUNTS + " WHERE id = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        Log.d("LoggedInResult", String.valueOf(cursor.getColumnIndex("LoggedIn")));

        return Integer.parseInt(String.valueOf(cursor.getColumnIndex("LoggedIn")));




    }

}