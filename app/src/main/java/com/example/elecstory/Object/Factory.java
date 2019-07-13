package com.example.elecstory.Object;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.elecstory.Craft.EarthObjectActivity;
import com.example.elecstory.Database.Database;
import com.example.elecstory.R;

import static android.widget.Toast.LENGTH_LONG;

public class Factory {

    protected int NbObject;
    protected String Name; /*Nom de l'usine*/
    protected int FactoryLevel; /*Niveau à laquelle elle est acheter*/
    protected int PriceFactory; /*Coût de son achat*/
    protected int UpgradeCost; /*Coût de l'update de l'usine*/
    protected int EnergyProd; /*Point générer par l'usine*/
    protected int OperatingCost; /*Coût d'utilisation hebdomadaire*/
    protected int PollutionTax; /*Taxe de pollution*/
    protected int Skin;


    /*Constructeur des centrales préfaites*/
    public Factory(int N){
        switch (N){
            case -1 :
                this.NbObject = 1;
                this.Name = "Machine of Gramme";
                this.FactoryLevel = 1;
                this.PriceFactory = 500;
                this.UpgradeCost = 100;
                this.EnergyProd = 2;
                this.OperatingCost = 1;
                this.PollutionTax = 2;
                this.Skin = R.drawable.eclair;
                break;
            case 0 :
                this.NbObject = 1;
                this.Name = "Solar Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 100000;
                this.UpgradeCost = 1000;
                this.EnergyProd = 20;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                this.Skin = R.drawable.eclair;
                break;
            case 1 :
                this.NbObject = 1;
                this.Name = "Geothermal Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 250000;
                this.UpgradeCost = 1500;
                this.EnergyProd = 150;
                this.OperatingCost = 15;
                this.PollutionTax = 5;
                this.Skin = R.drawable.eclair;
                break;
            case 2 :
                this.NbObject = 1;
                this.Name = "Wind Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 300000;
                this.UpgradeCost = 2000;
                this.EnergyProd = 1000;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                this.Skin = R.drawable.eclair;
                break;
            case 3 :
                this.NbObject = 1;
                this.Name = "Hydroelectric Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 500000;
                this.UpgradeCost = 10000;
                this.EnergyProd = 5000;
                this.OperatingCost = 50;
                this.PollutionTax = 20;
                this.Skin = R.drawable.eclair;
                break;
            case 4 :
                this.NbObject = 1;
                this.Name = "Thermic Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 750000;
                this.UpgradeCost = 15000;
                this.EnergyProd = 40000;
                this.OperatingCost = 150;
                this.PollutionTax = 50;
                this.Skin = R.drawable.eclair;
                break;
            case 5 :
                this.NbObject = 1;
                this.Name = "Nuclear Factory";
                this.FactoryLevel = 1;
                this.PriceFactory = 1000000;
                this.UpgradeCost = 20000;
                this.EnergyProd = 300000;
                this.OperatingCost = 250;
                this.PollutionTax = 100;
                this.Skin = R.drawable.eclair;
                break;
        }
    }

    public Factory(int NbObjects, String name, int factoryLevel, int priceFactory, int upgradeCost, int energyProd, int operatingCost, int pollutionTax, int skin) {
        NbObject = NbObjects;
        Name = name;
        FactoryLevel = factoryLevel;
        PriceFactory = priceFactory;
        UpgradeCost = upgradeCost;
        EnergyProd = energyProd;
        OperatingCost = operatingCost;
        PollutionTax = pollutionTax;
        Skin = skin;
    }

    public Factory Upgrade(Factory Fact, Database db, Activity activitys){
        if(Fact.getFactoryLevel() < 3) {
            db.updateCoin(db.infoFirstPlayer().getName(), -Fact.getUpgradeCost());

            Fact.setFactoryLevel(Fact.getFactoryLevel() + 1);
            db.updateLvLFactory(Fact.getName());

            Fact.setUpgradeCost(Fact.getUpgradeCost()*5);
            db.updateUpgradeCostFactory(Fact.getName(), 5);

            Fact.setEnergyProd(Fact.getEnergyProd()*2);
            db.updateEnergyGeneratedFactory(Fact.getName(), 2);

            Fact.setOperatingCost(Fact.getOperatingCost()*2);
            db.updateOperatingCostFactory(Fact.getName(), 2);

            Fact.setPollutionTax(Fact.getPollutionTax()*2);
            db.updatePollutionTaxFactory(Fact.getName(), 2);

        } else {
            Toast.makeText(activitys, "Vous n'avez pas assez d'argent ! ", Toast.LENGTH_SHORT).show();
        }
        return Fact;
    }

    public int getNbObject() {
        return NbObject;
    }

    public void setNbObject(int nbObject) {
        NbObject = nbObject;
    }

    public int getSkin() {
        return Skin;
    }

    public void setSkin(int skin) {
        Skin = skin;
    }

    public String getName() {
        return Name;
    }

    public int getPriceFactory() {
        return PriceFactory;
    }

    public int getFactoryLevel() {
        return FactoryLevel;
    }

    public int getUpgradeCost() {
        return UpgradeCost;
    }

    public int getEnergyProd() {
        return EnergyProd;
    }

    public int getOperatingCost() {
        return OperatingCost;
    }

    public int getPollutionTax() {
        return PollutionTax;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPriceFactory(int priceFactory) {
        PriceFactory = priceFactory;
    }

    public void setFactoryLevel(int factoryLevel) {
        FactoryLevel = factoryLevel;
    }

    public void setUpgradeCost(int upgradeCost) {
        UpgradeCost = upgradeCost;
    }

    public void setEnergyProd(int energyProd) {
        EnergyProd = energyProd;
    }

    public void setOperatingCost(int operatingCost) {
        OperatingCost = operatingCost;
    }

    public void setPollutionTax(int pollutionTax) {
        PollutionTax = pollutionTax;
    }
}
