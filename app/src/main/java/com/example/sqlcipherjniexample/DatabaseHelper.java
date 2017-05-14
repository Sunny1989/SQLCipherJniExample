package com.example.sqlcipherjniexample;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by Sumeet on 09-04-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DICTIONARY_NEW";

    public static final String TABLE_NAME = "FTS";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";

    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + WORD + TEXT_TYPE + "," + DEFINITION + TEXT_TYPE + " )";


    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

   
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
