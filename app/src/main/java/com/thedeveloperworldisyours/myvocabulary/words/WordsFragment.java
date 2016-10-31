package com.thedeveloperworldisyours.myvocabulary.words;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment implements WordsContract.View{

    public WordsFragment() {
        // Required empty public constructor
    }

    public static WordsFragment newInstance() {
        return new WordsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showWords(List<Word> words) {

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

    }
}
