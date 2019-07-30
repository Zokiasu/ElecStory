package com.example.elecstory.OtherClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecstory.Object.Item;
import com.example.elecstory.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("SetTextI18n")
public class ItemGvAdapter extends BaseAdapter {

    private Context Context;
    private Activity activity;
    private ArrayList<Item> listItems;
    private LayoutInflater layoutInflater;
    private View view;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    private SharedPreferences sharedPreferences;

    protected static final String TAG = "Elecstory.ItemGvAdapter";

    public ItemGvAdapter(Context o, ArrayList<Item> listItem, Activity activitys){
        this.Context = o;
        this.listItems = listItem;
        this.layoutInflater = LayoutInflater.from(Context);
        this.activity = activitys;

        sharedPreferences = Context.getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = new View(Context);
        view = layoutInflater.inflate(R.layout.item_adapter, null);

        ImageView imageView = view.findViewById(R.id.imageFactorys);
        TextView nameTitle = view.findViewById(R.id.FactorysName);
        TextView nbObject = view.findViewById(R.id.LevelFactorys);

        imageView.setImageResource(Integer.parseInt(listItems.get(position).getSkin()));
        nameTitle.setText(listItems.get(position).getName());
        nbObject.setText(String.valueOf(listItems.get(position).getNbObject()));

        return view;
    }
}
