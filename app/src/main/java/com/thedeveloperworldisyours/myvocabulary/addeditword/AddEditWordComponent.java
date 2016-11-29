package com.thedeveloperworldisyours.myvocabulary.addeditword;

import dagger.Component;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepositoryComponent;
import com.thedeveloperworldisyours.myvocabulary.util.FragmentScoped;

/**
 * Created by javierg on 29/11/2016.
 */
@FragmentScoped
@Component(dependencies = WordsRepositoryComponent.class, modules = AddEditWordPresenterModule.class)
public interface AddEditWordComponent {
    void inject(AddEditWordActivity addEditWordActivity);
}
