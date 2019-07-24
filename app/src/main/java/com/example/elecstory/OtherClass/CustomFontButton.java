package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CustomFontButton extends Button {

    public CustomFontButton(Context context)
    {
        super(context);
        init();
    }
    public CustomFontButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public CustomFontButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/comic-sans-ms.ttf"));
    }
}
