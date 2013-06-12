/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

import java.util.ArrayList;
import tiedostonkasittely.*;

/**
 *
 * @author Prod
 */
public class Lapikayntilauta {
    
    private int pituus;
    
    private int leveys;
    
    private ArrayList<int[]> siirrot;
    
    private Pelilauta lapikaytavaPeli;
    
    private String[][][] lapikaytavanPelinLaudanTilanteet;
    
    public Lapikayntilauta(Pelitiedostonkasittelija kasittelija) {
        pituus = kasittelija.getLadatunPelinLaudanPituus();
        leveys = kasittelija.getLadatunPelinLaudanLeveys();
        siirrot = kasittelija.getLadatunPelinSiirrot();
        lapikaytavaPeli = new Pelilauta(pituus,leveys);
        lapikaytavanPelinLaudanTilanteet = new String[siirrot.size()][pituus][leveys];
    }
    
    public int getPituus() {
        return pituus;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public int getSiirtojenMaara() {
        return siirrot.size();
    }
    
    public int getSiirronNumero(int[] siirronKoordinaatit) {
        int laskuri = 1;
        for (int[] siirto : siirrot) {
            if (siirto[0] == siirronKoordinaatit[0] && siirto[1] == siirronKoordinaatit[1]) return laskuri;
            laskuri++;
        }
        return -1;
    }
    
    public boolean luoLapikaytavanPelinLaudanTilanteet() {
        int laskuri = 0;
        for (int[] siirto : siirrot) {
            kopioiLaudanTilanne(laskuri);
            int rivi = siirto[0];
            int sarake = siirto[1];
            int vari = siirto[2];
            if (rivi == -1) lapikaytavaPeli.passaa();
            else lapikaytavaPeli.laitaSiirto(rivi,sarake,vari);
            laskuri++;
        }
        if (lapikaytavaPeli.getSiirtojenMaara() == siirrot.size()) return true;
        return false;
    }
    
    public void kopioiLaudanTilanne(int siirronNumero) {
        String[][] laudanTilanne = new String[pituus][leveys];
        int[][] lauta = lapikaytavaPeli.getLauta();
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                if (lauta[i][j] > 0) laudanTilanne[i][j] = "X";
                else if (lauta[i][j] < 0) laudanTilanne[i][j] = "O";
                else laudanTilanne[i][j] = ".";
            }
        }
        lapikaytavanPelinLaudanTilanteet[siirronNumero] = laudanTilanne;
    }
    
    public String[][] getLaudanTilanne(int siirronNumero) {
        return lapikaytavanPelinLaudanTilanteet[siirronNumero];
    }
    
}
