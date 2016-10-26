package com.thedeveloperworldisyours.myvocabulary.data.source;

import android.content.Context;

import com.google.common.collect.Lists;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
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

        verify(mWordsRemoteDataSource).getWords(any(WordsDataSource.LoadWordsCallback.class));
    }

}
