package com.thedeveloperworldisyours.myvocabulary.words;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.MenuItem;

import com.thedeveloperworldisyours.myvocabulary.MyVocabularyApplication;
import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.statistics.StatisticsActivity;
import com.thedeveloperworldisyours.myvocabulary.util.ActivityUtils;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;
import com.thedeveloperworldisyours.myvocabulary.util.TypefaceSpan;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.thedeveloperworldisyours.myvocabulary.R.style.styleActionBar;

public class WordsActivity extends AppCompatActivity implements WordsInteractionListener{
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    @BindView(R.id.words_act_toolbar)
    Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private ActionBar mActionBar;

    @Inject
    WordsPresenter mWordsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_act);

        ButterKnife.bind(this);

        // Set up the mToolbar.
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(spannableStringActionBar(getString(R.string.app_name)));

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
        DaggerWordsComponent.builder()
                .wordsRepositoryComponent(((MyVocabularyApplication) getApplication()).getWordsRepositoryComponent())
                .wordsPresenterModule(new WordsPresenterModule(wordsFragment)).build()
                .inject(this);

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
                // Open the navigation drawer_text_color_selector when the home icon is selected from the mToolbar.
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
                                Intent intent =
                                        new Intent(WordsActivity.this, StatisticsActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
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

    @Override
    public void onFragmentInteraction(String string) {
        mActionBar.setTitle(spannableStringActionBar(string));
    }

    public SpannableString spannableStringActionBar(String string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new TextAppearanceSpan(this, styleActionBar), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TypefaceSpan(this, "candy.ttf"), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
