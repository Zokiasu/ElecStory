package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context)
    {
        super(context);
        init();
    }
    public CustomFontTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/comic-sans-ms.ttf"));
    }
}
