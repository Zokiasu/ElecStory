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

        final Database db = new Database(this);

        TextView Title = findViewById(R.id.ShopTitle);
        Title.setText("Boutique d'usine");

        final Button Confirm = findViewById(R.id.confirm);
        Confirm.setVisibility(View.INVISIBLE);

        final Button Cancel = findViewById(R.id.cancel);
        Cancel.setVisibility(View.INVISIBLE);

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
        ListShopObject.add(new Factory(0));
        ListShopObject.add(new Factory(1));
        ListShopObject.add(new Factory(2));
        ListShopObject.add(new Factory(3));
        ListShopObject.add(new Factory(4));
        ListShopObject.add(new Factory(5));
        ListShopObject.add(new Factory(6));

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopAdapter(this, ListShopObject));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Factory N = ListShopObject.get(position);
                if(db.infoFirstPlayer().getCoin() >= N.getRequiredCost()) {
                    db.insertFactory(N.getNbObject(), N.getName(), N.getFactoryLevel(), N.getRequiredCost(), N.getUpgradeCost(), N.getElecGenerate(), N.getOperatingCost(), N.getPollutionTax(), N.getSkin());
                    db.updateCoin(db.infoFirstPlayer().getName(), -N.getRequiredCost());
                    Toast.makeText(ShopActivity.this, "Achat effectué !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShopActivity.this, "Vous n'avez pas assez d'argent !", Toast.LENGTH_SHORT).show();
                }
            }

        });
        db.close();
    }
}
