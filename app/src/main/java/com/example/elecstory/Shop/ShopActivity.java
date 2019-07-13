package com.example.elecstory.Shop;

import android.content.Intent;
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

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        final Database db = new Database(this);

        TextView Title = findViewById(R.id.ShopTitle);
        Title.setText("Factory's Shop");

        Button BackShop = findViewById(R.id.back);
        BackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                Intent myIntent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        final ArrayList<Factory> ListShopObject = new ArrayList<>();
        ListShopObject.add(new Factory(-1));
        ListShopObject.add(new Factory(0));
        ListShopObject.add(new Factory(1));
        ListShopObject.add(new Factory(2));
        ListShopObject.add(new Factory(3));
        ListShopObject.add(new Factory(4));
        ListShopObject.add(new Factory(5));


        ArrayList<Factory> ListFactoryPlayer = new ArrayList<>();
        ListFactoryPlayer = db.infoFactory(ListFactoryPlayer);

        for (int i = 0; i < ListFactoryPlayer.size(); i++){
            for (int j = 0; j < ListShopObject.size(); j++){
                if(ListFactoryPlayer.get(i).getName() == ListShopObject.get(j).getName()){
                    if(ListFactoryPlayer.get(i).getNbObject() > 1) {
                        for (int k = 0; k < ListFactoryPlayer.get(i).getNbObject(); k++) {
                            ListShopObject.get(j).setPriceFactory(ListShopObject.get(j).getPriceFactory() * 10);
                        }
                    } else if (ListFactoryPlayer.get(i).getFactoryLevel() > 1){
                        for (int k = 0; k < ListFactoryPlayer.get(i).getFactoryLevel(); k++) {
                            ListShopObject.get(j).setPriceFactory(ListShopObject.get(j).getPriceFactory() * 2);
                        }
                    }
                }
            }
        }

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopAdapter(this, ListShopObject));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Factory N = ListShopObject.get(position);
                if((db.infoFirstPlayer().getCoin() >= N.getPriceFactory()) && (db.infoFirstPlayer().getCoin()-N.getPriceFactory() >= 0)) {
                    db.insertFactory(N.getNbObject(), N.getName(), N.getFactoryLevel(), N.getPriceFactory(), N.getUpgradeCost(), N.getEnergyProd(), N.getOperatingCost(), N.getPollutionTax(), N.getSkin());
                    db.updateCoin(db.infoFirstPlayer().getName(), -N.getPriceFactory());
                } else {
                    Toast.makeText(ShopActivity.this, "Vous n'avez pas assez d'argent !", Toast.LENGTH_SHORT).show();
                }
            }

        });
        db.close();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
