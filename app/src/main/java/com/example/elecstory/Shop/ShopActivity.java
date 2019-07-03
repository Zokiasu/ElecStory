package com.example.elecstory.Shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elecstory.MainActivity;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

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
                Intent myIntent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<Factory> ListShopObject = new ArrayList<>();
        ListShopObject.add(new Factory(0));
        ListShopObject.add(new Factory(1));
        ListShopObject.add(new Factory(2));
        ListShopObject.add(new Factory(3));
        ListShopObject.add(new Factory(4));
        ListShopObject.add(new Factory(5));
        ListShopObject.add(new Factory(6));

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopAdapter(this, ListShopObject));
    }
}
