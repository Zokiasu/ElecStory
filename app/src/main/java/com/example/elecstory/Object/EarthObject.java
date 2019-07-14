package com.example.elecstory.Object;

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

    public EarthObject(int N, String M) {
        if(N == 0 || M.equals("Lamp")){
            this.Name = "Lamp";
            this.CoinWin = 2;
            this.PriceObject = 100;
            this.Skin = R.drawable.ampoule;
        } else if(N == 1 || M.equals("Floor Lamp")){
            this.Name = "Floor Lamp";
            this.CoinWin = 10;
            this.PriceObject = 500;
            this.Skin = R.drawable.ampoule;
        } else if(N == 2 || M.equals("House")){
            this.Name = "House";
            this.CoinWin = 30;
            this.PriceObject = 1500;
            this.Skin = R.drawable.ampoule;
        } else if(N == 3 || M.equals("Street")){
            this.Name = "Street";
            this.CoinWin = 250;
            this.PriceObject = 5000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 4 || M.equals("District")){
            this.Name = "District";
            this.CoinWin = 1500;
            this.PriceObject = 250000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 5 || M.equals("City")){
            this.Name = "City";
            this.CoinWin = 5000;
            this.PriceObject = 750000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 6 || M.equals("Municipality")){
            this.Name = "Municipality";
            this.CoinWin = 8000;
            this.PriceObject = 5000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 7 || M.equals("Region")){
            this.Name = "Region";
            this.CoinWin = 12500;
            this.PriceObject = 25000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 8 || M.equals("Capital")){
            this.Name = "Capital";
            this.CoinWin = 20000;
            this.PriceObject = 40000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 9 || M.equals("Country")){
            this.Name = "Country";
            this.CoinWin = 35000;
            this.PriceObject = 75000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 10 || M.equals("Metropolis")){
            this.Name = "Metropolis";
            this.CoinWin = 50000;
            this.PriceObject = 100000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 11 || M.equals("Continent")){
            this.Name = "Continent";
            this.CoinWin = 75000;
            this.PriceObject = 500000000;
            this.Skin = R.drawable.ampoule;
        } else if(N == 12 || M.equals("Planet")){
            this.Name = "Planet";
            this.CoinWin = 100000;
            this.PriceObject = 1000000000;
            this.Skin = R.drawable.ampoule;
        }

        this.NbObject = 1;
        this.EnergyCost = this.CoinWin*5;
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
