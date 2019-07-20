package com.example.elecstory.ShopFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Database.PlayerData;
import com.example.elecstory.MainActivity;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.util.ArrayList;

public class ShopFactoryActivity extends AppCompatActivity {


    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private static final String PREFS_ENERGY = "PREFS_ENERGY";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        final Database db = new Database(this);

        TextView Title = findViewById(R.id.ShopTitle);
        Title.setText("Factory's Shop");

        Button BackShop = findViewById(R.id.back);
        BackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                Intent myIntent = new Intent(ShopFactoryActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<Factory> ListFactoryShop = new ArrayList<>();
        ListFactoryShop = db.infoFactoryShop(ListFactoryShop);

        ArrayList<Factory> ListFactoryPlayer = new ArrayList<>();
        ListFactoryPlayer = db.infoFactory(ListFactoryPlayer);

        final ArrayList<Factory> finalListShopObject = ListFactoryShop;
        final ArrayList<Factory> finalListFactoryPlayer = ListFactoryPlayer;

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopFactoryAdapter(this, ListFactoryShop));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Factory N = finalListShopObject.get(position);
                if(checkNumberItemPlayer(N.getName(), finalListFactoryPlayer)) {
                    if ((sharedPreferences.getInt(PREFS_COIN, 0) >= N.getPriceFactory()) && ((sharedPreferences.getInt(PREFS_COIN, 0) - N.getPriceFactory()) >= 0)) {
                        db.insertFactory(N.getNbObject(), N.getName(), N.getFactoryLevel(), N.getPriceFactory(), N.getUpgradeCost(), N.getEnergyProd(), N.getOperatingCost(), N.getPollutionTax(), N.getSkin());
                        sharedPreferences
                                .edit()
                                .putInt(PREFS_COIN, (sharedPreferences.getInt(PREFS_COIN, 0) - N.getPriceFactory()))
                                .apply();
                        Toast.makeText(ShopFactoryActivity.this, "Vous avez acheté " + N.getName() +" !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShopFactoryActivity.this, "Vous n'avez pas assez d'argent !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShopFactoryActivity.this, "Vous possédez le nombre maximum de " + N.getName() + " autorisé.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        db.close();
    }

    public boolean checkNumberItemPlayer(String NameFactory, ArrayList<Factory> ListPlayer){
        for (int i = 0; i < ListPlayer.size(); i++){
            if(ListPlayer.get(i).getName().equals(NameFactory)){
                if(ListPlayer.get(i).getNbObject() < 5){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
