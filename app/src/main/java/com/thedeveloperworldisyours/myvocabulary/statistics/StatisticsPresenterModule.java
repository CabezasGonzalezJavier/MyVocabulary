package com.thedeveloperworldisyours.myvocabulary.statistics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 25/11/2016.
 */
@Module
public class StatisticsPresenterModule {
    private final StatisticsContract.View mView;

    public StatisticsPresenterModule(StatisticsContract.View view){
        mView = view;
    }

    @Provides
    StatisticsContract.View provideStatisticsContractView() {
        return mView;
    }
}
