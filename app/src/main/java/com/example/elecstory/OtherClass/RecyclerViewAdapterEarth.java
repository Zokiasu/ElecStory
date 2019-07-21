package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("LongLogTag")
public class RecyclerViewAdapterEarth extends RecyclerView.Adapter<RecyclerViewAdapterEarth.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<EarthObject> mEarthObject;
    private Context mContext;
    private Activity activity;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private SharedPreferences sharedPreferences;

    public RecyclerViewAdapterEarth(Context context, ArrayList<EarthObject> mEarthObjects, Activity activitys) {
        mEarthObject = mEarthObjects;
        mContext = context;
        activity = activitys;

        sharedPreferences = mContext.getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Database db = new Database(mContext);

        holder.image.setImageResource(mEarthObject.get(position).getSkin());

        holder.name.setText(mEarthObject.get(position).getName());

        holder.nbObject.setText(String.valueOf(mEarthObject.get(position).getNbObject()));

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final SalePopup salepopups = new SalePopup(activity);
                salepopups.setMessageSale("You want sale " + mEarthObject.get(position).getName() + " for " + (mEarthObject.get(position).getPriceObject()/2) + " coins!");
                salepopups.setNameObjectSale("Selling " + mEarthObject.get(position).getName());
                salepopups.getButton1().setText("Confirm");
                salepopups.getButton2().setText("Cancel");
                salepopups.getButton1().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mEarthObject.size() > 0) {
                            db.deleteEarthObject(mEarthObject.get(position).getName());
                            sharedPreferences
                                    .edit()
                                    .putInt(PREFS_COIN, (sharedPreferences.getInt(PREFS_COIN, 0) + (int)(mEarthObject.get(position).getPriceObject()/2)))
                                    .apply();
                            Toast.makeText(mContext, "This object will be deleted !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "You no longer have this object.", Toast.LENGTH_SHORT).show();
                        }
                        salepopups.dismiss();
                    }
                });

                salepopups.getButton2().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        salepopups.dismiss();
                    }
                });
                salepopups.getClose().setVisibility(View.INVISIBLE);
                salepopups.build();
                return true;
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return mEarthObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView nbObject;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.imagename);
            nbObject = itemView.findViewById(R.id.NumberObject);
        }
    }
}
