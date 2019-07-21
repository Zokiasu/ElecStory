package com.example.elecstory.Quest;


import com.example.elecstory.Database.Database;
import com.example.elecstory.Object.EarthObject;
import com.example.elecstory.R;

import java.util.ArrayList;

public class Quest {

    public int IdQuest;
    public String NameReward;
    public int SkinReward;
    public ArrayList<EarthObject> earthObjectRequest;
    public ArrayList<Integer> NbRequest;

    protected static final String TAG = "Elecstory.Quest";

    public Quest(int idQuest, String nameReward, int skinReward, ArrayList<EarthObject> earthObjectRequest, ArrayList<Integer> nbRequest) {
        IdQuest = idQuest;
        NameReward = nameReward;
        SkinReward = skinReward;
        this.earthObjectRequest = earthObjectRequest;
        NbRequest = nbRequest;
    }

    public Quest (int N){
        switch (N){
            /*Floor Lamp Quest*/
            case 1 :
                this.IdQuest = 1;
                this.SkinReward = R.drawable.floor_lamp;
                this.NameReward = "Floor Lamp";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(0,"Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(3);
                break;
            /*House Quest*/
            case 2 :
                this.IdQuest = 2;
                this.SkinReward = R.drawable.house;
                this.NameReward = "House";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(0, "Lamp"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(5);
                this.NbRequest.add(2);
                break;
            /*Street Quest*/
            case 3 :
                this.IdQuest = 3;
                this.SkinReward = R.drawable.street;
                this.NameReward = "Street";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(2, "House"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(8);
                this.NbRequest.add(20);
                break;
            /*District Quest*/
            case 4 :
                this.IdQuest = 4;
                this.SkinReward = R.drawable.district;
                this.NameReward = "District";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.earthObjectRequest.add(new EarthObject(2, "House"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(10);
                this.NbRequest.add(20);
                this.NbRequest.add(8);
                break;
            /*City Quest*/
            case 5 :
                this.IdQuest = 5;
                this.SkinReward = R.drawable.city;
                this.NameReward = "City";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(4, "District"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(10);
                this.NbRequest.add(20);
                this.NbRequest.add(50);
                break;
            /*Municipality Quest*/
            case 6 :
                this.IdQuest = 6;
                this.SkinReward = R.drawable.municipality;
                this.NameReward = "Municipality";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(5, "City"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(5);
                this.NbRequest.add(35);
                this.NbRequest.add(100);
                break;
            /*Region Quest*/
            case 7 :
                this.IdQuest = 7;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Region";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(6, "Municipality"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(10);
                this.NbRequest.add(50);
                this.NbRequest.add(150);
                break;
            /*Capital Quest*/
            case 8 :
                this.IdQuest = 8;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Capital";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(4, "District"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(20);
                this.NbRequest.add(50);
                this.NbRequest.add(150);
                break;
            /*Country Quest*/
            case 9 :
                this.IdQuest = 9;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Country";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(7, "Region"));
                this.earthObjectRequest.add(new EarthObject(8, "Capital"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(25);
                this.NbRequest.add(1);
                this.NbRequest.add(200);
                this.NbRequest.add(500);
                break;
            /*Metropolis Quest*/
            case 10 :
                this.IdQuest = 10;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Metropolis";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(4, "District"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(35);
                this.NbRequest.add(50);
                this.NbRequest.add(300);
                break;
            /*Continent Quest*/
            case 11 :
                this.IdQuest = 11;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Continent";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(9, "Country"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.earthObjectRequest.add(new EarthObject(10, "Metropolis"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(5);
                this.NbRequest.add(500);
                this.NbRequest.add(1000);
                this.NbRequest.add(5);
                break;
            /*Planet Quest*/
            case 12 :
                this.IdQuest = 12;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Planet";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(11, "Continent"));
                this.earthObjectRequest.add(new EarthObject(10, "Metropolis"));
                this.earthObjectRequest.add(new EarthObject(3, "Street"));
                this.earthObjectRequest.add(new EarthObject(1, "Floor Lamp"));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(8);
                this.NbRequest.add(10);
                this.NbRequest.add(1000);
                this.NbRequest.add(1500);
                break;
            default:
                this.IdQuest = 00;
                this.SkinReward = R.drawable.lamp;
                this.NameReward = "Nothing";
                this.earthObjectRequest = new ArrayList<>();
                this.NbRequest = new ArrayList<>();
                break;
        }
    }

    public ArrayList<Integer> ObjectRequestComparator(ArrayList<EarthObject> earthObjectLists){
        ArrayList<Integer> ObjectComparator = new ArrayList<>();
        int X = 0;
        for (int i = 0; i < this.getEarthObjectRequest().size(); i++) {
            X = 0;
            for (int j = 0; j < earthObjectLists.size(); j++) {
                if (this.getEarthObjectRequest().get(i).getName().equals(earthObjectLists.get(j).getName())) {
                    X = X + earthObjectLists.get(j).getNbObject();
                }
            }
            ObjectComparator.add(X);
        }
        return ObjectComparator;
    }

    public Boolean NbRequestComparator(ArrayList<EarthObject> earthObjectLists) {
        ArrayList<Integer> ObjectComparator = ObjectRequestComparator(earthObjectLists);
        for(int i = 0; i < this.getNbRequest().size(); i++) {
            if(this.getNbRequest().get(i) > ObjectComparator.get(i)){
                return false;
            }
        }
        return true;
    }

    public boolean checkQuest(ArrayList<EarthObject> earthObjectLists, Database db){
        if(this.getIdQuest() != 12) {
            if (NbRequestComparator(earthObjectLists)) {
                for (int i = 0; i < this.getEarthObjectRequest().size(); i++) { //Le nombre d'objet différent à supp
                    for (int k = 0; k < earthObjectLists.size(); k++) { //Check toute la liste du joueur
                        if (this.getEarthObjectRequest().get(i).getName().equals(earthObjectLists.get(k).getName())) { //Si l'objet est trouvé le supprime et interrompt la boucle
                            if(this.getNbRequest().get(i) < earthObjectLists.get(k).getNbObject()){
                                earthObjectLists.get(k).setNbObject(earthObjectLists.get(k).getNbObject()-this.getNbRequest().get(i));
                            } else {
                                earthObjectLists.remove(k);
                            }
                            k = earthObjectLists.size();
                        }
                    }
                }

                db.clearAllEarthObject();
                EarthObject TmpObject = new EarthObject(this.getIdQuest(), this.getNameReward());
                earthObjectLists.add(TmpObject); //Ajoute l'objet qui doit être craft
                for (int k = 0; k < earthObjectLists.size(); k++) { //Check toute la liste du joueur
                    db.insertEarthObject(earthObjectLists.get(k).getNbObject(),earthObjectLists.get(k).getName(),earthObjectLists.get(k).getCoinWin(),earthObjectLists.get(k).getPriceObject(),earthObjectLists.get(k).getEnergyCost(),earthObjectLists.get(k).getSkin());
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

    public int getSkinReward() {
        return SkinReward;
    }

    public void setSkinReward(int skinReward) {
        SkinReward = skinReward;
    }

    public String getNameReward() {
        return NameReward;
    }

    public void setNameReward(String nameReward) {
        NameReward = nameReward;
    }

    public ArrayList<EarthObject> getEarthObjectRequest() {
        return earthObjectRequest;
    }

    public void setEarthObjectRequest(ArrayList<EarthObject> earthObjectRequest) {
        this.earthObjectRequest = earthObjectRequest;
    }

    public ArrayList<Integer> getNbRequest() {
        return NbRequest;
    }

    public void setNbRequest(ArrayList<Integer> nbRequest) {
        NbRequest = nbRequest;
    }
}
