package com.example.elecstory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.OtherClass.ItemOffsetDecoration;
import com.example.elecstory.OtherClass.RecyclerViewAdapterEarth;
import com.example.elecstory.OtherClass.RecyclerViewAdapterFactory;
import com.example.elecstory.ShopEarthObject.ShopEarthActivity;
import com.example.elecstory.Database.*;
import com.example.elecstory.Object.*;
import com.example.elecstory.Quest.*;
import com.example.elecstory.ShopFactory.ShopFactoryActivity;

import java.text.DateFormat;
import java.text.NumberFormat;
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

    protected Button UpPoint;
    protected Button Unlock;
    protected ImageButton AnimationBallon;
    protected Button ShopFactory;
    protected Button ShopEarthObject;

    protected RecyclerView recyclerViewCity;
    protected RecyclerView recyclerViewFactory;

    protected RecyclerViewAdapterEarth adapterE;
    protected RecyclerViewAdapterFactory adapterF;

    protected ArrayList<EarthObject> mEarthObject = new ArrayList<>();
    protected ArrayList<Factory> mFactory = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    protected CardView CardPlayer;
    protected TextView PseudoPlayer;
    //Variable pub
    protected TextView CoinFreeText;
    protected CardView CoinFree;
    protected Calendar CoinFreeEnd;

    protected TextView SpeedAdText;
    protected CardView SpeedAd;
    protected Calendar SpeedAdEnd;
    protected int Speed = 1;

    protected TextView MultiAdText;
    protected CardView MultiAd;
    protected Calendar MultiAdEnd;
    protected int Multiple = 1;

    protected int FactoryEnergyWin = 0;
    protected int FactoryPollution = 0;
    protected int FactoryCost = 0;

    protected long EarthObjectEnergyCost = 0;
    protected int EarthObjectCoinWin = 0;

    protected int AfkCoinWin = 0;

    protected LinearLayout FactoryInfos;

    protected ConstraintLayout currentLayout;

    protected GridView gv;

    protected Quest ActualQuest;

    protected ImageView DisplayQuestImage;

    protected static PlayerData ActualPlayer;

    protected Database db = new Database(this);

    protected Boolean Start = true;

    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

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
        Start = true;
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Start = true;
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateByDb();
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

        initFindViewById();

        ActualPlayer = db.infoFirstPlayer();
        PseudoPlayer.setText(ActualPlayer.getName());
        ActualQuest = new Quest(ActualPlayer.getQuestId());

        DisplayQuestName.setText(ActualQuest.getNameReward());
        DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());
        displayQuest();

        ActualElecPoint.setText(numberFormat.format(ActualPlayer.getEnergyPoint()));
        ActualCoin.setText(numberFormat.format(ActualPlayer.getCoin()));

        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoEarthObject(mEarthObject);

        if(mFactory.size() < 1) {
            recyclerViewFactory.setVisibility(View.INVISIBLE);
            FactoryInfos.setVisibility(View.INVISIBLE);
        }

        CoinFree.setVisibility(View.INVISIBLE);
        AnimationBallon.setVisibility(View.INVISIBLE);
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

        if(Start) {
            //Augmente l'énergie en fonction de des usines actuelles
            if (mFactory.size() > 0) {
                if (FactoryEnergyWin != 0) {
                    ActualPlayer.setEnergyPoint(ActualPlayer.getEnergyPoint() + FactoryEnergyWin);
                }

                if (N%60 == 1) {
                    ActualPlayer.setCoin(ActualPlayer.getCoin() - (FactoryCost + FactoryPollution));
                }
            }

            //Réduit l'énergie & donne des coins en fonction des bâtiments possédé
            if (mEarthObject.size() > 0) {
                if (ActualPlayer.getEnergyPoint() >= EarthObjectEnergyCost &&
                   (ActualPlayer.getEnergyPoint() - EarthObjectEnergyCost) >= 0 && N%2 == 1)
                {
                    ActualPlayer.setEnergyPoint(ActualPlayer.getEnergyPoint() - EarthObjectEnergyCost);
                    ActualPlayer.setCoin(ActualPlayer.getCoin() + EarthObjectCoinWin*Multiple);
                }
            }

            if(ActualQuest.getIdQuest() >= 3) {
                MultiAd.setVisibility(View.VISIBLE);
                if(ActualQuest.getIdQuest() >= 4) {
                    SpeedAd.setVisibility(View.VISIBLE);
                }
            }

            if(N%300 == 1 && N > 1){
                CoinFreeAnimation();
            }

            if(N%120 == 1 && N > 1){
                updateByDb();
            }

            if(N%60 == 1 && N > 1){
                AnimationBallon();
            }

            checkBoostAds();

            ActualElecPoint.setText(numberFormat.format(ActualPlayer.getEnergyPoint()));
            ActualCoin.setText(numberFormat.format(ActualPlayer.getCoin()));

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
    protected void AnimationBallon(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        TranslateAnimation test = null;

        Random rand = new Random();
        int nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
        switch (nombreAleatoire){
            case 1:
                test = new TranslateAnimation(0, metrics.widthPixels, metrics.heightPixels, -metrics.heightPixels);
                break;
            case 2:
                test = new TranslateAnimation(metrics.widthPixels, -metrics.widthPixels, metrics.heightPixels, -metrics.heightPixels);
                break;
            case 3:
                test = new TranslateAnimation(metrics.widthPixels/2,0,metrics.heightPixels, -metrics.heightPixels);
                break;
        }

        test.setStartOffset(100);
        test.setDuration(35000);
        test.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                AnimationBallon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationBallon.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        AnimationBallon.setAnimation(test);
    }

    protected void CoinFreeAnimation(){
        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f,1.0f);
        alphaAnim.setDuration(60000);
        alphaAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
                CoinFree.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
                CoinFree.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        CoinFree.setAnimation(alphaAnim);
    }

    //Affiche la quête actuel du joueur
    protected void displayQuest(){
        gv.setAdapter(new QuestAdapter(this, ActualQuest.getEarthObjectRequest(), ActualQuest.getNbRequest()));
    }

    //Vérifie que la quête actuelle est réalisé et passe à la quête suivante
    protected void upgradeQuest(){
        if(ActualQuest.checkQuest(mEarthObject, db)) {

            mEarthObject.clear();
            mEarthObject = db.infoEarthObject(mEarthObject);
            initRecyclerViewEarthObject();

            EarthObject TmpObject = new EarthObject(ActualQuest.getIdQuest(), ActualQuest.getNameReward());
            db.insertCraft(TmpObject.getNbObject(), TmpObject.getName(), TmpObject.getCoinWin(), TmpObject.getPriceObject(), TmpObject.getEnergyCost(), TmpObject.getSkin());

            ActualQuest = new Quest(ActualQuest.getIdQuest() + 1);

            db.updateQuest(ActualQuest.getIdQuest());
            ActualPlayer.setQuestId(ActualQuest.getIdQuest());

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
        ActualPlayer.setEnergyPoint(ActualPlayer.getEnergyPoint() + 1);
        ActualPlayer.setCoin(ActualPlayer.getCoin() + 1);

        ActualElecPoint.setText(numberFormat.format(ActualPlayer.getEnergyPoint()));
        ActualCoin.setText(numberFormat.format(ActualPlayer.getCoin()));

        if(ActualPlayer.getCoin() >= 10 && mFactory.size() == 0){
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
    protected void updateByDb(){
        int X, Y;
        X = mFactory.size();
        Y = mEarthObject.size();
        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoEarthObject(mEarthObject);

        db.updateEnergyPoint(ActualPlayer.getName(), ActualPlayer.getEnergyPoint());
        db.updateCoin(ActualPlayer.getName(), ActualPlayer.getCoin());

        ActualPlayer = db.infoFirstPlayer();

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
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.size_factory_adapter_space);
        recyclerViewCity.addItemDecoration(itemDecoration);
    }

    //Initialise les RecyclerView des Factory
    protected void initRecyclerViewFactory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFactory.setLayoutManager(layoutManager);
        adapterF = new RecyclerViewAdapterFactory(this, mFactory, this);
        recyclerViewFactory.setAdapter(adapterF);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.size_factory_adapter_space);
        recyclerViewFactory.addItemDecoration(itemDecoration);
    }

    //Initialise tout les findViewById
    protected void initFindViewById(){
        currentLayout = findViewById(R.id.activity_main);
        CardPlayer = findViewById(R.id.cardPlayer);
        PseudoPlayer = findViewById(R.id.pseudoPlayer);

        //Relative ads
        CoinFree = findViewById(R.id.coinFree);
        SpeedAd = findViewById(R.id.speedAds);
        MultiAd = findViewById(R.id.multiAds);

        CoinFreeText = findViewById(R.id.coinFreeText);
        SpeedAdText = findViewById(R.id.speedAdsText);
        MultiAdText = findViewById(R.id.multiAdsText);

        //Information Player
        ActualCoin = findViewById(R.id.ElecCoins);
        ActualElecPoint = findViewById(R.id.ElecStockage);

        //Quest
        DisplayQuestImage = findViewById(R.id.requestImage);
        DisplayQuestName = findViewById(R.id.requestName);

        //Button
        ShopFactory = findViewById(R.id.ShopButton);
        Unlock = findViewById(R.id.ActionCraft);
        ShopEarthObject = findViewById(R.id.ListCraft);
        UpPoint = findViewById(R.id.ElecUp);
        AnimationBallon = findViewById(R.id.ballon);

        //Affichage liste
        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewFactory = findViewById(R.id.recyclerViewFactory);
        gv = findViewById(R.id.requestObject);
    }

    //Initialise les différentes actions des boutons
    protected void initButtonAction(){

        CoinFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call CoinFree");
                coinFree();
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

        Unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeQuest();
            }
        });

        ShopEarthObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ShopEarthActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ShopFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ShopFactoryActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        UpPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
                alpha.setDuration(200);

                UpPoint.startAnimation(alpha);
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

    /////Fonction Ads/////
    protected void coinFree(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(CoinFreeEnd)) {
            CoinFreeEnd = Calendar.getInstance();
            CoinFreeEnd.add(Calendar.MINUTE, 5);

            Random X = new Random();
            int nombreAleatoire = X.nextInt(100 - 1 + 1) + 1, A = 0;
            initEarthObjectVar();
            if (nombreAleatoire > 1 && nombreAleatoire < 20) {
                A = EarthObjectCoinWin*(15);
                ActualPlayer.setCoin(ActualPlayer.getCoin() + A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 20 && nombreAleatoire < 50) {
                A = EarthObjectCoinWin*(30);
                ActualPlayer.setCoin(ActualPlayer.getCoin() + A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 50 && nombreAleatoire < 80) {
                A = EarthObjectCoinWin*(60);
                ActualPlayer.setCoin(ActualPlayer.getCoin() + A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else if (nombreAleatoire > 80 && nombreAleatoire < 100) {
                A = EarthObjectCoinWin*(90);
                ActualPlayer.setCoin(ActualPlayer.getCoin() + A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            } else {
                A = EarthObjectCoinWin*(120);
                ActualPlayer.setCoin(ActualPlayer.getCoin() + A);
                Toast.makeText(MainActivity.this,"You have won "+A+" coins",Toast.LENGTH_LONG).show();
            }
        }
        ActualCoin.setText(numberFormat.format(ActualPlayer.getCoin()));
    }

    protected void speedAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(SpeedAdEnd)) {

            SpeedAdEnd = Calendar.getInstance();
            SpeedAdEnd.add(Calendar.HOUR, 4);
            db.insertSpeedAds(dateFormat.format(SpeedAdEnd.getTime()));

            Speed = 2;
            Toast.makeText(MainActivity.this,"The speed has been multiplied by 2!",Toast.LENGTH_LONG).show();
        }
    }

    protected void multiAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(MultiAdEnd)) {

            MultiAdEnd = Calendar.getInstance();
            MultiAdEnd.add(Calendar.HOUR, 4);
            db.insertMultiAds(dateFormat.format(MultiAdEnd.getTime()));

            Multiple = 2;
            Toast.makeText(MainActivity.this,"You earn " + Multiple + " times more coins",Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    protected String displayHMS(Calendar Deb, Calendar Fin){

        int Y, M, D, h, m, s, totalSecond;

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

        Date deb = new GregorianCalendar(Y,M,D,h,m,s).getTime();

        Y = Integer.valueOf(year.format(Fin.getTime()));
        M = Integer.valueOf(month.format(Fin.getTime()));
        D = Integer.valueOf(day.format(Fin.getTime()));

        h = Integer.valueOf(hours.format(Fin.getTime()));
        m = Integer.valueOf(minute.format(Fin.getTime()));
        s = Integer.valueOf(second.format(Fin.getTime()));

        Date fin = new GregorianCalendar(Y,M,D,h,m,s).getTime();

        long hello = deb.getTime() - fin.getTime();
        totalSecond = Math.abs((int)((hello)/(1000)));
        h = totalSecond/(60*60);
        m = (totalSecond/60)%60;
        s = totalSecond%60;

        return h+":"+m+":"+s;
    }

    //Reinitialisation des boosts
    protected void checkBoostAds(){
        Calendar ActualDate = Calendar.getInstance();

        if(ActualDate.after(MultiAdEnd)) {
            Multiple = 1;
            MultiAdText.setText("Coin x2");
        } else {
            Multiple = 2;
            MultiAdText.setText(displayHMS(ActualDate, MultiAdEnd));
        }

        if(ActualDate.after(SpeedAdEnd)) {
            Speed = 1;
            SpeedAdText.setText("Time x2");
        } else {
            Speed = 2;
            SpeedAdText.setText(displayHMS(ActualDate, SpeedAdEnd));
        }

        if(ActualDate.after(CoinFreeEnd)) {
            CoinFreeText.setText("Free Coin");
        } else {
            CoinFreeText.setText(displayHMS(ActualDate, CoinFreeEnd));
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

        CoinFreeEnd = Calendar.getInstance();
        try {
            date = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CoinFreeEnd.setTime(date);

        MultiAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoMultiAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MultiAdEnd.setTime(date);
    }
}