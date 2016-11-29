package com.thedeveloperworldisyours.myvocabulary;

import android.app.Application;

import com.thedeveloperworldisyours.myvocabulary.data.source.DaggerWordsRepositoryComponent;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepositoryComponent;

/**
 * Created by javierg on 25/11/2016.
 */

public class MyVocabularyApplication extends Application {
    private WordsRepositoryComponent mWordsRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mWordsRepositoryComponent = DaggerWordsRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public WordsRepositoryComponent getWordsRepositoryComponent() {
        return mWordsRepositoryComponent;
    }
}
