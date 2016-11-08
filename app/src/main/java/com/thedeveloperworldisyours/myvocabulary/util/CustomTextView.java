package com.thedeveloperworldisyours.myvocabulary.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by javierg on 08/11/2016.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/candy.ttf", context);
        setTypeface(customFont);
    }

}
