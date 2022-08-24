package com.fdev.lay.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "LayDB";
    private static final String TABLE_ACCOUNTS = "account";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST = "FirstTime";
    private static final String KEY_LOGGED_IN = "LoggedIn";
    private static final String KEY_LITEMODE = "LiteMode";
    private static final String KEY_SEEN = "ShowSeenContents";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_FIRST + " INTEGER ,"
                + KEY_LOGGED_IN + " INTEGER, "
                + KEY_LITEMODE + " INTEGER, "
                + KEY_SEEN + " INTEGER "
                + ")";
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
        values.put(KEY_FIRST, account.getIsFirstTime()); // account first time
        values.put(KEY_LOGGED_IN, account.getIsLoggedIn()); // account is logged in
        values.put(KEY_LITEMODE, account.getLitemode()); // account is using the lite mode
        values.put(KEY_SEEN, account.getShowSeenContents()); // should show already seen contents

        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single account
    Account getaccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID,
                        KEY_FIRST, KEY_LOGGED_IN, KEY_LITEMODE,KEY_SEEN }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Account account = new Account(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4))

        );
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
                account.setLitemode (Integer.parseInt(cursor.getString(3)));
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
        return String.valueOf(cursor.moveToFirst()).equals("true");

    }

    // Getting if the user logged in
    public boolean getIsLoggedIn() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] {
                        KEY_LOGGED_IN}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(0).equals("1");

    }


    // Getting if lite mode status
    public boolean getIsLiteMode() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] {
                        KEY_LITEMODE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(0).equals("1");

    }
    // Getting if lite mode status
    public boolean getShowSeenContents() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] {
                        KEY_SEEN}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        return cursor.getString(0).equals("1");

    }

    public void setFirstTimeFalse() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, 0);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setLoginTrue() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOGGED_IN, 1);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setLoginFalse() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOGGED_IN, 0);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setLiteModeOn() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LITEMODE, 1);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setLiteModeOff() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LITEMODE, 0);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setShowSeenContentsOn() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SEEN, 1);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

    public void setShowSeenContentsOff() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SEEN, 0);

        // updating row
        db.update(TABLE_ACCOUNTS, values, "id=?", new String[]{"1"});
        db.close();
    }

}