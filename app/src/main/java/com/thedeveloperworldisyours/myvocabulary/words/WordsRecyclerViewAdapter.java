package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thedeveloperworldisyours.myvocabulary.R;
import com.thedeveloperworldisyours.myvocabulary.data.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 01/11/2016.
 */

public class WordsRecyclerViewAdapter extends RecyclerView
        .Adapter<WordsRecyclerViewAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Word> mWordList;
    private static WordItemListener sListener;

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        @Nullable
        @BindView(R.id.words_list_item_learned)
        CheckBox mCheckBox;
        @Nullable
        @BindView(R.id.words_list_item_title)
        TextView mTitle;

        public DataObjectHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Word word = mWordList.get(getAdapterPosition());
            sListener.onWordClick(word);
        }
    }

    public WordsRecyclerViewAdapter(List<Word> myDataset, WordItemListener wordItemListener) {
        mWordList = myDataset;
        sListener = wordItemListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.words_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final Word word = mWordList.get(position);

        holder.mTitle.setText(mWordList.get(position).getTitle());
        holder.mCheckBox.setChecked(word.isLearned());
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!word.isLearned()) {
                    sListener.onLearnedWordClick(word);
                } else {
                    sListener.onActivateWordClick(word);
                }
            }
        });
    }

    private void setList(List<Word> wordList) {
        mWordList = checkNotNull(wordList);
    }

    public void replaceData(List<Word> wordList) {
        this.setList(wordList);
        notifyDataSetChanged();
    }

    public void addItem(Word word, int index) {
        mWordList.add(word);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mWordList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public interface WordItemListener {
        void onWordClick(Word word);

        void onLearnedWordClick(Word learnedWord);

        void onActivateWordClick(Word activatedWord);
    }

}
