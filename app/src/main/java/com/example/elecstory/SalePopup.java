package com.example.elecstory;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SalePopup extends Dialog {

    private Activity activity;
    private Button Confirm, Cancel, Close;
    private TextView MessageSaleView, NameObjectSaleView;
    private String MessageSale, NameObjectSale;

    public SalePopup(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.sale_popup);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        this.activity = activity;
        this.MessageSaleView = findViewById(R.id.messageSale);
        this.NameObjectSaleView = findViewById(R.id.nameObjectSale);
        this.Confirm = findViewById(R.id.confirmSale);
        this.Cancel = findViewById(R.id.cancelSale);
        this.Close = findViewById(R.id.closeWindows);
        this.MessageSale = "Hello\nHello\nHello\nHello\nHello\nHello\nHello\nHello";
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

    public Button getConfirm() {
        return Confirm;
    }

    public void setConfirm(Button confirm) {
        Confirm = confirm;
    }

    public Button getCancel() {
        return Cancel;
    }

    public void setCancel(Button cancel) {
        Cancel = cancel;
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
