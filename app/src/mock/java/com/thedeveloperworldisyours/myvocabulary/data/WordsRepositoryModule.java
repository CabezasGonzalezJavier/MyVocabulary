package com.thedeveloperworldisyours.myvocabulary.data;

import android.content.Context;

import com.thedeveloperworldisyours.myvocabulary.data.source.Local;
import com.thedeveloperworldisyours.myvocabulary.data.source.Remote;
import com.thedeveloperworldisyours.myvocabulary.data.source.WordsDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.local.WordsLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 25/11/2016.
 */
@Module
public class WordsRepositoryModule {
    @Singleton
    @Provides
    @Local
    WordsDataSource provideWordsLocalDataSource(Context context) {
        return new WordsLocalDataSource(context);
    }

    @Singleton
    @Provides
    @Remote
    WordsDataSource provideWordsRemoteDataSource() {
        return new FakeWordsRemoteDataSource();
    }

}
