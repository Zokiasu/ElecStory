package com.example.elecstory;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView ElecStockage;
    public TextView Coins;
    public TextView Factorys;

    public Button UpElecPoint;

    public ConstraintLayout currentLayout;

    public Factory Fact;
    public Player Toto = new Player("Toto", 0, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLayout = findViewById(R.id.activity_main);
        UpElecPoint = findViewById(R.id.ElecUp);
        ElecStockage = findViewById(R.id.ElecCount);
        Coins = findViewById(R.id.ElecCoins);
        Factorys = findViewById(R.id.Factory);

        Factorys.setText("You don't have an Factory");
        ElecStockage.setText("Energy Stock " + String.valueOf(0));
        Coins.setText("Coin " + String.valueOf(0));

        UpElecPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toto.setElectricityStockage(Toto.getElectricityStockage()+1);
                Toto.setCoin(Toto.getCoin() + 1);

                ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());
                Coins.setText("Coin " + Toto.getCoin());
                stage(Toto.getElectricityStockage());

                if(Toto.getElectricityStockage() == 10){
                    Fact = new Factory(-1);
                    Toto.setMyFact(Fact);
                    Factorys.setText(Toto.getMyFact().getName());
                }
            }
        });

        AutoIncrement();
    }

    public void AutoIncrement(){

        if(Toto.getMyFact() != null) {
            Toto.setElectricityStockage(Toto.getElectricityStockage() + Toto.getMyFact().getElecGenerate());
        }

        stage(Toto.getElectricityStockage());

        ElecStockage.setText("Energy Stock " + String.valueOf(Toto.getElectricityStockage()));

        if(Toto.getMyFact() != null){
            refresh(Toto.getMyFact().getElecByMillisecond());
        } else {
            refresh(5000);
        }
    }

    public void stage(int Point){
        switch (Point){
            case 10 :
                currentLayout.setBackgroundColor(Color.GREEN);
                break;
            case 20 :
                currentLayout.setBackgroundColor(Color.RED);
                break;
            case 30 :
                currentLayout.setBackgroundColor(Color.YELLOW);
                break;
            case 40 :
                currentLayout.setBackgroundColor(Color.CYAN);
                break;
            case 50 :
                currentLayout.setBackgroundColor(Color.WHITE);
                break;
        }
    }

    private void refresh(final int milli){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AutoIncrement();
            }
        };

        handler.postDelayed(runnable, milli);
    }
}
