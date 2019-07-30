package com.example.elecstory.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.elecstory.Object.Item;
import com.example.elecstory.Object.Factory;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    protected static final String TAG = "Elecstory.Database";

    //Database Name
    public static final String DATABASE_NAME = "Database.db";

    //Database Version
    public static final int DATABASE_VERSION = 11;

    //Table Name
    public static final String TABLE_PLAYER = "player_table";
    public static final String TABLE_EARTH = "earth_table";
    public static final String TABLE_FACTORY = "factory_table";
    public static final String TABLE_CRAFT = "craft_table";
    public static final String TABLE_ADS = "ads_table";
    public static final String TABLE_FACTORY_SHOP = "factory_shop_table";

    //Common Column names
    public static final String KEY_ID = "id";
    public static final String NUMBER_OBJECT = "number_object";

    //PlayerData Table - Column names
    public static final String UNIQUEID_PLAYER = "uniqueid";
    public static final String COIN_PLAYER = "coin";
    public static final String ELECPOINT_PLAYER = "elecpoint";
    public static final String QUEST_PLAYER = "quest";
    public static final String LASTCONNECTION_PLAYER = "last_connection";
    public static final String DIAMOND_PLAYER = "diamond";
    public static final String ENERGY_BY_CLICK_PLAYER = "energybyclick";
    public static final String COIN_BY_CLICK_PLAYER = "coinbyclick";

    //Item Table - Column names
    public static final String NAME_BUILDING = "name";
    public static final String COINWIN_BUILDING = "coinwin";
    public static final String PRICE_BUILDING = "price";
    public static final String ENERGYCOST_BUILDING = "energycost";
    public static final String SKIN_BUILDING = "skin";

    //Factory Table - Column names
    public static final String NAME_FACTORY = "name";
    public static final String LEVEL_FACTORY = "level";
    public static final String PRICE_FACTORY = "cost";
    public static final String UPGRADE_FACTORY = "upgadecost";
    public static final String POINTGENERATE_FACTORY = "pointgenerate";
    public static final String OPERATINGPRICE_FACTORY = "operatingcost";
    public static final String POLLUTIONTAX_FACTORY = "pollutiontax";
    public static final String SKIN_FACTORY = "skin";

    //Unlock Table - Column names
    public static final String NAME_CRAFT = "name";
    public static final String COINWIN_CRAFT = "coinwin";
    public static final String PRICE_CRAFT = "price";
    public static final String ENERGYCOST_CRAFT = "energycost";
    public static final String SKIN_CRAFT = "skin";

    //Ads Table - Column names
    public static final String TIMEEND_SPEEDADS = "end_speed";
    public static final String TIMEEND_MULTIADS = "end_multi";
    public static final String TIMEEND_SKIP = "end_skip";

    //Factory Shop Table - Column names
    public static final String NAME_FACTORY_SHOP = "name";
    public static final String LEVEL_FACTORY_SHOP = "level";
    public static final String PRICE_FACTORY_SHOP = "cost";
    public static final String UPGRADE_FACTORY_SHOP = "upgadecost";
    public static final String POINTGENERATE_FACTORY_SHOP = "pointgenerate";
    public static final String OPERATINGPRICE_FACTORY_SHOP = "operatingcost";
    public static final String POLLUTIONTAX_FACTORY_SHOP = "pollutiontax";
    public static final String SKIN_FACTORY_SHOP = "skin";


    //PlayerData Table Create
    public static final String CREATE_TABLE_PLAYER = "CREATE TABLE " + TABLE_PLAYER
            + " (" + UNIQUEID_PLAYER + " TEXT NOT NULL,"
            + COIN_PLAYER + " INTEGER DEFAULT 0,"
            + ELECPOINT_PLAYER + " INTEGER DEFAULT 0,"
            + QUEST_PLAYER + " INTEGER DEFAULT 1,"
            + DIAMOND_PLAYER + " INTEGER DEFAULT 0,"
            + LASTCONNECTION_PLAYER + " TEXT NOT NULL,"
            + ENERGY_BY_CLICK_PLAYER + " INTEGER DEFAULT 1,"
            + COIN_BY_CLICK_PLAYER + " INTEGER DEFAULT 1)";

    //Item Table Create
    public static final String CREATE_TABLE_EARTH = "CREATE TABLE " + TABLE_EARTH
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_BUILDING + " TEXT NOT NULL,"
            + COINWIN_BUILDING + " INTEGER DEFAULT 0,"
            + PRICE_BUILDING + " INTEGER DEFAULT 0,"
            + ENERGYCOST_BUILDING + " INTEGER DEFAULT 0,"
            + SKIN_BUILDING + " TEXT DEFAULT NULL)";

    //Factory Table Create
    public static final String CREATE_TABLE_FACTORY = "CREATE TABLE " + TABLE_FACTORY
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_FACTORY + " TEXT NOT NULL,"
            + LEVEL_FACTORY + " INTEGER DEFAULT 0,"
            + PRICE_FACTORY + " INTEGER DEFAULT 0,"
            + UPGRADE_FACTORY + " INTEGER DEFAULT 0,"
            + POINTGENERATE_FACTORY + " INTEGER DEFAULT 0,"
            + OPERATINGPRICE_FACTORY + " INTEGER DEFAULT 0,"
            + POLLUTIONTAX_FACTORY + " INTEGER DEFAULT 0,"
            + SKIN_FACTORY + " TEXT DEFAULT NULL)";

    //Craft Table Create
    public static final String CREATE_TABLE_CRAFT = "CREATE TABLE " + TABLE_CRAFT
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_CRAFT + " TEXT NOT NULL,"
            + COINWIN_CRAFT + " INTEGER DEFAULT 0,"
            + PRICE_CRAFT + " INTEGER DEFAULT 0,"
            + ENERGYCOST_CRAFT + " INTEGER DEFAULT 0,"
            + SKIN_CRAFT + " TEXT DEFAULT NULL)";

    //Ads Table Create
    public static final String CREATE_TABLE_ADS = "CREATE TABLE " + TABLE_ADS + " ("
            + TIMEEND_SPEEDADS + " TEXT NOT NULL,"
            + TIMEEND_MULTIADS + " TEXT NOT NULL,"
            + TIMEEND_SKIP + " TEXT NOT NULL)";

    //Factory Table Create
    public static final String CREATE_TABLE_FACTORY_SHOP = "CREATE TABLE " + TABLE_FACTORY_SHOP
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_FACTORY_SHOP + " TEXT NOT NULL,"
            + LEVEL_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + PRICE_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + UPGRADE_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + POINTGENERATE_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + OPERATINGPRICE_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + POLLUTIONTAX_FACTORY_SHOP + " INTEGER DEFAULT 0,"
            + SKIN_FACTORY_SHOP + " TEXT DEFAULT NULL)";

    ////// Basic db's function //////

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG,"Call Database");
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        Log.i(TAG,"Call onCreate");
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_EARTH);
        db.execSQL(CREATE_TABLE_FACTORY);
        db.execSQL(CREATE_TABLE_CRAFT);
        db.execSQL(CREATE_TABLE_ADS);
        db.execSQL(CREATE_TABLE_FACTORY_SHOP);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"Call onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EARTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRAFT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTORY_SHOP);
        onCreate(db);
    }

    ////// Player function //////
    /* Add a new player to the internal database * Ajoute un nouveau joueur à la base de donnée interne */
    public void insertPlayer (String uniqueid, String lastConnection) {
        String strSql = "INSERT INTO " +  TABLE_PLAYER + "(uniqueid, last_connection) " + "VALUES ('" + uniqueid + "', '" + lastConnection + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    /* Returns the information of the first player registered on the phone * Retourne les informations du premier joueur inscrit sur le téléphone*/
    public PlayerData infoFirstPlayer() {
        String strSql = "select * from " + TABLE_PLAYER;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        PlayerData player = new PlayerData(cursor.getString(0), //UniqueId
                cursor.getLong(1), //Coin
                cursor.getLong(2), //ElecPoint
                cursor.getInt(3), //QuestId
                cursor.getLong(4), //Diamond
                cursor.getString(5), //LastConnection
                cursor.getLong(6), //EnergyByClick
                cursor.getLong(7)); //CoinByClick
        return player;
    }

    public void updateCoin (long nbcoin) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + COIN_PLAYER + " = " + nbcoin;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateEnergyByClick (long energyByClick) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ENERGY_BY_CLICK_PLAYER + " = " + energyByClick;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateCoinByClick (long coinByClick) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + COIN_BY_CLICK_PLAYER + " = " + coinByClick;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateDiamond (long nbdiamond) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + DIAMOND_PLAYER + " = " + nbdiamond;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateEnergyPoint(long point) {
        if(this.infoFirstPlayer().getEnergyPoint() + point >= 0) {
            String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ELECPOINT_PLAYER + " = " + point;
            this.getWritableDatabase().execSQL(strSql);
        } else {
            String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ELECPOINT_PLAYER + " = " + ELECPOINT_PLAYER + " - " + ELECPOINT_PLAYER;
            this.getWritableDatabase().execSQL(strSql);
            Log.i(TAG, "Second Test Energy + Point");
        }
    }

    public void updateQuest (int newQuest) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + QUEST_PLAYER + " = " + newQuest;
        this.getWritableDatabase().execSQL(strSql);
    }

    ////// Item Player function //////
    public void insertItem(long NbObject, String name, long coinwin, long price, long energycost, String skin) {
        ArrayList<Item> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoItem(Test);
        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getName().equals(name)){
                    this.updateNbItem(name, NbObject);
                    i = Test.size();
                }
            }
        }
        if(Test.size() == 0 || i == Test.size()){
            name = name.replace("'", "''");
            String strSql =
                    "INSERT INTO " + TABLE_EARTH + "(number_object, name, coinwin, price, energycost, skin) " +
                    "VALUES (" + NbObject + ",'" + name + "', " + coinwin + ", " + price + ", " + energycost + ", " + skin + ")";
            this.getWritableDatabase().execSQL(strSql);
        }
    }

    public void updateNbItem(String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_EARTH + " SET " + NUMBER_OBJECT + " = "+ NUMBER_OBJECT + " + "+ X +" WHERE " + NAME_BUILDING + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void clearAllItem() {
        String strSql = "DELETE FROM " + TABLE_EARTH;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteItem(String name) {
        ArrayList<Item> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoItem(Test);

        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getNbObject() == 1) {
                    i = Test.size();
                } else if(Test.get(i).getName().equals(name)){
                    this.updateNbItem(name, -1);
                    i = Test.size()+1;
                }
            }
        }

        if(i == Test.size()+1){
            name = name.replace("'", "''");
            String strSql = "DELETE FROM " + TABLE_EARTH + " WHERE " + NAME_BUILDING + " = '" + name + "'";
            this.getWritableDatabase().execSQL(strSql);
        }
    }

    public ArrayList<Item> infoItem(ArrayList<Item> itemList) {
        String strSql = " SELECT * FROM " + TABLE_EARTH;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item citys = new Item(
                    cursor.getLong(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getLong(2), //CoinWin
                    cursor.getLong(3), //Price
                    cursor.getLong(4), //EnergyCost
                    cursor.getString(5)); //Skin
            itemList.add(citys);
            cursor.moveToNext();
        }

        return itemList;
    }

    ////// Factory Player function //////
    public void insertFactory (int NbObject, String name, int level, int cost, long upgradecost, long pointgenerate, long operatingcost, long pollutiontax, String skin) {
        name = name.replace("'", "''");
        String strSql =
                "INSERT INTO " + TABLE_FACTORY + "(number_object, name, level, cost, upgadecost, pointgenerate, operatingcost, pollutiontax, skin) " +
                "VALUES ('" + NbObject + "','" + name + "', '" + level + "', '" + cost + "', '" + upgradecost + "', '" + pointgenerate +  "', '" + operatingcost + "', '" + pollutiontax + "', '" + skin + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateNbFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + NUMBER_OBJECT + " = "+ NUMBER_OBJECT + " + "+ X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateLvLFactory (String name) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + LEVEL_FACTORY + " = "+ LEVEL_FACTORY + " + 1 WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updatePriceFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + PRICE_FACTORY + " = "+ PRICE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateUpgradeCostFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + UPGRADE_FACTORY + " = "+ UPGRADE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateEnergyGeneratedFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + POINTGENERATE_FACTORY + " = "+ POINTGENERATE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateOperatingCostFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + OPERATINGPRICE_FACTORY + " = "+ OPERATINGPRICE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updatePollutionTaxFactory (String name, long X) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + POLLUTIONTAX_FACTORY + " = "+ POLLUTIONTAX_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void clearAllFactory() {
        String strSql = "DELETE FROM " + TABLE_FACTORY;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteFactory(String name) {
        ArrayList<Factory> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoFactory(Test);

        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getNbObject() == 1) {
                    i = Test.size();
                } else if(Test.get(i).getName().equals(name)){
                    this.updateNbFactory(name, -1);
                    i = Test.size()+1;
                }
            }
        }

        if(i == Test.size()+1){
            name = name.replace("'", "''");
            String strSql = "DELETE FROM " + TABLE_FACTORY + " WHERE " + NAME_FACTORY + " = '" + name + "'";
            this.getWritableDatabase().execSQL(strSql);
        }
    }

    public ArrayList<Factory> infoFactory(ArrayList<Factory> FactoryList) {
        String strSql = " SELECT * FROM " + TABLE_FACTORY;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Factory factorys = new Factory(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //Level
                    cursor.getInt(3), //Price
                    cursor.getLong(4), //UpgradeCost
                    cursor.getLong(5), //PointGenerate
                    cursor.getLong(6), //ActualFactoryCost
                    cursor.getLong(7), //ActualFactoryTax
                    cursor.getString(8)); //Skin
            FactoryList.add(factorys);
            cursor.moveToNext();
        }

        return FactoryList;
    }

    ////// Unlock function //////
    public void insertFirstItem() {
        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_CRAFT);
        this.getWritableDatabase().execSQL(CREATE_TABLE_CRAFT);
        Item Test = new Item(0,"");
        String strSql =
        "INSERT INTO " + TABLE_CRAFT + "(number_object, name, coinwin, price, energycost, skin) " +
        "VALUES ("+Test.getNbObject()+", '" + Test.getName() + "', " + Test.getCoinWin() + ", " + Test.getPriceObject() + ", " + Test.getEnergyCost() + ", " + Test.getSkin() + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertCraft (long NbObject, String name, long coinwin, long price, long energycost, String skin) {
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " + TABLE_CRAFT + "(number_object, name, coinwin, price, energycost, skin) " +
        "VALUES ('" + NbObject + "','" + name + "', '" + coinwin + "', '" + price + "', '" + energycost + "', " + skin + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteCraft(String name) {
        String strSql = "DELETE FROM " + TABLE_CRAFT + " WHERE " + NAME_CRAFT + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public ArrayList<Item> infoCraft(ArrayList<Item> itemList) {
        String strSql = " SELECT * FROM " + TABLE_CRAFT;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Item citys = new Item(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //CoinWin
                    cursor.getInt(3), //Price
                    cursor.getInt(4), //EnergyCost
                    cursor.getString(5)); //Skin
            itemList.add(citys);
            cursor.moveToNext();
        }

        return itemList;
    }

    ////// Ads function //////
    public void insertAllTimerAds(){
        String strSql = "INSERT INTO " +  TABLE_ADS + "(end_speed, end_multi, end_skip) " + "VALUES ('0000/00/00 00:00:00', '0000/00/00 00:00:00', '0000/00/00 00:00:00')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertSpeedAds(String Speed){
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_SPEEDADS + " = '" + Speed + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertMultiAds(String Multi){
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_MULTIADS + " = '" + Multi + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertSkipAds(String Skip){
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_SKIP + " = '" + Skip + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public String infoSpeedAds(){
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String infoMultiAds(){
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public String infoSkipAds(){
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    ////// Factory Shop function //////
    public void insertFactoryShop (int NbObject, String name, int level, int cost, long upgradecost, long pointgenerate, long operatingcost, long pollutiontax, String skin) {
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " + TABLE_FACTORY_SHOP + "(number_object, name, level, cost, upgadecost, pointgenerate, operatingcost, pollutiontax, skin) " +
        "VALUES ('" + NbObject + "','" + name + "', '" + level + "', '" + cost + "', '" + upgradecost + "', '" + pointgenerate +  "', '" + operatingcost + "', '" + pollutiontax + "', '" + skin + "')";
        this.getWritableDatabase().execSQL(strSql);
    }


    public ArrayList<Factory> infoFactoryShop(ArrayList<Factory> FactoryList) {
        String strSql = " SELECT * FROM " + TABLE_FACTORY_SHOP;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Factory factorys = new Factory(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //Level
                    cursor.getInt(3), //Cost
                    cursor.getLong(4), //UpgradeCost
                    cursor.getInt(5), //PointGenerate
                    cursor.getInt(6), //ActualFactoryCost
                    cursor.getInt(7), //ActualFactoryTax
                    cursor.getString(8)); //Skin
            FactoryList.add(factorys);
            cursor.moveToNext();
        }

        return FactoryList;
    }

    public void fillShopFactory(){
        Factory test;
        for (int i = -1; i < 6; i++){
            test = new Factory(i);
            Log.i(TAG, "Name " + test.getName());
            insertFactoryShop(test.getNbObject(),test.getName(),test.getFactoryLevel(),test.getPriceFactory(),test.getUpgradeCost(),test.getEnergyProd(),test.getOperatingCost(),test.getPollutionTax(),test.getSkin());
        }
    }
}
