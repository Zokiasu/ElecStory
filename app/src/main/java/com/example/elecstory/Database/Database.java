package com.example.elecstory.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.elecstory.Object.City;
import com.example.elecstory.Object.Factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "MainActivity";

    //Database Name
    public static final String DATABASE_NAME = "GDB.db";

    //Database Version
    public static final int DATABASE_VERSION = 5;

    //Table Name
    public static final String TABLE_PLAYER = "player_table";
    public static final String TABLE_CITY_PLAYER = "city_player_table";
    public static final String TABLE_FACTORY_PLAYER = "factory_player_table";

    //Common Column names
    public static final String KEY_ID = "id";

    //PlayerData Table - Column names
    public static final String UNIQUEID_PLAYER = "uniqueid";
    public static final String USERNAME_PLAYER = "username";
    public static final String AGE_PLAYER = "age";
    public static final String COIN_PLAYER = "coin";
    public static final String ELECPOINT_PLAYER = "elecpoint";

    //City Table - Column names
    public static final String NAME_BUILDING = "name";
    public static final String CATEGORY_BUILDING = "category";
    public static final String COINWIN_BUILDING = "coinwin";
    public static final String PRICE_BUILDING = "price";
    public static final String ENERGYCOST_BUILDING = "energycost";
    public static final String LEVEL_BUILDING = "level";
    public static final String SKIN_BUILDING = "skin";

    //Factory Table - Column names
    public static final String NAME_FACTORY = "name";
    public static final String LEVEL_FACTORY = "level";
    public static final String COST_FACTORY = "cost";
    public static final String UPGRADECOST_FACTORY = "upgadecost";
    public static final String POINTGENERATE_FACTORY = "pointgenerate";
    public static final String MILLIGENERATE_FACTORY = "milligenerate";
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
            + ELECPOINT_PLAYER + " INTEGER DEFAULT 0)";

    //City Table Create
    public static final String CREATE_TABLE_CITY = "CREATE TABLE " + TABLE_CITY_PLAYER
            + " (" + NAME_BUILDING + " TEXT NOT NULL,"
            + CATEGORY_BUILDING + " TEXT NOT NULL,"
            + COINWIN_BUILDING + " INTEGER DEFAULT 0,"
            + PRICE_BUILDING + " INTEGER DEFAULT 0,"
            + ENERGYCOST_BUILDING + " INTEGER DEFAULT 0,"
            + LEVEL_BUILDING + " INTEGER DEFAULT 0,"
            + SKIN_BUILDING + " INTEGER DEFAULT NULL)";

    //Factory Table Create
    public static final String CREATE_TABLE_FACTORY = "CREATE TABLE " + TABLE_FACTORY_PLAYER
            + " (" + NAME_FACTORY + " TEXT NOT NULL,"
            + LEVEL_FACTORY + " INTEGER DEFAULT 0,"
            + COST_FACTORY + " INTEGER DEFAULT 0,"
            + UPGRADECOST_FACTORY + " INTEGER DEFAULT 0,"
            + POINTGENERATE_FACTORY + " INTEGER DEFAULT 0,"
            + MILLIGENERATE_FACTORY + " INTEGER DEFAULT 0,"
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
        db.execSQL(CREATE_TABLE_CITY);
        db.execSQL(CREATE_TABLE_FACTORY);
        Log.i(TAG, "onCreate DB invoked ");
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Update from version " + oldVersion
                + " to version " + newVersion
                + ", the old data will be destroyed.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTORY_PLAYER);
        onCreate(db);
    }

    ////// PlayerData's function //////

    /* Add a new player to the internal database * Ajoute un nouveau joueur à la base de donnée interne */
    public void insertPlayer (String name, int age, int coin, int elecpoint, String uniqueid) {
        name = name.replace("'", "''");
        String strSql =
            "INSERT INTO " +  TABLE_PLAYER + "(uniqueid, username, age, coin, elecpoint) " +
            "VALUES ('" + uniqueid + "', '" + name + "', '" + age + "', '" + coin + "', " + elecpoint + ")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i(TAG, "Create new player");
    }

    public void insertFactory (String name, int level, int cost, int upgadecost, int pointgenerate, int milligenerate, int operatingcost, int pollutiontax, int skin) {
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " +  TABLE_FACTORY_PLAYER + "(name, level, cost, upgadecost, pointgenerate, milligenerate, operatingcost, pollutiontax, skin) " +
        "VALUES ('" + name + "', '" + level + "', '" + cost + "', '" + upgadecost + "', '" + pointgenerate + "', " +
                "'" + milligenerate + "', '" + operatingcost + "', '" + pollutiontax + "', '" + skin + "')";
        this.getWritableDatabase().execSQL(strSql);
        Log.i(TAG, "Add new factory to player");
    }

    public void insertCity (String name, String category, int coinwin, int price, int energycost, int level, int skin) {
        name = name.replace("'", "''");
        String strSql =
        "INSERT INTO " +  TABLE_CITY_PLAYER + "(name, category, coinwin, price, energycost, level, skin) " +
        "VALUES ('" + name + "', '" + category + "', '" + coinwin + "', '" + price + "', '" + energycost + "', " + "'" + level + "', '" + skin + "')";
        this.getWritableDatabase().execSQL(strSql);
        Log.i(TAG, "Add new city to player");
    }

    /* Returns the information of the first player registered on the phone * Retourne les informations du premier joueur inscrit sur le téléphone*/
    public PlayerData infoFirstPlayer() {
        Log.i(TAG, "Call Info Player");
        String strSql = "select * from " + TABLE_PLAYER;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();
        PlayerData player = new PlayerData(cursor.getInt(0), //ID
                cursor.getString(1), //UniqueId
                cursor.getString(2), //Pseudo
                cursor.getInt(3), //Age
                cursor.getInt(4), //Coin
                cursor.getInt(5)); //ElecPoint

        return player;
    }

    public Factory infoFirstFactory() {
        Log.i(TAG, "Call Info Factory");

        String strSql = " SELECT * FROM " + TABLE_FACTORY_PLAYER;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        Factory fact = new Factory(cursor.getString(0), //Name
                cursor.getInt(1), //Level
                cursor.getInt(2), //Cost
                cursor.getInt(3), //UpgradeCost
                cursor.getInt(4), //PointGenerate
                cursor.getInt(5), //MilliGenerate
                cursor.getInt(6), //OperatingCost
                cursor.getInt(7), //PollutionTax
                cursor.getInt(8)); //Skin

        return fact;
    }

    public ArrayList infoCity(ArrayList<City> CityList) {

        String strSql = " SELECT * FROM " + TABLE_CITY_PLAYER;
        Cursor cursor = this.getReadableDatabase().rawQuery( strSql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            CityList.add((City) cursor);
            cursor.moveToNext();
        }

        return CityList;
    }
}
