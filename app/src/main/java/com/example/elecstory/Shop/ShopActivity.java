package com.example.elecstory.Shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.MainActivity;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.OtherClass.ShopPopup;
import com.example.elecstory.Quest.Quest;
import com.example.elecstory.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {


    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private static final String PREFS_ENERGY = "PREFS_ENERGY";
    SharedPreferences sharedPreferences;

    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    protected static final String TAG = "Elecstory.ShopActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        final Database db = new Database(this);

        Button BackCraft = findViewById(R.id.back);
        CardView CardPlayer = findViewById(R.id.cardPlayer);
        final TextView ActualCoin = findViewById(R.id.ElecCoins);
        final TextView ActualEnergyPoint = findViewById(R.id.ElecStockage);

        ActualEnergyPoint.setText(numberFormat.format(sharedPreferences.getLong(PREFS_ENERGY, 0)));
        ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));

        BackCraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                Intent myIntent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<EarthObject> ListEarthObject = new ArrayList<>();

        ListEarthObject = db.infoCraft(ListEarthObject);

        final ArrayList<EarthObject> finalListEarthObject = ListEarthObject;

        final List<Integer> number = new ArrayList<Integer>();

        for (int i = 1; i < 101; i++){
            if(i%10 == 0){
                number.add(i);
            } else if (i == 1 || i == 5) {
                number.add(i);
            }
        }

        GridView GV = findViewById(R.id.GridCraft);
        GV.setAdapter(new ShopAdapter(this, ListEarthObject));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final EarthObject N = finalListEarthObject.get(position);

                if((sharedPreferences.getLong(PREFS_COIN, 0) >= N.getPriceObject()) && ((sharedPreferences.getLong(PREFS_COIN, 0) - N.getPriceObject()) >= 0)) {
                    final ShopPopup shopPopup = new ShopPopup(ShopActivity.this);

                    shopPopup.setNumber(number);
                    shopPopup.setObjectsBuy(N.getName());
                    //Confirm
                    shopPopup.getButton1().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Recup;
                            int RecupNb;
                            if(!shopPopup.getNbObjectBuys().getText().toString().matches("")){
                                Recup = shopPopup.getNbObjectBuys().getText().toString();
                                RecupNb = Integer.valueOf(Recup);
                            } else {
                                RecupNb = 1;
                            }
                            if((sharedPreferences.getLong(PREFS_COIN, 0) >= (N.getPriceObject() * RecupNb)) && ((sharedPreferences.getLong(PREFS_COIN, 0) - (N.getPriceObject() * RecupNb)) >= 0)) {
                                db.insertEarthObject(N.getNbObject() * RecupNb, N.getName(), N.getCoinWin(), N.getPriceObject(), N.getEnergyCost(), N.getSkin());

                                sharedPreferences
                                        .edit()
                                        .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) - (int)(N.getPriceObject() * RecupNb)))
                                        .apply();
                                ActualCoin.setText(numberFormat.format(sharedPreferences.getLong(PREFS_COIN, 0)));

                            } else {
                                Toast.makeText(ShopActivity.this, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
                            }
                            shopPopup.dismiss();
                        }
                    });
                    //Cancel
                    shopPopup.getButton2().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shopPopup.dismiss();
                        }
                    });
                    shopPopup.build();
                } else {
                    Toast.makeText(ShopActivity.this, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        db.close();
    }

    ////!!!Faire une vrai fonction qui vérifie chaque craft!!!////

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
