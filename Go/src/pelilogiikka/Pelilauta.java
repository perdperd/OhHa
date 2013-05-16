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
    private boolean koKaynnissa;
    private int koX;
    private int koY;
    
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
        koKaynnissa = false;
        koX = koY = -1;
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
    
    /*
     * metodi lisää laudan kohdassa [x][y] olevan kiven vapaudet ryhmanNumeroa
     * vastaavaan ryhmään
     */
    
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
    
    /*
     * metodi poistaa laudan kohtaa [x][y] ympäröiviltä ryhmiltä kohtaa [x][y]
     * vastaavan vapauden
     */
    
    public void poistaKiviYmparoivienRyhmienVapauksista(int x, int y) {
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero = lauta[naapurix][naapuriy];
                if (ryhmanNumero != 0) {
                    Ryhma ryhma = getRyhma(ryhmanNumero);
                    ryhma.poistaVapaus(x,y);
                }
            }
        }
    }
    
    /*
     * metodi luo uuden ryhmän, lisää sen ryhmat-listaan ja laittaa laudan
     * kohtaan [x][y] ryhmän indeksin listassa ryhmat. tämän jälkeen poistetaan
     * ympäröiviltä ryhmiltä kohta [x][y] vapauksista, lisätään kohdan [x][y]
     * vapaudet luotuun ryhmään ja lisätään kohtaa [x][y] vastaava kivi ryhmään.
     */
    
    public void luoUusiRyhma(int x, int y, int vari) {
        Ryhma ryhma = new Ryhma(vari);
        ryhmat.add(ryhma);
        int ryhmanNumero = ryhmat.size();
        lauta[x][y] = ryhmanNumero;
        poistaKiviYmparoivienRyhmienVapauksista(x, y);
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
    }
    
    /*
     * metodi korvaa kohdan [x][y] ryhmanNumerolla, poistaa kohtaa [x][y] vastaavan
     * vapauden ympäröiviltä ryhmiltä ja lisää sen vapaudet ryhmanNumeroa vastaavaan
     * ryhmään. tämän jälkeen vielä lisätään kohtaa [x][y] vastaava kivi
     * ryhmän kivien joukkoon.
     */
    
    public void lisaaKiviRyhmaan(int x, int y, int ryhmanNumero) {
        lauta[x][y] = ryhmanNumero;
        Ryhma ryhma = getRyhma(ryhmanNumero);
        poistaKiviYmparoivienRyhmienVapauksista(x, y);
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
    }
    
    /*
     * metodi yhdistää ryhmanNumero1:ä ja ryhmanNumero2:a vastaavat ryhmät toisiinsa.
     * tämän jälkeen kaikki laudan kohdat, joissa on luku ryhmanNumero2 korvataan
     * luvulla ryhmanNumero1
     */
    
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
    
    /*
     * haetaan ryhmanNumeroa vastaava ryhma ja lisätään sen kivien määrä valkoisen
     * vankeihin, jos ryhmä on valka. muuten lisätään ne mustan vankeihin.
     * tämän jälkeen korvataan jokainen laudan kohta [x][y] jossa on luku ryhmanNumero
     * nollalla ja lisataan kohdan [x][y] vihollisnaapureille vapaudeksi [x][y].
     */
    
    
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
            lisaaPoistettavanKivenVihollisnaapureilleVapaudeksiPoistettavaKivi(x,y,ryhmanNumero);
        }
    }
    
    public void lisaaPoistettavanKivenVihollisnaapureilleVapaudeksiPoistettavaKivi(int x, int y, int ryhmanNumero) {
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
    
    public boolean siirtoSyoRyhmia(int x, int y, int vari) {
        for (int i = 0; i<naapurit.length; i++) {
           int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
               int ryhmanNumero = lauta[naapurix][naapuriy];
               if (ryhmanNumero != 0) {
                   Ryhma ryhma = getRyhma(ryhmanNumero);
                   if (ryhma.getVari() != vari && ryhma.getVapauksienMaara() == 1) return true;
               }
            }
         }
        return false;
    }
    
    public boolean siirtoEiSyoRyhmiaMuttaOnLaillinen(int x, int y, int vari) {
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero = lauta[naapurix][naapuriy];
                if (ryhmanNumero == 0) return true;
                Ryhma ryhma = getRyhma(ryhmanNumero);
                if (ryhma.getVari() == vari && ryhma.getVapauksienMaara() > 1) return true;
            }
        }
        return false;
    }
    
    public boolean siirtoOnLaillinen(int x, int y, int vari) {
        if (koKaynnissa && x == koX && y == koY) return false;
        return (siirtoSyoRyhmia(x, y, vari) || siirtoEiSyoRyhmiaMuttaOnLaillinen(x,y,vari));
    }
    
    public boolean siirtoOnKo(int x, int y, int vari) {
        int syotavienYhdenKivenRyhmienMaara = 0;
        for (int i = 0; i<naapurit.length; i++) {
           int naapurix = x + naapurit[i][0];
           int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
               int ryhmanNumero = lauta[naapurix][naapuriy];
               if (ryhmanNumero == 0) return false;
               else {
                   Ryhma ryhma = getRyhma(ryhmanNumero);
                   if (ryhma.getVari() != -vari) return false;
                   if (ryhma.getVapauksienMaara() == 1) {
                       if (ryhma.getKivienMaara() != 1) return false;
                       else { 
                            syotavienYhdenKivenRyhmienMaara++;
                       }
                       
                   }
               }
            }
         }
        return syotavienYhdenKivenRyhmienMaara == 1;
    }
    
    public void syoVastustajanRyhmatYmparilta(int x, int y, int vari) {
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero = lauta[naapurix][naapuriy];
                if (ryhmanNumero != 0) {
                    Ryhma ryhma = getRyhma(ryhmanNumero);
                    if (ryhma.getVari() != vari && ryhma.getVapauksienMaara() == 1) poistaRyhmaLaudalta(ryhmanNumero);
                }
            }
        }
    }
    
    public boolean yhdistaKiviJohonkinYmparoivaanRyhmaan(int x, int y, int vari) {
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero = lauta[naapurix][naapuriy];
                if (ryhmanNumero != 0) {
                    Ryhma ryhma = getRyhma(ryhmanNumero);
                    if (ryhma.getVari() == vari) {
                        lisaaKiviRyhmaan(x,y,ryhmanNumero);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void yhdistaRyhmaYmparoiviinRyhmiin(int x, int y, int vari) {
        int ryhmanNumero1 = lauta[x][y];
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero2 = lauta[naapurix][naapuriy];
                if (ryhmanNumero2 != 0) {
                    Ryhma ryhma = ryhmat.get(ryhmanNumero2);
                    if (ryhma.getVari() == vari) {
                        yhdistaKaksiRyhmaa(ryhmanNumero1, ryhmanNumero2);
                    }
                }
            }
        }
    }
    
    public void laitaSiirto(int x, int y, int vari) {
        if (siirtoOnLaillinen(x,y,vari)) {
            if (siirtoSyoRyhmia(x,y,vari)) {
                if (siirtoOnKo(x,y,vari)) {
                    koKaynnissa = true;
                    koX = x;
                    koY = y;
                }
                syoVastustajanRyhmatYmparilta(x,y,vari);
            }
            if  (yhdistaKiviJohonkinYmparoivaanRyhmaan(x,y,vari)) {
            yhdistaRyhmaYmparoiviinRyhmiin(x,y,vari);
            }
            else luoUusiRyhma(x,y,vari);
        }
        if (koX != x || koY != y) koKaynnissa = false;
    }
    
   
}
        
        

    
    
    

