package com.thedeveloperworldisyours.myvocabulary.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 24/10/2016.
 */
@Singleton
public class WordsLocalDataSource implements WordsDataSource {

    private static WordsLocalDataSource sInstance;

    private WordsDbHelper mDbHelper;

    // Prevent direct instantiation.
    public WordsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new WordsDbHelper(context);
    }

    public static WordsLocalDataSource getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new WordsLocalDataSource(context);
        }
        return sInstance;
    }

    /**
     * Note: {@link LoadWordsCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getWords(@NonNull LoadWordsCallback callback) {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED
        };
        Cursor cursor = db.query(WordsPersistenceContract.WordEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String itemID = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION));
                boolean learned = cursor.getInt(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED)) == 1;

                Word word = new Word(title, description, itemID, learned);
                words.add(word);
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (words.isEmpty()) {
            // This will be called if the table is new or just empty
            callback.onDataNotAvailable();
        } else {
            callback.onWordsLoaded(words);
        }
    }

    @Override
    public void getWord(@NonNull String wordId, @NonNull GetWordCallback callback) {
        checkNotNull(wordId);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        String[] projection = {
                WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION,
                WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED
        };

        String selection = WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { wordId};

        Cursor cursor = database.query(WordsPersistenceContract.WordEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Word word = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String itemId = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION));
            boolean learned = cursor.getInt(cursor.getColumnIndexOrThrow(WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED))==1;

            word = new Word(title, description, itemId, learned);
        }
        if (cursor != null) {
            cursor.close();
        }
        database.close();
        if (word != null) {
            callback.onWordLoaded(word);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveWord(@NonNull Word word) {
        checkNotNull(word);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID, word.getId());
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_TITLE, word.getTitle());
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_DESCRIPTION, word.getDescription());
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED, word.isLearned());

        database.insert(WordsPersistenceContract.WordEntry.TABLE_NAME, null, values);

        database.close();

    }

    @Override
    public void learnedWord(@NonNull Word word) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED, true);

        String selection = WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs ={ word.getId() };

        database.update(WordsPersistenceContract.WordEntry.TABLE_NAME, values, selection, selectionArgs);

        database.close();

    }

    @Override
    public void learnedWord(@NonNull String wordId) {
        // Not required for the local data source because the {@link WordsRepository} handles
        // converting from a {@code wordId} to a {@link word} using its cached data.
    }

    @Override
    public void activateWord(@NonNull Word word) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED, false);

        String selection = WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { word.getId() };

        database.update(WordsPersistenceContract.WordEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    @Override
    public void activateWord(@NonNull String wordId) {
        // Not required for the local data source because the {@link WordsRepository} handles
        // converting from a {@code wordId} to a {@link word} using its cached data.
    }

    @Override
    public void clearLearnedWords() {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        String selection = WordsPersistenceContract.WordEntry.COLUMN_NAME_LEARNED + " LIKE ?";
        String[] selectionArgs = { "1" };

        database.delete(WordsPersistenceContract.WordEntry.TABLE_NAME, selection, selectionArgs);

        database.close();

    }

    @Override
    public void refreshWords() {
        // Not required because the {@link WordsRepository} handles the logic of refreshing the
        // words from all the available data sources.
    }

    @Override
    public void deleteAllWords() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        database.delete(WordsPersistenceContract.WordEntry.TABLE_NAME, null, null);

        database.close();
    }

    @Override
    public void deleteWord(@NonNull String wordId) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        String selection = WordsPersistenceContract.WordEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { wordId };

        database.delete(WordsPersistenceContract.WordEntry.TABLE_NAME, selection, selectionArgs);

        database.close();
    }
}
