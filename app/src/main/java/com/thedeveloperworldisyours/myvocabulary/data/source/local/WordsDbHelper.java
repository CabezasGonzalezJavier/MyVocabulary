package com.thedeveloperworldisyours.myvocabulary.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javierg on 24/10/2016.
 */

public class WordsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Words.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordsPersistenceContract.WordEntry.TABLE_NAME + " (" +
                    WordsPersistenceContract.WordEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED + BOOLEAN_TYPE +
                    " )";

    public WordsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
