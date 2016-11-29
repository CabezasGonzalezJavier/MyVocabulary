package com.thedeveloperworldisyours.myvocabulary.addeditword;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 28/11/2016.
 */
@Module
public class AddEditWordPresenterModule {
    private final AddEditWordContract.View mView;

    private final String mWordId;

    public AddEditWordPresenterModule(AddEditWordContract.View view, String wordId) {
        this.mView = view;
        this.mWordId = wordId;
    }

    @Provides
    AddEditWordContract.View provideAddEditWordContractView(){
        return mView;
    }

    @Provides
    String provideWordId() {
        return mWordId;
    }
}
