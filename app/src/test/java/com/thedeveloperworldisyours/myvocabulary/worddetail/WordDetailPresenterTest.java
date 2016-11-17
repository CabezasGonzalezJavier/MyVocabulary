package com.thedeveloperworldisyours.myvocabulary.worddetail;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 16/11/2016.
 */

public class WordDetailPresenterTest {

    public static final String TITLE_TEST = "title";

    public static final String DESCRIPTION_TEST = "description";

    public static final String INVALID_WORD_ID = "";

    public static final Word ACTIVE_WORD = new Word(TITLE_TEST, DESCRIPTION_TEST);

    public static final Word LEARNED_WORD = new Word(TITLE_TEST, DESCRIPTION_TEST, true);

    WordDetailPresenter mPresenter;

    @Mock
    private WordDetailContract.View mWordDetailView;

    @Mock
    private WordsRepository mRepository;

    @Captor
    private ArgumentCaptor<WordsDataSource.GetWordCallback> mGetWordCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mWordDetailView.isActive()).thenReturn(true);
    }

    @Test
    public void getActiveWordFromRepositoryAndLoadIntoView(){

        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, ACTIVE_WORD.getId());
        mPresenter.start();

        verify(mRepository).getWord( eq(ACTIVE_WORD.getId()), mGetWordCallbackCaptor.capture());

        InOrder inOrder = inOrder(mWordDetailView);
        inOrder.verify(mWordDetailView).setLoadingIndicator(true);

        mGetWordCallbackCaptor.getValue().onWordLoaded(ACTIVE_WORD);

        inOrder.verify(mWordDetailView).setLoadingIndicator(false);
        verify(mWordDetailView).showTitle(TITLE_TEST);
        verify(mWordDetailView).showDescription(DESCRIPTION_TEST);
        verify(mWordDetailView).showLearnedStatus(false);
    }

    @Test
    public void getLearnedWordFromRepositoryAndLoadIntoView() {
        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, LEARNED_WORD.getId());
        mPresenter.start();

        verify(mRepository).getWord( eq(LEARNED_WORD.getId()), mGetWordCallbackCaptor.capture());

        InOrder inOrder = inOrder(mWordDetailView);
        inOrder.verify(mWordDetailView).setLoadingIndicator(true);

        mGetWordCallbackCaptor.getValue().onWordLoaded(LEARNED_WORD);

        inOrder.verify(mWordDetailView).setLoadingIndicator(false);

        verify(mWordDetailView).showTitle(TITLE_TEST);
        verify(mWordDetailView).showDescription(DESCRIPTION_TEST);
        verify(mWordDetailView).showLearnedStatus(true);
    }

    @Test
    public void deleteWord() {
        Word word = new Word(TITLE_TEST, DESCRIPTION_TEST);

        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, word.getId());

        mPresenter.start();

        mPresenter.deleteWord();

        verify(mRepository).deleteWord(word.getId());
        verify(mWordDetailView).showWordDeleted();
    }

    @Test
    public void learnedWordTest() {

        Word word = new Word(TITLE_TEST, DESCRIPTION_TEST);

        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, word.getId());

        mPresenter.start();

        mPresenter.learnedWord();

        verify(mRepository).learnedWord(word.getId());
        verify(mWordDetailView).showWordMarkedLearned();
    }

    @Test
    public void activateWordTest() {

        Word word = new Word(TITLE_TEST, DESCRIPTION_TEST, true);

        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, word.getId());

        mPresenter.activateWord();

        verify(mRepository).activateWord(word.getId());
        verify(mWordDetailView).showWordMarkedActive();

    }

    @Test
    public void editWordShowSuccessfulTest() {
//        When the edit of an ACTIVE_WORD is request
        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, ACTIVE_WORD.getId());
        mPresenter.start();
        mPresenter.editWord();

        verify(mWordDetailView).showEditWord(ACTIVE_WORD.getId());
    }

    @Test
    public void invalidWordIsNotShownWhenEditing() {
        mPresenter = new WordDetailPresenter(mRepository, mWordDetailView, INVALID_WORD_ID);

        mPresenter.editWord();

        verify(mWordDetailView, never()).showEditWord(INVALID_WORD_ID);
        verify(mWordDetailView).showMissingWord();
    }
}
