package com.thedeveloperworldisyours.myvocabulary.worddetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 29/11/2016.
 */
@Module
public class WordDetailPresenterModule {
    private final WordDetailContract.View mView;

    private final String mWordId;

    public WordDetailPresenterModule(WordDetailContract.View mView, String mWordId) {
        this.mView = mView;
        this.mWordId = mWordId;
    }

    @Provides
    WordDetailContract.View provideWordDetailContractView(){
        return mView;
    }

    @Provides
    String provideWordID() {
        return mWordId;
    }

}
