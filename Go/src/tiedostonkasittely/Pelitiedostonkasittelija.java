/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiedostonkasittely;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import pelilogiikka.*;
/**
 * Luokka hoitaa pelattujen pelien tallentamisen tiedostoiksi ja aiemmin tallennettujen pelien lataamisen
 *
 * @author Juuso Nyyssönen
 */
public class Pelitiedostonkasittelija {
    /**
     * Ladatun pelin siirrot taulukkomuodossa sisältävä ArrayList.
     */
    
    ArrayList<int[]> ladatunPelinSiirrot = new ArrayList<int[]>();
    
    /**
     * Luo uuden pelitiedostonkäsittelijän.
     */
    
    public Pelitiedostonkasittelija() {
        
    }
    
    public ArrayList<int[]> getLadatunPelinSiirrot() {
        return ladatunPelinSiirrot;
    }
    
    /**
     * Metodi yrittää tallentaa syötteenä annetun pelilaudan siirrot tiedostoon
     * 
     * @param pelilauta Pelilauta, jonka siirtoja ollaan tallentamassa
     * @param tiedostonimi Sen tiedoston nimi, johon ollaan tallentamassa siirtoja
     * @return true, jos tallentaminen onnistui, ja muuten false
     */
    
    public boolean tallennaPelattuPeli(Pelilauta pelilauta, String tiedostonimi) {
        ArrayList<int[]> siirrot = pelilauta.getSiirrot();
        try {
            PrintWriter kirjoittaja = new PrintWriter(new File(tiedostonimi));
            for (int[] siirto : siirrot) {
                kirjoittaja.println(siirto[0]+ " " + siirto[1] + " " + siirto[2]);
            }
            kirjoittaja.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Metodi yrittää ladata syötteenä annettua tiedoston nimeä vastaavan tiedoston
     * sisältämät siirrot
     * 
     * @param ladattavanPelinTiedostonimi
     * @return true, jos siirtojen lataaminen onnistui, ja muuten false
     */
    
    public boolean lataaTallennetunPelinSiirrot(String ladattavanPelinTiedostonimi) {
        try {
            Scanner lukija = new Scanner(new File(ladattavanPelinTiedostonimi));
            while (lukija.hasNextLine()) {
            String siirtoStringina = lukija.nextLine();
            String[] siirronOsat = siirtoStringina.split(" ");
            int siirronRivi = Integer.parseInt(siirronOsat[0]);
            int siirronSarake = Integer.parseInt(siirronOsat[1]);
            int siirronVari = Integer.parseInt(siirronOsat[2]);
            int[] siirto = {siirronRivi, siirronSarake, siirronVari};
            ladatunPelinSiirrot.add(siirto);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
