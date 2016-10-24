package com.thedeveloperworldisyours.myvocabulary.data.source;

/**
 * Created by javierg on 24/10/2016.
 */

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.Word;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load words from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class WordsRepository implements WordsDataSource {

    private static WordsRepository INSTANCE = null;

    private final WordsDataSource mWordsRemoteDataSource;

    private final WordsDataSource mWordsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Word> mCachedWords;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    public WordsRepository(@NonNull WordsDataSource mWordsRemoteDataSource, @NonNull WordsDataSource mWordsLocalDataSource) {
        this.mWordsRemoteDataSource = checkNotNull(mWordsRemoteDataSource);
        this.mWordsLocalDataSource = checkNotNull(mWordsLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param wordsRemoteDataSource the backend data source
     * @param wordsLocalDataSource  the device storage data source
     * @return the {@link WordsRepository} instance
     */
    public static WordsRepository getInstance(WordsDataSource wordsRemoteDataSource,
                                              WordsDataSource wordsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WordsRepository(wordsRemoteDataSource, wordsLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(WordsDataSource, WordsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadWordsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedWords != null && !mCacheIsDirty) {
            callback.onWordsLoaded(new ArrayList<>(mCachedWords.values()));
            return;
        }

        if (mCacheIsDirty) {
            //if the cache is dirty we need to fetch new data from the network.
            getWordsFromRemoteDataSource(callback);
        } else {
            mWordsLocalDataSource.getWords(new LoadWordsCallback() {
                @Override
                public void onWordsLoaded(List<Word> words) {
                    refreshCache(words);
                    callback.onWordsLoaded(new ArrayList<>(mCachedWords.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getWordsFromRemoteDataSource(callback);
                }
            });
        }

    }

    @Override
    public void getWord(@NonNull String wordId, @NonNull GetWordCallback callback) {

    }

    @Override
    public void saveWord(@NonNull Word word) {

    }

    @Override
    public void learnedWord(@NonNull Word word) {

    }

    @Override
    public void learnedWord(@NonNull String wordId) {

    }

    @Override
    public void activateWord(@NonNull Word word) {

    }

    @Override
    public void activateWord(@NonNull String wordId) {

    }

    @Override
    public void clearLearnedWords() {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteAllWords() {

    }

    @Override
    public void deleteWord(@NonNull String wordId) {

    }

    private void getWordsFromRemoteDataSource(@NonNull final LoadWordsCallback callback) {
        mWordsRemoteDataSource.getWords(new LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Word> words) {
                refreshCache(words);
                refreshLocalDataSource(words);
                callback.onWordsLoaded(new ArrayList<Word>(mCachedWords.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();

            }
        });

    }

    private void refreshCache(List<Word> words) {
        if (mCachedWords == null) {
            mCachedWords = new LinkedHashMap<>();
        }
        mCachedWords.clear();
        for (Word word : words) {
            mCachedWords.put(word.getId(), word);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Word> words) {
        mWordsLocalDataSource.deleteAllWords();
        for (Word word : words) {
            mWordsLocalDataSource.saveWord(word);
        }
    }
}
