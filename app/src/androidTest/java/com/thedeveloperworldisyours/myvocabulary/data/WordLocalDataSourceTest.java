package com.thedeveloperworldisyours.myvocabulary.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.local.WordsLocalDataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Created by javierg on 25/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class WordLocalDataSourceTest {

    private final static String TITLE = "title";

    private final static String TITLE2 = "title2";

    private final static String TITLE3 = "title3";

    private WordsLocalDataSource mLocalDataSource;

    private final Word mNewWord = new Word(TITLE, "");
    private final Word mNewWord2 = new Word(TITLE2, "");
    private final Word mNewWord3 = new Word(TITLE3, "");

    @Mock
    WordsDataSource.GetWordCallback mCallback;
    @Mock
    WordsDataSource.GetWordCallback mCallback2;
    @Mock
    WordsDataSource.GetWordCallback mCallback3;
    @Mock
    WordsDataSource.LoadWordsCallback mLoadCallback;

    @Before
    public void setup() {

        mLocalDataSource = WordsLocalDataSource.getInstance(InstrumentationRegistry.getTargetContext());
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllWords();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveWord_retrievesWord() {
        final Word newWord = new Word(TITLE, "");

        mLocalDataSource.saveWord(newWord);

        mLocalDataSource.getWord(newWord.getId(), new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(Word word) {
                assertThat(word, is(newWord));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void learnedTest_retrievedWordIsLearned() {

        mLocalDataSource.saveWord(mNewWord);
        mLocalDataSource.learnedWord(mNewWord);

        mLocalDataSource.getWord(mNewWord.getId(), new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(Word word) {
                assertThat(word, is(mNewWord));
                assertTrue(word.isLearned());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Test
    public void activiteWord_retrievedWordIsActive() {

        mLocalDataSource.saveWord(mNewWord);
        mLocalDataSource.learnedWord(mNewWord);
        mLocalDataSource.activateWord(mNewWord);

        mLocalDataSource.getWord(mNewWord.getId(), mCallback);

        verify(mCallback, never()).onDataNotAvailable();
        verify(mCallback).onWordLoaded(mNewWord);

        assertThat(mNewWord.isLearned(), is(false));

    }

    @Test
    public void clearLearnedWord_wordNotRetrieve() {


        mLocalDataSource.saveWord(mNewWord);
        mLocalDataSource.learnedWord(mNewWord);

        mLocalDataSource.saveWord(mNewWord2);
        mLocalDataSource.learnedWord(mNewWord2);

        mLocalDataSource.saveWord(mNewWord3);
        mLocalDataSource.learnedWord(mNewWord3);

        mLocalDataSource.clearLearnedWords();

        mLocalDataSource.getWord(mNewWord.getId(), mCallback);
        verify(mCallback, never()).onWordLoaded(mNewWord);
        verify(mCallback).onDataNotAvailable();

        mLocalDataSource.getWord(mNewWord2.getId(), mCallback2);
        verify(mCallback, never()).onWordLoaded(mNewWord2);
        verify(mCallback).onDataNotAvailable();

        mLocalDataSource.getWord(mNewWord3.getId(), mCallback3);
        verify(mCallback, never()).onWordLoaded(mNewWord3);
        verify(mCallback).onDataNotAvailable();
    }

    @Test
    public void deleteAllWords_emptyListOfRetrievedWord() {

        mLocalDataSource.saveWord(mNewWord);

        mLocalDataSource.deleteAllWords();

        mLocalDataSource.getWords(mLoadCallback);

        verify(mLoadCallback).onDataNotAvailable();
        verify(mLoadCallback, never()).onWordsLoaded(anyList());
    }

    @Test
    public void getWords_retrieveSavedWord() {
        mLocalDataSource.saveWord(mNewWord);
        mLocalDataSource.learnedWord(mNewWord);

        mLocalDataSource.saveWord(mNewWord2);
        mLocalDataSource.learnedWord(mNewWord2);

        mLocalDataSource.saveWord(mNewWord3);
        mLocalDataSource.learnedWord(mNewWord3);

        mLocalDataSource.getWords(mLoadCallback);

        verify(mLoadCallback, never()).onDataNotAvailable();

        mLocalDataSource.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Word> words) {
                assertNotNull(words);
                assertTrue(words.size() >= 3);

                boolean newTask1IdFound = false;
                boolean newTask2IdFound = false;
                boolean newTask3IdFound = false;

                for (Word word : words) {
                    if (word.getId().equals(mNewWord.getId())) {
                        newTask1IdFound = true;
                    }
                    if (word.getId().equals(mNewWord.getId())) {
                        newTask2IdFound = true;
                    }
                    if (word.getId().equals(mNewWord3.getId())) {
                        newTask3IdFound = true;
                    }
                }
                assertTrue(newTask1IdFound);
                assertTrue(newTask2IdFound);
                assertTrue(newTask3IdFound);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

}
