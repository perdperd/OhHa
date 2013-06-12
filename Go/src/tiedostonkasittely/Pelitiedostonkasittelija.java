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
     * Ladatun pelin laudan pituus
     */
    
    private int ladatunPelinLaudanPituus;
    
    /**
     * Ladatun pelin laudan leveys
     */
    
    private int ladatunPelinLaudanLeveys;
    
    /**
     * Ladatun pelin siirrot taulukkomuodossa sisältävä ArrayList.
     */
    
    private ArrayList<int[]> ladatunPelinSiirrot = new ArrayList<int[]>();
    
    /**
     * Luo uuden pelitiedostonkäsittelijän.
     */
    
    public Pelitiedostonkasittelija() {
        
    }
    
    public int getLadatunPelinLaudanPituus() {
        return ladatunPelinLaudanPituus;
    }
    
    public int getLadatunPelinLaudanLeveys() {
        return ladatunPelinLaudanLeveys;
    }
    
    public ArrayList<int[]> getLadatunPelinSiirrot() {
        return ladatunPelinSiirrot;
    }
    
    /**
     * Metodi yrittää tallentaa syötteenä annetun pelilaudan koon ja siirrot tiedostoon
     * 
     * @param pelilauta Pelilauta, jonka siirtoja ollaan tallentamassa
     * @param tiedostonimi Sen tiedoston nimi, johon ollaan tallentamassa siirtoja
     * @return true, jos tallentaminen onnistui, ja muuten false
     */
    
    public boolean tallennaPelattuPeli(Pelilauta pelilauta, String tiedostonimi) {
        ArrayList<int[]> siirrot = pelilauta.getSiirrot();
        try {
            PrintWriter kirjoittaja = new PrintWriter(new File(tiedostonimi));
            int pituus = pelilauta.getPituus();
            int leveys = pelilauta.getLeveys();
            kirjoittaja.println(pituus + " " + leveys);
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
     * sisältämän laudan pituuden, laudan leveyden ja pelin siirrot
     * 
     * @param ladattavanPelinTiedostonimi Sen tiedoston nimi, josta ollaan lataamassa peliä
     * @return true, jos pelin lataaminen onnistui, ja muuten false
     */
    
    public boolean lataaTallennettuPeli(String ladattavanPelinTiedostonimi) {
        try {
            Scanner lukija = new Scanner(new File(ladattavanPelinTiedostonimi));
            String pituusJaLeveysStringina = lukija.nextLine();
            String[] pituusJaLeveys = pituusJaLeveysStringina.split(" ");
            ladatunPelinLaudanPituus = Integer.parseInt(pituusJaLeveys[0]);
            ladatunPelinLaudanLeveys = Integer.parseInt(pituusJaLeveys[1]);
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
