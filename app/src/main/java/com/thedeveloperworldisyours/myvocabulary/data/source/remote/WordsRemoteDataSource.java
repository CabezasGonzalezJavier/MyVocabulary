package com.thedeveloperworldisyours.myvocabulary.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by javierg on 24/10/2016.
 */
@Singleton
public class WordsRemoteDataSource implements WordsDataSource {

    private static WordsRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Word> WORDS_SERVICE_DATA;

    static {
        WORDS_SERVICE_DATA = new LinkedHashMap<>(2);
        addWord("Maintain", "Mantener, encargarse del mantenimiento.");
        addWord("Upset", "molesto, amargado");
    }

    public static WordsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WordsRemoteDataSource();
        }
        return INSTANCE;
    }

    private WordsRemoteDataSource() {}

    private static void addWord(String title, String description) {
        Word newWord = new Word(title, description);
        WORDS_SERVICE_DATA.put(newWord.getId(), newWord);
    }

    @Override
    public void getWords(final @NonNull LoadWordsCallback callback) {

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onWordsLoaded(Lists.newArrayList(WORDS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getWord(@NonNull String wordId, final  @NonNull GetWordCallback callback) {

        final Word word = WORDS_SERVICE_DATA.get(wordId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onWordLoaded(word);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveWord(@NonNull Word word) {
        WORDS_SERVICE_DATA.put(word.getId(), word);
    }

    @Override
    public void learnedWord(@NonNull Word word) {
        Word learnedWord = new Word(word.getTitle(), word.getDescription(), word.getId(), true);
        WORDS_SERVICE_DATA.put(word.getId(), learnedWord);
    }

    @Override
    public void learnedWord(@NonNull String wordId) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void activateWord(@NonNull Word word) {
        Word activeWord = new Word(word.getTitle(), word.getDescription(), word.getId());
        WORDS_SERVICE_DATA.put(word.getId(), activeWord);
//    }
    }

    @Override
    public void activateWord(@NonNull String wordId) {
    // Not required for the remote data source because the {@link WordsRepository} handles
        // converting from a {@code wordId} to a {@link word} using its cached data.
    }

    @Override
    public void clearLearnedWords() {
        Iterator<Map.Entry<String, Word>> iterator = WORDS_SERVICE_DATA.entrySet().iterator();
      while (iterator.hasNext()) {
          Map.Entry<String, Word> entry = iterator.next();
          if (entry.getValue().isLearned()) {
              iterator.remove();
          }
      }
    }

    @Override
    public void refreshWords() {
        // Not required because the {@link WordsRepository} handles the logic of refreshing the
        // words from all the available data sources.
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
