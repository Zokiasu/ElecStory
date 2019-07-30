package com.example.elecstory.Database;

public class PlayerData {

    protected String UniqueId;
    protected long Coin;
    protected long EnergyPoint;
    protected int QuestId;
    protected long Diamond;
    protected String LastConnection;
    protected long EnergyByClick;
    protected long CoinByClick;

    public PlayerData(String uniqueId, long coin, long energyPoint, int questId, long diamond, String lastConnection, long energyByClick, long coinByClick) {
        UniqueId = uniqueId;
        Coin = coin;
        EnergyPoint = energyPoint;
        QuestId = questId;
        Diamond = diamond;
        LastConnection = lastConnection;
        EnergyByClick = energyByClick;
        CoinByClick = coinByClick;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
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

    public long getDiamond() {
        return Diamond;
    }

    public void setDiamond(long diamond) {
        Diamond = diamond;
    }

    public String getLastConnection() {
        return LastConnection;
    }

    public void setLastConnection(String lastConnection) {
        LastConnection = lastConnection;
    }

    public long getEnergyByClick() {
        return EnergyByClick;
    }

    public void setEnergyByClick(long energyByClick) {
        EnergyByClick = energyByClick;
    }

    public long getCoinByClick() {
        return CoinByClick;
    }

    public void setCoinByClick(long coinByClick) {
        CoinByClick = coinByClick;
    }
}
