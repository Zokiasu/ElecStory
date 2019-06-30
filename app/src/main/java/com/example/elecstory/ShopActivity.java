package com.example.elecstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.elecstory.Object.City;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

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

        final List<City> ListCityObject = new ArrayList<>();

        ///
        ListCityObject.add(new City( "Maison", "Maison", 100, 150,100, 1, R.drawable.ampoule));
        ListCityObject.add(new City( "Manoir", "Maison", 250, 1000,500, 1, R.drawable.ampoule));
        ListCityObject.add(new City( "Building", "Immeuble", 500, 10000,1000, 1, R.drawable.ampoule));
        ///

        GridView GV = findViewById(R.id.GridShop);
        GV.setAdapter(new ShopAdapter(this, ListCityObject));

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.getToto().getCoin() - ListCityObject.get(position).getPriceObject() >= 0){
                    Confirm.setVisibility(View.VISIBLE);
                    Cancel.setVisibility(View.VISIBLE);
                    Toast.makeText(ShopActivity.this, "Please confirm your choice", Toast.LENGTH_SHORT).show();
                    Confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Confirm.setVisibility(View.INVISIBLE);
                            Cancel.setVisibility(View.INVISIBLE);
                        }
                    });
                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(ShopActivity.this, "Purchase cancelled", Toast.LENGTH_SHORT).show();
                            Confirm.setVisibility(View.INVISIBLE);
                            Cancel.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(ShopActivity.this, "Not enough money ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
