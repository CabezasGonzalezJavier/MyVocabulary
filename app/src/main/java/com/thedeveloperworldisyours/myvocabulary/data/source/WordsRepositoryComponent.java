package com.thedeveloperworldisyours.myvocabulary.data.source;

import dagger.Component;

import com.thedeveloperworldisyours.myvocabulary.ApplicationModule;
import com.thedeveloperworldisyours.myvocabulary.data.WordsRepositoryModule;

import javax.inject.Singleton;

/**
 * Created by javierg on 28/11/2016.
 */
@Singleton
@Component(modules = {WordsRepositoryModule.class, ApplicationModule.class})
public interface WordsRepositoryComponent {

    WordsRepository getWordsRepository();
}
