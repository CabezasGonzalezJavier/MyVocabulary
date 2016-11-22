package com.thedeveloperworldisyours.myvocabulary.statistics;

import android.content.Intent;
import android.support.design.widget.NavigationView;
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

import com.thedeveloperworldisyours.myvocabulary.Injection;
import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.util.ActivityUtils;
import com.thedeveloperworldisyours.myvocabulary.util.TypefaceSpan;
import com.thedeveloperworldisyours.myvocabulary.words.WordsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.thedeveloperworldisyours.myvocabulary.R.style.styleActionBar;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.statistics_act_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.statistics_act_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.statistics_act_nav_view)
    NavigationView mNavigationView;

    private StatisticsPresenter mPresenter;

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_act);

        ButterKnife.bind(this);

        // Set up the mToolbar.
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(spannableStringActionBar(getString(R.string.app_name)));

        // Set up the navigation drawer_text_color_selector.
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        StatisticsFragment statisticsFragment = (StatisticsFragment) getSupportFragmentManager().findFragmentById(R.id.statistics_act_contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = statisticsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), statisticsFragment, R.id.statistics_act_contentFrame);
        }

        //Create Presenter
        mPresenter = new StatisticsPresenter(Injection.provideWordsRepository(getApplicationContext()), statisticsFragment);

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
                                Intent intent =
                                        new Intent(StatisticsActivity.this, WordsActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.statistics_navigation_menu_item:
                                // Do nothing, we're already on that screen
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

    public SpannableString spannableStringActionBar(String string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new TextAppearanceSpan(this, styleActionBar), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TypefaceSpan(this, "candy.ttf"), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
