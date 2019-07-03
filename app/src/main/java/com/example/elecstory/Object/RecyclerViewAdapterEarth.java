package com.example.elecstory.Object;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elecstory.R;

import java.util.ArrayList;

public class RecyclerViewAdapterEarth extends RecyclerView.Adapter<RecyclerViewAdapterEarth.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterEarth";

    private ArrayList<EarthObject> mEarthObject;
    private Context mContext;

    public RecyclerViewAdapterEarth(Context context, ArrayList<EarthObject> mEarthObjects) {
        mEarthObject = mEarthObjects;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(BitmapFactory.decodeResource(mContext.getResources(), mEarthObject.get(position).getSkin()))
                .into(holder.image);

        holder.name.setText(mEarthObject.get(position).getName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mEarthObject.get(position).getName());
                Toast.makeText(mContext, mEarthObject.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEarthObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.imagename);
        }
    }
}
