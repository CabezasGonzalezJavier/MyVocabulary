package com.thedeveloperworldisyours.myvocabulary.words;

import dagger.Module;

import dagger.Provides;

/**
 * Created by javierg on 28/11/2016.
 */
@Module
public class WordsPresenterModule {

    private final WordsContract.View mView;

    public WordsPresenterModule(WordsContract.View view) {
        mView = view;
    }

    @Provides
    WordsContract.View provideWordsContractView() {
        return mView;
    }

}
