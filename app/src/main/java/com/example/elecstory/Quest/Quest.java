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

    public Quest(int idQuest, String nameReward, int skinReward, ArrayList<EarthObject> earthObjectRequest, ArrayList<Integer> nbRequest) {
        IdQuest = idQuest;
        NameReward = nameReward;
        SkinReward = skinReward;
        this.earthObjectRequest = earthObjectRequest;
        NbRequest = nbRequest;
    }

    public Quest (int N){
        switch (N){
            /*Mansion Quest*/
            case 1 :
                this.IdQuest = 1;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "House";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Lamp", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(3);
                break;
            /*Street Quest*/
            case 2 :
                this.IdQuest = 2;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Street";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"House", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(8);
                break;
            /*District Quest*/
            case 3 :
                this.IdQuest = 3;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "District";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Street", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(4);
                break;
            /*City Quest*/
            case 4 :
                this.IdQuest = 4;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "City";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"District", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(10);
                break;
            /*Municipality Quest*/
            case 5 :
                this.IdQuest = 5;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Municipality";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"City", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(5);
                break;
            /*Region Quest*/
            case 6 :
                this.IdQuest = 6;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Region";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Municipality", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(6);
                break;
            /*Country Quest*/
            case 7 :
                this.IdQuest = 7;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Country";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Region", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(25);
                break;
            /*Continent Quest*/
            case 8 :
                this.IdQuest = 8;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Continent";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Country", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(5);
                break;
            /*Planet Quest*/
            case 9 :
                this.IdQuest = 9;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Planet";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject(1,"Continent", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(8);
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
            if(this.getNbRequest().get(i) <= ObjectComparator.get(i)){
                return true;
            }
        }
        return false;
    }

    public boolean checkQuest(ArrayList<EarthObject> earthObjectLists, Database db){
        if(this.getIdQuest() != 9) {
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

                db.clearAllCity();
                EarthObject TmpObject = new EarthObject(this.getIdQuest(), this.getNameReward());
                earthObjectLists.add(TmpObject); //Ajoute l'objet qui doit être craft
                for (int k = 0; k < earthObjectLists.size(); k++) { //Check toute la liste du joueur
                    db.insertCity(earthObjectLists.get(k).getNbObject(),earthObjectLists.get(k).getName(),earthObjectLists.get(k).getCoinWin(),earthObjectLists.get(k).getPriceObject(),earthObjectLists.get(k).getEnergyCost(),earthObjectLists.get(k).getSkin());
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
