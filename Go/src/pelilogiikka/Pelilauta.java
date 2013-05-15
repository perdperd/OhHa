/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

/**
 *
 * @author Prod
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Pelilauta {
    private int pituus;
    private int leveys;
    private int[][] lauta;
    private int tasoituskivet;
    private int mustanVangit;
    private int valkeanVangit;
    private ArrayList<Ryhma> ryhmat;
    
    // Aputaulukko kiven naapurien läpikäyntiä varten
    
    private final int[][] naapurit = {{1,0}, {0, 1}, {-1, 0}, {0,-1}};
    
    public Pelilauta(int pituus, int leveys, int tasoituskivet) {
        this.pituus = pituus;
        this.leveys = leveys;
        lauta = new int[pituus][leveys];
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                lauta[i][j] = 0;
            }
        }
        this.tasoituskivet = tasoituskivet;
        mustanVangit = valkeanVangit = 0;
        ryhmat = new ArrayList<Ryhma>();
    }
    
    public int getPituus() {
        return pituus;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public int getRyhmanNumero(int x, int y) {
        return lauta[x][y];
    }
    
    public Ryhma getRyhma(int ryhmanNumero) {
        return ryhmat.get(ryhmanNumero-1);
    }
    
    public void lisaaKivenVapaudetRyhmaan(int x, int y, int ryhmanNumero) {
         Ryhma ryhma = getRyhma(ryhmanNumero);
         for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                if (lauta[naapurix][naapuriy] == 0) {
                    ryhma.lisaaVapaus(naapurix, naapuriy);
                }
            }
         }
    }
    
    public void luoUusiRyhma(int x, int y, int vari) {
        Ryhma ryhma = new Ryhma(vari);
        ryhmat.add(ryhma);
        int ryhmanNumero = ryhmat.size();
        lauta[x][y] = ryhmanNumero;
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
    }
    
    public void lisaaKiviRyhmaan(int x, int y, int ryhmanNumero) {
        lauta[x][y] = ryhmanNumero;
        Ryhma ryhma = getRyhma(ryhmanNumero);
        ryhma.poistaVapaus(x, y);
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
    }
    
    public void yhdistaKaksiRyhmaa(int ryhmanNumero1, int ryhmanNumero2) {
        Ryhma ryhma1 = getRyhma(ryhmanNumero1);
        Ryhma ryhma2 = getRyhma(ryhmanNumero2);
        ryhma1.yhdistaToiseenRyhmaan(ryhma2);
        HashSet<String> kivet = ryhma2.getKivet();
        for (String s : kivet) {
            int x = (int) (s.charAt(0)) - 48;
            int y = (int) (s.charAt(2)) - 48;
            lauta[x][y] = ryhmanNumero1;
        }
    }
    
    public void poistaRyhmaLaudalta(int ryhmanNumero) {
        Ryhma ryhma = getRyhma(ryhmanNumero);
        int vari = ryhma.getVari();
        if (vari == 1) {
            valkeanVangit += ryhma.getKivienMaara();
        } else mustanVangit += ryhma.getKivienMaara();
        HashSet<String> kivet = ryhma.getKivet();
        for (String s : kivet) {
            int x = (int) (s.charAt(0)) - 48;
            int y = (int) (s.charAt(2)) - 48;
            lauta[x][y] = 0;
            paivitaNaapuroivienVihollistenKivienVapaudet(x,y,ryhmanNumero);
        }
    }
    
    public void paivitaNaapuroivienVihollistenKivienVapaudet(int x, int y, int ryhmanNumero) {
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                if (lauta[naapurix][naapuriy] != 0 && lauta[naapurix][naapuriy] != ryhmanNumero) {
                    Ryhma ryhma = getRyhma(lauta[naapurix][naapuriy]);
                    ryhma.lisaaVapaus(x, y);
                }
            }
         }
    }
    
 /*   public boolean siirtoSyoRyhmia(int x, int y, int vari) {
        for (int i = 0; i<naapurit.length; i++) {
           int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
               int ryhmanNumero = lauta[naapurix][naapuriy];
               if (ryhmanNumero != 0) {
                   Ryhma ryhma = getRyhma(ryhmanNumero);
                   if (ryhma.getVari() == -vari && ryhma.getVapauksienMaara() == 1) return true;
               }
            }
         }
        return false;
    }
    
 */   
}
        
        

    
    
    

