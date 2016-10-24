package com.thedeveloperworldisyours.myvocabulary.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Created by javierg on 24/10/2016.
 */

public final class Word {
    @NonNull
    private final String mId;

    @Nullable
    private final String mTitle;

    @Nullable
    private final String mDescription;

    private final boolean mLearned;

    /**
     * Use this constructor to create a new active Word.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    public Word(@Nullable String title, @Nullable String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    /**
     * Use this constructor to create an active Word if the Task already has an id (copy of another
     * Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    public Word(@Nullable String title, @Nullable String description, @NonNull String id) {
        this(title, description, id, false);
    }

    /**
     * Use this constructor to create a new learned word.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    public Word(@Nullable String title, @Nullable String description, boolean completed) {
        this(title, description, UUID.randomUUID().toString(), completed);
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     * @param completed   true if the task is learned, false if it's active
     */
    public Word(@Nullable String title, @Nullable String description, @NonNull String id, boolean completed) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mLearned = completed;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isLearned() {
        return mLearned;
    }

    public boolean isActive() {
        return !mLearned;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
                Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equal(mId, word.mId) &&
                Objects.equal(mTitle, word.mTitle) &&
                Objects.equal(mDescription, word.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Word with title " + mTitle;
    }
}
