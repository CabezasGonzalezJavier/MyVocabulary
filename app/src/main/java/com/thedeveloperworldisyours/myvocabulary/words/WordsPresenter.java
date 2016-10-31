package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 31/10/2016.
 */

public class WordsPresenter implements WordsContract.Presenter{

    private final WordsRepository mWordsRepository;

    private final WordsContract.View mWordsView;

    private WordsFilterType mCurrentFiltering = WordsFilterType.ALL_WORDS;

    private boolean mFirstLoad = true;

    public WordsPresenter(@NonNull WordsRepository wordsRepository,@NonNull WordsContract.View wordsView) {
        mWordsRepository = checkNotNull(wordsRepository, "WordsRepository cannot be null");
        mWordsView = checkNotNull(wordsView, "WordsView cannot be null!");

        mWordsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadWords(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadWords(boolean forceUpdate) {
        loadWords(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void addNewWord() {

    }

    @Override
    public void openWordDetails(@NonNull Word requestedWord) {

    }

    @Override
    public void completeWord(@NonNull Word completedWord) {

    }

    @Override
    public void activeteWord(@NonNull Word activeWord) {

    }

    @Override
    public void clearCompletedWord() {

    }

    @Override
    public void setFiltering(WordsFilterType requestType) {

    }

    @Override
    public WordsFilterType getFiltering() {
        return null;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadWords(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mWordsView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mWordsRepository.refreshWords();
        }
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice
    }

}
