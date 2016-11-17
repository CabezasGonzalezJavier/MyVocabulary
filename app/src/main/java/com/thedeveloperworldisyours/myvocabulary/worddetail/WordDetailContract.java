package com.thedeveloperworldisyours.myvocabulary.worddetail;

import com.thedeveloperworldisyours.myvocabulary.BasePresenter;
import com.thedeveloperworldisyours.myvocabulary.BaseView;
import com.thedeveloperworldisyours.myvocabulary.words.WordsContract;

/**
 * Created by javierg on 15/11/2016.
 */

public class WordDetailContract {

    interface View extends BaseView<WordDetailContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showMissingWord();

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showLearnedStatus(boolean learned);

        void showEditWord(String wordId);

        void showWordDeleted();

        void showWordMarkedLearned();

        void showWordMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editWord();

        void deleteWord();

        void learnedWord();

        void activateWord();
    }
}
