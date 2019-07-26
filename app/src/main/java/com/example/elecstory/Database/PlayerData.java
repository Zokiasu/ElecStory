package com.example.elecstory.Database;

public class PlayerData {

    protected int IdPlayer;
    protected String UniqueId;
    protected String Name;
    protected int Age;
    protected long Coin;
    protected long EnergyPoint;
    protected int QuestId;
    protected int Diamond;
    protected String LastConnection;

    public PlayerData(int idPlayer, String uniqueId, String name, int age, long coin, long energyPoint, int questId, int diamond, String lastConnection) {
        IdPlayer = idPlayer;
        UniqueId = uniqueId;
        Name = name;
        Age = age;
        Coin = coin;
        EnergyPoint = energyPoint;
        QuestId = questId;
        Diamond = diamond;
        LastConnection = lastConnection;
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

    public long getCoin() {
        return Coin;
    }

    public void setCoin(long coin) {
        Coin = coin;
    }

    public long getEnergyPoint() {
        return EnergyPoint;
    }

    public void setEnergyPoint(long energyPoint) {
        EnergyPoint = energyPoint;
    }

    public int getQuestId() {
        return QuestId;
    }

    public void setQuestId(int questId) {
        QuestId = questId;
    }

    public int getDiamond() {
        return Diamond;
    }

    public void setDiamond(int diamond) {
        Diamond = diamond;
    }

    public String getLastConnection() {
        return LastConnection;
    }

    public void setLastConnection(String lastConnection) {
        LastConnection = lastConnection;
    }
}
