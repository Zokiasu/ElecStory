package com.example.elecstory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elecstory.Object.EarthObject;

import java.util.ArrayList;

public class CraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft);

        TextView Title = findViewById(R.id.CraftTitle);
        Title.setText("Boutique d'usine");

        final Button Confirm = findViewById(R.id.confirm);
        Confirm.setVisibility(View.INVISIBLE);

        final Button Cancel = findViewById(R.id.cancel);
        Cancel.setVisibility(View.INVISIBLE);

        Button BackCraft = findViewById(R.id.back);

        BackCraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CraftActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<EarthObject> ListEarthObject = new ArrayList<>();
        ListEarthObject.add(new EarthObject(1,""));
        GridView GV = findViewById(R.id.GridCraft);
        GV.setAdapter(new CraftAdapter(this, ListEarthObject));
    }
}
