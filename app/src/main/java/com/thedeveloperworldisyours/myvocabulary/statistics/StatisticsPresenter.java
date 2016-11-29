package com.thedeveloperworldisyours.myvocabulary.statistics;

import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by javierg on 18/11/2016.
 */

public class StatisticsPresenter implements StatisticsContract.Presenter {

    private WordsRepository mWordsRepository;
    private StatisticsContract.View mView;

    @Inject
    StatisticsPresenter(WordsRepository mWordsRepository, StatisticsContract.View mView) {

        this.mWordsRepository = mWordsRepository;
        this.mView = mView;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadStatistics();
    }

    private void loadStatistics() {
        mView.setProgressIndicator(true);

        mWordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Word> words) {
                int activeWord = 0;
                int learnedWord = 0;

                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }

                for (Word word : words) {
                    if (word.isLearned()) {
                        learnedWord += 1;
                    } else {
                        activeWord += 1;
                    }
                }

                if (!mView.isActive()) {
                    return;
                }
                mView.setProgressIndicator(false);

                mView.showStatistics(activeWord, learnedWord);
            }

            @Override
            public void onDataNotAvailable() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showLoadingStatisticsError();
            }
        });

    }

}
