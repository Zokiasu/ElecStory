package com.example.elecstory.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.elecstory.R;

public class InformationPopup extends Dialog {

    private Activity activity;
    private Button Close;
    private TextView MessageSaleView, NameObjectSaleView;
    private String MessageSale, NameObjectSale;

    public InformationPopup(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_information);

        this.activity = activity;
        this.MessageSaleView = findViewById(R.id.messageSale);
        this.NameObjectSaleView = findViewById(R.id.nameObjectSale);
        this.Close = findViewById(R.id.closeWindows);
        this.MessageSale = "Hello World";
        this.NameObjectSale = "NameObject";
    }

    public void build(){
        show();
        MessageSaleView.setText(MessageSale);
        NameObjectSaleView.setText(NameObjectSale);
    }

    public Button getClose() {
        return Close;
    }

    public void setClose(Button close) {
        Close = close;
    }

    public TextView getNameObjectSaleView() {
        return NameObjectSaleView;
    }

    public void setNameObjectSaleView(TextView nameObjectSaleView) {
        NameObjectSaleView = nameObjectSaleView;
    }

    public String getNameObjectSale() {
        return NameObjectSale;
    }

    public void setNameObjectSale(String nameObjectSale) {
        NameObjectSale = nameObjectSale;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TextView getMessageSaleView() {
        return MessageSaleView;
    }

    public void setMessageSaleView(TextView messageSaleView) {
        MessageSaleView = messageSaleView;
    }

    public String getMessageSale() {
        return MessageSale;
    }

    public void setMessageSale(String messageSale) {
        MessageSale = messageSale;
    }
}
