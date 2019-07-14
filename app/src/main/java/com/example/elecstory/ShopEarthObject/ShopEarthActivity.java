package com.example.elecstory.ShopEarthObject;

import android.content.Intent;
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
import com.example.elecstory.Quest.Quest;
import com.example.elecstory.R;

import java.util.ArrayList;

public class ShopEarthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        final Database db = new Database(this);

        TextView Title = findViewById(R.id.CraftTitle);
        Title.setText("Unlock List");

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

        GridView GV = findViewById(R.id.GridCraft);
        GV.setAdapter(new ShopEarthAdapter(this, ListEarthObject));

        final ArrayList<EarthObject> finalListEarthObject = ListEarthObject;

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final PlayerData ActualPlayer = db.infoFirstPlayer();
                EarthObject N = finalListEarthObject.get(position);
                Log.i("ShopEarthActivit", "ActualPlayer.getCoin() : " + ActualPlayer.getCoin());
                Log.i("ShopEarthActivit", "N.getPriceObject() : " + N.getPriceObject());
                Log.i("ShopEarthActivit", "(ActualPlayer.getCoin()-N.getPriceObject()) : " + (ActualPlayer.getCoin()-N.getPriceObject()));
                if((ActualPlayer.getCoin() >= N.getPriceObject()) && ((ActualPlayer.getCoin()-N.getPriceObject()) >= 0)) {
                    db.insertEarthObject(N.getNbObject(), N.getName(), N.getCoinWin(), N.getPriceObject(), N.getEnergyCost(), N.getSkin());
                    db.updateCoin(ActualPlayer.getName(), ActualPlayer.getCoin() - N.getPriceObject());
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
