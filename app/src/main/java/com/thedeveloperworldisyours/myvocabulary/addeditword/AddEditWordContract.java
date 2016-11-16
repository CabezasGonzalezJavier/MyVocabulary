package com.thedeveloperworldisyours.myvocabulary.addeditword;

import com.thedeveloperworldisyours.myvocabulary.BasePresenter;
import com.thedeveloperworldisyours.myvocabulary.BaseView;

/**
 * Created by javierg on 08/11/2016.
 */

public class AddEditWordContract {
    public interface View extends BaseView<Presenter> {

        void showEmptyWordError();

        void showWordsList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveWord(String title, String description);

        void populateWord();
    }
}
