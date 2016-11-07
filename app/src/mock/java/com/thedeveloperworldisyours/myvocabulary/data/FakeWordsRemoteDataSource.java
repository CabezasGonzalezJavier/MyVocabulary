package com.thedeveloperworldisyours.myvocabulary.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.Lists;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by javierg on 07/11/2016.
 */

public class FakeWordsRemoteDataSource implements WordsDataSource {

    private static FakeWordsRemoteDataSource INSTANCE;

    private static final Map<String, Word> WORDS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeWordsRemoteDataSource() {}

    public static FakeWordsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeWordsRemoteDataSource();
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public void addTasks(Word... words) {
        for (Word word : words) {
            WORDS_SERVICE_DATA.put(word.getId(), word);
        }
    }

    @Override
    public void getWords(@NonNull LoadWordsCallback callback) {
        callback.onWordsLoaded(Lists.newArrayList(WORDS_SERVICE_DATA.values()));
    }

    @Override
    public void getWord(@NonNull String wordId, @NonNull GetWordCallback callback) {

        Word task = WORDS_SERVICE_DATA.get(wordId);
        callback.onWordLoaded(task);
    }

    @Override
    public void saveWord(@NonNull Word word) {

        WORDS_SERVICE_DATA.put(word.getId(), word);
    }

    @Override
    public void learnedWord(@NonNull Word word) {

        Word completedWord = new Word(word.getTitle(), word.getDescription(), word.getId(), true);
        WORDS_SERVICE_DATA.put(word.getId(), completedWord);
    }

    @Override
    public void learnedWord(@NonNull String wordId) {

    }

    @Override
    public void activateWord(@NonNull Word word) {

        Word activeTask = new Word(word.getTitle(), word.getDescription(), word.getId());
        WORDS_SERVICE_DATA.put(word.getId(), activeTask);
    }

    @Override
    public void activateWord(@NonNull String wordId) {

    }

    @Override
    public void clearLearnedWords() {
        Iterator<Map.Entry<String, Word>> it = WORDS_SERVICE_DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Word> entry = it.next();
            if (entry.getValue().isLearned()) {
                it.remove();
            }
        }
    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteAllWords() {
        WORDS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteWord(@NonNull String wordId) {
        WORDS_SERVICE_DATA.remove(wordId);
    }
}
