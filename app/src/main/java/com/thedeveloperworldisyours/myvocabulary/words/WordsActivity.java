package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thedeveloperworldisyours.myvocabulary.Injection;
import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.util.ActivityUtils;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;

public class WordsActivity extends AppCompatActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private WordsPresenter mWordsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_act);
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.words_act_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer_text_color_selector.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.words_act_drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.words_act_nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        WordsFragment wordsFragment =
                (WordsFragment) getSupportFragmentManager().findFragmentById(R.id.words_act_contentFrame);
        if (wordsFragment == null) {
            // Create the fragment
            wordsFragment = WordsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), wordsFragment, R.id.words_act_contentFrame);
        }

        // Create the presenter
        mWordsPresenter = new WordsPresenter(
                Injection.provideTasksRepository(getApplicationContext()), wordsFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            WordsFilterType currentFiltering =
                    (WordsFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mWordsPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mWordsPresenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer_text_color_selector when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
//                                Intent intent =
//                                        new Intent(WordsActivity.this, StatisticsActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer_text_color_selector when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
