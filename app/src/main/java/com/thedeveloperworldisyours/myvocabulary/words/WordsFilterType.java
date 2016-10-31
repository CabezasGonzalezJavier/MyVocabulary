package com.thedeveloperworldisyours.myvocabulary.words;

/**
 * Created by javierg on 31/10/2016.
 */
/**
 * Used with the filter spinner in the words list.
 */
public enum  WordsFilterType {
    /**
     * Do not filter words.
     */
    ALL_WORDS,

    /**
     * Filters only the active (not completed yet) words.
     */
    ACTIVE_WORDS,

    /**
     * Filters only the completed words.
     */
    COMPLETED_WORDS
}
