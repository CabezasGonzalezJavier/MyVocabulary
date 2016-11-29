package com.thedeveloperworldisyours.myvocabulary.words;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepositoryComponent;
import com.thedeveloperworldisyours.myvocabulary.util.FragmentScoped;

import dagger.Component;

/**
 * Created by javierg on 29/11/2016.
 */
@FragmentScoped
@Component(dependencies = WordsRepositoryComponent.class, modules = WordsPresenterModule.class)
public interface WordsComponent {

    void inject(WordsActivity wordsActivity);
}
