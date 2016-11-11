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

        void showLearnedWordsCleared();

        void showLoadingWordsError();

        void showNoWords();

        void showActiveFilterLabel();

        void showLearnedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveWords();

        void showNoLearnedWords();

        void showSuccessfullySavedMessage();

        boolean isActive();


    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void addNewWord();

        void openWordDetails(@NonNull Word requestedWord);

        void learnWord(@NonNull Word completedWord);

        void activateWord(@NonNull Word activeWord);

        void clearLearnedWord();

        void setFiltering(WordsFilterType requestType);

        WordsFilterType getFiltering();
    }
}
