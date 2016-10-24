package com.thedeveloperworldisyours.myvocabulary.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by javierg on 24/10/2016.
 */

public class WordsPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private WordsPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LEARNED = "learned";
    }
}
