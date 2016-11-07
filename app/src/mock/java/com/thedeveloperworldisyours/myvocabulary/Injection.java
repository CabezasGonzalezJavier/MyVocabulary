package com.thedeveloperworldisyours.myvocabulary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.myvocabulary.data.source.WordsRepository;
import com.thedeveloperworldisyours.myvocabulary.data.source.local.WordsLocalDataSource;
import com.thedeveloperworldisyours.myvocabulary.data.source.remote.WordsRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 07/11/2016.
 */

public class Injection {
    public static WordsRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return WordsRepository.getInstance(WordsRemoteDataSource.getInstance(),
                WordsLocalDataSource.getInstance(context));
    }
}
