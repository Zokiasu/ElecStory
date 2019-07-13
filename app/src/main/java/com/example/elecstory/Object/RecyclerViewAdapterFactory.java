package com.example.elecstory.Object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.elecstory.Database.Database;
import com.example.elecstory.Database.PlayerData;
import com.example.elecstory.R;
import com.example.elecstory.SalePopup;

import java.util.ArrayList;

@SuppressLint("LongLogTag")
public class RecyclerViewAdapterFactory extends RecyclerView.Adapter<RecyclerViewAdapterFactory.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<Factory> mFactory;
    private Context mContext;
    private Activity activity;

    public RecyclerViewAdapterFactory(Context context, ArrayList<Factory> mFactorys, Activity activitys) {
        mFactory = mFactorys;
        mContext = context;
        activity = activitys;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.factory_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Database db = new Database(mContext);
        Log.i(TAG, "Call onBindViewHolder");

        Glide.with(mContext)
                .asBitmap()
                .load(BitmapFactory.decodeResource(mContext.getResources(), mFactory.get(position).getSkin()))
                .into(holder.image);

        holder.name.setText(mFactory.get(position).getName());

        holder.nbObject.setText(String.valueOf(mFactory.get(position).getNbObject()));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SalePopup salepopups = new SalePopup(activity);
                salepopups.getConfirm().setText("Sell");
                salepopups.getCancel().setText("Update");
                salepopups.setNameObjectSale(mFactory.get(position).getName() + "'s Info");
                salepopups.setMessageSale(
                        "Actual Level : " + mFactory.get(position).getFactoryLevel() +
                        "\nUpdate Cost : " + mFactory.get(position).getUpgradeCost() +
                        "\nSell Price : " + (mFactory.get(position).getPriceFactory()/2) +
                        "\nEnergy Gen : " + mFactory.get(position).getEnergyProd() + "/s" +
                        "\nOperating Cost : " + mFactory.get(position).getOperatingCost() + "/m" +
                        "\nEnvironment Tax : " + mFactory.get(position).getPollutionTax() + "/m");

                salepopups.getConfirm().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mFactory.size() > 0) {
                            db.deleteFactory(mFactory.get(position).getName());
                            db.updateCoin(db.infoFirstPlayer().getName(), (mFactory.get(position).getPriceFactory() / 2));
                            Toast.makeText(mContext, "This object will be deleted !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "You no longer have this object.", Toast.LENGTH_SHORT).show();
                        }
                        salepopups.dismiss();
                    }
                });

                salepopups.getCancel().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(db.infoFirstPlayer().getCoin() >= mFactory.get(position).getUpgradeCost()) {
                            if(mFactory.get(position).getFactoryLevel() == 5){
                                Toast.makeText(activity, "You cannot improve this type of Factory.", Toast.LENGTH_LONG).show();
                            } else {
                                mFactory.get(position).Upgrade(mFactory.get(position), db);
                                Toast.makeText(mContext, mFactory.get(position).getName() + " will be upgrade!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "You don't have money!", Toast.LENGTH_SHORT).show();
                        }
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
                salepopups.getConfirm().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mFactory.size() > 0) {
                            db.deleteFactory(mFactory.get(position).getName());
                            db.updateCoin(db.infoFirstPlayer().getName(), (mFactory.get(position).getPriceFactory()/2));
                            Toast.makeText(mContext, "This object will be deleted !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "You no longer have this object.", Toast.LENGTH_SHORT).show();
                        }
                        salepopups.dismiss();
                    }
                });

                salepopups.getCancel().setOnClickListener(new View.OnClickListener() {
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
                Log.i(TAG, "Player.getCoin() : " + db.infoFirstPlayer().getCoin());
                Log.i(TAG, "mFactory.get(position).getUpgradeCost() : " + mFactory.get(position).getUpgradeCost());
                if(db.infoFirstPlayer().getCoin() >= mFactory.get(position).getUpgradeCost()) {
                    if(mFactory.get(position).getFactoryLevel() == 5){
                        Toast.makeText(activity, "You cannot improve this type of Factory.", Toast.LENGTH_LONG).show();
                    } else {
                        mFactory.get(position).Upgrade(mFactory.get(position), db);
                        Toast.makeText(mContext, mFactory.get(position).getName() + " will be upgrade!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   Toast.makeText(mContext, "You don't have money!", Toast.LENGTH_SHORT).show();
                }
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
}
