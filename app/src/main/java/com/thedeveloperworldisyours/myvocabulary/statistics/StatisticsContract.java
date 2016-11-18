package com.thedeveloperworldisyours.myvocabulary.statistics;

import com.thedeveloperworldisyours.myvocabulary.BasePresenter;
import com.thedeveloperworldisyours.myvocabulary.BaseView;

/**
 * Created by javierg on 18/11/2016.
 */

public class StatisticsContract {

    public interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfActiveWords, int numberOfLearnWords);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter{

    }
}
