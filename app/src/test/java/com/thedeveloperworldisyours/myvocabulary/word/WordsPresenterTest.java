package com.thedeveloperworldisyours.myvocabulary.word;

import com.google.common.collect.Lists;
import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;
import com.thedeveloperworldisyours.myvocabulary.words.WordsContract;
import com.thedeveloperworldisyours.myvocabulary.words.WordsFilterType;
import com.thedeveloperworldisyours.myvocabulary.words.WordsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/11/2016.
 */

public class WordsPresenterTest {

    @Mock
    private WordsRepository mWordsRepository;

    @Mock
    private WordsContract.View mView;

    private WordsPresenter mWordsPresenter;

    private Word mTestWord;

    private static List<Word> WORDS;

    @Captor
    private ArgumentCaptor<WordsDataSource.LoadWordsCallback> mLoadWordCallbackArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mView.isActive()).thenReturn(true);
        mWordsPresenter = new WordsPresenter(mWordsRepository, mView);
        mTestWord = new Word("title", "description");
        WORDS = Lists.newArrayList(new Word("Title1", "Description1", true), new Word("Title2", "Description2"), new Word("Title3", "Description3", true));
    }

    @Test
    public void loadAllWordFromRepositoryAndLoadIntoView() {
        mWordsPresenter.setFiltering(WordsFilterType.ALL_WORDS);
        mWordsPresenter.loadWords(true);

        // Callback is captured and invoked with stubbed words
        verify(mWordsRepository).getWords(mLoadWordCallbackArgumentCaptor.capture());
        mLoadWordCallbackArgumentCaptor.getValue().onWordsLoaded(WORDS);

        //Then progress indicator is shown
        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);

        inOrder.verify(mView).setLoadingIndicator(false);
        ArgumentCaptor<List> showWordArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mView).showWords(showWordArgumentCaptor.capture());
        assertTrue(showWordArgumentCaptor.getValue().size()==3);

    }

    @Test
    public void loadActiveRepository() {
        mWordsPresenter.setFiltering(WordsFilterType.ACTIVE_WORDS);
        mWordsPresenter.loadWords(true);

        verify(mWordsRepository).getWords(mLoadWordCallbackArgumentCaptor.capture());
        mLoadWordCallbackArgumentCaptor.getValue().onWordsLoaded(WORDS);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);

        inOrder.verify(mView).setLoadingIndicator(false);
        ArgumentCaptor<List> showActiveCapture = ArgumentCaptor.forClass(List.class);
        verify(mView).showWords(showActiveCapture.capture());
        assertTrue(mLoadWordCallbackArgumentCaptor.getAllValues().size()==1);
    }

    @Test
    public void loadLearnedRepository() {
        mWordsPresenter.setFiltering(WordsFilterType.LEARNED_WORDS);
        mWordsPresenter.loadWords(true);

        verify(mWordsRepository).getWords(mLoadWordCallbackArgumentCaptor.capture());
        mLoadWordCallbackArgumentCaptor.getValue().onWordsLoaded(WORDS);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);

        inOrder.verify(mView).setLoadingIndicator(false);
        ArgumentCaptor<List> showLearnedCapture = ArgumentCaptor.forClass(List.class);
        verify(mView).showWords(showLearnedCapture.capture());
        assertTrue(showLearnedCapture.getValue().size()==2);
    }

    @Test
    public void clickOnWordTest() {
        //Given a sttubbed as compled
        Word testWord = new Word("title", "description");

        //When open word details is requested
        mWordsPresenter.openWordDetails(testWord);

        //Then word detail UI is shown
        verify(mView).showWordDetailsUi(any(String.class));
    }

    @Test
    public void clearLearnedTest() {
        mWordsPresenter.clearLearnedWord();
        verify(mWordsRepository).clearLearnedWords();
        verify(mView).showLearnedWordsCleared();
    }


    @Test
    public void learnWord() {
        mWordsPresenter.loadWords(true);
        mWordsPresenter.learnWord(mTestWord);
        verify(mWordsRepository).learnedWord(mTestWord);
        verify(mView).showWordMarkedComplete();
    }

    @Test
    public void activeWordTest() {
        mWordsPresenter.loadWords(true);
        mWordsPresenter.activateWord(mTestWord);
        verify(mWordsRepository).activateWord(mTestWord);
        verify(mView).showWordMarkedActive();
    }

    @Test
    public void unavailableWordTest() {
        mWordsPresenter.setFiltering(WordsFilterType.ALL_WORDS);
        mWordsPresenter.loadWords(true);

        verify(mWordsRepository).getWords(mLoadWordCallbackArgumentCaptor.capture());
        mLoadWordCallbackArgumentCaptor.getValue().onDataNotAvailable();


        verify(mView).showLoadingWordsError();

    }
}
