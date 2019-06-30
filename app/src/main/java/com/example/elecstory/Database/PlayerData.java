package com.example.elecstory.Database;

public class PlayerData {

    protected int IdPlayer;
    protected String UniqueId;
    protected String Name;
    protected int Age;
    protected int Coin;
    protected int ElectricityStockage;

    public PlayerData(int idPlayer, String uniqueId, String name, int age, int coin, int electricityStockage) {
        IdPlayer = idPlayer;
        UniqueId = uniqueId;
        Name = name;
        Age = age;
        Coin = coin;
        ElectricityStockage = electricityStockage;
    }

    public int getIdPlayer() {
        return IdPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        IdPlayer = idPlayer;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getCoin() {
        return Coin;
    }

    public void setCoin(int coin) {
        Coin = coin;
    }

    public int getElectricityStockage() {
        return ElectricityStockage;
    }

    public void setElectricityStockage(int electricityStockage) {
        ElectricityStockage = electricityStockage;
    }
}
