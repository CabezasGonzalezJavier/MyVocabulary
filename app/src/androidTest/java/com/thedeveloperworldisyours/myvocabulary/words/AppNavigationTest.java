package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.thedeveloperworldisyours.myvocabulary.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by javierg on 21/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<WordsActivity> mActivityTestRule = new ActivityTestRule<WordsActivity>(WordsActivity.class);

//    @Test
//    public void clickOnStatisticsNavigationItem_ShowsStatisticsScreen() {
//        onView(withId(R.id.words_act_drawer_layout))
//        .check(matches(isClosed(Gravity.LEFT)))
//        .perform(open());
//
//        onView(withId(R.id.words_act_nav_view))
//                .perform(navigateTo(R.id.statistics_navigation_menu_item));
//
//        onView(withId(R.id.activity_statistics)).check(matches(isDisplayed()));
//    }

}
