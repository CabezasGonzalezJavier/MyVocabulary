package com.thedeveloperworldisyours.myvocabulary.worddetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import com.thedeveloperworldisyours.myvocabulary.Injection;
import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.util.ActivityUtils;
import com.thedeveloperworldisyours.myvocabulary.util.TypefaceSpan;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.thedeveloperworldisyours.myvocabulary.R.style.styleActionBar;

public class WordDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORD_ID = "WORD_ID";

    @BindView(R.id.word_detail_act_toolbar)
    Toolbar mToolbar;

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail_act);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(spannableStringActionBar(getString(R.string.word_detail_act_title)));

        String wordId = getIntent().getStringExtra(EXTRA_WORD_ID);

        WordDetailFragment wordDetailFragment = (WordDetailFragment) getSupportFragmentManager().findFragmentById(R.id.word_detail_act_content_frame);

        if (wordDetailFragment == null) {
            wordDetailFragment = WordDetailFragment.newInstance(wordId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    wordDetailFragment, R.id.word_detail_act_content_frame);
        }

        // Create the presenter
        new WordDetailPresenter(
                Injection.provideWordsRepository(getApplicationContext()),
                wordDetailFragment, wordId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public SpannableString spannableStringActionBar(String string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new TextAppearanceSpan(this, styleActionBar), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TypefaceSpan(this, "candy.ttf"), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
