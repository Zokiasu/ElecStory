package com.example.elecstory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.Database.PlayerData;
import com.example.elecstory.Object.RecyclerViewAdapterEarth;
import com.example.elecstory.Object.RecyclerViewAdapterFactory;
import com.example.elecstory.Quest.Quest;
import com.example.elecstory.Quest.QuestAdapter;
import com.example.elecstory.Shop.ShopActivity;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    public TextView ActualElecStockage;
    public TextView ActualCoin;

    public TextView ElecGenerate;
    public TextView OperatingCost;
    public TextView PollutionTax;
    public TextView ElecCost;
    public TextView CoinWins;
    public TextView DisplayQuestName;

    public Button UpElecPoint;
    public Button Shop;
    public Button Craft;
    public Button ListCraft;

    public ConstraintLayout currentLayout;

    public GridView gv;

    public Quest ActualQuest;
    public ImageView DisplayQuestImage;

    public static PlayerData ActualPlayer;

    public RecyclerView recyclerViewCity;
    public RecyclerView recyclerViewFactory;

    private ArrayList<EarthObject> mEarthObject = new ArrayList<>();
    private ArrayList<Factory> mFactory = new ArrayList<>();

    private Database db;

    private static final String TAG = "MainActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindViewById();

        db = new Database(this);

        ActualPlayer = db.infoFirstPlayer();
        ActualQuest = new Quest(db.infoFirstPlayer().getQuestId());
        DisplayQuestName.setText(ActualQuest.getNameReward());
        DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());
        displayQuest();

        ActualCoin.setText("Coin " + ActualPlayer.getCoin());
        ActualElecStockage.setText("Energy " + ActualPlayer.getElectricityStockage());

        mFactory = db.infoFactory(mFactory);
        mEarthObject = db.infoCity(mEarthObject);

        initRecyclerViewFactory();
        initRecyclerViewCity();

        Craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeQuest();
            }
        });

        ListCraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CraftActivity.class);
                startActivity(myIntent);
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
                upgradeEnergy();
            }
        });

        incrementEnergy(0);
        db.close();
    }

    private void initFindViewById(){
        currentLayout = findViewById(R.id.activity_main);

        //Information Player
        ActualCoin = findViewById(R.id.ElecCoins);
        ActualElecStockage = findViewById(R.id.ElecStockage);

        //Information Factory
        ElecGenerate = findViewById(R.id.ElecGenerate);
        OperatingCost = findViewById(R.id.OperatingCost);
        PollutionTax = findViewById(R.id.PollutionTax);

        //Information ObjectEarth
        ElecCost = findViewById(R.id.EnergyCost);
        CoinWins = findViewById(R.id.CoinWins);

        //Quest
        DisplayQuestImage = findViewById(R.id.requestImage);
        DisplayQuestName = findViewById(R.id.requestName);

        //Button
        Shop = findViewById(R.id.ShopButton);
        Craft = findViewById(R.id.ActionCraft);
        ListCraft = findViewById(R.id.ListCraft);
        UpElecPoint = findViewById(R.id.ElecUp);

        //Affichage liste
        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewFactory = findViewById(R.id.recyclerViewFactory);
        gv = findViewById(R.id.requestObject);
    }

    public void upgradeQuest(){
        if(ActualQuest.checkQuest(mEarthObject, db)) {
            mEarthObject.clear();
            mEarthObject = db.infoCity(mEarthObject);
            initRecyclerViewCity();

            ActualQuest = new Quest(ActualQuest.getIdQuest() + 1);

            db.updateQuest(ActualQuest.getIdQuest());
            db.infoFirstPlayer().setQuestId(ActualQuest.getIdQuest());

            DisplayQuestName.setText(ActualQuest.getNameReward());
            DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());

            displayQuest();

            if(ActualQuest.getIdQuest() == 9){
                Toast.makeText(this, "Vous avez réalisé la dernière quête pour le moment !", Toast.LENGTH_LONG).show();
                Craft.setVisibility(View.INVISIBLE);
            }
        } else {
            /*A modifier dès que possible NON PRIORITAIRE*/
            Toast.makeText(this, "Vous ne possédez pas les objets nécessaire pour cette fabrication", Toast.LENGTH_LONG).show();
        }
    }

    public void upgradeEnergy() {
        ActualPlayer.setElectricityStockage(ActualPlayer.getElectricityStockage() + 1);
        db.updateElecPoint(ActualPlayer.getName(), 1);
        ActualPlayer.setCoin(ActualPlayer.getCoin() + 1);
        db.updateCoin(ActualPlayer.getName(), 1);

        ActualElecStockage.setText("Energy " + ActualPlayer.getElectricityStockage());
        ActualCoin.setText("Coin " + ActualPlayer.getCoin());

        if(ActualPlayer.getCoin() >= 10 && mFactory.get(0).getName().equals("Not Factory")){
            Factory MyFact = new Factory(-1);
            db.deleteFactory("Not Factory");
            mFactory.remove(0);
            db.insertFactory(MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getRequiredCost(), MyFact.getUpgradeCost(), MyFact.getElecGenerate(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
            mFactory = db.infoFactory(mFactory);
            initRecyclerViewFactory();
        }
    }

    public void displayQuest(){
        gv.setAdapter(new QuestAdapter(this, ActualQuest.getEarthObjectRequest(), ActualQuest.getNbRequest()));
    }

    public void incrementEnergy(int N){
        db = new Database(this);
        //Augmente l'énergie en fonction de l'usine actuel
        if(mFactory.size() > 0){
            int EnergyWin = 0, Pollution = 0, Cost = 0;
            for (int i = 0; i < mFactory.size(); i++){
                EnergyWin = EnergyWin + mFactory.get(i).getElecGenerate();
                Pollution = Pollution + mFactory.get(i).getPollutionTax();
                Cost = Cost + mFactory.get(i).getOperatingCost();
            }
            ElecGenerate.setText("Énergie produite : " + EnergyWin);
            OperatingCost.setText("Coût de production : " + Cost);
            PollutionTax.setText("Taxe : " + Pollution);
            ActualPlayer.setElectricityStockage(ActualPlayer.getElectricityStockage() + EnergyWin);
            if(EnergyWin != 0) {
                db.updateElecPoint(ActualPlayer.getName(), EnergyWin);
            }
            ActualElecStockage.setText("Energy " + ActualPlayer.getElectricityStockage());
        }
        //Réduit l'énergie & donne des coins en fonction des bâtiments possédé
        if(mEarthObject.size() > 0){
            int EnergyCost = 0, CoinWin = 0;
            for (int i = 0; i < mEarthObject.size(); i++){
                EnergyCost = EnergyCost + mEarthObject.get(i).getEnergyCost();
                CoinWin = CoinWin + mEarthObject.get(i).getCoinWin();
            }
            if(ActualPlayer.getElectricityStockage() > EnergyCost && ActualPlayer.getElectricityStockage() - EnergyCost > 0) {
                ActualPlayer.setElectricityStockage(ActualPlayer.getElectricityStockage() - EnergyCost);
                db.updateElecPoint(ActualPlayer.getName(), -EnergyCost);
                ActualElecStockage.setText("Energy " + ActualPlayer.getElectricityStockage());

                ActualPlayer.setCoin(ActualPlayer.getCoin() + CoinWin);
                db.updateCoin(ActualPlayer.getName(), CoinWin);
                ActualCoin.setText("Coin " + ActualPlayer.getCoin());
            }
            ElecCost.setText("Coût en Energie : " + EnergyCost);
            CoinWins.setText("Argent Produit : " + CoinWin);
        }

        if(N == 10) {
            decrementCoin();
            N = 0;
        }

        db.close();
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
        if(mFactory.size() > 0){
            int OperatingCost = 0, PollutionTax = 0;
            for (int i = 0; i < mFactory.size(); i++){
                OperatingCost = OperatingCost + mFactory.get(i).getOperatingCost();
                PollutionTax = PollutionTax + mFactory.get(i).getPollutionTax();
            }
            ActualPlayer.setCoin(ActualPlayer.getCoin() -(OperatingCost + PollutionTax));
            db.updateCoin(ActualPlayer.getName(), -(OperatingCost + PollutionTax));
            ActualCoin.setText("Coin " + ActualPlayer.getCoin());
        }
    }

    public static PlayerData getActualPlayer() {
        return ActualPlayer;
    }

    private void initRecyclerViewCity(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCity.setLayoutManager(layoutManager);
        RecyclerViewAdapterEarth adapter = new RecyclerViewAdapterEarth(this, mEarthObject);
        recyclerViewCity.setAdapter(adapter);

    }

    private void initRecyclerViewFactory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewFactory.setLayoutManager(layoutManager);
        RecyclerViewAdapterFactory adapter = new RecyclerViewAdapterFactory(this, mFactory);
        recyclerViewFactory.setAdapter(adapter);

    }
}
