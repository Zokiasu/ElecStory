package com.example.elecstory.Object;

import android.util.Log;

import com.example.elecstory.R;

public class Factory {

    protected int NbObject;
    protected String Name; /*Nom de l'usine*/
    protected int FactoryLevel; /*Niveau à laquelle elle est acheter*/
    protected int RequiredCost; /*Coût de son achat*/
    protected int UpgradeCost; /*Coût de l'update de l'usine*/
    protected int ElecGenerate; /*Point générer par l'usine*/
    protected int OperatingCost; /*Coût d'utilisation hebdomadaire*/
    protected int PollutionTax; /*Taxe de pollution*/
    protected int Skin;


    /*Constructeu des centrales préfaites*/
    public Factory(int N){
        switch (N){
            case 0 :
                this.NbObject = 1;
                this.Name = "Solar Factory";
                this.RequiredCost = 1000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 500;
                this.ElecGenerate = 2;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                this.Skin = R.drawable.eclair;
                break;
            case 1 :
                this.NbObject = 1;
                this.Name = "Geothermal Factory";
                this.RequiredCost = 2000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 1500;
                this.ElecGenerate = 1;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                this.Skin = R.drawable.eclair;
                break;
            case 2 :
                this.NbObject = 1;
                this.Name = "Wind Factory";
                this.RequiredCost = 2000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2000;
                this.ElecGenerate = 2;
                this.OperatingCost = 20;
                this.PollutionTax = 10;
                this.Skin = R.drawable.eclair;
                break;
            case 3 :
                this.NbObject = 1;
                this.Name = "Hydroelectric Factory";
                this.RequiredCost = 5000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2000;
                this.ElecGenerate = 1;
                this.OperatingCost = 50;
                this.PollutionTax = 15;
                this.Skin = R.drawable.eclair;
                break;
            case 4 :
                this.NbObject = 1;
                this.Name = "Thermic Factory";
                this.RequiredCost = 7500000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2500;
                this.ElecGenerate = 2;
                this.OperatingCost = 150;
                this.PollutionTax = 25;
                this.Skin = R.drawable.eclair;
                break;
            case 5 :
                this.NbObject = 1;
                this.Name = "Nuclear Factory";
                this.RequiredCost = 10000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 5000;
                this.ElecGenerate = 5;
                this.OperatingCost = 250;
                this.PollutionTax = 50;
                this.Skin = R.drawable.eclair;
                break;
            default:
                this.NbObject = 1;
                this.Name = "Machine of Gramme";
                this.RequiredCost = 1000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 100;
                this.ElecGenerate = 1;
                this.OperatingCost = 2;
                this.PollutionTax = 0;
                this.Skin = R.drawable.eclair;
                break;
        }
    }

    public Factory(int NbObjects, String name, int factoryLevel, int requiredCost, int upgradeCost, int elecGenerate, int operatingCost, int pollutionTax, int skin) {
        NbObject = NbObjects;
        Name = name;
        FactoryLevel = factoryLevel;
        RequiredCost = requiredCost;
        UpgradeCost = upgradeCost;
        ElecGenerate = elecGenerate;
        OperatingCost = operatingCost;
        PollutionTax = pollutionTax;
        Skin = skin;
    }

    public Factory Upgrade(Factory Fact){
        if(Fact.getFactoryLevel() < 10) {
            Fact.setFactoryLevel(Fact.getFactoryLevel() + 1);
            Fact.setUpgradeCost(Fact.getUpgradeCost() * 2);
            Fact.setElecGenerate(Fact.getElecGenerate() + 1);
        } else {
            int N = -1;
            switch (Fact.getName()){
                case "Machine de Gramme" : N = -1;
                    break;
                case "Centrale Solaire" : N = 0;
                    break;
                case "Centrale Geothermique" : N = 1;
                    break;
                case "Centrale Eolienne" : N = 2;
                    break;
                case "Centrale Hydroelectrique" : N = 3;
                    break;
                case "Centrale Thermique" : N = 4;
                    break;
                case "Centrale Nucleaire" : N = 5;
                    break;
            }
            Fact = new Factory(N+1);
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

    public int getRequiredCost() {
        return RequiredCost;
    }

    public int getFactoryLevel() {
        return FactoryLevel;
    }

    public int getUpgradeCost() {
        return UpgradeCost;
    }

    public int getElecGenerate() {
        return ElecGenerate;
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

    public void setRequiredCost(int requiredCost) {
        RequiredCost = requiredCost;
    }

    public void setFactoryLevel(int factoryLevel) {
        FactoryLevel = factoryLevel;
    }

    public void setUpgradeCost(int upgradeCost) {
        UpgradeCost = upgradeCost;
    }

    public void setElecGenerate(int elecGenerate) {
        ElecGenerate = elecGenerate;
    }

    public void setOperatingCost(int operatingCost) {
        OperatingCost = operatingCost;
    }

    public void setPollutionTax(int pollutionTax) {
        PollutionTax = pollutionTax;
    }
}
