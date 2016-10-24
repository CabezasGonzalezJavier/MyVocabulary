package com.thedeveloperworldisyours.myvocabulary.data.source;

/**
 * Created by javierg on 24/10/2016.
 */

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.Word;

import java.util.List;

/**
 * Main entry point for accessing words data.
 */
public interface WordsDataSource {
    interface LoadWordsCallback {

        void onWordsLoaded(List<Word> words);

        void onDataNotAvailable();
    }

    interface GetWordCallback {

        void onWordLoaded(Word word);

        void onDataNotAvailable();
    }

    void getWords(@NonNull LoadWordsCallback callback);

    void getWord(@NonNull String wordId, @NonNull GetWordCallback callback);

    void saveWord(@NonNull Word word);

    void learnedWord(@NonNull Word word);

    void learnedWord(@NonNull String wordId);

    void activateWord(@NonNull Word word);

    void activateWord(@NonNull String wordId);

    void clearLearnedWords();

    void refreshWords();

    void deleteAllWords();

    void deleteWord(@NonNull String wordId);
}
