package com.thedeveloperworldisyours.myvocabulary.addeditword;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.thedeveloperworldisyours.myvocabulary.Injection;
import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.util.ActivityUtils;
import com.thedeveloperworldisyours.myvocabulary.util.EspressoIdlingResource;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditWordActivity extends AppCompatActivity {

    @BindView(R.id.add_edit_word_act_toolbar)
    Toolbar mToolbar;

//    @BindView(R.id.add_edit_word_act_contentFrame)
//    FrameLayout mFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_word_act);
        overridePendingTransition(R.anim.go_in, R.anim.go_out);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        String wordId = getIntent().getStringExtra(AddEditWordFragment.ARGUMENT_EDIT_WORD_ID);
//        AddEditWordFragment mAddEditWordFragment = mFrame;
        AddEditWordFragment mAddEditWordFragment = (AddEditWordFragment) getSupportFragmentManager().findFragmentById(R.id.add_edit_word_act_contentFrame);
        if (mAddEditWordFragment == null) {
            mAddEditWordFragment = AddEditWordFragment.newInstance();

            if (getIntent().hasExtra(AddEditWordFragment.ARGUMENT_EDIT_WORD_ID)) {
                actionBar.setTitle(R.string.add_edit_word_act_edit_word);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditWordFragment.ARGUMENT_EDIT_WORD_ID, wordId);
                mAddEditWordFragment.setArguments(bundle);
            } else {
                if (actionBar != null) {
                    actionBar.setTitle(R.string.add_edit_word_act_add_word);
                }
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mAddEditWordFragment, R.id.add_edit_word_act_contentFrame);
        }

        new AddEditWordPresenter(wordId, Injection.provideWordsRepository(getApplicationContext()), mAddEditWordFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public void onBackPressed() {
        finishMyActivity();
    }

    public void finishMyActivity() {
        finish();
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }
}
