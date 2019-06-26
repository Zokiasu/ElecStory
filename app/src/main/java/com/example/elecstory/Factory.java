package com.example.elecstory;

import android.util.Log;

public class Factory {

    protected String Name; /*Nom de l'usine*/
    protected int RequiredCost; /*Coût de son achat*/
    protected int FactoryLevel; /*Niveau à laquelle elle est acheter*/
    protected int UpgradeCost; /*Coût de l'update de l'usine*/
    protected int ElecGenerate; /*Point générer par l'usine*/
    protected int ElecByMillisecond; /*Nombre de milliseconde auquel les points sont générer*/
    protected int OperatingCost; /*Coût d'utilisation hebdomadaire*/
    protected int PollutionTax; /*Taxe de pollution*/

    /*
     * Machine de Gramme
     * 0 Centrale Solaires 500k
     * 1 Centrale Géothermique 5M
     * 2 Centrale Eoliennes 5M
     * 3 Centrale hydroélectrique 92M
     * 4 Centrale thermique 500M
     * 5 Centrale Nucléaire 1,5MM
     * */

    public Factory(int N){
        switch (N){
            case 0 :
                this.Name = "Centrale Solaire";
                this.RequiredCost = 1000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 500;
                this.ElecGenerate = 2;
                this.ElecByMillisecond = 2500;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                break;
            case 1 :
                this.Name = "Centrale Geothermique";
                this.RequiredCost = 2000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 1500;
                this.ElecGenerate = 1;
                this.ElecByMillisecond = 2500;
                this.OperatingCost = 10;
                this.PollutionTax = 5;
                break;
            case 2 :
                this.Name = "Centrale Eolienne";
                this.RequiredCost = 2000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2000;
                this.ElecGenerate = 2;
                this.ElecByMillisecond = 2500;
                this.OperatingCost = 20;
                this.PollutionTax = 10;
                break;
            case 3 :
                this.Name = "Centrale Hydroelectrique";
                this.RequiredCost = 5000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2000;
                this.ElecGenerate = 1;
                this.ElecByMillisecond = 1500;
                this.OperatingCost = 50;
                this.PollutionTax = 15;
                break;
            case 4 :
                this.Name = "Centrale Thermique";
                this.RequiredCost = 7500000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 2500;
                this.ElecGenerate = 2;
                this.ElecByMillisecond = 1000;
                this.OperatingCost = 150;
                this.PollutionTax = 25;
                break;
            case 5 :
                this.Name = "Centrale Nucleaire";
                this.RequiredCost = 10000000;
                this.FactoryLevel = 1;
                this.UpgradeCost = 5000;
                this.ElecGenerate = 5;
                this.ElecByMillisecond = 1000;
                this.OperatingCost = 250;
                this.PollutionTax = 50;
                break;
            default:
                this.Name = "Machine de Gramme";
                this.RequiredCost = 100;
                this.FactoryLevel = 1;
                this.UpgradeCost = 1000;
                this.ElecGenerate = 1;
                this.ElecByMillisecond = 3500;
                this.OperatingCost = 5;
                this.PollutionTax = 5;
                break;
        }
    }

    public Factory Upgrade2(Factory Fact){
        Log.i("#Upgrade2#","");
        switch (Fact.getName()){
            case "Centrale Solaire" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(1000);
                Fact.setElecByMillisecond(2000);
                return Fact;
            case "Centrale Geothermique" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(3000);
                Fact.setElecByMillisecond(2000);
                return Fact;
            case "Centrale Eolienne" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(4000);
                Fact.setElecByMillisecond(2000);
                return Fact;
            case "Centrale Hydroelectrique" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(4000);
                Fact.setElecByMillisecond(1000);
                return Fact;
            case "Centrale Thermique" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(5000);
                Fact.setElecByMillisecond(750);
                return Fact;
            case "Centrale Nucleaire" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(10000);
                Fact.setElecByMillisecond(750);
                return Fact;
            case "Machine de Gramme" :
                Fact.setFactoryLevel(2);
                Fact.setUpgradeCost(2000);
                Fact.setElecByMillisecond(3000);
                return Fact;
        }
        return Fact;
    }

    public Factory Upgrade3(Factory Fact){
        Log.i("#Upgrade2#","");
        switch (Fact.getName()){
            case "Centrale Solaire" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(3);
                return Fact;
            case "Centrale Geothermique" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(2);
                return Fact;
            case "Centrale Eolienne" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(3);
                return Fact;
            case "Centrale Hydroelectrique" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(2);
                return Fact;
            case "Centrale Thermique" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(3);
                return Fact;
            case "Centrale Nucleaire" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(6);
                return Fact;
            case "Machine de Gramme" :
                Fact.setFactoryLevel(3);
                Fact.setUpgradeCost(0);
                Fact.setElecGenerate(2);
                return Fact;
        }
        return Fact;
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

    public int getElecByMillisecond() {
        return ElecByMillisecond;
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

    public void setElecByMillisecond(int elecByMillisecond) {
        ElecByMillisecond = elecByMillisecond;
    }

    public void setOperatingCost(int operatingCost) {
        OperatingCost = operatingCost;
    }

    public void setPollutionTax(int pollutionTax) {
        PollutionTax = pollutionTax;
    }
}
