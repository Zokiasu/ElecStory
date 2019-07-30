package com.example.elecstory.Shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.Object.Item;
import com.example.elecstory.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter {

    public android.content.Context Context;
    public ArrayList<Item> ListObject;
    public LayoutInflater layoutInflater;
    public View views;
    public Button test;
    public NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    protected static final String TAG = "Elecstory.ShopAdapter";

    public ShopAdapter(Context Contexts, ArrayList<Item> ListCO) {
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
        views = layoutInflater.inflate(R.layout.shop_adapter, null);

        ImageView imageView = views.findViewById(R.id.objectView);
        TextView objectName = views.findViewById(R.id.objectName);
        TextView objectPrice = views.findViewById(R.id.objectCost);

        imageView.setImageResource(Integer.parseInt(ListObject.get(position).getSkin()));
        objectName.setText(String.valueOf(ListObject.get(position).getName()));
        objectPrice.setText("Price : " + numberFormat.format(ListObject.get(position).getPriceObject()));

        return views;
    }
}
