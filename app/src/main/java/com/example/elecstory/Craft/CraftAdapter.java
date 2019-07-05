package com.example.elecstory.Craft;

import android.annotation.SuppressLint;
import android.content.Context;
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

    @SuppressLint("ServiceCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        views = new View(Context);
        views = layoutInflater.inflate(R.layout.shop_adapter, null);

        ImageView imageView = views.findViewById(R.id.objectView);
        TextView objectPrice = views.findViewById(R.id.objectCount);
        TextView objectName = views.findViewById(R.id.objectName);

        objectPrice.setText(String.valueOf(ListObject.get(position).getPriceObject()));
        objectName.setText(String.valueOf(ListObject.get(position).getName()));
        imageView.setImageResource(ListObject.get(position).getSkin());

        return views;
    }
}
