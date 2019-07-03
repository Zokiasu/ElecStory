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
    public static final int DATABASE_VERSION = 1;

    //Table Name
    public static final String TABLE_PLAYER = "player_table";
    public static final String TABLE_EARTH = "earth_table";
    public static final String TABLE_FACTORY = "factory_table";

    //Common Column names
    public static final String KEY_ID = "id";

    //PlayerData Table - Column names
    public static final String UNIQUEID_PLAYER = "uniqueid";
    public static final String USERNAME_PLAYER = "username";
    public static final String AGE_PLAYER = "age";
    public static final String COIN_PLAYER = "coin";
    public static final String ELECPOINT_PLAYER = "elecpoint";
    public static final String QUEST_PLAYER = "quest";

    //EarthObject Table - Column names
    public static final String NAME_BUILDING = "name";
    public static final String COINWIN_BUILDING = "coinwin";
    public static final String PRICE_BUILDING = "price";
    public static final String ENERGYCOST_BUILDING = "energycost";
    public static final String SKIN_BUILDING = "skin";

    //Factory Table - Column names
    public static final String NAME_FACTORY = "name";
    public static final String LEVEL_FACTORY = "level";
    public static final String COST_FACTORY = "cost";
    public static final String UPGRADECOST_FACTORY = "upgadecost";
    public static final String POINTGENERATE_FACTORY = "pointgenerate";
    public static final String OPERATINGCOST_FACTORY = "operatingcost";
    public static final String POLLUTIONTAX_FACTORY = "pollutiontax";
    public static final String SKIN_FACTORY = "skin";


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
            + " (" + NAME_BUILDING + " TEXT NOT NULL,"
            + COINWIN_BUILDING + " INTEGER DEFAULT 0,"
            + PRICE_BUILDING + " INTEGER DEFAULT 0,"
            + ENERGYCOST_BUILDING + " INTEGER DEFAULT 0,"
            + SKIN_BUILDING + " INTEGER DEFAULT NULL)";

    //Factory Table Create
    public static final String CREATE_TABLE_FACTORY = "CREATE TABLE " + TABLE_FACTORY
            + " (" + NAME_FACTORY + " TEXT NOT NULL,"
            + LEVEL_FACTORY + " INTEGER DEFAULT 0,"
            + COST_FACTORY + " INTEGER DEFAULT 0,"
            + UPGRADECOST_FACTORY + " INTEGER DEFAULT 0,"
            + POINTGENERATE_FACTORY + " INTEGER DEFAULT 0,"
            + OPERATINGCOST_FACTORY + " INTEGER DEFAULT 0,"
            + POLLUTIONTAX_FACTORY + " INTEGER DEFAULT 0,"
            + SKIN_FACTORY + " INTEGER DEFAULT NULL)";

    ////// Basic db's function //////

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_EARTH);
        db.execSQL(CREATE_TABLE_FACTORY);
        Log.i(TAG, "onCreate DB invoked ");
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Update from version " + oldVersion
                + " to version " + newVersion
                + ", the old data will be destroyed.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EARTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTORY);
        onCreate(db);
    }

    ////// Player function //////
    /* Add a new player to the internal database * Ajoute un nouveau joueur à la base de donnée interne */
    public void insertPlayer (String name, int age, int coin, int elecpoint, String uniqueid) {
        name = name.replace("'", "''");
        String strSql =
                "INSERT INTO " +  TABLE_PLAYER + "(uniqueid, username, age, coin, elecpoint) " +
                        "VALUES ('" + uniqueid + "', '" + name + "', '" + age + "', '" + coin + "', " + elecpoint + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    /* Returns the information of the first player registered on the phone * Retourne les informations du premier joueur inscrit sur le téléphone*/
    public PlayerData infoFirstPlayer() {
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

    public void updateCoin (String name, int nbcoin) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + COIN_PLAYER + " = "+ COIN_PLAYER + " + " + nbcoin + " WHERE " + USERNAME_PLAYER + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateElecPoint (String name, int point) {
        name = name.replace("'", "''");
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + ELECPOINT_PLAYER + " = "+ ELECPOINT_PLAYER + " + " + point + " WHERE " + USERNAME_PLAYER + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void updateQuest (int newQuest) {
        String strSql = "UPDATE " + TABLE_PLAYER + " SET " + QUEST_PLAYER + " = " + newQuest;
        this.getWritableDatabase().execSQL(strSql);
    }

    ////// Earth function //////
    public void insertCity (String name, int coinwin, int price, int energycost, int skin) {
        name = name.replace("'", "''");
        String strSql =
                "INSERT INTO " + TABLE_EARTH + "(name, coinwin, price, energycost, skin) " +
                        "VALUES ('" + name + "', '" + coinwin + "', '" + price + "', '" + energycost + "', " + skin + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteCity(String name) {
        String strSql = "DELETE FROM " + TABLE_EARTH + " WHERE " + NAME_BUILDING + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public ArrayList<EarthObject> infoCity(ArrayList<EarthObject> earthObjectList) {

        String strSql = " SELECT * FROM " + TABLE_EARTH;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            EarthObject citys = new EarthObject(cursor.getString(0), //Name
                    cursor.getInt(1), //CoinWin
                    cursor.getInt(2), //Price
                    cursor.getInt(3), //EnergyCost
                    cursor.getInt(4)); //Skin
            earthObjectList.add(citys);
            cursor.moveToNext();
        }

        return earthObjectList;
    }

    ////// Factory function //////
    public void insertFactory (String name, int level, int cost, int upgadecost, int pointgenerate, int operatingcost, int pollutiontax, int skin) {
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " + TABLE_FACTORY + "(name, level, cost, upgadecost, pointgenerate, operatingcost, pollutiontax, skin) " +
        "VALUES ('" + name + "', '" + level + "', '" + cost + "', '" + upgadecost + "', '" + pointgenerate +  "', '" + operatingcost + "', '" + pollutiontax + "', '" + skin + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public void deleteFactory(String name) {
        String strSql = "DELETE FROM " + TABLE_FACTORY + " WHERE " + NAME_FACTORY + " = '" + name + "'";
        this.getWritableDatabase().execSQL(strSql);
    }

    public Factory infoFirstFactory() {

        String strSql = " SELECT * FROM " + TABLE_FACTORY;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        Factory fact = new Factory(cursor.getString(0), //Name
                cursor.getInt(1), //Level
                cursor.getInt(2), //Cost
                cursor.getInt(3), //UpgradeCost
                cursor.getInt(4), //PointGenerate
                cursor.getInt(5), //OperatingCost
                cursor.getInt(6), //PollutionTax
                cursor.getInt(7)); //Skin

        return fact;
    }

    public ArrayList<Factory> infoFactory(ArrayList<Factory> FactoryList) {

        String strSql = " SELECT * FROM " + TABLE_FACTORY;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Factory factorys = new Factory(cursor.getString(0), //Name
                    cursor.getInt(1), //Level
                    cursor.getInt(2), //Cost
                    cursor.getInt(3), //UpgradeCost
                    cursor.getInt(4), //PointGenerate
                    cursor.getInt(5), //OperatingCost
                    cursor.getInt(6), //PollutionTax
                    cursor.getInt(7)); //Skin
            FactoryList.add(factorys);
            cursor.moveToNext();
        }

        return FactoryList;
    }
}
