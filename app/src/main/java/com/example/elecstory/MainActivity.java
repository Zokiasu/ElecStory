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
import android.widget.RelativeLayout;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    protected TextView ActualElecPoint;
    protected TextView ActualCoin;
    protected TextView ElecGenerate;
    protected TextView ActualFactoryCost;
    protected TextView ElecCost;
    protected TextView CoinWins;
    protected TextView DisplayQuestName;

    protected Button UpElecPoint;
    protected Button Shop;
    protected Button Unlock;
    protected Button ListCraft;
    protected Button RandomBonus;

    protected RecyclerView recyclerViewCity;
    protected RecyclerView recyclerViewFactory;

    protected RecyclerViewAdapterEarth adapterE;
    protected RecyclerViewAdapterFactory adapterF;

    protected ArrayList<EarthObject> mEarthObject = new ArrayList<>();
    protected ArrayList<Factory> mFactory = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    protected TextView CoinAdText;
    protected RelativeLayout CoinAd;
    protected Calendar CoinAdEnd;

    protected TextView SpeedAdText;
    protected RelativeLayout SpeedAd;
    protected Calendar SpeedAdEnd;
    protected int Speed = 1;

    protected TextView MultiAdText;
    protected RelativeLayout MultiAd;
    protected Calendar MultiAdEnd;
    protected int Multiple = 1;

    protected int FactoryEnergyWin = 0;
    protected int FactoryPollution = 0;
    protected int FactoryCost = 0;

    protected int EarthObjectEnergyCost = 0;
    protected int EarthObjectCoinWin = 0;

    protected LinearLayout FactoryInfos;

    protected ConstraintLayout currentLayout;

    protected GridView gv;

    protected Quest ActualQuest;

    protected ImageView DisplayQuestImage;

    protected static PlayerData ActualPlayer;

    protected Database db = new Database(this);

    protected Boolean Start = true;

    protected static final String TAG = "MainActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        createOrRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        recursionUpDownPoint(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    protected void createOrRestart(){
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        initFindViewById();

        ActualPlayer = db.infoFirstPlayer();
        ActualQuest = new Quest(db.infoFirstPlayer().getQuestId());

        DisplayQuestName.setText(ActualQuest.getNameReward());
        DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());
        displayQuest();

        ActualCoin.setText("Coin : " + db.infoFirstPlayer().getCoin());
        ActualElecPoint.setText("Energy : " + db.infoFirstPlayer().getElectricityPoint());

        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoCity(mEarthObject);

        if(mFactory.size() < 1) {
            recyclerViewFactory.setVisibility(View.INVISIBLE);
            FactoryInfos.setVisibility(View.INVISIBLE);
        }

        RandomBonus.setVisibility(View.INVISIBLE);
        CoinAd.setVisibility(View.INVISIBLE);
        SpeedAd.setVisibility(View.INVISIBLE);
        MultiAd.setVisibility(View.INVISIBLE);

        initButtonAction();
        initFactoryVar();
        initEarthObjectVar();
        initRecyclerViewFactory();
        initRecyclerViewEarthObject();
        initAdEnd();

        recursionUpDownPoint(0);
        db.close();
    }

    protected void recursionUpDownPoint(int N){

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        if(Start) {
            ActualPlayer = db.infoFirstPlayer();
            //Augmente l'énergie en fonction de des usines actuelles
            if (mFactory.size() > 0) {
                if (FactoryEnergyWin != 0) {
                    db.updateElecPoint(ActualPlayer.getName(), FactoryEnergyWin);
                }

                if (N%60 == 1) {
                    db.updateCoin(ActualPlayer.getName(), -(FactoryCost + FactoryPollution));
                }
            }

            //Réduit l'énergie & donne des coins en fonction des bâtiments possédé
            if (mEarthObject.size() > 0) {
                if (db.infoFirstPlayer().getElectricityPoint() >= EarthObjectEnergyCost && (db.infoFirstPlayer().getElectricityPoint() - EarthObjectEnergyCost) >= 0 && N % 2 == 1) {
                    db.updateElecPoint(ActualPlayer.getName(), -EarthObjectEnergyCost);
                    db.updateCoin(ActualPlayer.getName(), EarthObjectCoinWin*Multiple);
                }
            }

            if(ActualQuest.getIdQuest() >= 2){
                CoinAd.setVisibility(View.VISIBLE);
                if(ActualQuest.getIdQuest() >= 3) {
                    MultiAd.setVisibility(View.VISIBLE);
                    if(ActualQuest.getIdQuest() >= 4) {
                        SpeedAd.setVisibility(View.VISIBLE);
                    }
                }
            }

            if(N%60 == 1 && N > 1){
                //randomButton();
                N = 0;
            }

            if(N%30 == 1 && N > 1) {
                updatingList();
                adapterE.notifyDataSetChanged();
                adapterF.notifyDataSetChanged();
                initFactoryVar();
                initEarthObjectVar();
            }

            checkBoostAds();

            ActualElecPoint.setText("Energy : " + ActualPlayer.getElectricityPoint());
            ActualCoin.setText("Coin : " + ActualPlayer.getCoin());
            ElecCost.setText("Energy Use: " + EarthObjectEnergyCost);
            CoinWins.setText("Coin Win: " + EarthObjectCoinWin);
            ElecGenerate.setText("Energy Prod: " + FactoryEnergyWin + "/s");
            ActualFactoryCost.setText("Coin Use: " + FactoryCost + "/m");

            refreshRecursion(1000/Speed, N);
        }

    }

    protected void refreshRecursion(int milli, final int N){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recursionUpDownPoint(N+1);
            }
        };

        handler.postDelayed(runnable, milli);
    }

    //Choisit le bouton bonus à afficher aléatoirement toute les minutes
    protected void randomButton(){
        Random r = new Random();
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f,0.0f);
        alphaAnim.setStartOffset(1);
        alphaAnim.setDuration(10000);
        alphaAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
                RandomBonus.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
                RandomBonus.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        RandomBonus.setAnimation(alphaAnim);
    }

    //Convertis les millisecondes en string format hh:mm:ss
    protected static String timeConversion(int milli) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        final int MILLISECOND_IN_A_SECOND = 1000;
        int totalSeconds = milli/MILLISECOND_IN_A_SECOND;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return hours + ":" + minutes + ":" + seconds;
    }

    //Affiche la quête actuel du joueur
    protected void displayQuest(){
        gv.setAdapter(new QuestAdapter(this, ActualQuest.getEarthObjectRequest(), ActualQuest.getNbRequest()));
    }

    //Vérifie que la quête actuelle est réalisé et passe à la quête suivante
    protected void upgradeQuest(){
        if(ActualQuest.checkQuest(mEarthObject, db)) {

            mEarthObject.clear();
            mEarthObject = db.infoCity(mEarthObject);
            initRecyclerViewEarthObject();

            EarthObject TmpObject = new EarthObject(ActualQuest.getIdQuest(), ActualQuest.getNameReward());
            db.insertCraft(TmpObject.getNbObject(), TmpObject.getName(), TmpObject.getCoinWin(), TmpObject.getPriceObject(), TmpObject.getEnergyCost(), TmpObject.getSkin());

            ActualQuest = new Quest(ActualQuest.getIdQuest() + 1);

            db.updateQuest(ActualQuest.getIdQuest());
            db.infoFirstPlayer().setQuestId(ActualQuest.getIdQuest());

            DisplayQuestName.setText(ActualQuest.getNameReward());
            DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());

            displayQuest();
            initEarthObjectVar();
            Toast.makeText(this, "A new object has been added to the ShopCraft", Toast.LENGTH_LONG).show();
            if(ActualQuest.getIdQuest() == 12){
                Toast.makeText(this, "You have completed the last quest for now!", Toast.LENGTH_LONG).show();
                Unlock.setVisibility(View.INVISIBLE);
            }
        } else {
            /*A modifier dès que possible NON PRIORITAIRE*/
            Toast.makeText(this, "You do not have the necessary objects for this production", Toast.LENGTH_LONG).show();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Fonction du bouton +1
    protected void upgradeEnergy() {
        ActualPlayer = db.infoFirstPlayer();

        ActualElecPoint.setText("Energy : " + ActualPlayer.getElectricityPoint());
        db.updateElecPoint(ActualPlayer.getName(), 1);

        db.updateCoin(ActualPlayer.getName(), 1);
        ActualCoin.setText("Coin : " + ActualPlayer.getCoin());

        if(db.infoFirstPlayer().getCoin() >= 10 && mFactory.size() == 0){
            Factory MyFact = new Factory(-1);
            db.insertFactory(MyFact.getNbObject(), MyFact.getName(), MyFact.getFactoryLevel(), MyFact.getPriceFactory(), MyFact.getUpgradeCost(), MyFact.getEnergyProd(), MyFact.getOperatingCost(), MyFact.getPollutionTax(), MyFact.getSkin());
            mFactory = db.infoFactory(mFactory);
            initFactoryVar();
            initEarthObjectVar();
            initRecyclerViewFactory();
            recyclerViewFactory.setVisibility(View.VISIBLE);
            FactoryInfos.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "You win a "+ MyFact.getName() +"!", Toast.LENGTH_SHORT).show();
        }
    }

    //Update les listes utiliser par les recyclerview
    protected void  updatingList(){
        int X, Y;
        X = mFactory.size();
        Y = mEarthObject.size();
        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoCity(mEarthObject);
        if(Y != mEarthObject.size()) {
            adapterE.notifyDataSetChanged();
        }
        if(X != mFactory.size()) {
            adapterF.notifyDataSetChanged();
        }
        db.close();
    }

    //Initialise les RecyclerView des EarthObject
    protected void initRecyclerViewEarthObject(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCity.setLayoutManager(layoutManager);
        adapterE = new RecyclerViewAdapterEarth(this, mEarthObject, this);
        recyclerViewCity.setAdapter(adapterE);

    }

    //Initialise les RecyclerView des Factory
    protected void initRecyclerViewFactory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFactory.setLayoutManager(layoutManager);
        adapterF = new RecyclerViewAdapterFactory(this, mFactory, this, db);
        recyclerViewFactory.setAdapter(adapterF);
    }

    //Initialise tout les findViewById
    protected void initFindViewById(){
        currentLayout = findViewById(R.id.activity_main);
        FactoryInfos = findViewById(R.id.FactoryInfo);

        //Relative ads
        CoinAd = findViewById(R.id.coinAds);
        SpeedAd = findViewById(R.id.speedAds);
        MultiAd = findViewById(R.id.multiAds);

        CoinAdText = findViewById(R.id.coinAdsText);
        SpeedAdText = findViewById(R.id.speedAdsText);
        MultiAdText = findViewById(R.id.multiAdsText);

        //Information Player
        ActualCoin = findViewById(R.id.ElecCoins);
        ActualElecPoint = findViewById(R.id.ElecStockage);

        //Information Factory
        ElecGenerate = findViewById(R.id.ElecGenerate);
        ActualFactoryCost = findViewById(R.id.OperatingCost);

        //Information ObjectEarth
        ElecCost = findViewById(R.id.EnergyCost);
        CoinWins = findViewById(R.id.CoinWins);

        //Quest
        DisplayQuestImage = findViewById(R.id.requestImage);
        DisplayQuestName = findViewById(R.id.requestName);

        //Button
        Shop = findViewById(R.id.ShopButton);
        Unlock = findViewById(R.id.ActionCraft);
        ListCraft = findViewById(R.id.ListCraft);
        UpElecPoint = findViewById(R.id.ElecUp);
        RandomBonus = findViewById(R.id.randomButton);

        //Affichage liste
        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewFactory = findViewById(R.id.recyclerViewFactory);
        gv = findViewById(R.id.requestObject);
    }

    //Initialise les différentes actions que font chaque bouton
    protected void initButtonAction(){
        CoinAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call CoinAd");
                coinAds();
            }
        });

        SpeedAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call SpeedAd");
                speedAds();
            }
        });

        MultiAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call MultiAd");
                multiAds();
            }
        });

        RandomBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////IDK
            }
        });

        Unlock.setOnClickListener(new View.OnClickListener() {
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
    }

    //Initalise ou actualise les variables global qui sont en fonction des Factorys
    protected void initFactoryVar(){
        FactoryEnergyWin = 0;
        FactoryPollution = 0;
        FactoryCost = 0;

        for (int i = 0; i < mFactory.size(); i++) {
            FactoryEnergyWin = FactoryEnergyWin + (mFactory.get(i).getEnergyProd()*mFactory.get(i).getNbObject());
            FactoryPollution = FactoryPollution + (mFactory.get(i).getPollutionTax()*mFactory.get(i).getNbObject());
            FactoryCost = FactoryCost + (mFactory.get(i).getOperatingCost()*mFactory.get(i).getNbObject()) + (mFactory.get(i).getPollutionTax()*mFactory.get(i).getNbObject());
        }
    }

    //Initalise ou actualise les variables global qui sont en fonction des EarthObject
    protected void initEarthObjectVar(){
        EarthObjectEnergyCost = 0;
        EarthObjectCoinWin = 0;

        for (int i = 0; i < mEarthObject.size(); i++) {
            EarthObjectEnergyCost = EarthObjectEnergyCost + (mEarthObject.get(i).getEnergyCost()*mEarthObject.get(i).getNbObject());
            EarthObjectCoinWin = EarthObjectCoinWin + (mEarthObject.get(i).getCoinWin()*mEarthObject.get(i).getNbObject());
        }
    }

    protected void initAdEnd(){

        Date date = null;

        SpeedAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoSpeedAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SpeedAdEnd.setTime(date);

        CoinAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoCoinAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CoinAdEnd.setTime(date);

        MultiAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoMultiAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MultiAdEnd.setTime(date);
    }

    protected String displayHMS(Calendar Deb, Calendar Fin){

        int Y, M, D, h, m, s, totalS;

        SimpleDateFormat hours = new SimpleDateFormat("HH");
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        SimpleDateFormat second = new SimpleDateFormat("ss");

        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        Y = Integer.valueOf(year.format(Deb.getTime()));
        M = Integer.valueOf(month.format(Deb.getTime()));
        D = Integer.valueOf(day.format(Deb.getTime()));

        h = Integer.valueOf(hours.format(Deb.getTime()));
        m = Integer.valueOf(minute.format(Deb.getTime()));
        s = Integer.valueOf(second.format(Deb.getTime()));

        Date deb1 = new GregorianCalendar(Y,M,D,h,m,s).getTime();

        Y = Integer.valueOf(year.format(Fin.getTime()));
        M = Integer.valueOf(month.format(Fin.getTime()));
        D = Integer.valueOf(day.format(Fin.getTime()));

        h = Integer.valueOf(hours.format(Fin.getTime()));
        m = Integer.valueOf(minute.format(Fin.getTime()));
        s = Integer.valueOf(second.format(Fin.getTime()));

        Date fin2 = new GregorianCalendar(Y,M,D,h,m,s).getTime();

        long hello = deb1.getTime() - fin2.getTime();
        totalS = Math.abs((int)((hello)/(1000)));
        h = totalS/(60*60);
        m = (totalS/60)%60;
        s = totalS%60;

        return h+":"+m+":"+s;
    }

    //Reinitialisation des boosts
    protected void checkBoostAds(){
        Calendar ActualDate = Calendar.getInstance();

        if(ActualDate.after(MultiAdEnd)) {
            Multiple = 1;
            MultiAdText.setText("X2");
        } else {
            Multiple = 2;
            MultiAdText.setText(displayHMS(ActualDate, MultiAdEnd));
        }

        if(ActualDate.after(SpeedAdEnd)) {
            Speed = 1;
            SpeedAdText.setText("Speed");
        } else {
            Speed = 2;
            SpeedAdText.setText(displayHMS(ActualDate, SpeedAdEnd));
        }

        if(ActualDate.after(CoinAdEnd)) {
            CoinAdText.setText("Coins");
        } else {
            CoinAdText.setText(displayHMS(ActualDate, CoinAdEnd));
        }
    }

    /////Fonction Ads/////
    protected void coinAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(CoinAdEnd)) {
            CoinAdEnd = Calendar.getInstance();
            CoinAdEnd.add(Calendar.HOUR, 4);
            db.insertCoinAds(dateFormat.format(CoinAdEnd.getTime()));

            Random X = new Random();
            int nombreAleatoire = X.nextInt(100 - 1 + 1) + 1, A = 0;
            initEarthObjectVar();
            if (nombreAleatoire > 1 && nombreAleatoire < 20) {
                A = EarthObjectCoinWin*(900);
                db.updateCoin(db.infoFirstPlayer().getName(), A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 20 && nombreAleatoire < 50) {
                A = EarthObjectCoinWin*(1800);
                db.updateCoin(db.infoFirstPlayer().getName(), A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 50 && nombreAleatoire < 80) {
                A = EarthObjectCoinWin*(2700);
                db.updateCoin(db.infoFirstPlayer().getName(), A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 80 && nombreAleatoire < 100) {
                A = EarthObjectCoinWin*(3600);
                db.updateCoin(db.infoFirstPlayer().getName(), A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else {
                A = EarthObjectCoinWin*(7200);
                db.updateCoin(db.infoFirstPlayer().getName(), A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            }
        }
        ActualCoin.setText("Coin : " + db.infoFirstPlayer().getCoin());
    }

    protected void speedAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(SpeedAdEnd)) {

            SpeedAdEnd = Calendar.getInstance();
            SpeedAdEnd.add(Calendar.HOUR, 4);
            db.insertSpeedAds(dateFormat.format(SpeedAdEnd.getTime()));

            Speed = 2;
            Toast.makeText(MainActivity.this,"The speed has been multiplied by 2!",Toast.LENGTH_LONG);
        }
    }

    protected void multiAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(MultiAdEnd)) {

            MultiAdEnd = Calendar.getInstance();
            MultiAdEnd.add(Calendar.MINUTE, 30);
            db.insertMultiAds(dateFormat.format(MultiAdEnd.getTime()));

            Multiple = 2;
            Toast.makeText(MainActivity.this,"You earn " + Multiple + " times more coins",Toast.LENGTH_LONG);
        }
    }
}