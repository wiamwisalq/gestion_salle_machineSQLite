package com.example.sqlitesallesmachines.bean;

public class ResultatStatique {
    int nbr;
    int salle;
    public ResultatStatique(){}
    public ResultatStatique(int s,int n){
        salle=s;
        nbr=n;
    }
    public void setNbr(int v){
        nbr=v;
    }
    public void setSalle(int s){
        salle=s;
    }
    public int getSalle(){
        return salle;
    }
    public int getNbr(){
        return nbr;
    }

    @Override
    public String toString() {
        return "ResultatStatique{" +
                "nbr=" + nbr +
                ", salle=" + salle +
                '}';
    }
}
