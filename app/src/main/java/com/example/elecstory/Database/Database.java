package com.example.elecstory.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.Object.Factory;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    //Database Name
    public static final String DATABASE_NAME = "Database.db";

    //Database Version
    public static final int DATABASE_VERSION = 5;

    //Table Name
    public static final String TABLE_PLAYER = "player_table";
    public static final String TABLE_EARTH = "earth_table";
    public static final String TABLE_FACTORY = "factory_table";
    public static final String TABLE_CRAFT = "craft_table";
    public static final String TABLE_ADS = "ads_table";

    //Common Column names
    public static final String KEY_ID = "id";
    public static final String NUMBER_OBJECT = "number_object";

    //PlayerData Table - Column names
    public static final String UNIQUEID_PLAYER = "uniqueid";
    public static final String USERNAME_PLAYER = "username";
    public static final String AGE_PLAYER = "age";
    public static final String COIN_PLAYER = "coin";
    public static final String ELECPOINT_PLAYER = "elecpoint";
    public static final String QUEST_PLAYER = "quest";
    public static final String LEVEL_PLAYER = "level_player";

    //EarthObject Table - Column names
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
    public static final String TIMEEND_COINADS = "end_coin";
    public static final String TIMEEND_MULTIADS = "end_multi";


    //PlayerData Table Create
    public static final String CREATE_TABLE_PLAYER = "CREATE TABLE " + TABLE_PLAYER
            + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UNIQUEID_PLAYER + " TEXT NOT NULL,"
            + USERNAME_PLAYER + " TEXT NOT NULL,"
            + AGE_PLAYER + " INTEGER NOT NULL,"
            + COIN_PLAYER + " INTEGER DEFAULT 0,"
            + ELECPOINT_PLAYER + " INTEGER DEFAULT 0,"
            + QUEST_PLAYER + " INTEGER DEFAULT 1)";

    //EarthObject Table Create
    public static final String CREATE_TABLE_EARTH = "CREATE TABLE " + TABLE_EARTH
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_BUILDING + " TEXT NOT NULL,"
            + COINWIN_BUILDING + " INTEGER DEFAULT 0,"
            + PRICE_BUILDING + " INTEGER DEFAULT 0,"
            + ENERGYCOST_BUILDING + " INTEGER DEFAULT 0,"
            + SKIN_BUILDING + " INTEGER DEFAULT NULL)";

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
            + SKIN_FACTORY + " INTEGER DEFAULT NULL)";

    //EarthObject Table Create
    public static final String CREATE_TABLE_CRAFT = "CREATE TABLE " + TABLE_CRAFT
            + " (" + NUMBER_OBJECT + " INTEGER DEFAULT 1, "
            + NAME_CRAFT + " TEXT NOT NULL,"
            + COINWIN_CRAFT + " INTEGER DEFAULT 0,"
            + PRICE_CRAFT + " INTEGER DEFAULT 0,"
            + ENERGYCOST_CRAFT + " INTEGER DEFAULT 0,"
            + SKIN_CRAFT + " INTEGER DEFAULT NULL)";

    //Ads Table Create
    public static final String CREATE_TABLE_ADS = "CREATE TABLE " + TABLE_ADS + " ("
            + TIMEEND_SPEEDADS + " TEXT NOT NULL,"
            + TIMEEND_COINADS + " TEXT NOT NULL,"
            + TIMEEND_MULTIADS + " TEXT NOT NULL)";

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
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"Call onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EARTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRAFT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS);
        onCreate(db);
    }

    ////// Player function //////
    /* Add a new player to the internal database * Ajoute un nouveau joueur à la base de donnée interne */
    public void insertPlayer (String name, int age, int coin, int elecpoint, String uniqueid) {
        Log.i(TAG,"Call insertPlayer");
        name = name.replace("'", "''");
        String strSql =
                "INSERT INTO " +  TABLE_PLAYER + "(uniqueid, username, age, coin, elecpoint) " +
                        "VALUES ('" + uniqueid + "', '" + name + "', '" + age + "', '" + coin + "', " + elecpoint + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    /* Returns the information of the first player registered on the phone * Retourne les informations du premier joueur inscrit sur le téléphone*/
    public PlayerData infoFirstPlayer() {
        Log.i(TAG,"Call infoFirstPlayer");
        String strSql = "select * from " + TABLE_PLAYER;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        PlayerData player = new PlayerData(cursor.getInt(0), //ID
                cursor.getString(1), //UniqueId
                cursor.getString(2), //Pseudo
                cursor.getInt(3), //Age
                cursor.getInt(4), //Coin
                cursor.getInt(5), //ElecPoint
                cursor.getInt(6)); //QuestId

        return player;
    }

    public void updateCoin (String name, long nbcoin) {
        Log.i(TAG,"Call updateCoin");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + COIN_PLAYER + " = " + nbcoin + " WHERE " + USERNAME_PLAYER + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateEnergyPoint(String name, long point) {
        Log.i(TAG,"Call updateEnergyPoint");
        if(this.infoFirstPlayer().getEnergyPoint() + point >= 0) {
            name = name.replace("'", "''");
            String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ELECPOINT_PLAYER + " = " + point + " WHERE " + USERNAME_PLAYER + " = '" + name + "'";
            this.getWritableDatabase().execSQL(strSql);
        } else {
            name = name.replace("'", "''");
            String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ELECPOINT_PLAYER + " = " + ELECPOINT_PLAYER + " - " + ELECPOINT_PLAYER + " WHERE " + USERNAME_PLAYER + " = '" + name + "'";
            this.getWritableDatabase().execSQL(strSql);
            Log.i(TAG, "Second Test Energy + Point");
        }
    }

    public void updateQuest (int newQuest) {
        Log.i(TAG,"Call updateQuest");
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + QUEST_PLAYER + " = " + newQuest;
        this.getWritableDatabase().execSQL(strSql);
    }

    ////// EarthObject function //////
    public void insertEarthObject(int NbObject, String name, int coinwin, long price, int energycost, int skin) {
        Log.i(TAG,"Call insertEarthObject");
        ArrayList<EarthObject> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoEarthObject(Test);
        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getName().equals(name)){
                    this.updateNbEarthObject(name, NbObject);
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

    public void updateNbEarthObject(String name, int X) {
        Log.i(TAG,"Call updateNbEarthObject");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_EARTH + " SET " + NUMBER_OBJECT + " = "+ NUMBER_OBJECT + " + "+ X +" WHERE " + NAME_BUILDING + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void clearAllEarthObject() {
        Log.i(TAG,"Call clearAllEarthObject");
        String strSql = "DELETE FROM " + TABLE_EARTH;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteEarthObject(String name) {
        Log.i(TAG,"Call deleteEarthObject");
        ArrayList<EarthObject> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoEarthObject(Test);

        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getNbObject() == 1) {
                    i = Test.size();
                } else if(Test.get(i).getName().equals(name)){
                    this.updateNbEarthObject(name, -1);
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

    public ArrayList<EarthObject> infoEarthObject(ArrayList<EarthObject> earthObjectList) {
        Log.i(TAG,"Call infoEarthObject");

        String strSql = " SELECT * FROM " + TABLE_EARTH;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EarthObject citys = new EarthObject(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //CoinWin
                    cursor.getLong(3), //Price
                    cursor.getInt(4), //EnergyCost
                    cursor.getInt(5)); //Skin
            earthObjectList.add(citys);
            cursor.moveToNext();
        }

        return earthObjectList;
    }

    ////// Factory function //////
    public void insertFactory (int NbObject, String name, int level, int cost, int upgadecost, int pointgenerate, int operatingcost, int pollutiontax, int skin) {
        Log.i(TAG,"Call insertFactory");
        ArrayList<Factory> Test = new ArrayList<>();
        int i = 0;
        Test = this.infoFactory(Test);
        if(Test.size() > 0){
            for(i = 0; i < Test.size(); i++){
                if(Test.get(i).getName().equals(name)){
                    this.updateNbFactory(name, 1);
                    i = Test.size()+1;
                }
            }
        }
        if(Test.size() == 0 || i == Test.size()){
            name = name.replace("'", "''");
            String strSql =
                    "INSERT INTO " + TABLE_FACTORY + "(number_object, name, level, cost, upgadecost, pointgenerate, operatingcost, pollutiontax, skin) " +
                    "VALUES ('" + NbObject + "','" + name + "', '" + level + "', '" + cost + "', '" + upgadecost + "', '" + pointgenerate +  "', '" + operatingcost + "', '" + pollutiontax + "', '" + skin + "')";
            this.getWritableDatabase().execSQL(strSql);
        }
    }

    public void updateNbFactory (String name, int X) {
        Log.i(TAG,"Call updateNbFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + NUMBER_OBJECT + " = "+ NUMBER_OBJECT + " + "+ X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateLvLFactory (String name) {
        Log.i(TAG,"Call updateLvLFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + LEVEL_FACTORY + " = "+ LEVEL_FACTORY + " + 1 WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updatePriceFactory (String name, int X) {
        Log.i(TAG,"Call updatePriceFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + PRICE_FACTORY + " = "+ PRICE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateUpgradeCostFactory (String name, int X) {
        Log.i(TAG,"Call updateUpgradeCostFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + UPGRADE_FACTORY + " = "+ UPGRADE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateEnergyGeneratedFactory (String name, int X) {
        Log.i(TAG,"Call updateEnergyGeneratedFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + POINTGENERATE_FACTORY + " = "+ POINTGENERATE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateOperatingCostFactory (String name, int X) {
        Log.i(TAG,"Call updateOperatingCostFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + OPERATINGPRICE_FACTORY + " = "+ OPERATINGPRICE_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updatePollutionTaxFactory (String name, int X) {
        Log.i(TAG,"Call updatePollutionTaxFactory");
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_FACTORY + " SET " + POLLUTIONTAX_FACTORY + " = "+ POLLUTIONTAX_FACTORY + "*" + X +" WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void clearAllFactory() {
        Log.i(TAG,"Call clearAllFactory");
        String strSql = "DELETE FROM " + TABLE_FACTORY;
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteFactory(String name) {
        Log.i(TAG,"Call deleteFactory");
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
        Log.i(TAG,"Call infoFactory");

        String strSql = " SELECT * FROM " + TABLE_FACTORY;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Factory factorys = new Factory(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //Level
                    cursor.getInt(3), //Cost
                    cursor.getInt(4), //UpgradeCost
                    cursor.getInt(5), //PointGenerate
                    cursor.getInt(6), //ActualFactoryCost
                    cursor.getInt(7), //ActualFactoryTax
                    cursor.getInt(8)); //Skin
            FactoryList.add(factorys);
            cursor.moveToNext();
        }

        return FactoryList;
    }

    ////// Unlock function //////
    public void insertFirstCraft() {
        Log.i(TAG,"Call insertFirstCraft");
        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_CRAFT);
        this.getWritableDatabase().execSQL(CREATE_TABLE_CRAFT);
        EarthObject Test = new EarthObject(0,"");
        String strSql =
        "INSERT INTO " + TABLE_CRAFT + "(number_object, name, coinwin, price, energycost, skin) " +
        "VALUES ("+Test.getNbObject()+", '" + Test.getName() + "', " + Test.getCoinWin() + ", " + Test.getPriceObject() + ", " + Test.getEnergyCost() + ", " + Test.getSkin() + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertCraft (int NbObject, String name, int coinwin, long price, int energycost, int skin) {
        Log.i(TAG,"Call insertCraft");
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " + TABLE_CRAFT + "(number_object, name, coinwin, price, energycost, skin) " +
        "VALUES ('" + NbObject + "','" + name + "', '" + coinwin + "', '" + price + "', '" + energycost + "', " + skin + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteCraft(String name) {
        Log.i(TAG,"Call deleteCraft");
        String strSql = "DELETE FROM " + TABLE_CRAFT + " WHERE " + NAME_CRAFT + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public ArrayList<EarthObject> infoCraft(ArrayList<EarthObject> earthObjectList) {
        Log.i(TAG,"Call infoCraft");

        String strSql = " SELECT * FROM " + TABLE_CRAFT;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            EarthObject citys = new EarthObject(
                    cursor.getInt(0), //NbObject
                    cursor.getString(1), //Name
                    cursor.getInt(2), //CoinWin
                    cursor.getInt(3), //Price
                    cursor.getInt(4), //EnergyCost
                    cursor.getInt(5)); //Skin
            earthObjectList.add(citys);
            cursor.moveToNext();
        }

        return earthObjectList;
    }

    ////// Ads function //////
    public void insertAllAds(){
        Log.i(TAG,"Call insertAllAds");
        String strSql = "INSERT INTO " +  TABLE_ADS + "(end_speed, end_coin, end_multi) " + "VALUES ('0000/00/00 00:00:00', '0000/00/00 00:00:00', '0000/00/00 00:00:00')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertSpeedAds(String Speed){
        Log.i(TAG,"Call insertSpeedAds");
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_SPEEDADS + " = '" + Speed + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertCoinAds(String Coin){
        Log.i(TAG,"Call insertCoinAds");
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_COINADS + " = '" + Coin + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertMultiAds(String Multi){
        Log.i(TAG,"Call insertMultiAds");
        String strSql = "UPDATE " +  TABLE_ADS + " SET " + TIMEEND_MULTIADS + " = '" + Multi + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public String infoSpeedAds(){
        Log.i(TAG,"Call infoSpeedAds");
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String infoCoinAds(){
        Log.i(TAG,"Call infoCoinAds");
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public String infoMultiAds(){
        Log.i(TAG,"Call infoMultiAds");
        String strSql = "select * from " + TABLE_ADS;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }
}
