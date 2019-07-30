package com.example.elecstory.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.elecstory.R;

import java.util.ArrayList;
import java.util.List;

public class ShopPopup extends Dialog {

    protected Activity activity;
    protected EditText NbObjectBuys;
    protected Button button1, button2;
    protected List<Integer> number = new ArrayList<Integer>();
    protected TextView ObjectBuy;
    protected String ObjectsBuy;
    protected int Test;

    public ShopPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_shop_select_number);

        this.activity = activity;
        this.NbObjectBuys = findViewById(R.id.NbObjectBuy);
        this.button1 = findViewById(R.id.confirmBuy);
        this.button2 = findViewById(R.id.cancelBuy);
        this.ObjectBuy = findViewById(R.id.ObjectBuy);
        this.ObjectsBuy = "Text";
        this.Test = 1;
    }

    public void build(){
        ObjectBuy.setText(ObjectsBuy);
        show();
    }

    public EditText getNbObjectBuys() {
        return NbObjectBuys;
    }

    public void setNbObjectBuys(EditText nbObjectBuys) {
        NbObjectBuys = nbObjectBuys;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public String getObjectsBuy() {
        return ObjectsBuy;
    }

    public void setObjectsBuy(String objectsBuy) {
        ObjectsBuy = objectsBuy;
    }

    public TextView getObjectBuy() {
        return ObjectBuy;
    }

    public void setObjectBuy(TextView objectBuy) {
        ObjectBuy = objectBuy;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Integer> getNumber() {
        return number;
    }

    public void setNumber(List<Integer> number) {
        this.number = number;
    }
}
