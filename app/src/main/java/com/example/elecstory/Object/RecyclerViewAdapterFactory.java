package com.example.elecstory.Object;

import android.content.Context;
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
import com.example.elecstory.Database.PlayerData;
import com.example.elecstory.MainActivity;
import com.example.elecstory.R;

import java.util.ArrayList;

public class RecyclerViewAdapterFactory extends RecyclerView.Adapter<RecyclerViewAdapterFactory.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<Factory> mFactory;
    private Context mContext;

    public RecyclerViewAdapterFactory(Context context, ArrayList<Factory> mFactorys) {
        mFactory = mFactorys;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.factory_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Database db = new Database(mContext);
        final PlayerData Player = db.infoFirstPlayer();
        Glide.with(mContext)
                .asBitmap()
                .load(BitmapFactory.decodeResource(mContext.getResources(), mFactory.get(position).getSkin()))
                .into(holder.image);

        holder.name.setText(mFactory.get(position).getName() + " " + mFactory.get(position).getFactoryLevel());
        holder.nbObject.setText(String.valueOf(mFactory.get(position).getNbObject()));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFactory.get(position).getUpgradeCost() <= Player.getCoin()) {
                    mFactory.get(position).Upgrade(mFactory.get(position), db);
                } else {
                    Toast.makeText(mContext, "Vous n'avez pas assez d'argent !", Toast.LENGTH_SHORT).show();
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

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.imagename);
            nbObject = itemView.findViewById(R.id.NumberObject);
        }
    }
}
