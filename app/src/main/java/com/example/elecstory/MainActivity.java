package com.example.elecstory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public TextView ElecStockage;
    public TextView Coins;
    public TextView Factorys;

    public Button UpElecPoint;
    public Button FactoryUpgrade;
    public ImageButton Shop;

    public ConstraintLayout currentLayout;

    public Factory Fact;
    public Player Toto = new Player("Toto", 0, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLayout = findViewById(R.id.activity_main);

        FactoryUpgrade = findViewById(R.id.FactoryUpgrade);
        FactoryUpgrade.setText("Upgrade");
        FactoryUpgrade.setVisibility(View.INVISIBLE);
        UpElecPoint = findViewById(R.id.ElecUp);
        UpElecPoint.setText("+1 Energy");
        Shop = findViewById(R.id.ShopButton);

        ElecStockage = findViewById(R.id.ElecCount);
        ElecStockage.setText("Energy Stock " + 0);
        Coins = findViewById(R.id.ElecCoins);
        Coins.setText("Coin " + 0);
        Factorys = findViewById(R.id.Factory);
        Factorys.setText("You don't have a Factory");

        FactoryUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeFactory();
            }
        });

        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(myIntent);
            }
        });

        UpElecPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toto.setElectricityStockage(Toto.getElectricityStockage()+1);
                Toto.setCoin(Toto.getCoin() + 1);

                ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());

                Coins.setText("Coin " + Toto.getCoin());

                stageEnergy(Toto.getElectricityStockage());

                if(Toto.getElectricityStockage() == 10 && Toto.getMyFact() == null){
                    Fact = new Factory(5);
                    Toto.setMyFact(Fact);
                    Factorys.setText(Toto.getMyFact().getName() + " " + Toto.getMyFact().getFactoryLevel());
                    FactoryUpgrade.setVisibility(View.VISIBLE);
                }
            }
        });
        incrementEnergy();
    }

    public void upgradeFactory(){
        if(Toto.getMyFact() != null){
            if(Toto.getCoin() >= Toto.getMyFact().getUpgradeCost()) {
                if (Toto.getMyFact().getFactoryLevel() == 1) {
                    Toto.setMyFact(Toto.getMyFact().Upgrade2(Toto.getMyFact()));
                } else if (Toto.getMyFact().getFactoryLevel() == 2) {
                    Toto.setMyFact(Toto.getMyFact().Upgrade3(Toto.getMyFact()));
                } else {
                    Toast.makeText(MainActivity.this, "You have reached the maximum level !", Toast.LENGTH_SHORT).show();
                }
                Factorys.setText(Toto.getMyFact().getName() + " " + Toto.getMyFact().getFactoryLevel());
            } else {
                Toast.makeText(MainActivity.this, "Not enough money !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void incrementEnergy(){

        if(Toto.getMyFact() != null) {
            Toto.setElectricityStockage(Toto.getElectricityStockage() + Toto.getMyFact().getElecGenerate());
        }

        stageEnergy(Toto.getElectricityStockage());

        ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());

        if(Toto.getMyFact() != null){
            refreshEnergy(Toto.getMyFact().getElecByMillisecond());
        } else {
            refreshEnergy(5000);
        }
    }

    public void stageEnergy(int Point){
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

    private void refreshEnergy(final int milli){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementEnergy();
            }
        };

        handler.postDelayed(runnable, milli);
    }
}
