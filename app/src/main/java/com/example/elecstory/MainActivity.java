package com.example.elecstory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.City;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.Database.PlayerData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TextView ElecStockage;
    public TextView Coins;
    public TextView Factorys;

    public Button UpElecPoint;
    public Button FactoryUpgrade;
    public Button Shop;

    public ConstraintLayout currentLayout;

    public Factory MyFact = null;
    public static PlayerData Toto;
    private static final String TAG = "MainActivity";
    public RecyclerView recyclerView;
    private ArrayList<City> mCity = new ArrayList<>();

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        recyclerView = findViewById(R.id.recyclerViewCity);
        currentLayout = findViewById(R.id.activity_main);
        FactoryUpgrade = findViewById(R.id.FactoryUpgrade);
        UpElecPoint = findViewById(R.id.ElecUp);
        Shop = findViewById(R.id.ShopButton);
        ElecStockage = findViewById(R.id.ElecCount);
        Coins = findViewById(R.id.ElecCoins);
        Factorys = findViewById(R.id.Factory);

        FactoryUpgrade.setText("Upgrade");
        UpElecPoint.setText("+1 Energy");
        Factorys.setText("You don't have a Factory");

        Toto = db.infoFirstPlayer();

        Coins.setText("Coin " + Toto.getCoin());
        ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());

        try {
            if(db.infoFirstFactory() != null){
                Log.i(TAG, "Call " + db.infoFirstFactory().getName());
                MyFact = db.infoFirstFactory();
                Log.i(TAG, "Call2 " + MyFact.getName());
                Factorys.setText(MyFact.getName() + " LvL" + MyFact.getFactoryLevel());
            }
        } catch (Exception e) {}

        mCity = db.infoCity(mCity);
        initRecyclerView();

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

                db.updateCoin(Toto.getName(), Toto.getCoin());
                db.updateElecPoint(Toto.getName(), Toto.getElectricityStockage());

                if(Toto.getCoin() == 10 && MyFact == null){
                    MyFact = new Factory(-1);
                    db.insertFactory(MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getRequiredCost(), MyFact.getUpgradeCost(), MyFact.getElecGenerate(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
                    Factorys.setText(MyFact.getName() + " LvL" + MyFact.getFactoryLevel());
                }
            }
        });

        incrementEnergy(0);
        db.close();
    }

    public void incrementEnergy(int N){
        //Augmente l'énergie en fonction de l'usine actuel
        if (MyFact != null) {
            Toto.setElectricityStockage(Toto.getElectricityStockage() + MyFact.getElecGenerate());
        }

        ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());
        //Réduit l'énergie en fonction des bâtiments possédé
        if(mCity.size() > 0){
            int EnergyCost = 0, CoinWin = 0;
            for (int i = 0; i < mCity.size(); i++){
                EnergyCost = EnergyCost + mCity.get(i).getEnergyCost();
                CoinWin = CoinWin + mCity.get(i).getCoinWin();
            }
            if(Toto.getElectricityStockage() > EnergyCost && Toto.getElectricityStockage() - EnergyCost > 0) {
                Toto.setElectricityStockage(Toto.getElectricityStockage() - EnergyCost);
                ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());

                Toto.setCoin(Toto.getCoin() + CoinWin);
                Coins.setText("Coin " + Toto.getCoin());
            }
        }
        if(N == 10) {
            decrementCoin();
            N = 0;
        }
        refreshEnergy(1000, N);
    }

    private void refreshEnergy(int milli, final int N){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementEnergy(N+1);
            }
        };

        handler.postDelayed(runnable, milli);
    }

    public void decrementCoin() {
        if(MyFact != null){

            int OperatingCost = MyFact.getOperatingCost() + MyFact.getPollutionTax();

            Toto.setCoin(Toto.getCoin() - OperatingCost);
            Coins.setText("Coin " + Toto.getCoin());
        }
    }

    public void upgradeFactory(){
        if(MyFact != null){
            if(Toto.getCoin() >= MyFact.getUpgradeCost()) {
                if (MyFact.getFactoryLevel() == 10 && MyFact.getName() == "Centrale Nucleaire") {
                    Toast.makeText(MainActivity.this, "You have reached the maximum level !", Toast.LENGTH_SHORT).show();
                } else {
                    MyFact.Upgrade(MyFact);
                    if(MyFact.getFactoryLevel() == 10 && MyFact.getName() == "Centrale Nucleaire"){
                        FactoryUpgrade.setVisibility(View.INVISIBLE);
                    }
                }
                Factorys.setText(MyFact.getName() + " " + MyFact.getFactoryLevel());
            } else {
                Toast.makeText(MainActivity.this, "Not enough money !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static PlayerData getToto() {
        return Toto;
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mCity);
        recyclerView.setAdapter(adapter);

    }
}
