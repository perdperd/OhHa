/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

import java.util.ArrayList;
import tiedostonkasittely.*;

/**
 * Luokka hoitaa tallennetun pelin läpikäyntiin liittyvän logiikan
 * 
 * @author Juuso Nyyssönen
 */
public class Lapikayntilauta {
    
    /**
     * Pelilaudan pituus
     */
    
    private int pituus;
    
    /**
     * Pelilaudan leveys
     */
    
    private int leveys;
    
    /**
     * Arraylist, joka sisältää pelin siirrot taulukkomuodossa
     */
    
    private ArrayList<int[]> siirrot;
    
    /**
     * Taulukko, joka sisältää läpikäytävän pelin laudan tilanteen eri siirroilla
     */
    
    private String[][][] lapikaytavanPelinLaudanTilanteet;
    
    /**
     * Boolean, joka kertoo onnistuiko pelin lataus käsittelijästä
     */
    
    private boolean pelinLatausOnnistui;
    
    
    /**
     * Metodi luo uuden läpikäyntilaudan pelitiedostonkäsittelijän lataaman pelin pohjalta
     * 
     * @param kasittelija Pelitiedostonkäsittelijä, joka sisältää ladatun pelin
     */
    
    public Lapikayntilauta(Pelitiedostonkasittelija kasittelija) {
        pituus = kasittelija.getLadatunPelinLaudanPituus();
        leveys = kasittelija.getLadatunPelinLaudanLeveys();
        siirrot = kasittelija.getLadatunPelinSiirrot();
        lapikaytavanPelinLaudanTilanteet = new String[siirrot.size()+1][pituus][leveys];
        Pelilauta pelilauta = new Pelilauta(pituus,leveys);
        if (luoLapikaytavanPelinLaudanTilanteet(pelilauta)) pelinLatausOnnistui = true;
        else pelinLatausOnnistui = false;
    }
    
    public int getPituus() {
        return pituus;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public ArrayList<int[]> getSiirrot() {
        return siirrot;
    }
    
    public int getSiirtojenMaara() {
        return siirrot.size();
    }
    
    public boolean getPelinLatausOnnistui() {
        return pelinLatausOnnistui;
    }
    
    /**
     * Metodi palauttaa sen siirron numeron, jolloin syötteenä annettuja koordinaatteja 
     * vastaava siirto ensimmäisen kerran pelattiin
     * 
     * @param siirronKoordinaatit Sen siirron koordinaatit, jonka numeroa ollaan etsimässä
     * @return Siirron ensimmäisen esiintymän kohdan listassa siirrot, jos siirto on listassa, ja muuten -1
     */
    
    public int getSiirronNumero(int[] siirronKoordinaatit) {
        int laskuri = 1;
        for (int[] siirto : siirrot) {
            if (siirto[0] == siirronKoordinaatit[0] && siirto[1] == siirronKoordinaatit[1]) return laskuri;
            laskuri++;
        }
        return -1;
    }
    
    /**
     * Metodi luo jokaista siirtoa vastaavan läpikäytävän pelin laudan tilanteen taulukkoon
     * lapikaytavanPelinLaudanTilanteet
     * 
     * @param pelilauta Pelilauta, jota käytetään apuna siirtojen pelaamisessa
     * @return true, jos jokainen siirto siirrot-listassa oli laillinen, muuten false
     */
    
    public boolean luoLapikaytavanPelinLaudanTilanteet(Pelilauta pelilauta) {
        kopioiLaudanTilanne(0,pelilauta);
        int siirronNumero = 0;
        for (int[] siirto : siirrot) {
            siirronNumero++;
            int rivi = siirto[0];
            int sarake = siirto[1];
            int vari = siirto[2];
            if (rivi == -1) pelilauta.passaa();
            else pelilauta.laitaSiirto(rivi,sarake,vari);
            kopioiLaudanTilanne(siirronNumero, pelilauta);
        }
        if (pelilauta.getSiirtojenMaara() == siirrot.size()) return true;
        return false;
    }
    
    /**
     * Metodi kopioi pelilaudan tilanteen syötteenä annetulla siirrolla taulukkoon
     * lapikaytavanPelinLaudanTilanteet
     * 
     * @param siirronNumero Sen siirron numero, jota vastaavaa pelilaudan tilannetta ollaan kopioimassa
     * @param pelilauta Pelilauta, jonka tilannetta ollaan kopioimassa
     */
    
    public void kopioiLaudanTilanne(int siirronNumero, Pelilauta pelilauta) {
        String[][] laudanTilanne = new String[pituus][leveys];
        int[][] lauta = pelilauta.getLauta();
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                if (lauta[i][j] > 0) laudanTilanne[i][j] = "X";
                else if (lauta[i][j] < 0) laudanTilanne[i][j] = "O";
                else laudanTilanne[i][j] = ".";
            }
        }
        lapikaytavanPelinLaudanTilanteet[siirronNumero] = laudanTilanne;
    }
    
    /**
     * Metodi palauttaa laudan tilanteen annetulla siirron numerolla
     * 
     * @param siirronNumero Sen siirron numero, jota vastaavaa laudan tilannetta ollaan palauttamassa
     * @return Taulukko, joka vastaa pelilaudan tilannetta syötteenä annetulla siirrolla
     */
    
    public String[][] getLaudanTilanne(int siirronNumero) {
        return lapikaytavanPelinLaudanTilanteet[siirronNumero];
    }
    
}
