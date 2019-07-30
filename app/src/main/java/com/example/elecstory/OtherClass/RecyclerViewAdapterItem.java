package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.Item;
import com.example.elecstory.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("LongLogTag")
public class RecyclerViewAdapterItem extends RecyclerView.Adapter<RecyclerViewAdapterItem.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterItem";

    private ArrayList<Item> mItem;
    private Context mContext;
    private Activity activity;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private SharedPreferences sharedPreferences;

    public RecyclerViewAdapterItem(Context context, ArrayList<Item> mItems, Activity activitys) {
        mItem = mItems;
        mContext = context;
        activity = activitys;

        sharedPreferences = mContext.getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Database db = new Database(mContext);

        holder.image.setImageResource(Integer.parseInt(mItem.get(position).getSkin()));

        holder.name.setText(mItem.get(position).getName());

        holder.nbObject.setText(String.valueOf(mItem.get(position).getNbObject()));

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final SalePopup salepopups = new SalePopup(activity);
                salepopups.setMessageSale("You want sale " + mItem.get(position).getName() + " for " + (mItem.get(position).getPriceObject()/2) + " coins!");
                salepopups.setNameObjectSale("Selling " + mItem.get(position).getName());
                salepopups.getButton1().setText("Confirm");
                salepopups.getButton2().setText("Cancel");
                salepopups.getButton1().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItem.size() > 0 && mItem.get(position).getNbObject() > 0) {
                            db.deleteItem(mItem.get(position).getName());
                            sharedPreferences
                                    .edit()
                                    .putLong(PREFS_COIN, (sharedPreferences.getLong(PREFS_COIN, 0) + (int)(mItem.get(position).getPriceObject()/2)))
                                    .apply();
                            Toast.makeText(mContext, "This object will be deleted !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "You no longer have this object.", Toast.LENGTH_SHORT).show();
                        }
                        holder.nbObject.setText(String.valueOf(mItem.get(position).getNbObject()));
                        salepopups.dismiss();
                    }
                });

                salepopups.getButton2().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        salepopups.dismiss();
                    }
                });
                //salepopups.getClose().setVisibility(View.INVISIBLE);
                salepopups.build();
                return true;
            }
        });

        db.close();
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView nbObject;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageFactorys);
            name = itemView.findViewById(R.id.FactorysName);
            nbObject = itemView.findViewById(R.id.LevelFactorys);
        }
    }
}
