package com.thedeveloperworldisyours.myvocabulary.words;

import com.thedeveloperworldisyours.myvocabulary.BasePresenter;
import com.thedeveloperworldisyours.myvocabulary.BaseView;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by javierg on 31/10/2016.
 */

public interface WordsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Word> words);

        void showAddWord();

        void showWordDetailsUi(String wordId);

        void showWordMarkedComplete();

        void showWordMarkedActive();

        void showCompletedWordsCleared();

        void showLoadingWordsError();

        void showNoWords();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveWords();

        void showNoCompletedWords();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void addNewWord();

        void openWordDetails(@NonNull Word requestedWord);

        void completeWord(@NonNull Word completedWord);

        void activateWord(@NonNull Word activeWord);

        void clearCompletedWord();

        void setFiltering(WordsFilterType requestType);

        WordsFilterType getFiltering();
    }
}
