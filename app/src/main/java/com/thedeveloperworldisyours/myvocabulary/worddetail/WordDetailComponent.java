package com.thedeveloperworldisyours.myvocabulary.worddetail;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepositoryComponent;
import com.thedeveloperworldisyours.myvocabulary.util.FragmentScoped;

import dagger.Component;

/**
 * Created by javierg on 29/11/2016.
 */
@FragmentScoped
@Component(dependencies = WordsRepositoryComponent.class, modules = WordDetailPresenterModule.class)
public interface WordDetailComponent {
    void inject(WordDetailActivity wordDetailActivity);
}
