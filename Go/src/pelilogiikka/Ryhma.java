/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

import java.util.HashSet;

/**
 * Luokka esittää laudalla sijaitsevista kivistä muodostuvaa ryhmää
 * 
 * @author Juuso Nyyssönen
 */

public class Ryhma {
    
    /**
     * Ryhmän kivien väri
     */
    
    private int vari;
    
    /**
     * Ryhmän vapaudet laudalla sisältävä HashSet
     */
    
    private HashSet<String> vapaudet;
    
    /**
     * Ryhmän kivet laudalla sisältävä HashSet
     */
    
    private HashSet<String> kivet;
    
    /**
     * Luo annetun värisen ryhmän
     * 
     * @param vari Luotavan ryhmän väri
     */
    
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
    
    /** 
     * Metodi lisää syötelukuja vastaavan kiven ryhmään.
     * 
     * @param x  Lisättävän kiven rivi laudalla
     * @param y Lisättävän kiven sarake laudalla
     */
    
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
    
    /**
     * Metodi lisää syötelukuja vastaavan vapauden ryhmään.
     * 
     * @param x Lisättävän vapauden rivi laudalla
     * @param y Lisättävän vapauden sarake laudalla
     */
    
    public void lisaaVapaus(int x, int y) {
        String vapaus = x+"."+y;
        vapaudet.add(vapaus);
    }
    
    public void poistaVapaus(int x, int y) {
        vapaudet.remove(x+"."+y);
    }
    
    /**
     * Metodi yhdistää ryhmän toiseen ryhmään lisäämällä siihen toisen ryhmän
     * kivet ja vapaudet ja sen jälkeen poistamalla toisen ryhmän kivet vapauksista.
     * 
     * @param ryhma Ryhma, johon ollaan yhdistämässä.
     */
    
    public void yhdistaToiseenRyhmaan(Ryhma ryhma) {
        HashSet<String> toisenVapaudet = ryhma.getVapaudet();
        HashSet<String> toisenKivet = ryhma.getKivet();
        vapaudet.addAll(toisenVapaudet);
        kivet.addAll(toisenKivet);
        vapaudet.removeAll(kivet);
    }
    
}
