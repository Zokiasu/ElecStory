package com.example.elecstory.Quest;


import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.Item;
import com.example.elecstory.R;

import java.util.ArrayList;

public class Quest {

    public int IdQuest;
    public String NameReward;
    public int SkinReward;
    public ArrayList<Item> itemRequest;
    public ArrayList<Long> NbRequest;

    protected static final String TAG = "Elecstory.Quest";

    public Quest(int idQuest, String nameReward, int skinReward, ArrayList<Item> itemRequest, ArrayList<Long> nbRequest) {
        IdQuest = idQuest;
        NameReward = nameReward;
        SkinReward = skinReward;
        this.itemRequest = itemRequest;
        NbRequest = nbRequest;
    }

    public Quest (int N){
        switch (N){
            /*Floor Lamp Quest*/
            case 1 :
                this.IdQuest = 1;
                this.SkinReward = R.drawable.floor_lamp;
                this.NameReward = "Floor Lamp";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0,"Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 3);
                break;
            /*House Quest*/
            case 2 :
                this.IdQuest = 2;
                this.SkinReward = R.drawable.house;
                this.NameReward = "House";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 25);
                this.NbRequest.add((long) 2);
                break;
            /*House Quest*/
            case 3 :
                this.IdQuest = 3;
                this.SkinReward = R.drawable.house;
                this.NameReward = "Building";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 50);
                this.NbRequest.add((long) 20);
                break;
            /*Street Quest*/
            case 4 :
                this.IdQuest = 4;
                this.SkinReward = R.drawable.street;
                this.NameReward = "Street";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 75);
                this.NbRequest.add((long) 40);
                this.NbRequest.add((long) 8);
                break;
            /*District Quest*/
            case 5 :
                this.IdQuest = 5;
                this.SkinReward = R.drawable.district;
                this.NameReward = "District";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 150);
                this.NbRequest.add((long) 100);
                this.NbRequest.add((long) 25);
                this.NbRequest.add((long) 5);
                break;
            /*City Quest*/
            case 6 :
                this.IdQuest = 6;
                this.SkinReward = R.drawable.city;
                this.NameReward = "City";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 200);
                this.NbRequest.add((long) 125);
                this.NbRequest.add((long) 50);
                this.NbRequest.add((long) 15);
                this.NbRequest.add((long) 5);
                break;
            /*Municipality Quest*/
            case 7 :
                this.IdQuest = 7;
                this.SkinReward = R.drawable.municipality;
                this.NameReward = "Municipality";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 250);
                this.NbRequest.add((long) 150);
                this.NbRequest.add((long) 75);
                this.NbRequest.add((long) 25);
                this.NbRequest.add((long) 10);
                this.NbRequest.add((long) 3);
                break;
            /*Region Quest*/
            case 8 :
                this.IdQuest = 8;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Region";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.itemRequest.add(new Item(6, "Municipality"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 300);
                this.NbRequest.add((long) 175);
                this.NbRequest.add((long) 100);
                this.NbRequest.add((long) 35);
                this.NbRequest.add((long) 20);
                this.NbRequest.add((long) 8);
                this.NbRequest.add((long) 5);
                break;
            /*Capital Quest*/
            case 9 :
                this.IdQuest = 9;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Capital";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 1750000);
                this.NbRequest.add((long) 1250000);
                this.NbRequest.add((long) 1000000);
                this.NbRequest.add((long) 25000000);
                this.NbRequest.add((long) 35000);
                this.NbRequest.add((long) 1);
                break;
            /*Metropolis Quest*/
            case 10 :
                this.IdQuest = 10;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Metropolis";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 7800000);
                this.NbRequest.add((long) 7750000);
                this.NbRequest.add((long) 7500000);
                this.NbRequest.add((long) 35000000);
                this.NbRequest.add((long) 300000);
                break;
            /*Country Quest*/
            case 11 :
                this.IdQuest = 11;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Country";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.itemRequest.add(new Item(6, "Municipality"));
                this.itemRequest.add(new Item(9, "Capital"));
                this.itemRequest.add(new Item(10, "Metropolis"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 20000000);
                this.NbRequest.add((long) 17500000);
                this.NbRequest.add((long) 15000000);
                this.NbRequest.add((long) 75000000);
                this.NbRequest.add((long) 37500000);
                this.NbRequest.add((long) 750000);
                this.NbRequest.add((long) 1);
                this.NbRequest.add((long) 2);
                break;
            /*Continent Quest*/
            case 12 :
                this.IdQuest = 12;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Continent";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.itemRequest.add(new Item(6, "Municipality"));
                this.itemRequest.add(new Item(9, "Capital"));
                this.itemRequest.add(new Item(10, "Metropolis"));
                this.itemRequest.add(new Item(11, "Country"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 175000000);
                this.NbRequest.add((long) 150000000);
                this.NbRequest.add((long) 125000000);
                this.NbRequest.add((long) 625000000);
                this.NbRequest.add((long) 312500000);
                this.NbRequest.add((long) 6250000);
                this.NbRequest.add((long) 4300000);
                this.NbRequest.add((long) 5);
                this.NbRequest.add((long) 10);
                this.NbRequest.add((long) 5);
                break;
            /*Planet Quest*/
            case 13 :
                this.IdQuest = 13;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Planet";
                this.itemRequest = new ArrayList<>();
                this.itemRequest.add(new Item(0, "Lamp"));
                this.itemRequest.add(new Item(1, "Floor Lamp"));
                this.itemRequest.add(new Item(2, "House"));
                this.itemRequest.add(new Item(3, "Street"));
                this.itemRequest.add(new Item(4, "District"));
                this.itemRequest.add(new Item(5, "City"));
                this.itemRequest.add(new Item(6, "Municipality"));
                this.itemRequest.add(new Item(9, "Capital"));
                this.itemRequest.add(new Item(10, "Metropolis"));
                this.itemRequest.add(new Item(11, "Country"));
                this.itemRequest.add(new Item(12, "Continent"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add((long) 1500000000);
                this.NbRequest.add((long) 1250000000);
                this.NbRequest.add((long) 1000000000);
                this.NbRequest.add(Long.valueOf("5000000000"));
                this.NbRequest.add((long) 350000000);
                this.NbRequest.add((long) 50000000);
                this.NbRequest.add((long) 35000000);
                this.NbRequest.add((long) 45);
                this.NbRequest.add((long) 100);
                this.NbRequest.add((long) 45);
                this.NbRequest.add((long) 8);
                break;
            default:
                this.IdQuest = 00;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Nothing";
                this.itemRequest = new ArrayList<>();
                this.NbRequest = new ArrayList<>();
                break;
        }
    }

    public ArrayList<Long> ObjectRequestComparator(ArrayList<Item> itemLists){
        ArrayList<Long> ObjectComparator = new ArrayList<>();
        long X = 0;
        for (int i = 0; i < this.getItemRequest().size(); i++) {
            X = 0;
            for (int j = 0; j < itemLists.size(); j++) {
                if (this.getItemRequest().get(i).getName().equals(itemLists.get(j).getName())) {
                    X = X + itemLists.get(j).getNbObject();
                }
            }
            ObjectComparator.add(X);
        }
        return ObjectComparator;
    }

    public Boolean NbRequestComparator(ArrayList<Item> itemLists) {
        ArrayList<Long> ObjectComparator = ObjectRequestComparator(itemLists);
        for(int i = 0; i < this.getNbRequest().size(); i++) {
            if(this.getNbRequest().get(i) > ObjectComparator.get(i)){
                return false;
            }
        }
        return true;
    }

    public boolean checkQuest(ArrayList<Item> itemLists, Database db){
        if(this.getIdQuest() != 12) {
            if (NbRequestComparator(itemLists)) {
                db.clearAllItem();
                Item TmpObject = new Item(this.getIdQuest(), this.getNameReward());
                itemLists.add(TmpObject); //Ajoute l'objet qui doit être craft dans la liste
                for (int k = 0; k < itemLists.size(); k++) { //Check toute la liste du joueur et les ajoutes à la db
                    db.insertItem(itemLists.get(k).getNbObject(), itemLists.get(k).getName(), itemLists.get(k).getCoinWin(), itemLists.get(k).getPriceObject(), itemLists.get(k).getEnergyCost(), itemLists.get(k).getSkin());
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int getIdQuest() {
        return IdQuest;
    }

    public void setIdQuest(int idQuest) {
        IdQuest = idQuest;
    }

    public String getNameReward() {
        return NameReward;
    }

    public void setNameReward(String nameReward) {
        NameReward = nameReward;
    }

    public int getSkinReward() {
        return SkinReward;
    }

    public void setSkinReward(int skinReward) {
        SkinReward = skinReward;
    }

    public ArrayList<Item> getItemRequest() {
        return itemRequest;
    }

    public void setItemRequest(ArrayList<Item> itemRequest) {
        this.itemRequest = itemRequest;
    }

    public ArrayList<Long> getNbRequest() {
        return NbRequest;
    }

    public void setNbRequest(ArrayList<Long> nbRequest) {
        NbRequest = nbRequest;
    }
}
