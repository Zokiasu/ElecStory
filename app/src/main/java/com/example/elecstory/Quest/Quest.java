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
                this.NameReward = "Maison";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject("Lampe", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(3);
                break;
            /*Street Quest*/
            case 2 :
                this.IdQuest = 2;
                this.SkinReward = R.drawable.ampoule;
                this.NameReward = "Rue";
                this.earthObjectRequest = new ArrayList<>();
                this.earthObjectRequest.add(new EarthObject("Maison", 1, 1, 5, R.drawable.ampoule));
                this.NbRequest = new ArrayList<>();
                this.NbRequest.add(8);
                break;
            /*District Quest*/
            case 3 :
                break;
            /*EarthObject Quest*/
            case 4 :
                break;
            /*Municipality Quest*/
            case 5 :
                break;
            /*Region Quest*/
            case 6 :
                break;
            /*Country Quest*/
            case 7 :
                break;
            /*Continent Quest*/
            case 8 :
                break;
            /*Planet Quest*/
            case 9 :
                break;
            /*??? Quest*/
            case 10 :
                break;
            /*??? Quest*/
            case 11 :
                break;
            /*Galaxy Quest*/
            case 12 :
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
                    X++;
                }
            }
            ObjectComparator.add(X);
        }
        return ObjectComparator;
    }

    public Boolean NbRequestComparator(ArrayList<EarthObject> earthObjectLists) {
        ArrayList<Integer> ObjectComparator = ObjectRequestComparator(earthObjectLists);
        for(int i = 0; i < this.getNbRequest().size(); i++) {
            if(this.getNbRequest().get(i).equals(ObjectComparator.get(i))){
                return true;
            }
        }
        return false;
    }

    public boolean checkQuest(ArrayList<EarthObject> earthObjectLists, Database db){
        if(this.getIdQuest() != 9) {
            if (NbRequestComparator(earthObjectLists)) {
                EarthObject TmpObject = new EarthObject(this.getIdQuest(), this.getNameReward());
                earthObjectLists.add(TmpObject); //Ajoute l'objet qui doit être craft
                db.insertCity(TmpObject.getName(), TmpObject.getCoinWin(), TmpObject.getPriceObject(), TmpObject.getEnergyCost(), TmpObject.getSkin());
                for (int i = 0; i < this.getNbRequest().size(); i++) { //Le nombre d'objet différent à supp
                    for (int j = 0; j < this.getNbRequest().get(i); j++) { //Le nombre de fois où cette objet apparaît
                        for (int k = 0; k < earthObjectLists.size(); k++) { //Check toute la liste du joueur
                            if (this.getEarthObjectRequest().get(i).getName().equals(earthObjectLists.get(k).getName())) { //Si l'objet est trouvé le supprime et interrompt la boucle
                                db.deleteCity(this.getEarthObjectRequest().get(i).getName());
                                earthObjectLists.remove(k);
                                k = earthObjectLists.size();
                            }
                        }
                    }
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
