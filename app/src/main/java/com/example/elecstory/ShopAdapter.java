package com.example.elecstory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.Object.City;

import java.util.List;

public class ShopAdapter extends BaseAdapter {

    public Context Context;
    public List<City> ListCityObject;
    public LayoutInflater layoutInflater;
    public View views;

    public ShopAdapter (Context Contexts, List<City> ListCO) {
        this.Context = Contexts;
        this.ListCityObject = ListCO;
        this.layoutInflater = LayoutInflater.from(Context);
    }

    @Override
    public int getCount() {
        return ListCityObject.size();
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

        imageView.setImageResource(ListCityObject.get(position).getSkin());
        objectPrice.setText(String.valueOf(ListCityObject.get(position).getPriceObject()));
        objectName.setText(String.valueOf(ListCityObject.get(position).getName()));

        return views;
    }
}
