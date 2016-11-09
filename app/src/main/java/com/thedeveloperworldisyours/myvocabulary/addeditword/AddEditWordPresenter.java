package com.thedeveloperworldisyours.myvocabulary.addeditword;

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;

/**
 * Created by javierg on 08/11/2016.
 */

public class AddEditWordPresenter implements AddEditTaskContract.Presenter, WordsDataSource.GetWordCallback {

    @NonNull
    private final WordsDataSource mWordsDataSource;

    @NonNull
    private final AddEditTaskContract.View mAddEditWordView;

    @NonNull
    private String mWordId;

    public AddEditWordPresenter(@NonNull String wordId, @NonNull WordsDataSource wordsDataSource, @NonNull AddEditTaskContract.View addEditWordView) {
        this.mWordId = wordId;
        this.mWordsDataSource = wordsDataSource;
        this.mAddEditWordView = addEditWordView;

        addEditWordView.setPresenter(this);
    }

    @Override
    public void saveWord(String title, String description) {
        if (isNewWord()) {
            createWord(title, description);
        } else {
            updateWord(title, description);
        }
    }

    @Override
    public void populateWord() {
        if (isNewWord()) {
            throw new RuntimeException("poulateWord() was called but word is new.");
        }
        mWordsDataSource.getWord(mWordId,this);

    }

    @Override
    public void start() {
        if (!isNewWord()) {
            populateWord();
        }

    }

    @Override
    public void onWordLoaded(Word word) {
        //the view may not be able to handle UI updates anymore
        if (mAddEditWordView.isActive()) {
            mAddEditWordView.setTitle(word.getTitle());
            mAddEditWordView.setDescription(word.getDescription());
        }
    }

    @Override
    public void onDataNotAvailable() {
        //the view may not be able to handle UI updates anymore
        if (mAddEditWordView.isActive()) {
            mAddEditWordView.showEmptyWordError();
        }
    }

    private boolean isNewWord() {
        return mWordId == null;
    }

    private void createWord(String title, String description) {
        Word newWord = new Word(title, description);
        if (newWord.isEmpty()) {
            mAddEditWordView.showEmptyWordError();
        } else {
            mWordsDataSource.saveWord(newWord);
            mAddEditWordView.showWordsList();
        }
    }

    private void updateWord(String title, String description) {
        if (isNewWord()) {
            throw new RuntimeException("updateWord() was called but word is new.");
        }
        mWordsDataSource.saveWord(new Word(title, description, mWordId));
        mAddEditWordView.showWordsList();
    }
}
