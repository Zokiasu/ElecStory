package com.example.elecstory.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.R;

public class CongratsPopup extends Dialog {

    protected Activity activity;
    protected TextView GratzMessage;
    protected ImageView ImageCongrats;
    protected String Gratz;

    public CongratsPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.congrats_popup);

        this.activity = activity;

        this.GratzMessage = findViewById(R.id.GratzMessage);

        this.ImageCongrats = findViewById(R.id.ImageJok);

        this.Gratz = "Hello World";
    }

    public void build(){
        show();
        GratzMessage.setText(Gratz);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TextView getGratzMessage() {
        return GratzMessage;
    }

    public void setGratzMessage(TextView gratzMessage) {
        GratzMessage = gratzMessage;
    }

    public ImageView getImageCongrats() {
        return ImageCongrats;
    }

    public void setImageCongrats(ImageView imageCongrats) {
        ImageCongrats = imageCongrats;
    }

    public String getGratz() {
        return Gratz;
    }

    public void setGratz(String gratz) {
        Gratz = gratz;
    }
}
