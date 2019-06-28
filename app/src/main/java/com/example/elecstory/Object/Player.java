package com.example.elecstory.Object;

public class Player {

    protected String Name;
    protected int Coin;
    protected Factory MyFact;
    protected int ElectricityStockage;

    public Player(String name, int coin, Factory myFact, int electricityStockage) {
        Name = name;
        Coin = coin;
        MyFact = myFact;
        ElectricityStockage = electricityStockage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCoin() {
        return Coin;
    }

    public void setCoin(int coin) {
        Coin = coin;
    }

    public Factory getMyFact() {
        return MyFact;
    }

    public void setMyFact(Factory myFact) {
        MyFact = myFact;
    }

    public int getElectricityStockage() {
        return ElectricityStockage;
    }

    public void setElectricityStockage(int electricityStockage) {
        ElectricityStockage = electricityStockage;
    }
}
