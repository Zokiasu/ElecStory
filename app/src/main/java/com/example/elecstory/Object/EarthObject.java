package com.example.elecstory.Object;

import android.util.Log;

import com.example.elecstory.R;

public class EarthObject {

    protected int NbObject;
    protected String Name;
    protected int CoinWin;
    protected int PriceObject;
    protected int EnergyCost;
    protected int Skin;

    public EarthObject(int NbObjects, String name, int coinWin, int priceObject, int energyCost, int skin) {
        NbObject = NbObjects;
        Name = name;
        CoinWin = coinWin;
        PriceObject = priceObject;
        EnergyCost = energyCost;
        Skin = skin;
    }

    public EarthObject(int N, String M){
        if(N == 0 || M.equals("Lamp")){
            this.NbObject = 1;
            this.Name = "Lamp";
            this.CoinWin = 1;
            this.PriceObject = 10;
            this.EnergyCost = 5;
            this.Skin = R.drawable.eclair;
        } else if(N == 1 || M.equals("House")){
            this.NbObject = 1;
            this.Name = "House";
            this.CoinWin = 15;
            this.PriceObject = 1000;
            this.EnergyCost = 25;
            this.Skin = R.drawable.eclair;
        } else if(N == 2 || M.equals("Street")){
            this.NbObject = 1;
            this.Name = "Street";
            this.CoinWin = 150;
            this.PriceObject = 7000;
            this.EnergyCost = 1000;
            this.Skin = R.drawable.eclair;
        } else if(N == 3 || M.equals("District")){
            this.NbObject = 1;
            this.Name = "District";
            this.CoinWin = 300;
            this.PriceObject = 100000;
            this.EnergyCost = 3000;
            this.Skin = R.drawable.eclair;
        } else if(N == 4 || M.equals("City")){
            this.NbObject = 1;
            this.Name = "City";
            this.CoinWin = 1000;
            this.PriceObject = 1000000;
            this.EnergyCost = 10000;
            this.Skin = R.drawable.eclair;
        } else if(N == 5 || M.equals("Municipality")){
            this.NbObject = 1;
            this.Name = "Municipality";
            this.CoinWin = 5000;
            this.PriceObject = 3000000;
            this.EnergyCost = 25000;
            this.Skin = R.drawable.eclair;
        } else if(N == 6 || M.equals("Region")){
            this.NbObject = 1;
            this.Name = "Region";
            this.CoinWin = 10000;
            this.PriceObject = 5000000;
            this.EnergyCost = 50000;
            this.Skin = R.drawable.eclair;
        } else if(N == 7 || M.equals("Country")){
            this.NbObject = 1;
            this.Name = "Country";
            this.CoinWin = 100000;
            this.PriceObject = 10000000;
            this.EnergyCost = 75000;
            this.Skin = R.drawable.eclair;
        } else if(N == 8 || M.equals("Continent")){
            this.NbObject = 1;
            this.Name = "Continent";
            this.CoinWin = 1000000;
            this.PriceObject = 25000000;
            this.EnergyCost = 500000;
            this.Skin = R.drawable.eclair;
        } else if(N == 9 || M.equals("Planet")){
            this.NbObject = 1;
            this.Name = "Planet";
            this.CoinWin = 10000000;
            this.PriceObject = 100000000;
            this.EnergyCost = 1000000;
            this.Skin = R.drawable.eclair;
        }
    }

    public int getNbObject() {
        return NbObject;
    }

    public void setNbObject(int nbObject) {
        NbObject = nbObject;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCoinWin() {
        return CoinWin;
    }

    public void setCoinWin(int coinWin) {
        CoinWin = coinWin;
    }

    public int getPriceObject() {
        return PriceObject;
    }

    public void setPriceObject(int priceObject) {
        PriceObject = priceObject;
    }

    public int getEnergyCost() {
        return EnergyCost;
    }

    public void setEnergyCost(int energyCost) {
        EnergyCost = energyCost;
    }

    public int getSkin() {
        return Skin;
    }

    public void setSkin(int skin) {
        Skin = skin;
    }
}
