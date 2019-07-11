package com.example.elecstory.Craft;

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
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.Quest.Quest;
import com.example.elecstory.R;

import java.util.ArrayList;

public class CraftActivity extends AppCompatActivity {

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
                Intent myIntent = new Intent(CraftActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<EarthObject> ListEarthObject = new ArrayList<>();
        ArrayList<EarthObject> ListEarthObjectPlayer = new ArrayList<>();

        ListEarthObject = db.infoCraft(ListEarthObject);
        ListEarthObjectPlayer = db.infoCity(ListEarthObjectPlayer);

        GridView GV = findViewById(R.id.GridCraft);
        GV.setAdapter(new CraftAdapter(this, ListEarthObject));

        final ArrayList<EarthObject> finalListEarthObject = ListEarthObject;
        final ArrayList<EarthObject> finalListEarthObjectPlayer = ListEarthObjectPlayer;

        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                EarthObject N = finalListEarthObject.get(position);
                if((db.infoFirstPlayer().getCoin() >= N.getPriceObject()) && (db.infoFirstPlayer().getCoin()-N.getPriceObject() >= 0)) {
                    //if(position == 0 || checkObject(finalListEarthObjectPlayer, position, db)) {
                        db.insertCity(N.getNbObject(), N.getName(), N.getCoinWin(), N.getPriceObject(), N.getEnergyCost(), N.getSkin());
                        db.updateCoin(db.infoFirstPlayer().getName(), -N.getPriceObject());
                        Toast.makeText(CraftActivity.this, "Unlock effectué ! ", Toast.LENGTH_SHORT).show();
                   /* } else {
                        Toast.makeText(CraftActivity.this, "Vous n'avez pas les objets requis ! ", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Toast.makeText(CraftActivity.this, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
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
