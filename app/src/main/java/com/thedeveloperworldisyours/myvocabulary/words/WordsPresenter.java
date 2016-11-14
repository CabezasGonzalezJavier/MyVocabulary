package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.annotation.NonNull;
import android.view.View;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 31/10/2016.
 */

public class WordsPresenter implements WordsContract.Presenter {

    private final WordsRepository mWordsRepository;

    private final WordsContract.View mWordsView;

    private WordsFilterType mCurrentFiltering = WordsFilterType.ALL_WORDS;

    private boolean mFirstLoad = true;

    public WordsPresenter(@NonNull WordsRepository wordsRepository, @NonNull WordsContract.View wordsView) {
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
        //if a word was successfully added, show snackbar
//        if (AddEd)
//        mWordsView.showSuccessfullySavedMessage();
    }

    @Override
    public void loadWords(boolean forceUpdate) {
        loadWords(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void addNewWord() {
        mWordsView.showAddWord();
    }

    @Override
    public void openWordDetails(@NonNull Word requestedWord) {
        checkNotNull(requestedWord, "requestedWord can not be null!");
        mWordsView.showWordDetailsUi(requestedWord.getId());
    }

    @Override
    public void learnWord(@NonNull Word completedWord) {
        checkNotNull(completedWord, "completedWord");
        mWordsRepository.learnedWord(completedWord);
        mWordsView.showWordMarkedComplete();
        loadWords(false, false);

    }

    @Override
    public void activateWord(@NonNull Word activeWord) {
        checkNotNull(activeWord, "activeWord can not be null!");
        mWordsRepository.activateWord(activeWord);
        mWordsView.showWordMarkedActive();
        loadWords(false, false);
    }

    @Override
    public void clearLearnedWord() {
        mWordsRepository.clearLearnedWords();
        mWordsView.showLearnedWordsCleared();
        loadWords(false, false);
    }

    @Override
    public void setFiltering(WordsFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public WordsFilterType getFiltering() {
        return mCurrentFiltering;
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

        mWordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Word> words) {
                List<Word> wordsToShow = new ArrayList<Word>();

                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }

                for (Word word : words) {
                    switch (mCurrentFiltering) {
                        case ALL_WORDS:
                            wordsToShow.add(word);
                            break;
                        case ACTIVE_WORDS:
                            if (word.isActive()) {
                                wordsToShow.add(word);
                            }
                            break;
                        case LEARNED_WORDS:
                            if (word.isLearned()) {
                                wordsToShow.add(word);
                            }
                            break;
                        default:
                            wordsToShow.add(word);
                            break;
                    }
                }

                if (!mWordsView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mWordsView.setLoadingIndicator(false);
                }

                processWords(wordsToShow);
            }

            @Override
            public void onDataNotAvailable() {

                if (!mWordsView.isActive()) {
                    return;
                }
                mWordsView.showLoadingWordsError();
            }
        });
    }

    private void processWords(List<Word> words) {
        if (words.isEmpty()) {
            processEmptyWords();
        } else {
            mWordsView.showWords(words);
        }
        showFilterLabel();
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ACTIVE_WORDS:
                mWordsView.showActiveFilterLabel();
                break;
            case LEARNED_WORDS:
                mWordsView.showLearnedFilterLabel();
                break;
            default:
                mWordsView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyWords() {
        switch (mCurrentFiltering) {
            case ACTIVE_WORDS:
                mWordsView.showNoActiveWords();
                break;
            case LEARNED_WORDS:
                mWordsView.showNoLearnedWords();
                break;
            default:
                mWordsView.showNoWords();
                break;
        }
    }

}
