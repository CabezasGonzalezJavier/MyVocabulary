package com.thedeveloperworldisyours.myvocabulary.addedithword;

import com.thedeveloperworldisyours.myvocabulary.addeditword.AddEditWordContract;
import com.thedeveloperworldisyours.myvocabulary.addeditword.AddEditWordPresenter;
import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 09/11/2016.
 */

public class AddEditWordPresenterTest {
    @Mock
    private WordsRepository mWordsRepository;

    @Mock
    private AddEditWordContract.View mAddEditView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<WordsDataSource.GetWordCallback> mGetWordCallbackArgumentCaptor;

    private String mWordId;

    private AddEditWordPresenter mAddEditWordPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mWordId = "wordId";
        when(mAddEditView.isActive()).thenReturn(true);
    }

    @Test
    public void saveNewWordToRepositoryWithShowSuccessful() {
        //Get reference to the class under test
        mAddEditWordPresenter = new AddEditWordPresenter(mWordId, mWordsRepository, mAddEditView);

        mAddEditWordPresenter.saveWord("title", "description");

        //Then a word is saved in the repository and the view
        verify(mWordsRepository).saveWord(any(Word.class));
        verify(mAddEditView).showWordsList();

    }

    @Test
    public void saveNewWordToRepositoryWithShowError() {

        mAddEditWordPresenter = new AddEditWordPresenter(null, mWordsRepository, mAddEditView);

        mAddEditWordPresenter.saveWord("", "");

        verify(mAddEditView).showEmptyWordError();
    }

    @Test
    public void populateWordCallsRepository() {

        Word testWord = new Word("title", "description");

        mAddEditWordPresenter = new AddEditWordPresenter(testWord.getId(), mWordsRepository, mAddEditView);

        mAddEditWordPresenter.populateWord();

        //Then the word repository is queried and the view updated
        verify(mWordsRepository).getWord(eq(testWord.getId()), mGetWordCallbackArgumentCaptor.capture());


        //Simulate callback
        mGetWordCallbackArgumentCaptor.getValue().onWordLoaded(testWord);

        verify(mAddEditView).setTitle(testWord.getTitle());
        verify(mAddEditView).setDescription(testWord.getDescription());

    }
}
