package com.example.elecstory.ShopEarthObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Database.PlayerData;
import com.example.elecstory.MainActivity;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.OtherClass.ShopPopup;
import com.example.elecstory.Quest.Quest;
import com.example.elecstory.R;

import java.util.ArrayList;
import java.util.List;

public class ShopEarthActivity extends AppCompatActivity {


    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    SharedPreferences sharedPreferences;

    protected static final String TAG = "Elecstory.ShopEarthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_shop);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        final Database db = new Database(this);

        TextView Title = findViewById(R.id.CraftTitle);
        Title.setText("Object's Shop");

        Button BackCraft = findViewById(R.id.back);

        BackCraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                Intent myIntent = new Intent(ShopEarthActivity.this, MainActivity.class);
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
        GV.setAdapter(new ShopEarthAdapter(this, ListEarthObject));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final EarthObject N = finalListEarthObject.get(position);
                Log.i("Shop","sharedPreferences.getInt(PREFS_COIN, 0) : " + sharedPreferences.getInt(PREFS_COIN, 0));
                if((sharedPreferences.getInt(PREFS_COIN, 0) >= N.getPriceObject()) && ((sharedPreferences.getInt(PREFS_COIN, 0) - N.getPriceObject()) >= 0)) {
                    final ShopPopup shopPopup = new ShopPopup(ShopEarthActivity.this);

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
                            if((sharedPreferences.getInt(PREFS_COIN, 0) >= (N.getPriceObject() * RecupNb)) && ((sharedPreferences.getInt(PREFS_COIN, 0) - (N.getPriceObject() * RecupNb)) >= 0)) {
                                db.insertEarthObject(N.getNbObject() * RecupNb, N.getName(), N.getCoinWin(), N.getPriceObject(), N.getEnergyCost(), N.getSkin());

                                sharedPreferences
                                        .edit()
                                        .putInt(PREFS_COIN, (sharedPreferences.getInt(PREFS_COIN, 0) - (int)(N.getPriceObject() * RecupNb)))
                                        .apply();

                            } else {
                                Toast.makeText(ShopEarthActivity.this, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ShopEarthActivity.this, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
                }
            }

        });
        db.close();
    }

    ////!!!Faire une vrai fonction qui vérifie chaque craft!!!////

    public boolean checkObject(ArrayList<EarthObject> ListObject, int position, Database db){
        Quest TestTmp = new Quest(position);
        return TestTmp.checkQuest(ListObject, db);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
