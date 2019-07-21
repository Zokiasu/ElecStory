package com.example.elecstory.ShopFactory;

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

import java.text.NumberFormat;
import java.util.ArrayList;

public class ShopFactoryAdapter extends BaseAdapter {

    public Context Context;
    public ArrayList<Factory> ListObject;
    public LayoutInflater layoutInflater;
    public View views;
    public NumberFormat numberFormat = NumberFormat.getInstance(java.util.Locale.FRENCH);

    protected static final String TAG = "Elecstory.ShopFactoryAdapter";

    public ShopFactoryAdapter(Context Contexts, ArrayList<Factory> ListCO) {
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
        views = layoutInflater.inflate(R.layout.shop_factory_adapter, null);

        ImageView imageView = views.findViewById(R.id.objectView);
        TextView objectPrice = views.findViewById(R.id.factoryCost);
        TextView objectName = views.findViewById(R.id.objectName);

        TextView energyGen = views.findViewById(R.id.EnergyProduc);
        TextView operatingCost = views.findViewById(R.id.CostProduction);
        TextView operatingTax = views.findViewById(R.id.TaxProduction);

        energyGen.setText("Energy Prod : " + ListObject.get(position).getEnergyProd() + "/s");
        operatingCost.setText("Operating Cost : " + ListObject.get(position).getOperatingCost() + "/m");
        operatingTax.setText("Pollution Tax : " + ListObject.get(position).getPollutionTax() + "/m");
        objectPrice.setText("Price : " + numberFormat.format(ListObject.get(position).getPriceFactory()) + " Coins");
        objectName.setText(String.valueOf(ListObject.get(position).getName()));
        imageView.setImageResource(ListObject.get(position).getSkin());

        return views;
    }
}
