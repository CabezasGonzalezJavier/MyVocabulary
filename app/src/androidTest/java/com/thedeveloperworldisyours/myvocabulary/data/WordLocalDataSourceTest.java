package com.thedeveloperworldisyours.myvocabulary.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.thedeveloperworldisyours.myvocabulary.data.source.local.WordsLocalDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;


/**
 * Created by javierg on 25/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class WordLocalDataSourceTest {

    private final static String TITLE = "title";

    private final static String TITLE2 = "title2";

    private final static String TITLE3 = "title3";

    private WordsLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        mLocalDataSource = WordsLocalDataSource.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() { mLocalDataSource.deleteAllWords();}

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveTask_retrievesTask() {
        final Word newWord = new Word(TITLE, "");

        mLocalDataSource.saveWord(newWord);

        mLocalDataSource.saveWord(newWord);
    }

}
