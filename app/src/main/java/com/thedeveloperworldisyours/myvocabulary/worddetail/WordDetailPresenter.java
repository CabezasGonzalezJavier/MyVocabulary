package com.thedeveloperworldisyours.myvocabulary.worddetail;

import android.support.annotation.Nullable;

import com.google.common.base.Strings;
import com.thedeveloperworldisyours.myvocabulary.data.Word;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;

import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 15/11/2016.
 */

public class WordDetailPresenter implements WordDetailContract.Presenter {

    private final WordsRepository mWordsRepository;

    private final WordDetailContract.View mView;

    @Nullable
    private String mWordId;

    public WordDetailPresenter(@NotNull WordsRepository mWordsRepository, @NotNull WordDetailContract.View mView, @Nullable String mWordId) {
        this.mWordsRepository = checkNotNull(mWordsRepository, "WordsRepository cannot be null!!");
        this.mView = checkNotNull(mView, "WordDetailContract cannot be null!!");
        this.mWordId = mWordId;
    }

    @Override
    public void editWord() {
        if (Strings.isNullOrEmpty(mWordId)) {
            mView.showMissingWord();
            return;
        }
        mView.showEditWord(mWordId);
    }

    @Override
    public void deleteWord() {
        if (Strings.isNullOrEmpty(mWordId)) {
            mView.showMissingWord();
            return;
        }
        mWordsRepository.deleteWord(mWordId);
        mView.showWordDeleted();
    }

    @Override
    public void learnedWord() {
        if (Strings.isNullOrEmpty(mWordId)) {
            mView.showMissingWord();
            return;
        }
        mWordsRepository.learnedWord(mWordId);
        mView.showWordMarkedLearned();
    }

    @Override
    public void activateWord() {
        if (Strings.isNullOrEmpty(mWordId)) {
            mView.showMissingWord();
        }
        mWordsRepository.activateWord(mWordId);
        mView.showWordMarkedActive();
    }

    @Override
    public void start() {
        openWord();
    }

    public void openWord() {
        if (Strings.isNullOrEmpty(mWordId)) {
            mView.showMissingWord();
            return;
        }
        mView.setLoadingIndicator(true);
        mWordsRepository.getWord(mWordId, new WordsDataSource.GetWordCallback() {
            @Override
            public void onWordLoaded(Word word) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                if (null == word) {
                    mView.showMissingWord();
                } else {
                    showWord(word);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showMissingWord();
            }
        });
    }

    private void showWord(@NotNull Word word) {
        String title = word.getTitle();
        String description = word.getDescription();

        if (Strings.isNullOrEmpty(title)) {
            mView.hideTitle();
        } else {
            mView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mView.hideDescription();
        } else {
            mView.showDescription(description);
        }
        mView.showLearnedStatus(word.isLearned());
    }
}
