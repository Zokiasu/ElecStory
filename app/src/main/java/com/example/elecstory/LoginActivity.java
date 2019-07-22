package com.example.elecstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.Object.Factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    protected TextView Pseudo, Age;
    protected Button Confirm;
    private Database db;

    private static final String TAG = "Elecstory.LoginActivity";

    public String generateUniqueId(String Pseudo, int Age) {
        db = new Database(this);

        int Min = 1, Max = 999999;
        int First;
        Random rand = new Random();

        First = rand.nextInt(Max - Min + 1) + Min;

        char lettre = Pseudo.charAt(0);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
        String date_hour = sdf.format(new Date());

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(actuelle);

        String iD = date + "-" + date_hour + "-" + First + "-" + lettre + Age;
        db.close();
        return iD;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        db = new Database(this);

        Pseudo = findViewById(R.id.Pseudo);
        Age = findViewById(R.id.Age);
        Confirm = findViewById(R.id.Confirm);

        try {
            if(db.infoFirstPlayer() != null) {
                db.close();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        } catch (Exception e) {}

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Pseudo.getText().toString().matches("") && !Age.getText().toString().matches("")) {

                    db.insertPlayer(Pseudo.getText().toString(), Integer.parseInt(Age.getText().toString()), 0, 0, generateUniqueId(Pseudo.getText().toString(), Integer.parseInt(Age.getText().toString())));

                    EarthObject Test = new EarthObject(0, "");
                    db.insertEarthObject(Test.getNbObject(), Test.getName(), Test.getCoinWin(), Test.getPriceObject(), Test.getEnergyCost(), Test.getSkin());

                    for (int i = -1; i < 6; i++) {
                        Factory MyFact = new Factory(i);
                        db.insertFactory(MyFact.getNbObject(), MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getPriceFactory(), MyFact.getUpgradeCost(), MyFact.getEnergyProd(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
                    }

                    db.insertFirstItem();
                    db.insertAllTimerAds();
                    db.fillShopFactory();

                    db.close();
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "You have not entered a username or age", Toast.LENGTH_LONG).show();
                }
            }
        });
        db.close();
    }
}
