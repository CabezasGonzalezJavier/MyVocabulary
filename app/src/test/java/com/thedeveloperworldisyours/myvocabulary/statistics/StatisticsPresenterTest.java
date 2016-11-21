package com.thedeveloperworldisyours.myvocabulary.statistics;

import com.google.common.collect.Lists;
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

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 18/11/2016.
 */

public class StatisticsPresenterTest {

    private static List<Word> WORDS;

    @Mock
    private WordsRepository mRepository;

    @Mock
    private StatisticsContract.View mView;

    @Captor
    private ArgumentCaptor<WordsDataSource.LoadWordsCallback> mCapture;

    private StatisticsPresenter mPresenter;

    private List<Word> mWordList;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mPresenter = new StatisticsPresenter(mRepository, mView);
        when(mView.isActive()).thenReturn(true);
        WORDS = Lists.newArrayList(new Word("Title1", "Description1"),
                new Word("Title2", "Description2", true), new Word("Title3", "Description3", true));
    }

    @Test
    public void loadStatisticsSuccessfulTest() {
        WORDS.clear();
        mPresenter.start();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setProgressIndicator(true);

        verify(mRepository).getWords(mCapture.capture());
        mCapture.getValue().onWordsLoaded(WORDS);

        verify(mView).setProgressIndicator(false);
        verify(mView).showStatistics(0,0);

    }

    @Test
    public void loadSuccessfulDataTest() {
        mPresenter.start();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setProgressIndicator(true);

        verify(mRepository).getWords(mCapture.capture());
        mCapture.getValue().onWordsLoaded(WORDS);

        verify(mView).setProgressIndicator(false);
        verify(mView).showStatistics(2, 1);

    }

    @Test
    public void loadErrorDataTest() {
        mPresenter.start();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).setProgressIndicator(true);

        verify(mRepository).getWords(mCapture.capture());
        mCapture.getValue().onDataNotAvailable();

        verify(mView).setProgressIndicator(true);
        verify(mView).showLoadingStatisticsError();

    }

}
