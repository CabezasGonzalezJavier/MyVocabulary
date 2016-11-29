package com.thedeveloperworldisyours.myvocabulary.statistics;

import dagger.Component;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepositoryComponent;
import com.thedeveloperworldisyours.myvocabulary.util.FragmentScoped;

/**
 * Created by javierg on 28/11/2016.
 */
@FragmentScoped
@Component(dependencies = WordsRepositoryComponent.class, modules = StatisticsPresenterModule.class)
public interface StatisticsComponent {
    void inject(StatisticsActivity statisticsActivity);
}
