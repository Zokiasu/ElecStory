package com.example.elecstory.Object;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.elecstory.Database.Database;
import com.example.elecstory.R;

import static android.content.Context.MODE_PRIVATE;

public class Factory {

    protected int NbObject;
    protected String Name; /*Nom de l'usine*/
    protected int FactoryLevel; /*Niveau à laquelle elle est acheter*/
    protected int PriceFactory; /*Coût de son achat*/
    protected long UpgradeCost; /*Coût de l'update de l'usine*/
    protected long EnergyProd; /*Point générer par l'usine*/
    protected long OperatingCost; /*Coût d'utilisation hebdomadaire*/
    protected long PollutionTax; /*Taxe de pollution*/
    protected String Skin;

    private static final String PREFS = "PREFS";
    private static final String PREFS_COIN = "PREFS_COIN";
    SharedPreferences sharedPreferences;

    public Factory(int nbObject, String name, int factoryLevel, int priceFactory, long upgradeCost, long energyProd, long operatingCost, long pollutionTax, String skin) {
        NbObject = nbObject;
        Name = name;
        FactoryLevel = factoryLevel;
        PriceFactory = priceFactory;
        UpgradeCost = upgradeCost;
        EnergyProd = energyProd;
        OperatingCost = operatingCost;
        PollutionTax = pollutionTax;
        Skin = skin;
    }

    /*Constructeur des centrales préfaites*/
    public Factory(int N){
        switch (N){
            case -1 :
                this.NbObject = 0;
                this.Name = "Machine of Gramme";
                this.FactoryLevel = 0;
                this.PriceFactory = 50;
                this.EnergyProd = 1;
                this.OperatingCost = 1;
                this.PollutionTax = 0;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 0 :
                this.NbObject = 0;
                this.Name = "Solar Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 100000;
                this.EnergyProd = 20;
                this.OperatingCost = 5;
                this.PollutionTax = 2;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 1 :
                this.NbObject = 0;
                this.Name = "Geothermal Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 500000;
                this.EnergyProd = 20;
                this.OperatingCost = 15;
                this.PollutionTax = 5;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 2 :
                this.NbObject = 0;
                this.Name = "Wind Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 1000000;
                this.EnergyProd = 20;
                this.OperatingCost = 25;
                this.PollutionTax = 5;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 3 :
                this.NbObject = 0;
                this.Name = "Hydroelectric Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 5000000;
                this.EnergyProd = 100;
                this.OperatingCost = 35;
                this.PollutionTax = 10;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 4 :
                this.NbObject = 0;
                this.Name = "Thermic Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 10000000;
                this.EnergyProd = 150;
                this.OperatingCost = 50;
                this.PollutionTax = 15;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
            case 5 :
                this.NbObject = 0;
                this.Name = "Nuclear Factory";
                this.FactoryLevel = 0;
                this.PriceFactory = 100000000;
                this.EnergyProd = 200;
                this.OperatingCost = 150;
                this.PollutionTax = 50;
                this.Skin = String.valueOf(R.drawable.eclair);
                break;
        }
        this.UpgradeCost = this.PriceFactory/5;
    }

    public Factory Upgrade(Factory Fact, Database db, Activity activitys){

        sharedPreferences = activitys.getSharedPreferences(PREFS, MODE_PRIVATE);

        if (Fact.getNbObject() < 1) {
            Fact.setNbObject(1);
            Fact.setFactoryLevel(1);
            db.updateNbFactory(Fact.getName(), 1);
            db.updateLvLFactory(Fact.getName());

            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, sharedPreferences.getLong(PREFS_COIN, 0) - Fact.getPriceFactory())
                    .apply();
            
        } else {
            sharedPreferences
                    .edit()
                    .putLong(PREFS_COIN, sharedPreferences.getLong(PREFS_COIN, 0) - Fact.getUpgradeCost())
                    .apply();

            Fact.setFactoryLevel(Fact.getFactoryLevel() + 1);
            db.updateLvLFactory(Fact.getName());

            Fact.setUpgradeCost(Fact.getUpgradeCost() * 3);
            db.updateUpgradeCostFactory(Fact.getName(), 3);

            Fact.setEnergyProd(Fact.getEnergyProd() * 2);
            db.updateEnergyGeneratedFactory(Fact.getName(), 2);

            Fact.setOperatingCost(Fact.getOperatingCost() * 3);
            db.updateOperatingCostFactory(Fact.getName(), 3);

            Fact.setPollutionTax(Fact.getPollutionTax() * 2);
            db.updatePollutionTaxFactory(Fact.getName(), 2);
        }
        
        return Fact;
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

    public int getFactoryLevel() {
        return FactoryLevel;
    }

    public void setFactoryLevel(int factoryLevel) {
        FactoryLevel = factoryLevel;
    }

    public int getPriceFactory() {
        return PriceFactory;
    }

    public void setPriceFactory(int priceFactory) {
        PriceFactory = priceFactory;
    }

    public long getUpgradeCost() {
        return UpgradeCost;
    }

    public void setUpgradeCost(long upgradeCost) {
        UpgradeCost = upgradeCost;
    }

    public long getEnergyProd() {
        return EnergyProd;
    }

    public void setEnergyProd(long energyProd) {
        EnergyProd = energyProd;
    }

    public long getOperatingCost() {
        return OperatingCost;
    }

    public void setOperatingCost(long operatingCost) {
        OperatingCost = operatingCost;
    }

    public long getPollutionTax() {
        return PollutionTax;
    }

    public void setPollutionTax(long pollutionTax) {
        PollutionTax = pollutionTax;
    }

    public String getSkin() {
        return Skin;
    }

    public void setSkin(String skin) {
        Skin = skin;
    }

    public static String getPREFS() {
        return PREFS;
    }

    public static String getPrefsCoin() {
        return PREFS_COIN;
    }
}
