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
import com.example.elecstory.MainActivity;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ShopFactoryActivity extends AppCompatActivity {

    private final Database db = new Database(this);
    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    SharedPreferences sharedPreferences;

    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    protected static final String TAG = "Elecstory.ShopFactoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_shop);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

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
        final ArrayList<Factory> finalListShopObject = ListFactoryShop;

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopFactoryAdapter(this, ListFactoryShop));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Factory N = finalListShopObject.get(position);
                if(checkNumberItemPlayer(N.getName())) {
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

    public boolean checkNumberItemPlayer(String NameFactory){
        ArrayList<Factory> ListPlayer = new ArrayList<>();
        ListPlayer = db.infoFactory(ListPlayer);
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
