package com.thedeveloperworldisyours.myvocabulary.worddetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.addeditword.AddEditWordActivity;
import com.thedeveloperworldisyours.myvocabulary.addeditword.AddEditWordFragment;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordDetailFragment extends Fragment implements WordDetailContract.View {

    @BindView(R.id.word_detail_frag_title)
    TextView mTitle;

    @BindView(R.id.word_detail_frag_description)
    TextView mDescription;

    @BindView(R.id.word_detail_frag_learned_check_box)
    CheckBox mCheckBox;

    WordDetailPresenter mPresenter;

    @NonNull
    private static final String ARGUMENT_WORD_ID = "WORD_ID";

    @NonNull
    private static final int REQUEST_EDIT_WORD = 1;

    public WordDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WordDetailFragment.
     */
    public static WordDetailFragment newInstance(@Nullable String wordId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_WORD_ID, wordId);
        WordDetailFragment fragment = new WordDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.word_detail_frag, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mTitle.setText("");
            mDescription.setText(getString(R.string.loading));
        }
    }

    @Override
    public void showMissingWord() {
        mTitle.setText("");
        mDescription.setText(getString(R.string.no_data));
    }

    @Override
    public void hideTitle() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void hideDescription() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showLearnedStatus(boolean learned) {
        mCheckBox.setChecked(learned);
        mCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            mPresenter.learnedWord();
                        } else {
                            mPresenter.activateWord();
                        }
                    }
                }
        );
    }

    @Override
    public void showEditWord(String wordId) {
        Intent intent = new Intent(getActivity(), AddEditWordActivity.class);
        intent.putExtra(AddEditWordFragment.ARGUMENT_EDIT_WORD_ID, wordId);
        startActivityForResult(intent, REQUEST_EDIT_WORD);
    }

    @Override
    public void showWordDeleted() {
        getActivity().finish();
    }

    @Override
    public void showWordMarkedLearned() {
        Snackbar.make(getView(), getString(R.string.word_detail_frag_marked_learned), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWordMarkedActive() {
        Snackbar.make(getView(), getString(R.string.word_detail_frag_marked_active), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull WordDetailContract.Presenter presenter) {
        mPresenter = (WordDetailPresenter) checkNotNull(presenter);
    }
}
