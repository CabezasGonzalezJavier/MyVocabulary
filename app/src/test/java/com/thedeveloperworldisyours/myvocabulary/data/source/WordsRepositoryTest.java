package com.thedeveloperworldisyours.myvocabulary.data.source;

import android.content.Context;

import com.google.common.collect.Lists;
import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.local.WordsLocalDataSource;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created by javierg on 26/10/2016.
 */

public class WordsRepositoryTest {

    private final static String WORD_TITTLE = "title";

    private final static String WORD_TITLE2 = "title2";

    private final static String WORD_TITLE3 = "title3";

    private static List<Word> sWords = Lists.newArrayList(new Word("Title1", "Description1"),
            new Word("Title2", "Description2"));

    private WordsRepository mWordsRepository;

    @Mock
    private WordsDataSource mWordsRemoteDataSource;

    @Mock
    private WordsDataSource mWordsLocalDataSource;

    @Mock
    private Context mContext;

    @Mock
    private WordsDataSource.GetWordCallback mGetWordCallback;

    @Mock
    private WordsDataSource.LoadWordsCallback mLoadWordsCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<WordsDataSource.LoadWordsCallback> mWordsCallbackCaptor;

    @Captor
    private ArgumentCaptor<WordsDataSource.GetWordCallback> mWordCallbackCaptor;

    @Before
    public void setupWordsRepository() {
        MockitoAnnotations.initMocks(this);

        mWordsRepository = WordsRepository.getInstance( mWordsRemoteDataSource, mWordsLocalDataSource);

    }

    @After
    public void destroyRepositoryInstance() {
        WordsRepository.destroyInstance();
    }

    @Test
    public void getWords_RepositoryCachesAfterFirstApiCall() {

    }

    @Test
    public void getWords_requestsAllTasksFromLocalDataSource() {
        mWordsRepository.getWords(mLoadWordsCallback);

        verify(mWordsLocalDataSource).getWords(any(WordsDataSource.LoadWordsCallback.class));
    }

    @Test
    public void getWord_savesWordToServiceAPI(){
        Word newWord = new Word(WORD_TITTLE, "Some Word Description");

        mWordsRepository.saveWord(newWord);

        verify(mWordsRemoteDataSource).saveWord(newWord);
        verify(mWordsLocalDataSource).saveWord(newWord);
        assertThat(mWordsRepository.mCachedWords.size(), is(1));
    }

    @Test
    public void learnedWord_learnedWordToServiceAPIUpdatesCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description");
        mWordsRepository.saveWord(newWord);

        mWordsRepository.learnedWord(newWord);

        verify(mWordsRemoteDataSource).saveWord(newWord);
        verify(mWordsLocalDataSource).saveWord(newWord);
        assertThat(mWordsRepository.mCachedWords.size(), is(1));
        assertFalse(mWordsRepository.mCachedWords.get(newWord.getId()).isActive());
    }

    @Test
    public void learnWordId_completesWordToServiceAPIUpdatesCache() {

        Word newWord = new Word(WORD_TITTLE, "Wdrd description");

        mWordsRepository.saveWord(newWord);
        mWordsRepository.learnedWord(newWord.getId());

        verify(mWordsRemoteDataSource).saveWord(newWord);
        verify(mWordsLocalDataSource).saveWord(newWord);
        assertThat(mWordsRepository.mCachedWords.size(), is(1));
        assertFalse(mWordsRepository.mCachedWords.get(newWord.getId()).isActive());
    }

    @Test
    public void activateWord_activatesWordToServiceAPIUpdatesCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description");

        mWordsRepository.saveWord(newWord);
        mWordsRepository.activateWord(newWord);

        verify(mWordsRemoteDataSource).saveWord(newWord);
        verify(mWordsRemoteDataSource).activateWord(newWord);
        assertThat(mWordsRepository.mCachedWords.size(), is(1));
        assertTrue(mWordsRepository.mCachedWords.get(newWord.getId()).isActive());
    }

    @Test
    public void activeWordId_activatesWordToServiceAPIUpdatesCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description");

        mWordsRepository.saveWord(newWord);
        mWordsRepository.activateWord(newWord.getId());
        verify(mWordsRemoteDataSource).saveWord(newWord);
        verify(mWordsRemoteDataSource).activateWord(newWord);
        assertThat(mWordsRepository.mCachedWords.size(), is(1));
        assertTrue(mWordsRepository.mCachedWords.get(newWord.getId()).isActive());
    }

    @Test
    public void getWord_requestsSingleWordFromLocalDataSource() {
        mWordsRepository.getWord(WORD_TITTLE, mGetWordCallback);

        verify(mWordsLocalDataSource).getWord(eq(WORD_TITTLE), any(WordsLocalDataSource.GetWordCallback.class));
    }

    @Test
    public void deleteCompletedWord_deleteCompletedTasksToServiceAPIUpdatesCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description", true);
        mWordsRepository.saveWord(newWord);
        Word newWord2 = new Word(WORD_TITLE2, "Word description");
        mWordsRepository.saveWord(newWord2);
        Word newWord3 = new Word(WORD_TITLE3, "Word description", true);
        mWordsRepository.saveWord(newWord3);

        mWordsRepository.clearLearnedWords();

        verify(mWordsRemoteDataSource).clearLearnedWords();
        verify(mWordsLocalDataSource).clearLearnedWords();

        assertThat(mWordsRepository.mCachedWords.size(), is(1));
        assertTrue(mWordsRepository.mCachedWords.get(newWord2.getId()).isActive());
        assertThat(mWordsRepository.mCachedWords.get(newWord2.getId()).getTitle(), is(WORD_TITLE2));
    }

    @Test
    public void deleteAllWords_deleteWordsToServiceAPIUpdatesCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description", true);
        mWordsRepository.saveWord(newWord);
        Word newWord2 = new Word(WORD_TITLE2, "Word description");
        mWordsRepository.saveWord(newWord2);
        Word newWord3 = new Word(WORD_TITLE3, "Word description", true);
        mWordsRepository.saveWord(newWord3);

        mWordsRepository.deleteAllWords();

        verify(mWordsLocalDataSource).deleteAllWords();
        verify(mWordsRemoteDataSource).deleteAllWords();
        assertThat(mWordsRepository.mCachedWords.size(), is(0));
    }

    @Test
    public void deleteTask_deleteTaskToServiceAPIRemovedFromCache() {
        Word newWord = new Word(WORD_TITTLE, "Word description", true);
        mWordsRepository.saveWord(newWord);
        assertTrue(mWordsRepository.mCachedWords.get(newWord.getId()).isLearned());

        mWordsRepository.deleteWord(newWord.getId());

        verify(mWordsLocalDataSource).deleteWord(newWord.getId());
        verify(mWordsRemoteDataSource).deleteWord(newWord.getId());
        assertThat(mWordsRepository.mCachedWords.size(), is(0));

        assertFalse(mWordsRepository.mCachedWords.containsKey(newWord.getId()));
    }

    @Test
    public void getWordWithDirtyCache_wordAreRetrievedFromRemote() {
        mWordsRepository.refreshWords();
        mWordsRepository.getWords(mLoadWordsCallback);

        setWordsAvailable(mWordsRemoteDataSource, sWords);

        verify(mWordsLocalDataSource, never()).getWords(mLoadWordsCallback);
        verify(mLoadWordsCallback).onWordsLoaded(sWords);

    }

    @Test
    public void getWordWithLocalDataSourcedSourcesUnavailable_WordsAreRetrievedFromRemote() {

        mWordsRepository.getWords(mLoadWordsCallback);

        setWordsNotAvailable(mWordsLocalDataSource);

        setWordsAvailable(mWordsRemoteDataSource, sWords);

        verify(mLoadWordsCallback).onWordsLoaded(sWords);

    }

    @Test
    public void getWordsWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        mWordsRepository.getWords(mLoadWordsCallback);

        setWordsNotAvailable(mWordsLocalDataSource);
        setWordsNotAvailable(mWordsRemoteDataSource);

        verify(mLoadWordsCallback).onDataNotAvailable();
    }

    @Test
    public void getWordWithBothDataSourcesUnavailable_firesOnDataUnavailable() {
        final String wordId = "123";

        mWordsRepository.getWord(wordId, mGetWordCallback);

        setWordNotAvailable(mWordsLocalDataSource, wordId);

        setWordNotAvailable(mWordsRemoteDataSource, wordId);

        verify(mGetWordCallback).onDataNotAvailable();
    }

    @Test
    public void getWord_refreshLocalDataSource() {
        mWordsRepository.refreshWords();

        mWordsRepository.getWords(mLoadWordsCallback);

        setWordsAvailable(mWordsRemoteDataSource, sWords);

        verify(mWordsLocalDataSource, times(sWords.size())).saveWord(any(Word.class));
    }

//    @Test
//    public void twoWordsLoadCallsToRepository(WordsDataSource.LoadWordsCallback callback) {
//        mWordsRepository.getWords(callback);
//
//        verify(mWordsLocalDataSource).getWords(mWordsCallbackCaptor.capture());
//
//        mWordsCallbackCaptor.getValue().onDataNotAvailable();
//
//        verify(mWordsRemoteDataSource).getWords(mWordsCallbackCaptor.capture());
//
//        mWordsCallbackCaptor.getValue().onWordsLoaded(sWords);
//
//        mWordsRepository.getWords(callback);
//    }

    private void setWordsNotAvailable(WordsDataSource dataSource) {
        verify(dataSource).getWords(mWordsCallbackCaptor.capture());
        mWordsCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setWordsAvailable(WordsDataSource dataSource, List<Word> words) {
        verify(dataSource).getWords(mWordsCallbackCaptor.capture());
        mWordsCallbackCaptor.getValue().onWordsLoaded(words);
    }

    private void setWordNotAvailable(WordsDataSource dataSource, String wordId){
        verify(dataSource).getWord(eq(wordId), mWordCallbackCaptor.capture());
        mWordCallbackCaptor.getValue().onDataNotAvailable();
    }

}
