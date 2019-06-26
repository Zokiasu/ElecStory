package com.example.elecstory;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int ElecPoint;
    public TextView ElecPoints;
    public Button UpElecPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpElecPoint = findViewById(R.id.ElecUp);
        ElecPoints = findViewById(R.id.ElecCount);
        ElecPoints.setText(String.valueOf(0));

        UpElecPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElecPoint ++;
                ElecPoints.setText(String.valueOf(ElecPoint));
            }
        });
        test(1500);
    }

    public void test(int milliseconds){
        if(ElecPoint >= 10) {
            ElecPoint++;
        }

        ElecPoints.setText(String.valueOf(ElecPoint));

        refresh(milliseconds);
    }

    private void refresh(final int milli){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                test(milli);
            }
        };

        handler.postDelayed(runnable, milli);
    }
}
