package com.thedeveloperworldisyours.myvocabulary.addeditword;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thedeveloperworldisyours.myvocabulary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddEditWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditWordFragment extends Fragment implements AddEditWordContract.View {

    @BindView(R.id.add_word_frag_title)
    TextView mTitle;

    @BindView(R.id.add_word_frag_description)
    TextView mDescription;

    public static final String ARGUMENT_EDIT_WORD_ID = "EDIT_WORD_ID";

    private AddEditWordContract.Presenter mPresenter;


    public AddEditWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddEditWordFragment.
     */
    public static AddEditWordFragment newInstance() {
        AddEditWordFragment fragment = new AddEditWordFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddEditWordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton mFloatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.add_edit_word_act_done);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveWord(mTitle.getText().toString(), mDescription.getText().toString());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_word_frag, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void showEmptyWordError() {
        Snackbar.make(mTitle, getString(R.string.add_edit_word_frag_title_empty), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWordsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
