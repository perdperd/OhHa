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
            int pisteenKohta = s.indexOf('.');
            int x = (int) Integer.parseInt(s.substring(0,pisteenKohta));
            int y = (int) Integer.parseInt(s.substring(pisteenKohta+1));
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
            int pisteenKohta = s.indexOf('.');
            int x = (int) Integer.parseInt(s.substring(0,pisteenKohta));
            int y = (int) Integer.parseInt(s.substring(pisteenKohta+1));
            lauta[x][y] = 0;
            lisaaPoistettavanKivenVihollisnaapureilleVapaudeksiPoistettavaKivi(x,y,ryhmanNumero);
        }
    }
    
    /*
     * metodi käy kohdan [x][y] naapurit läpi ja lisää niille vapauden, jos ne
     * eivät ole samaa ryhmää kuin ryhmanNumero
     */
    
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
    
    /*
     * metodi tutkii söisiko kohtaan [x][y] laitettu kivi, jonka väri on vari,
     * kiviä. tämän metodi tekee tutkimalla onko kohdalla [x][y] naapureina
     * ryhmiä, joiden väri ei ole sama kuin vari ja joiden vapauksien määrä on 
     * 1. tällöin metodi palauttaa true. muuten metodi palauttaa false.
     */
    
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
    
    /*
     * metodi tutkii olisiko kohtaan [x][y] laitettu kivi, jonka vari on vari,
     * laillinen sillä oletuksella, että siirto ei syö kiviä. metodi tekee tämän
     * tutkimalla onko kohdalla [x][y] naapureina kohtia, joissa on luku 0 (eli
     * vapauksia), tai kohtia, joissa on samanvärinen ryhmä, jolla on yli yksi vapaus.
     * tässä tapauksessa palautetaan true. muuten palautetaan false.
     */
    
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
    
    /*
     * metodi tutkii olisiko kohtaan [x][y] laitettu kivi, jonka väri on vari,
     * laillinen siirto. ensiksi katsotaan, onko kohta [x][y] vapaa. jos ei ole,
     * palautetaan false. Tämän jälkeem katsotaan, onko käynnissä kota ja onko kohta
     * [x][y] kohta, josta ko viimeksi syötiin. tässä tapauksessa palautetaan false.
     * muuten palautetaan siirtoSyoRyhmia(x,y,vari) || siirtoEiSyoRyhmiaMuttaOnLaillinen(x,y,vari)
     */
    
    public boolean siirtoOnLaillinen(int x, int y, int vari) {
        if (lauta[x][y] != 0) return false;
        if (koKaynnissa && x == koX && y == koY) return false;
        return (siirtoSyoRyhmia(x, y, vari) || siirtoEiSyoRyhmiaMuttaOnLaillinen(x,y,vari));
    }
    
    /*
     * metodi tutkii, aloittaisiko kohtaan [x][y] laitettu kivi, jonka väri on vari,
     * kon. tämä tehdään käymällä kohdan [x][y] naapurit läpi. jos kohdalla on naapurina
     * vapaus tai samanvärinen ryhmä kuin vari, palautetaan false. jos kohdalla
     * on naapurina erivärinen ryhmä, tutkitaan ovatko sen vapaudet 1. jos näin
     * on ja ryhmä sisältää yli yhden kiven, kyseessä ei ole ko ja palautetaan false.
     * jos taas ryhmä sisältää vain yhden kiven, kasvatetaan laskuria
     * syotavienYhdenKivenRyhmienMaara. lopuksi palautetaan true jos
     * tämä laskuri on 1, muuten palautetaan false.
     */
    
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
    
    /*
     * metodi syö kohdan [x][y] ympäriltä kaikki ryhmät, joiden väri ei ole vari
     * ja joiden vapauksien määrä on 1. tämä tehdään kutsumalla metodia poistaRyhmaLaudalta
     * parametrina ymparoivan ryhman numero.
     */
    
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
    
    /*
     * metodi koittaa etsiä kohdan [x][y] naapureista ryhmän, jonka väri on sama
     * kuin vari. jos tällainen löytyy, kutsutaan metodia lisaaKiviRyhmaan
     * parametreina x,y ja ryhmanNumero ja palautetaan true. muussa tapauksessa
     * palautetaan false.
     */
    
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
    
    /*
     * metodi etsii kohdan [x][y] naapureista ryhmiä, joiden väri on sama kuin
     * kohdan [x][y] ryhmän. jos tällainen löytyy, kutsutaan metodia yhdistaKaksiRyhmaa
     * parametreina kohdan [x][y] luku ja ympäröivän ryhmän numero
     */
    
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
    
    /*
     * metodi tutkii aluksi, onko siirto, joka laitetaan kohtaan [x][y] ja
     * jonka väri on vari, laillinen. jos näin on, tutkitaan syökö siirto kiviä.
     * jos siirto syö kiviä, tarkistetaan vielä aloittaako siirto kon. jos näin
     * on, merkitään että ko on käynnissä ja laitetaan kon x-koordinaatiksi x
     * ja y-koordinaatiksi y. tämän jälkeen syödään vastustajan ryhmät kohdan [x][y]
     * ympäriltä.
     * 
     * tämän jälkeen koitetaan yhdistää kohdan [x][y] kivi, jonka vari on vari,
     * johonkin ympäröivään ryhmään. jos tämä onnistui, eli yhdistaKiviJohonkinYmparoivaanRyhmaan
     * palautti true, yhdistetään vielä kohdan [x][y] ryhmä kaikkiin ympäröiviin ryhmiin.
     * jos tämä ei onnistunut, luodaan uusi ryhmä, jonka väri on vari, kohtaan [x][y].
     * 
     * lopuksi tarkistetaan vielä oliko tämä siirto ollut kon syönti vertaamalla
     * x:ää koX:ään ja y:tä koY:n. jos jompikumpi näistä eroaa toisesta, merkataan
     * että ko ei ole enää käynnissä.
     */
    
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
        
        

    
    
    

