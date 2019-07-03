package com.example.elecstory.Shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.Object.Factory;
import com.example.elecstory.R;

import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter {

    public Context Context;
    public ArrayList<Factory> ListObject;
    public LayoutInflater layoutInflater;
    public View views;

    public ShopAdapter (Context Contexts, ArrayList<Factory> ListCO) {
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

    @SuppressLint("ServiceCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        views = new View(Context);
        views = layoutInflater.inflate(R.layout.shop_adapter, null);

        ImageView imageView = views.findViewById(R.id.objectView);
        TextView objectPrice = views.findViewById(R.id.objectCount);
        TextView objectName = views.findViewById(R.id.objectName);

        objectPrice.setText(String.valueOf(ListObject.get(position).getRequiredCost()));
        objectName.setText(String.valueOf(ListObject.get(position).getName()));
        imageView.setImageResource(ListObject.get(position).getSkin());

        return views;
    }
}
