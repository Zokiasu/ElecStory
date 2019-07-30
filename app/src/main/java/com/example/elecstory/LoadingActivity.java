package com.example.elecstory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.Object.Item;
import com.example.elecstory.OtherClass.ProgressBarAnimation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    private Database db = new Database(this);

    private static final String TAG = "Elecstory.LoginActivity";

    protected static final String PREFS = "PREFS";
    protected static final String PREFS_COIN = "PREFS_COIN";
    protected static final String PREFS_ENERGY = "PREFS_ENERGY";
    protected static final String PREFS_DIAMOND = "PREFS_DIAMOND";
    protected static final String PREFS_ENERGYBYCLICK = "PREFS_ENERGYBYCLICK";
    protected static final String PREFS_COINBYCLICK = "PREFS_COINBYCLICK";

    protected SharedPreferences sharedPreferences;

    public String generateUniqueId() {
        int Min = 1, Max = 999999;
        int First;
        Random rand = new Random();

        First = rand.nextInt(Max - Min + 1) + Min;

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
        String date_hour = sdf.format(new Date());

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(actuelle);

        String iD = date + "-" + date_hour + "-" + First + "-" + "";
        return iD;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.percent);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();

        try {
            if(db.infoFirstPlayer() != null) {
                db.close();
            }
        } catch (Exception e) {
            db.insertPlayer(generateUniqueId(), "0000/00/00 00:00:00");

            Item Test = new Item(0, "");
            db.insertItem(Test.getNbObject(), Test.getName(), Test.getCoinWin(), Test.getPriceObject(), Test.getEnergyCost(), Test.getSkin());

            for (int i = -1; i < 6; i++) {
                Factory MyFact = new Factory(i);
                db.insertFactory(MyFact.getNbObject(), MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getPriceFactory(), MyFact.getUpgradeCost(), MyFact.getEnergyProd(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
            }

            db.insertFirstItem();
            db.insertAllTimerAds();
            db.fillShopFactory();
            sharedPreferences.getAll().clear();
            sharedPreferences.edit().clear().apply();
            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, 0)
                    .putLong(PREFS_ENERGY, 0)
                    .putLong(PREFS_DIAMOND, 0)
                    .putLong(PREFS_ENERGYBYCLICK, 1)
                    .putLong(PREFS_COINBYCLICK, 1)
                    .apply();

            db.close();
        }
    }

    public void progressAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(5000);
        progressBar.setAnimation(anim);
    }
}
