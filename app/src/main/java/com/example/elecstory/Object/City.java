package com.example.elecstory.Object;

public class City {

    protected String Name;
    protected String Category;
    protected int CoinWin;
    protected int PriceObject;
    protected int EnergyCount;
    protected int Level;
    protected int Skin;

    public City(String name, String category, int coinWin, int priceObject, int energyCount, int level, int skin) {
        Name = name;
        Category = category;
        CoinWin = coinWin;
        PriceObject = priceObject;
        EnergyCount = energyCount;
        Level = level;
        Skin = skin;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public int getEnergyCount() {
        return EnergyCount;
    }

    public void setEnergyCount(int energyCount) {
        EnergyCount = energyCount;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getSkin() {
        return Skin;
    }

    public void setSkin(int skin) {
        Skin = skin;
    }
}
