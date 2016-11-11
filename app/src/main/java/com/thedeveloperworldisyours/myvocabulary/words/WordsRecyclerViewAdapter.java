package com.thedeveloperworldisyours.myvocabulary.words;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        @Nullable
        @BindView(R.id.words_list_item_complete)
        CheckBox mCheckBox;
        @Nullable
        @BindView(R.id.words_list_item_title)
        TextView mTitle;

        public DataObjectHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sListener.onWordClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(WordItemListener myClickListener) {
        this.sListener = myClickListener;
    }

    public WordsRecyclerViewAdapter(List<Word> myDataset) {
        mWordList = myDataset;
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
        holder.mTitle.setText(mWordList.get(position).getTitle());
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
        void onWordClick(int position, View v);

        void onCompleteWordClick(Word completedWord);

        void onActivateWordClick(Word activatedWord);
    }

}
