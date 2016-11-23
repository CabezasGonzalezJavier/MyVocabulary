package com.thedeveloperworldisyours.myvocabulary.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thedeveloperworldisyours.myvocabulary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    @BindView(R.id.statistics_frag_text_view)
    TextView mStatistics;

    StatisticsContract.Presenter mPresenter;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment statisticsFragment = new StatisticsFragment();

        return statisticsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.statistics_frag, container, false);
        ButterKnife.bind(StatisticsFragment.this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mStatistics.setText(getString(R.string.loading));
        } else {
            mStatistics.setText("");
        }
    }

    @Override
    public void showStatistics(int numberOfActiveWords, int numberOfLearnWords) {
        if (numberOfActiveWords == 0 && numberOfLearnWords == 0) {
            mStatistics.setText(getResources().getString(R.string.no_data));
        } else {
            String displayString = getResources().getString(R.string.statistics_frag_active_word) + " "
                    + numberOfActiveWords + "\n" + getResources().getString(
                    R.string.statistics_frag_learned) + " " + numberOfLearnWords;
            mStatistics.setText(displayString);
        }
    }

    @Override
    public void showLoadingStatisticsError() {
        mStatistics.setText(getString(R.string.no_data));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(StatisticsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
