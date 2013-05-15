/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

/**
 *
 * @author Prod
 */

import java.util.HashSet;

public class Ryhma {
    private int vari;
    private HashSet<String> vapaudet;
    private HashSet<String> kivet;
    
    public Ryhma(int vari) {
        this.vari = vari;
        vapaudet = new HashSet<String>();
        kivet = new HashSet<String>();
    }
    
    public int getVari() {
        return vari;
    }
    
    public HashSet<String> getKivet() {
        return kivet;
    }
    
    public int getKivienMaara() {
        return kivet.size();
    }
    
    public void lisaaKivi(int x, int y) {
        String kivi = x+"."+y;
        kivet.add(kivi);
    }
    
    
    public HashSet<String> getVapaudet() {
        return vapaudet;
    }
    
    public int getVapauksienMaara() {
        return vapaudet.size();
    }
    
    public void lisaaVapaus(int x, int y) {
        String vapaus = x+"."+y;
        vapaudet.add(vapaus);
    }
    
    public void poistaVapaus(int x, int y) {
        vapaudet.remove(x+"."+y);
    }
    
    public void yhdistaToiseenRyhmaan(Ryhma ryhma) {
        HashSet<String> toisenVapaudet = ryhma.getVapaudet();
        HashSet<String> toisenKivet = ryhma.getKivet();
        vapaudet.addAll(toisenVapaudet);
        kivet.addAll(toisenKivet);
        vapaudet.removeAll(kivet);
    }
    
}
