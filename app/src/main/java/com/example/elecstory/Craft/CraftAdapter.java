package com.example.elecstory.Craft;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.R;

import java.util.ArrayList;

public class CraftAdapter extends BaseAdapter {

    public android.content.Context Context;
    public ArrayList<EarthObject> ListObject;
    public LayoutInflater layoutInflater;
    public View views;

    public CraftAdapter (Context Contexts, ArrayList<EarthObject> ListCO) {
        this.Context = Contexts;
        this.ListObject = ListCO;
        this.layoutInflater = LayoutInflater.from(Context);
    }

    @Override
    public int getCount() {
        return ListObject.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ServiceCast", "LongLogTag"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        views = new View(Context);
        views = layoutInflater.inflate(R.layout.shop_craft_adapter, null);

        ImageView imageView = views.findViewById(R.id.objectView);
        TextView objectName = views.findViewById(R.id.objectName);
        TextView objectPrice = views.findViewById(R.id.objectCost);
        TextView coinProduc = views.findViewById(R.id.CoinProduc);
        TextView costProduc = views.findViewById(R.id.CostProduction);

        imageView.setImageResource(ListObject.get(position).getSkin());
        objectName.setText(String.valueOf(ListObject.get(position).getName()));
        objectPrice.setText("Price : " + ListObject.get(position).getPriceObject() + " Coins");
        coinProduc.setText("Coin win : " + ListObject.get(position).getCoinWin() + "/s");
        costProduc.setText("Energy Cost : " + ListObject.get(position).getEnergyCost() + "/s");

        return views;
    }
}
