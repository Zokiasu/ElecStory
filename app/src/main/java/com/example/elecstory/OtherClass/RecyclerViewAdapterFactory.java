package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.text.NumberFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("LongLogTag")
public class RecyclerViewAdapterFactory extends RecyclerView.Adapter<RecyclerViewAdapterFactory.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<Factory> mFactory;
    private Context mContext;
    private Activity activity;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private SharedPreferences sharedPreferences;
    protected NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    public RecyclerViewAdapterFactory(Context context, ArrayList<Factory> mFactorys, Activity activitys) {
        mFactory = mFactorys;
        mContext = context;
        activity = activitys;
        sharedPreferences = mContext.getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.factory_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Database db = new Database(mContext);

        holder.image.setImageResource(mFactory.get(position).getSkin());

        holder.name.setText(mFactory.get(position).getName());

        holder.nbObject.setText(String.valueOf(mFactory.get(position).getNbObject()));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SalePopup salepopups = new SalePopup(activity);
                salepopups.getButton1().setText("Sell");
                salepopups.getButton2().setText("Update");
                salepopups.setNameObjectSale(mFactory.get(position).getName() + "'s Info");
                if(mFactory.get(position).getFactoryLevel() >= 3) {
                    salepopups.setMessageSale(
                            "Actual Level : Level Max" +
                            "\nSell Price : " + numberFormat.format(mFactory.get(position).getPriceFactory() / 2) +
                            "\nEnergy Gen : " + numberFormat.format(mFactory.get(position).getEnergyProd()) + "/s" +
                            "\nOperating Cost : " + numberFormat.format(mFactory.get(position).getOperatingCost()) + "/m" +
                            "\nEnvironment Tax : " + numberFormat.format(mFactory.get(position).getPollutionTax()) + "/m");
                } else {
                    salepopups.setMessageSale(
                            "Actual Level : " + mFactory.get(position).getFactoryLevel() +
                            "\nUpdate Cost : " + numberFormat.format(mFactory.get(position).getUpgradeCost()) +
                            "\nSell Price : " + numberFormat.format(mFactory.get(position).getPriceFactory() / 2) +
                            "\nEnergy Gen : " + numberFormat.format(mFactory.get(position).getEnergyProd()) + "/s" +
                            "\nOperating Cost : " + numberFormat.format(mFactory.get(position).getOperatingCost()) + "/m" +
                            "\nEnvironment Tax : " + numberFormat.format(mFactory.get(position).getPollutionTax()) + "/m");
                }
                salepopups.getButton1().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saleFactory(position, db);
                        salepopups.dismiss();
                    }
                });

                salepopups.getButton2().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upgradeFactory(db, position);
                        salepopups.dismiss();
                    }
                });

                salepopups.getClose().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        salepopups.dismiss();
                    }
                });
                salepopups.build();
            }
        });

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final SalePopup salepopups = new SalePopup(activity);

                salepopups.setMessageSale("You want sale " + mFactory.get(position).getName() + " for " + (mFactory.get(position).getPriceFactory()/2) + " coins!");
                salepopups.setNameObjectSale("Selling " + mFactory.get(position).getName());
                salepopups.getButton1().setText("Confirm");
                salepopups.getButton2().setText("Cancel");

                salepopups.getButton1().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saleFactory(position, db);
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

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgradeFactory(db, position);
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return mFactory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView nbObject;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.imagename);
            nbObject = itemView.findViewById(R.id.NumberObject);
            button = itemView.findViewById(R.id.infoObject);
        }
    }

    public void saleFactory(int position, Database db){
        //Vendre une usine pour la moitié de son prix
        if(mFactory.get(position).getNbObject() > 0) {
            db.deleteFactory(mFactory.get(position).getName());
            sharedPreferences
                    .edit()
                    .putInt(PREFS_COIN, (sharedPreferences.getInt(PREFS_COIN, 0) + (mFactory.get(position).getPriceFactory() / 2)))
                    .apply();
            Toast.makeText(mContext, "This object will be deleted !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "You no longer have this object.", Toast.LENGTH_SHORT).show();
        }
    }

    public void upgradeFactory(Database db, int position){
        if(sharedPreferences.getInt(PREFS_COIN, 0) >= mFactory.get(position).getUpgradeCost()) {
            if(mFactory.get(position).getFactoryLevel() == 3){
                Toast.makeText(activity, "You can' t improve this type of factory.", Toast.LENGTH_LONG).show();
            } else {
                mFactory.get(position).Upgrade(mFactory.get(position), db, activity);
                Toast.makeText(mContext, mFactory.get(position).getName() + " will be upgrade!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "You don't have enough money!", Toast.LENGTH_SHORT).show();
        }
    }
}
