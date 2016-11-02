package com.thedeveloperworldisyours.myvocabulary.words;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment implements WordsContract.View, WordsRecyclerViewAdapter.WordItemListener {

    @BindView(R.id.fragment_words_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_words_text_view)
    TextView mTextView;

    @BindView(R.id.fragment_words_refresh_layout)
    ScrollChildSwipeRefreshLayout mRefresh;

    private WordsContract.Presenter mPresenter;
    private WordsRecyclerViewAdapter mAdapter;
    private List<Word> mWordList;

    public WordsFragment() {
        // Required empty public constructor
    }

    public static WordsFragment newInstance() {
        return new WordsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new WordsRecyclerViewAdapter(mWordList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_words, container, false);
        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        mRecyclerView.setAdapter(mAdapter);
//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
//        mRecyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(active);

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
//                mPresenter.clearCompletedTasks();
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
//                mPresenter.loadTasks(true);
                break;
        }
        return true;
    }

    @Override
    public void showWords(List<Word> words) {

        mAdapter.replaceData(words);
    }

    @Override
    public void showAddWord() {

    }

    @Override
    public void showWordDetailsUi(String wordId) {

    }

    @Override
    public void showWordMarkedComplete() {

    }

    @Override
    public void showWordMarkedActive() {

    }

    @Override
    public void showCompletedWordsCleared() {

    }

    @Override
    public void showLoadingWordsError() {

    }

    @Override
    public void showNoWords() {

    }

    @Override
    public void showActiveFilterLabel() {

    }

    @Override
    public void showCompletedFilterLabel() {

    }

    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public void showNoActiveWords() {

    }

    @Override
    public void showNoCompletedWords() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showFilteringPopUpMenu() {


    }

    @Override
    public void setPresenter(WordsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onWordClick(int position, View v) {
        mPresenter.openWordDetails(mWordList.get(position));
    }

    @Override
    public void onCompleteWordClick(Word completedWord) {
        mPresenter.completeWord(completedWord);
    }

    @Override
    public void onActivateWordClick(Word activatedWord) {
        mPresenter.activateWord(activatedWord);
    }
}
