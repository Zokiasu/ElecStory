package com.example.elecstory;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.City;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.Database.PlayerData;

import java.util.ArrayList;

import static com.example.elecstory.Database.Database.TABLE_CITY_PLAYER;

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


    public ArrayList test(ArrayList<City> mCitys){

        db = new Database(this);

        String strSql = " SELECT * FROM " + TABLE_CITY_PLAYER;
        Cursor cursor = db.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            City citys = new City(cursor.getString(0), //Name
                                  cursor.getString(1), //Category
                                  cursor.getInt(2), //CoinWin
                                  cursor.getInt(3), //Price
                                  cursor.getInt(4), //EnergyCost
                                  cursor.getInt(5), //Level
                                  cursor.getInt(6)); //Skin
            Log.i("##",citys.getName());
            mCitys.add(citys);
            cursor.moveToNext();
        }

        db.close();
        return mCitys;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCity);
        currentLayout = findViewById(R.id.activity_main);
        FactoryUpgrade = findViewById(R.id.FactoryUpgrade);
        UpElecPoint = findViewById(R.id.ElecUp);
        Shop = findViewById(R.id.ShopButton);
        ElecStockage = findViewById(R.id.ElecCount);
        Coins = findViewById(R.id.ElecCoins);
        Factorys = findViewById(R.id.Factory);

        FactoryUpgrade.setText("Upgrade");
        Coins.setText("Coin " + 0);
        ElecStockage.setText("Energy Stock " + 0);
        UpElecPoint.setText("+1 Energy");
        Factorys.setText("You don't have a Factory");

        db = new Database(this);
        Toto = db.infoFirstPlayer();

        String strSql = " SELECT * FROM " + TABLE_CITY_PLAYER;
        Cursor cursor = db.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            City citys = new City(cursor.getString(0), //Name
                    cursor.getString(1), //Category
                    cursor.getInt(2), //CoinWin
                    cursor.getInt(3), //Price
                    cursor.getInt(4), //EnergyCost
                    cursor.getInt(5), //Level
                    cursor.getInt(6)); //Skin
            mCity.add(citys);
            cursor.moveToNext();
        }

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

                if(Toto.getElectricityStockage() == 10 && MyFact == null){
                    MyFact = new Factory(-1);
                    db.insertFactory(MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getRequiredCost(), MyFact.getUpgradeCost(), MyFact.getElecGenerate(), MyFact.getElecByMillisecond(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
                    Factorys.setText(MyFact.getName() + " LvL" + MyFact.getFactoryLevel());
                }
            }
        });
        incrementEnergy();
        db.close();
    }

    public void decrementEnergy(){

    }

    public void incrementEnergy(){
        if(Toto.getElectricityStockage() >= 10) {
            if (MyFact != null) {
                Toto.setElectricityStockage(Toto.getElectricityStockage() + MyFact.getElecGenerate());
            } else {
                Toto.setElectricityStockage(Toto.getElectricityStockage() + 1);
            }

        }
        ElecStockage.setText("Energy Stock " + Toto.getElectricityStockage());
        if (MyFact != null) {
            refreshEnergy(MyFact.getElecByMillisecond());
        } else {
            refreshEnergy(5000);
        }
    }

    private void refreshEnergy(int milli){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementEnergy();
            }
        };

        handler.postDelayed(runnable, milli);
    }

    public void upgradeFactory(){
        if(MyFact != null){
            if(Toto.getCoin() >= MyFact.getUpgradeCost()) {
                if (MyFact.getFactoryLevel() == 10 && MyFact.getName() == "Centrale Nucleaire") {
                    Toast.makeText(MainActivity.this, "You have reached the maximum level !", Toast.LENGTH_SHORT).show();
                } else {
                    MyFact.Upgrade(MyFact);
                    if(MyFact.getFactoryLevel() == 10){
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
