package com.example.elecstory.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.elecstory.R;

public class MenuPopup extends Dialog {

    protected Activity activity;
    protected TextView Close;
    protected Button Ranking;
    protected Button Option;
    protected Button Feedback;

    public MenuPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.activity = activity;

        this.Close = findViewById(R.id.closeMenu);
        this.Ranking = findViewById(R.id.rankingMenu);
        this.Option = findViewById(R.id.optionMenu);
        this.Feedback = findViewById(R.id.feedbackMenu);
    }

    public void build(){
        show();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TextView getClose() {
        return Close;
    }

    public void setClose(TextView close) {
        Close = close;
    }

    public Button getRanking() {
        return Ranking;
    }

    public void setRanking(Button ranking) {
        Ranking = ranking;
    }

    public Button getOption() {
        return Option;
    }

    public void setOption(Button option) {
        Option = option;
    }

    public Button getFeedback() {
        return Feedback;
    }

    public void setFeedback(Button feedback) {
        Feedback = feedback;
    }
}
