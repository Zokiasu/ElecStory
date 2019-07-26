package com.example.elecstory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.OtherClass.AdPopup;
import com.example.elecstory.OtherClass.DiamondPopup;
import com.example.elecstory.OtherClass.InformationPopup;
import com.example.elecstory.OtherClass.ItemOffsetDecorationItem;
import com.example.elecstory.OtherClass.ItemOffsetDecorationFactory;
import com.example.elecstory.OtherClass.RecyclerViewAdapterItem;
import com.example.elecstory.OtherClass.RecyclerViewAdapterFactory;
import com.example.elecstory.Shop.ShopActivity;
import com.example.elecstory.Database.*;
import com.example.elecstory.Object.*;
import com.example.elecstory.Quest.*;

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

    protected TextView ActualEnergyPoint;
    protected TextView ActualCoin;
    protected TextView ActualDiamond;
    protected TextView DisplayQuestName;
    protected TextView MyItem;
    protected TextView CoinFreeText;
    protected TextView SpeedAdText;
    protected TextView MultiAdText;
    protected TextView SkipAdText;

    protected Button UpPoint;
    protected Button Unlock;
    protected Button Menu;
    protected Button ShopEarthObject;
    protected ImageButton AnimationBallon;

    protected LinearLayout SkipAd;
    protected LinearLayout Skip1D;
    protected LinearLayout Skip7D;
    protected LinearLayout SpeedAd;
    protected LinearLayout MultiAd;

    protected RecyclerView recyclerViewCity;
    protected RecyclerView recyclerViewFactory;

    protected RecyclerViewAdapterItem adapterE;
    protected RecyclerViewAdapterFactory adapterF;
    
    protected CardView CardPlayer;
    protected CardView CoinFree;
    protected CardView DiamondStock;

    protected Calendar CoinFreeEnd;
    protected Calendar SpeedAdEnd;
    protected Calendar MultiAdEnd;
    protected Calendar SkipAdEnd;

    protected ArrayList<EarthObject> mEarthObject = new ArrayList<>();
    protected ArrayList<Factory> mFactory = new ArrayList<>();

    protected int PriceSkip1D = 20;
    protected int PriceSkip7D = 40;
    protected int Speed = 1;
    protected int Multiple = 1;
    protected long FactoryEnergyWin = 0;
    protected long EarthObjectEnergyCost = 0;
    protected long EarthObjectCoinWin = 0;

    protected ConstraintLayout currentLayout;

    protected GridView gv;

    protected Quest ActualQuest;

    protected ImageView DisplayQuestImage;

    protected static PlayerData ActualPlayer;

    protected Database db = new Database(this);

    protected Boolean Start = true;
    protected Boolean FreeCoinAd = false;

    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    @SuppressLint("SimpleDateFormat")
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    protected static final String PREFS = "PREFS";
    protected static final String PREFS_COIN = "PREFS_COIN";
    protected static final String PREFS_ENERGY = "PREFS_ENERGY";
    protected static final String PREFS_DIAMOND = "PREFS_DIAMOND";
    protected static final String TAG = "Elecstory.MainActivity";
    
    protected SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        initFindViewById();

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        ActualPlayer = db.infoFirstPlayer();

        ActualQuest = new Quest(ActualPlayer.getQuestId());

        DisplayQuestName.setText(ActualQuest.getNameReward());
        DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());
        displayQuest();

        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoEarthObject(mEarthObject);

        if(sharedPreferences.contains(PREFS_DIAMOND) && sharedPreferences.contains(PREFS_COIN) && sharedPreferences.contains(PREFS_ENERGY)){
            ActualEnergyPoint.setText(numberFormat.format(sharedPreferences.getLong(PREFS_ENERGY, 0)));
            ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
            ActualDiamond.setText(numberFormat.format(sharedPreferences.getInt(PREFS_DIAMOND, 0)));
        } else {
            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, ActualPlayer.getCoin())
                    .putLong(PREFS_ENERGY, ActualPlayer.getEnergyPoint())
                    .putInt(PREFS_DIAMOND, ActualPlayer.getDiamond())
                    .apply();

            ActualEnergyPoint.setText(numberFormat.format(sharedPreferences.getLong(PREFS_ENERGY, 0)));
            ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
            ActualDiamond.setText(numberFormat.format(sharedPreferences.getInt(PREFS_DIAMOND, 0)));
        }

        CoinFree.setVisibility(View.INVISIBLE);
        SpeedAd.setVisibility(View.INVISIBLE);
        MultiAd.setVisibility(View.INVISIBLE);
        SkipAd.setVisibility(View.INVISIBLE);
        Skip1D.setVisibility(View.INVISIBLE);
        Skip7D.setVisibility(View.INVISIBLE);
        DiamondStock.setVisibility(View.INVISIBLE);
        AnimationBallon.setVisibility(View.INVISIBLE);

        initButtonAction();
        initFactoryVar();
        initItemVar();
        initRecyclerViewFactory();
        initRecyclerViewItem();
        initAdEnd();

        db.close();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        updateDatabase();
        initFactoryVar();
        initItemVar();
        Start = true;
        recursionVariationPoint(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        updateDatabase();
        Start = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().clear().apply();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        // do nothing.
    }

    //Initialise tout les findViewById
    protected void initFindViewById(){
        currentLayout = findViewById(R.id.activity_main);

        AnimationBallon = findViewById(R.id.ballon);

        CardPlayer = findViewById(R.id.cardPlayer);
        ActualCoin = findViewById(R.id.ElecCoins);
        ActualEnergyPoint = findViewById(R.id.ElecStockage);

        DiamondStock = findViewById(R.id.diamondStock);
        ActualDiamond = findViewById(R.id.diamondStockNb);

        CoinFree = findViewById(R.id.coinFree);
        SpeedAd = findViewById(R.id.speedAds);
        MultiAd = findViewById(R.id.multiAds);
        SkipAd = findViewById(R.id.SkipAd);
        Skip1D = findViewById(R.id.Skip1D);
        Skip7D = findViewById(R.id.Skip7D);

        CoinFreeText = findViewById(R.id.coinFreeText);
        SpeedAdText = findViewById(R.id.speedAdsText);
        MultiAdText = findViewById(R.id.multiAdsText);
        SkipAdText = findViewById(R.id.SkipAdText);

        MyItem = findViewById(R.id.MyItems);

        DisplayQuestImage = findViewById(R.id.requestImage);
        DisplayQuestName = findViewById(R.id.requestName);

        Unlock = findViewById(R.id.ActionCraft);
        ShopEarthObject = findViewById(R.id.ShopItem);
        UpPoint = findViewById(R.id.ElecUp);

        recyclerViewCity = findViewById(R.id.recyclerViewCity);
        recyclerViewFactory = findViewById(R.id.recyclerViewFactory);
        gv = findViewById(R.id.requestObject);

        Menu = findViewById(R.id.Menu);
    }

    //Initialise les RecyclerView des EarthObject
    protected void initRecyclerViewItem(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCity.setLayoutManager(layoutManager);
        adapterE = new RecyclerViewAdapterItem(this, mEarthObject, this);
        recyclerViewCity.setAdapter(adapterE);
        ItemOffsetDecorationItem itemDecoration = new ItemOffsetDecorationItem(this, R.dimen.size_adapter_space);
        recyclerViewCity.addItemDecoration(itemDecoration);
    }

    //Initialise les RecyclerView des Factory
    protected void initRecyclerViewFactory(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFactory.setLayoutManager(layoutManager);
        adapterF = new RecyclerViewAdapterFactory(this, mFactory, this);
        recyclerViewFactory.setAdapter(adapterF);
        ItemOffsetDecorationFactory itemDecoration = new ItemOffsetDecorationFactory(this, R.dimen.size_adapter_space);
        recyclerViewFactory.addItemDecoration(itemDecoration);
    }

    //Initialise les différentes actions des boutons
    protected void initButtonAction(){

        SkipAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call SkipAd");
                SkipFor30();
            }
        });

        Skip1D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call Skip1D");
                SkipFor1D();
            }
        });

        Skip7D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Call Skip7D");
                SkipFor7D();
            }
        });

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        MyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InformationPopup infoPopUp = new InformationPopup(MainActivity.this);
                infoPopUp.setNameObjectSale("Your Item's Info :");
                infoPopUp.setMessageSale("Energy Used : " + numberFormat.format(EarthObjectEnergyCost) + "\nCoin Generated : " + numberFormat.format(EarthObjectCoinWin));
                infoPopUp.getClose().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        infoPopUp.dismiss();
                    }
                });
                infoPopUp.build();
            }
        });
        CoinFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FreeCoinAd) {
                    coinFreeAd();
                } else {
                    coinFree();
                }
            }
        });

        SpeedAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedAds();
            }
        });

        MultiAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent myIntent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(myIntent);
            }
        });

        UpPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeEnergy();
            }
        });
    }

    //Initalise ou actualise les variables global qui sont en fonction des Factorys
    protected void initFactoryVar(){
        FactoryEnergyWin = 0;

        for (int i = 0; i < mFactory.size(); i++) {
            FactoryEnergyWin = FactoryEnergyWin + (mFactory.get(i).getEnergyProd()*mFactory.get(i).getNbObject());
        }
    }

    //Initalise ou actualise les variables global qui sont en fonction des items
    protected void initItemVar(){
        EarthObjectEnergyCost = 0;
        EarthObjectCoinWin = 0;

        for (int i = 0; i < mEarthObject.size(); i++) {
            EarthObjectEnergyCost = EarthObjectEnergyCost + (mEarthObject.get(i).getEnergyCost()*mEarthObject.get(i).getNbObject());
            EarthObjectCoinWin = EarthObjectCoinWin + (mEarthObject.get(i).getCoinWin()*mEarthObject.get(i).getNbObject());
        }
    }

    protected void recursionVariationPoint(int N) {

        if (Start) {
            //Augmente l'énergie en fonction de des usines actuelles
            if (mFactory.size() > 0) {
                if (FactoryEnergyWin != 0) {
                    sharedPreferences
                            .edit()
                            .putLong(PREFS_ENERGY, (sharedPreferences.getLong(PREFS_ENERGY, 0) + FactoryEnergyWin))
                            .apply();
                }
            }

            //Réduit l'énergie & donne des coins en fonction des bâtiments possédé
            if (mEarthObject.size() > 0) {
                if (sharedPreferences.getLong(PREFS_ENERGY, 0) >= EarthObjectEnergyCost && (sharedPreferences.getLong(PREFS_ENERGY, 0) - EarthObjectEnergyCost) >= 0 && N % 2 == 1) {
                    sharedPreferences
                            .edit()
                            .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + EarthObjectCoinWin * Multiple))
                            .putLong(PREFS_ENERGY, (sharedPreferences.getLong(PREFS_ENERGY, 0) - EarthObjectEnergyCost))
                            .apply();
                }
            }

            if (ActualQuest.getIdQuest() >= 3) {
                MultiAd.setVisibility(View.VISIBLE);
                if (ActualQuest.getIdQuest() >= 4) {
                    SpeedAd.setVisibility(View.VISIBLE);
                    if (ActualQuest.getIdQuest() >= 5) {
                        SkipAd.setVisibility(View.VISIBLE);
                        Skip1D.setVisibility(View.VISIBLE);
                        Skip7D.setVisibility(View.VISIBLE);
                        DiamondStock.setVisibility(View.VISIBLE);
                    }
                }
            }

            if (N % (60*Speed) == 1 && N > 1) {
                AnimationBalloon();
                updateDatabase();
            }

            if (N%180 == 1 && N > 1) {
                choiceCoinFree();
            }

            if (N%195 == 1 && N > 1) {
                CoinFree.setVisibility(View.INVISIBLE);
            }

            checkBoostAds();

            ActualEnergyPoint.setText(numberFormat.format(sharedPreferences.getLong(PREFS_ENERGY, 0)));
            ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
            ActualDiamond.setText(numberFormat.format(sharedPreferences.getInt(PREFS_DIAMOND, 0)));

            refreshRecursion(1000 / Speed, N);
        }

    }

    protected void refreshRecursion(int milli, final int N){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recursionVariationPoint(N+1);
            }
        };

        handler.postDelayed(runnable, milli);
    }

    //Choisit le bouton bonus à afficher aléatoirement toute les minutes
    protected void AnimationBalloon(){

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

    //Affiche la quête actuel du joueur
    protected void displayQuest(){
        gv.setAdapter(new QuestAdapter(this, ActualQuest.getEarthObjectRequest(), ActualQuest.getNbRequest()));
    }

    //Vérifie que la quête actuelle est réalisé et passe à la quête suivante
    protected void upgradeQuest(){
        if(ActualQuest.checkQuest(mEarthObject, db)) {

            mEarthObject.clear();
            mEarthObject = db.infoEarthObject(mEarthObject);
            initRecyclerViewItem();

            EarthObject TmpObject = new EarthObject(ActualQuest.getIdQuest(), ActualQuest.getNameReward());
            db.insertCraft(TmpObject.getNbObject(), TmpObject.getName(), TmpObject.getCoinWin(), TmpObject.getPriceObject(), TmpObject.getEnergyCost(), TmpObject.getSkin());

            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + (TmpObject.getEnergyCost())))
                    .apply();

            if(ActualQuest.getIdQuest() == 3 || ActualQuest.getIdQuest() == 7 || ActualQuest.getIdQuest() == 11 ||ActualQuest.getIdQuest() == 12) {
                sharedPreferences
                        .edit()
                        .putInt(PREFS_DIAMOND, (sharedPreferences.getInt(PREFS_DIAMOND, 0) + 5))
                        .apply();
            }

            if(ActualQuest.getIdQuest() != 12) {
                ActualQuest = new Quest(ActualQuest.getIdQuest() + 1);
            } else {
                ActualQuest = new Quest(1);
            }

            db.updateQuest(ActualQuest.getIdQuest());
            ActualPlayer.setQuestId(ActualQuest.getIdQuest());

            DisplayQuestName.setText(ActualQuest.getNameReward());
            DisplayQuestImage.setImageResource(ActualQuest.getSkinReward());

            displayQuest();
            initItemVar();
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
        sharedPreferences
                .edit()
                .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + 1))
                .putLong(PREFS_ENERGY,(sharedPreferences.getLong(PREFS_ENERGY, 0) + 1))
                .apply();

        ActualEnergyPoint.setText(numberFormat.format(sharedPreferences.getLong(PREFS_ENERGY, 0)));
        ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));

        if(sharedPreferences.getLong(PREFS_COIN, 0) >= 10 && mFactory.get(0).getNbObject() < 1 ){
            mFactory.get(0).Upgrade(mFactory.get(0), db, this);
            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + mFactory.get(0).getPriceFactory()))
                    .apply();
            initFactoryVar();
            initItemVar();
            initRecyclerViewFactory();
            Toast.makeText(MainActivity.this, "You have win a "+ mFactory.get(0).getName() +"!", Toast.LENGTH_SHORT).show();
        }
    }

    //Update les listes utiliser par les recyclerview
    public void updateDatabase(){
        int X, Y;

        X = mFactory.size();
        Y = mEarthObject.size();

        mFactory.clear();
        mFactory = db.infoFactory(mFactory);
        mEarthObject.clear();
        mEarthObject = db.infoEarthObject(mEarthObject);

        db.updateEnergyPoint(ActualPlayer.getName(), sharedPreferences.getLong(PREFS_ENERGY, 0));
        db.updateCoin(ActualPlayer.getName(), sharedPreferences.getLong(PREFS_COIN, 0));
        db.updateDiamond(ActualPlayer.getName(), sharedPreferences.getInt(PREFS_DIAMOND,0));

        ActualPlayer = db.infoFirstPlayer();

        if(Y != mEarthObject.size()) {
            adapterE.notifyDataSetChanged();
        }

        if(X != mFactory.size()) {
            adapterF.notifyDataSetChanged();
        }

        db.close();
    }

    ////// Fonction Option //////
    protected void choiceCoinFree(){
        Random X = new Random();
        int nombreAleatoire = X.nextInt(5 - 1 + 1) + 1;

        switch (nombreAleatoire) {
            case 1:
                FreeCoinAd = false;
                CoinFree.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
                break;
            default:
                FreeCoinAd = true;
                CoinFree.getBackground().setColorFilter(Color.parseColor("#0bab08"), PorterDuff.Mode.DARKEN);
                break;
        }

        CoinFree.setVisibility(View.VISIBLE);
    }

    protected void coinFree() {
        Calendar ActualDate = Calendar.getInstance();
        Random X = new Random();

        if(ActualDate.after(CoinFreeEnd)) {
            CoinFreeEnd = Calendar.getInstance();
            CoinFreeEnd.add(Calendar.SECOND, 30);
            long nombreAleatoire = X.nextInt(100 - 1 + 1) + 1, A = 0;
            initItemVar();
            if (nombreAleatoire > 1 && nombreAleatoire < 20) {
                A = EarthObjectCoinWin*(15);
            } else if (nombreAleatoire > 20 && nombreAleatoire < 50) {
                A = EarthObjectCoinWin*(30);
            } else if (nombreAleatoire > 50 && nombreAleatoire < 80) {
                A = EarthObjectCoinWin*(60);
            } else if (nombreAleatoire > 80 && nombreAleatoire < 100) {
                A = EarthObjectCoinWin*(90);
            } else {
                A = EarthObjectCoinWin*(120);
            }

            CoinFree.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this,"You earn " + numberFormat.format(A) + " coins",Toast.LENGTH_LONG).show();
            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + A))
                    .apply();
        }
        ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
    }

    protected void coinFreeAd() {
        Calendar ActualDate = Calendar.getInstance();
        final Random X = new Random();

        if(ActualDate.after(CoinFreeEnd)) {
            CoinFreeEnd = Calendar.getInstance();
            long nbalea = X.nextInt(100 - 1 + 1) + 1, A;
            initItemVar();

            if (nbalea > 1 && nbalea < 50) {
                A = EarthObjectCoinWin*(180);
            } else if (nbalea > 50 && nbalea < 100) {
                A = EarthObjectCoinWin*(240);
            } else {
                A = EarthObjectCoinWin*(300);
            }

            final AdPopup adPopups = new AdPopup(this);
            final long finalA = A;

            adPopups.setTitleAd("More Coins!");
            adPopups.setNumberWinAd("Watch this ad to earn " + numberFormat.format(A) + " coins");
            adPopups.getImageAd().setImageResource(R.drawable.morecoins);
            adPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CoinFreeEnd.add(Calendar.SECOND, 30);
                    Toast.makeText(MainActivity.this,"You have won "+numberFormat.format(finalA)+" coins",Toast.LENGTH_LONG).show();
                    sharedPreferences
                            .edit()
                            .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + finalA))
                            .apply();
                    CoinFree.setVisibility(View.INVISIBLE);
                    ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
                    adPopups.dismiss();
                }
            });

            adPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adPopups.dismiss();
                }
            });
            adPopups.build();
        }
        ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));
    }

    protected void speedAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(SpeedAdEnd)) {
            final AdPopup adPopups = new AdPopup(this);

            adPopups.setTitleAd("Speed Up");
            adPopups.setNumberWinAd("Speed x2 for 4 hours");
            adPopups.getImageAd().setImageResource(R.drawable.speedrun);

            adPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpeedAdEnd = Calendar.getInstance();
                    SpeedAdEnd.add(Calendar.HOUR, 4);
                    db.insertSpeedAds(dateFormat.format(SpeedAdEnd.getTime()));

                    Speed = 2;
                    adPopups.dismiss();
                    Toast.makeText(MainActivity.this,"The speed has been multiplied by 2!",Toast.LENGTH_LONG).show();
                }
            });

            adPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adPopups.dismiss();
                }
            });
            adPopups.build();
        }
    }

    protected void multiAds(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(MultiAdEnd)) {
            final AdPopup adPopups = new AdPopup(this);

            adPopups.setTitleAd("Double Coin!");
            adPopups.setNumberWinAd("Profit x2 for 4 hours");
            adPopups.getImageAd().setImageResource(R.drawable.coinx2);

            adPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MultiAdEnd = Calendar.getInstance();
                    MultiAdEnd.add(Calendar.HOUR, 4);
                    db.insertMultiAds(dateFormat.format(MultiAdEnd.getTime()));

                    Multiple = 2;
                    adPopups.dismiss();
                    Toast.makeText(MainActivity.this,"You earn " + Multiple + " times more coins",Toast.LENGTH_LONG).show();
                }
            });

            adPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adPopups.dismiss();
                }
            });
            adPopups.build();
        }
    }

    protected void SkipFor30(){
        Calendar ActualDate = Calendar.getInstance();
        if(ActualDate.after(SkipAdEnd)) {
            final AdPopup adPopups = new AdPopup(this);

            adPopups.setTitleAd("Skip Time");
            adPopups.setNumberWinAd("Watch this ad to get 30 mins worth of coins\n" + numberFormat.format(EarthObjectCoinWin*(1800)) + " coins");
            adPopups.getImageAd().setImageResource(R.drawable.skip30);

            adPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkipAdEnd = Calendar.getInstance();
                    SkipAdEnd.add(Calendar.HOUR, 4);
                    db.insertSkipAds(dateFormat.format(SkipAdEnd.getTime()));

                    sharedPreferences
                            .edit()
                            .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + EarthObjectCoinWin*(1800)))
                            .apply();
                    adPopups.dismiss();
                    Toast.makeText(MainActivity.this,"You have earned 30 min of coins",Toast.LENGTH_LONG).show();
                }
            });

            adPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adPopups.dismiss();
                }
            });
            adPopups.build();
        }
    }

    protected void SkipFor1D(){
        Calendar ActualDate = Calendar.getInstance();
            final DiamondPopup DiamondPopups = new DiamondPopup(this);

            DiamondPopups.setTitleAd("Skip Time");
            DiamondPopups.setNumberWinAd("Get 1 day worth of coins\n" + numberFormat.format(EarthObjectCoinWin*(86400)) + " coins");
            DiamondPopups.getImageAd().setImageResource(R.drawable.skip1d);
            DiamondPopups.getButton1().setText(String.valueOf(PriceSkip7D));

            DiamondPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sharedPreferences.getInt(PREFS_DIAMOND, 0) >= PriceSkip1D) {
                        sharedPreferences
                                .edit()
                                .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + EarthObjectCoinWin * (86400)))
                                .apply();
                        DiamondPopups.dismiss();
                        Toast.makeText(MainActivity.this, "You have earned 1 day of coins", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You don't have enough diamonds", Toast.LENGTH_LONG).show();
                    }
                }
            });

            DiamondPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiamondPopups.dismiss();
                }
            });
            DiamondPopups.build();
    }

    protected void SkipFor7D(){
        Calendar ActualDate = Calendar.getInstance();
            final DiamondPopup DiamondPopups = new DiamondPopup(this);

            DiamondPopups.setTitleAd("Skip Time");
            DiamondPopups.setNumberWinAd("Get 7 day worth of coins\n" + numberFormat.format(EarthObjectCoinWin*(604800)) + " coins");
            DiamondPopups.getImageAd().setImageResource(R.drawable.skip7d);
            DiamondPopups.getButton1().setText(String.valueOf(PriceSkip7D));
            DiamondPopups.getButton1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sharedPreferences.getInt(PREFS_DIAMOND, 0) >= PriceSkip7D) {
                        sharedPreferences
                                .edit()
                                .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + EarthObjectCoinWin * (604800)))
                                .apply();
                        DiamondPopups.dismiss();
                        Toast.makeText(MainActivity.this, "You have earned 7 day of coins", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You don't have enough diamonds", Toast.LENGTH_LONG).show();
                    }
                }
            });

            DiamondPopups.getButton2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiamondPopups.dismiss();
                }
            });
            DiamondPopups.build();
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

        if(ActualDate.after(SkipAdEnd)) {
            SkipAdText.setText("Skip 30m");
        } else {
            SkipAdText.setText(displayHMS(ActualDate, SkipAdEnd));
        }
    }

    protected void initAdEnd(){

        Date date = null;

        MultiAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoMultiAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MultiAdEnd.setTime(date);

        SpeedAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoSpeedAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SpeedAdEnd.setTime(date);

        SkipAdEnd = Calendar.getInstance();
        try {
            date = dateFormat.parse(db.infoSkipAds());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SkipAdEnd.setTime(date);

        CoinFreeEnd = Calendar.getInstance();
        try {
            date = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CoinFreeEnd.setTime(date);
    }
}