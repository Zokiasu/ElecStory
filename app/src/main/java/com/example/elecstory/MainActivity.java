package com.example.elecstory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Craft.CraftActivity;
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
import java.util.Date;
import java.util.Random;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    public TextView ActualElecPoint;
    public TextView ActualCoin;
    public TextView ElecGenerate;
    public TextView ActualFactoryCost;
    public TextView ActualFactoryTax;
    public TextView ElecCost;
    public TextView CoinWins;
    public TextView DisplayQuestName;

    public Button UpElecPoint;
    public Button Shop;
    public Button Craft;
    public Button ListCraft;
    public Button RandomBonus;

    public RecyclerView recyclerViewCity;
    public RecyclerView recyclerViewFactory;

    private ArrayList<EarthObject> mEarthObject = new ArrayList<>();
    private ArrayList<Factory> mFactory = new ArrayList<>();

    private Date TimeStart;
    private Date TimeEnd;

    private int FactoryEnergyWin = 0;
    private int FactoryPollution = 0;
    private int FactoryCost = 0;

    private int EarthObjectEnergyCost = 0;
    private int EarthObjectCoinWin = 0;

    public ConstraintLayout currentLayout;

    public GridView gv;

    public Quest ActualQuest;

    public ImageView DisplayQuestImage;

    public static PlayerData ActualPlayer;

    private Database db;

    private Boolean Start = true;

    private static final String TAG = "MainActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        initFindViewById();

        db = new Database(this);

        ActualPlayer = db.infoFirstPlayer();
        ActualQuest = new Quest(db.infoFirstPlayer().getQuestId());
        DisplayQuestName.setText(ActualQuest.getNameReward());
        DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());
        displayQuest();

        RandomBonus.setVisibility(View.INVISIBLE);

        ActualCoin.setText("Coin " + ActualPlayer.getCoin());
        ActualElecPoint.setText("Energy " + ActualPlayer.getElectricityPoint());

        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
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
                finish();
            }
        });

        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        UpElecPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeEnergy();
            }
        });
        initFactoryVar();
        initEarthObjectVar();
        recursionUpDownPoint(0);
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new Database(this);
        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoCity(mEarthObject);
        db.close();

        Start = true;
        initFactoryVar();
        initEarthObjectVar();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Start = false;
    }

    public void recursionUpDownPoint(int N){
        if(Start) {
            //Augmente l'énergie en fonction de l'usine actuel
            if (mFactory.size() > 0) {
                if (FactoryEnergyWin != 0) {
                    db.updateElecPoint(ActualPlayer.getName(), FactoryEnergyWin);
                    ActualPlayer = db.infoFirstPlayer();
                }

                if (N%60 == 1) {
                    db.updateCoin(ActualPlayer.getName(), -(FactoryCost + FactoryPollution));
                    ActualPlayer = db.infoFirstPlayer();
                }
            }

            //Réduit l'énergie & donne des coins en fonction des bâtiments possédé
            if (mEarthObject.size() > 0) {
                if (ActualPlayer.getElectricityPoint() >= EarthObjectEnergyCost && (ActualPlayer.getElectricityPoint() - EarthObjectEnergyCost) >= 0 && N % 2 == 1) {
                    db.updateElecPoint(ActualPlayer.getName(), -EarthObjectEnergyCost);
                    db.updateCoin(ActualPlayer.getName(), EarthObjectCoinWin);
                    ActualPlayer = db.infoFirstPlayer();
                }
            }

            if(N == 300){
                Toast.makeText(MainActivity.this, "RandomButton", Toast.LENGTH_LONG).show();
                N = 0;
            }

            if (N%60 == 1) {
                initRecyclerViewCity();
                initRecyclerViewFactory();
                randomButton();
            }

            if(N%30 == 1) {
                initFactoryVar();
                initEarthObjectVar();
            }

            ActualElecPoint.setText("Energy " + ActualPlayer.getElectricityPoint());
            ActualCoin.setText("Coin " + ActualPlayer.getCoin());
            ElecCost.setText("Cost: " + EarthObjectEnergyCost);
            CoinWins.setText("Coin Win: " + EarthObjectCoinWin);
            ElecGenerate.setText("Production E: " + FactoryEnergyWin);
            ActualFactoryCost.setText("Production Cost: " + FactoryCost);
            ActualFactoryTax.setText("Tax: " + FactoryPollution);

            refreshRecursion(1000, N);
        }
    }

    private void refreshRecursion(int milli, final int N){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recursionUpDownPoint(N+1);
            }
        };

        handler.postDelayed(runnable, milli);
    }

    @SuppressLint("NewApi")
    protected void randomButton(){
        Random r = new Random();
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f,0.0f);
        alphaAnim.setStartOffset(1);                        // start in 5 seconds
        alphaAnim.setDuration(10000);
        alphaAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
                RandomBonus.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation)
            {
                // make invisible when animation completes, you could also remove the view from the layout
                RandomBonus.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        RandomBonus.setAnimation(alphaAnim);
    }

    public void displayQuest(){
        gv.setAdapter(new QuestAdapter(this, ActualQuest.getEarthObjectRequest(), ActualQuest.getNbRequest()));
    }

    public void upgradeQuest(){
        if(ActualQuest.checkQuest(mEarthObject, db)) {

            mEarthObject.clear();
            mEarthObject = db.infoCity(mEarthObject);
            initRecyclerViewCity();

            EarthObject TmpObject = new EarthObject(ActualQuest.getIdQuest(), ActualQuest.getNameReward());
            db.insertCraft(TmpObject.getNbObject(), TmpObject.getName(), TmpObject.getCoinWin(), TmpObject.getPriceObject(), TmpObject.getEnergyCost(), TmpObject.getSkin());

            ActualQuest = new Quest(ActualQuest.getIdQuest() + 1);

            db.updateQuest(ActualQuest.getIdQuest());
            db.infoFirstPlayer().setQuestId(ActualQuest.getIdQuest());

            DisplayQuestName.setText(ActualQuest.getNameReward());
            DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());

            displayQuest();
            initEarthObjectVar();

            if(ActualQuest.getIdQuest() == 9){
                Toast.makeText(this, "Vous avez réalisé la dernière quête pour le moment !", Toast.LENGTH_LONG).show();
                Craft.setVisibility(View.INVISIBLE);
            }
        } else {
            /*A modifier dès que possible NON PRIORITAIRE*/
            Toast.makeText(this, "Vous ne possédez pas les objets nécessaire pour cette fabrication", Toast.LENGTH_LONG).show();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void upgradeEnergy() {
        db.updateElecPoint(ActualPlayer.getName(), 1);
        db.updateCoin(ActualPlayer.getName(), 1);
        ActualPlayer = db.infoFirstPlayer();
        ActualElecPoint.setText("Energy " + ActualPlayer.getElectricityPoint());
        ActualCoin.setText("Coin " + ActualPlayer.getCoin());

        if(ActualPlayer.getCoin() >= 10 && mFactory.size() == 0){
            Factory MyFact = new Factory(-1);
            /*db.deleteFactory("Not Factory");
            mFactory.remove(0);*/
            db.insertFactory(MyFact.getNbObject(), MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getPriceFactory(), MyFact.getUpgradeCost(), MyFact.getElecGenerate(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
            mFactory = db.infoFactory(mFactory);
            initFactoryVar();
            initEarthObjectVar();
            initRecyclerViewFactory();
            Toast.makeText(MainActivity.this, "You win a "+ MyFact.getName() +"!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerViewCity(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCity.setLayoutManager(layoutManager);
        RecyclerViewAdapterEarth adapter = new RecyclerViewAdapterEarth(this, mEarthObject);
        recyclerViewCity.setAdapter(adapter);

    }

    public void initRecyclerViewFactory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewFactory.setLayoutManager(layoutManager);
        RecyclerViewAdapterFactory adapter = new RecyclerViewAdapterFactory(this, mFactory);
        recyclerViewFactory.setAdapter(adapter);

    }

    private void initFindViewById(){
        currentLayout = findViewById(R.id.activity_main);

        //Information Player
        ActualCoin = findViewById(R.id.ElecCoins);
        ActualElecPoint = findViewById(R.id.ElecStockage);

        //Information Factory
        ElecGenerate = findViewById(R.id.ElecGenerate);
        ActualFactoryCost = findViewById(R.id.OperatingCost);
        ActualFactoryTax = findViewById(R.id.PollutionTax);

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
        RandomBonus = findViewById(R.id.randomButton);

        //Affichage liste
        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewFactory = findViewById(R.id.recyclerViewFactory);
        gv = findViewById(R.id.requestObject);
    }

    private void initFactoryVar(){
        FactoryEnergyWin = 0;
        FactoryPollution = 0;
        FactoryCost = 0;

        for (int i = 0; i < mFactory.size(); i++) {
            FactoryEnergyWin = FactoryEnergyWin + (mFactory.get(i).getElecGenerate()*mFactory.get(i).getNbObject());
            FactoryPollution = FactoryPollution + (mFactory.get(i).getPollutionTax()*mFactory.get(i).getNbObject());
            FactoryCost = FactoryCost + (mFactory.get(i).getOperatingCost()*mFactory.get(i).getNbObject());
        }
    }

    private void initEarthObjectVar(){
        EarthObjectEnergyCost = 0;
        EarthObjectCoinWin = 0;

        for (int i = 0; i < mEarthObject.size(); i++) {
            EarthObjectEnergyCost = EarthObjectEnergyCost + (mEarthObject.get(i).getEnergyCost()*mEarthObject.get(i).getNbObject());
            EarthObjectCoinWin = EarthObjectCoinWin + (mEarthObject.get(i).getCoinWin()*mEarthObject.get(i).getNbObject());
        }
    }
}
