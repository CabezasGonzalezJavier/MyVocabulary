package com.thedeveloperworldisyours.myvocabulary;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 25/11/2016.
 */
@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
