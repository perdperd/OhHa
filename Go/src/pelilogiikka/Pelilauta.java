/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

/**
 * Luokka hoitaa itse pelaamisen liittyvän logiikan, johon kuuluvat ryhmien
 * luonti ja lisääminen laudalle, niiden yhdistely, kivien syöminen ja siirtojen 
 * laillisuuden tarkistus.
 * 
 * 
 * @author Juuso Nyyssönen
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Pelilauta {
    /**
     * Laudan pituus
     */
    
    private int pituus;
    
    /**
     * Laudan leveys
     */
    
    private int leveys;
    
    /**
     * Taulukko joka esittää lautaa, jolla pelataan
     * 
     * Sisältää laudan ryhmien numeroita
     */
    
    private int[][] lauta;
    
    /**
     * Mustan vankien määrä
     */
    
    private int mustanVangit;
    
    /**
     * Valkean vankien määrä 
     */
    
    private int valkeanVangit;
    
    /**
     * Seuraavaksi luotavalle uudelle ryhmälle annettava numero
     */
    
    private int uudenRyhmanNumero;
    
    /**
     * Laudan sisältämät ryhmät sisältävä HashMap, jonka avaimet vastaavat
     * ryhmien numeroita
     */
    
    private HashMap<Integer,Ryhma> ryhmat;
    
    /**
     * Kuolleeksi merkittyen ryhmien numerot sisältävä HashSet
     */
    
    private HashSet<Integer> kuolleeksiMerkitytRyhmat;
    /**
     * Pelilaudalle pelatut siirrot taulukkomuodossa sisältävä ArrayList
     */
    
    private ArrayList<int[]> siirrot;
    
    /**
     * Boolean, joka kertoo söikö viimeisin siirto kon
     */
    
    private boolean viimeisinSiirtoOliKonSyonti;
    
    /**
     * Viimeisimmän siirron, joka söi kon, sarake laudalla
     */
    
    private int viimeisimmanKonSyonninXKoordinaatti;
    
    /**
     * Viimeisimmän siirron, joka söi kon, rivi laudalla
     */
    
    private int viimeisimmanKonSyonninYKoordinaatti;
    
    /** 
    * Aputaulukko kiven naapurien läpikäyntiä varten
    */
    
    private final int[][] naapurit = {{1,0}, {0, 1}, {-1, 0}, {0,-1}};
    
    /**
     * Luo uuden pelilaudan, jonka pituus ja leveys ovat annetut
     * 
     * @param pituus Luotavan pelilaudan pituus
     * @param leveys Luotavan pelilaudan leveys
     */
    
    public Pelilauta(int pituus, int leveys) {
        this.pituus = pituus;
        this.leveys = leveys;
        lauta = new int[pituus][leveys];
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                lauta[i][j] = 0;
            }
        }
        mustanVangit = valkeanVangit = 0;
        uudenRyhmanNumero = 1;
        ryhmat = new HashMap<Integer,Ryhma>();
        kuolleeksiMerkitytRyhmat = new HashSet<Integer>();
        siirrot = new ArrayList<int[]>();
        viimeisinSiirtoOliKonSyonti = false;
        viimeisimmanKonSyonninXKoordinaatti = viimeisimmanKonSyonninYKoordinaatti = -1;
    }
    
    public int[][] getLauta() {
        return lauta;
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
    
    /**
     * Metodi hakee ryhmat-hashMapista sen ryhmän, jonka avain on syöteluku
     * 
     * @param ryhmanNumero Haettavan ryhmän avain hashMapissa ryhmat
     * @return ryhmanNumeroa vastaava ryhma
     */
    
    public Ryhma getRyhma(int ryhmanNumero) {
        return ryhmat.get(ryhmanNumero);
    }
    
    public HashSet<Integer> getKuolleeksiMerkitytRyhmat() {
        return kuolleeksiMerkitytRyhmat;
    }
    
    public int getMustanVangit() {
        return mustanVangit;
    }
    
    public int getValkeanVangit() {
        return valkeanVangit;
    }
    
    public ArrayList<int[]> getSiirrot() {
        return siirrot;
    }
    
    public int getSiirtojenMaara() {
        return siirrot.size();
    }
    
    public boolean getViimeisinSiirtoOliKonSyonti() {
        return viimeisinSiirtoOliKonSyonti;
    }
    
    public int getViimeisimmanKonSyonninXKoordinaatti() {
        return viimeisimmanKonSyonninXKoordinaatti;
    }
    
    public int getViimeisimmanKonSyonninYKoordinaatti() {
        return viimeisimmanKonSyonninYKoordinaatti;
    }
    
    
    /**
     * Metodi lisää syötekoordinaatteja vastaavan kiven vapaudet syötelukua
     * vastaavaan ryhmään
     * 
     * @param x Kiven rivi laudalla
     * @param y Kiven sarake laudalla
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, johon vapauksia ollaan lisäämässä.
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
    
    /**
     * Metodi poistaa syötekoordinaatteja ympäröiviltä ryhmiltä syötekoordinaatteja
     * vastaavan kohdan vapauksista
     * 
     * @param x Kiven rivi laudalla
     * @param y Kiven sarake laudalla
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
    
    /**
     * Metodi luo uuden ryhmän, jonka väri on syötteenä annnettu väri, syötekoordinaatteja
     * vastaavaan laudan kohtaan ja päivittää ympäröivien ryhmien vapaudet.
     * 
     * @param x Sen laudan kohdan rivi, johon ryhmää ollaan luomassa
     * @param y Sen laudan kohdan sarake, johon ryhmää ollaan luomassa
     * @param vari Luotavan ryhmän väri
     */
    
    public void luoUusiRyhma(int x, int y, int vari) {
        Ryhma ryhma = new Ryhma(vari);
        int ryhmanNumero = vari*uudenRyhmanNumero;
        ryhmat.put(ryhmanNumero,ryhma);
        lauta[x][y] = ryhmanNumero;
        poistaKiviYmparoivienRyhmienVapauksista(x, y);
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
        uudenRyhmanNumero++;
        
    }
    
    /**
     * Metodi lisää syötekoordinaatteja vastaavan laudan kiven syöteavainta
     * vastaavaan ryhmään
     * 
     * @param x Lisättävän kiven rivi laudalla
     * @param y Lisättävän kiven sarake laudalla
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, johon ollaan lisäämässä kiveä
     */
    
    public void lisaaKiviRyhmaan(int x, int y, int ryhmanNumero) {
        lauta[x][y] = ryhmanNumero;
        Ryhma ryhma = getRyhma(ryhmanNumero);
        poistaKiviYmparoivienRyhmienVapauksista(x, y);
        lisaaKivenVapaudetRyhmaan(x,y,ryhmanNumero);
        ryhma.lisaaKivi(x,y);
    }
    
    /**
     * Metodi yhdistää syötteenä annettuja ryhmien numeroita vastaavat ryhmät toisiinsa
     * siten, että yhdistämisen jälkeen laudalla on enää vain ensimmäiseksi annettu ryhmä
     * 
     * @param ryhmanNumero1 Ensimmäistä yhdistettävää ryhmää vastaava avain hashMapissa ryhmat
     * @param ryhmanNumero2 Toista yhdistettävää ryhmää vastaava avain hashMapissa ryhmat
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
    
    /**
     * Metodi hakee syötteenä annettua ryhmän numeroa vastaavan ryhmän ryhmat-
     * hashMapista ja poistaa sen laudalta, kasvattaen samalla vankilaskuria ryhmän
     * koolla ja päivittäen ympäröivien kivien vapaudet
     * 
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, jota ollaan poistamassa.
     */
    
    
    public void poistaRyhmaLaudalta(int ryhmanNumero) {
        Ryhma ryhma = getRyhma(ryhmanNumero);
        int vari = ryhma.getVari();
        if (vari == 1) {
            valkeanVangit += ryhma.getKivienMaara();
        } else mustanVangit += ryhma.getKivienMaara();
        HashSet<String> kivet = ryhma.getKivet();
        ryhmat.remove(ryhmanNumero);
        for (String s : kivet) {
            int pisteenKohta = s.indexOf('.');
            int x = (int) Integer.parseInt(s.substring(0,pisteenKohta));
            int y = (int) Integer.parseInt(s.substring(pisteenKohta+1));
            lauta[x][y] = 0;
            lisaaPoistettavanKivenVihollisnaapureilleVapaudeksiPoistettavaKivi(x,y,ryhmanNumero);
        }
    }
    
    /**
     * Metodi käy syötekoordinaatteja vastaavan laudan kohdan naapurit läpi ja lisää naapuria
     * vastaavalle ryhmälle kohdan vapaudeksi, jos naapuri ei ole 0 tai syötteenä annettu ryhmän
     * numero
     * 
     * @param x Lisättävän vapauden rivi laudalla
     * @param y Lisättävän vapauden sarake laudalla
     * @param ryhmanNumero Sitä ryhmää vastaava avain hashMapissa ryhmat, jolle ei sallita vapauden lisäämistä
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
    
    /**
     * Metodi tutkii, söisiko syötekoordinaatteja vastaavaan laudan kohtaan laitettu
     * kivi, jonka väri on syötteenä annettu väri, kiviä
     * 
     * @param x Tutkittavan kohdan rivi laudalla
     * @param y Tutkittavan kohdan sarake laudalla
     * @param vari Kiven väri
     * @return true, jos siirto syö ryhmiä, ja muuten false
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
    
    /**
     * Metodi tutkii olisiko syötekoordinaatteja vastaavaan kohtaan laitettu kivi,
     * jonka väri on syötteenä annettu väri, laillinen sillä oletuksella, että se ei syö kiviä.
     * 
     * @param x Tutkittavan kohdan rivi laudalla
     * @param y Tutkittavan kohdan sarake laudalla
     * @param vari Kiven väri
     * @return true, jos siirto ei syö ryhmiä, mutta on laillinen, ja muuten false
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
    
    /**
     * Metodi tutkii onko syötekoordinaatteja vastaavan laudan naapurina kivi, joka
     * söi viimeksi kon.
     * 
     * @param x Tutkittavan kohdan rivi laudalla
     * @param y Tutkittavan kohdan sarake laudalla
     * @return true, jos laudan naapurina on viimeksi kon syönyt kivi, ja muuten false
     */
    
    public boolean naapuriOnKoKivi(int x, int y) {
         for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                if (naapurix == viimeisimmanKonSyonninXKoordinaatti && naapuriy == viimeisimmanKonSyonninYKoordinaatti) return true;
            }
         }
         return false;
   }
    
    /**
     * Metodi tutkii olisiko syötekoordinaatteina annettuun kohtaan laitettu kivi,
     * jonka väri on syötteenä annettu väri, laillinen siirto
     * 
     * @param x Tutkittavan kohdan rivi laudalla
     * @param y Tutkittavan kohdan sarake laudalla
     * @param vari Kiven väri
     * @return Boolean, joka kertoo onko siirto laillinen
     */
    
    public boolean siirtoOnLaillinen(int x, int y, int vari) {
        if (x < 0 || x >= pituus || y < 0 || y >= pituus) return false;
        if (lauta[x][y] != 0) return false;
        if (viimeisinSiirtoOliKonSyonti && naapuriOnKoKivi(x,y)) return false;
        return (siirtoSyoRyhmia(x, y, vari) || siirtoEiSyoRyhmiaMuttaOnLaillinen(x,y,vari));
    }
    
    /**
     * Metodi tutkii aloittaisiko syötekoordinaatteja vastaavaan laudan kohtaan
     * laitettu kivi, jonka väri on syötteenä annettu väri, kon.
     * 
     * @param x Tutkittavan kohdan rivi laudalla
     * @param y Tutkittavan kohdan sarake laudalla
     * @param vari Kiven väri
     * @return Boolean, joka kertoo aloittaako siirto kon.
     */
    
    public boolean siirtoAloittaaKon(int x, int y, int vari) {
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
    
    /**
     * Metodi syö syötekoordinaatteja vastaavan laudan kohdan ympäriltä kaikki ryhmät,
     * joiden väri ei ole sama kuin syötteenä annettu väri.
     * 
     * @param x Kohdan rivi laudalla
     * @param y Kohdan sarake laudalla
     * @param vari Väri, jota poistettavat ryhmät eivät ole
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
    
    /**
     * Metodi koittaa etsiä syötekoordinaatteja vastaavan laudan kohdan naapureista
     * jonkun ryhmän, jonka väri on syötteenä annettu väri, ja sitten yhdistää kohtaa
     * vastaavan kiven tähän ryhmään.
     * 
     * @param x Yhdistettävän kiven rivi laudalla
     * @param y Yhdistettävän kiven sarake laudalla
     * @param vari Niiden ryhmien väri, joihin ollaan yhdistämässä
     * @return Boolean, joka kertoo löytyikö naapurista jokin syötteenä annetun värin värinen ryhmä
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
    
    /**
     * Metodi yhdistää syötekoordinaatteja vastaavan laudan kohdan avainta vastaavan ryhmän
     * ympäröiviin samanvärisiin ryhmiin
     * 
     * @param x Laudan kohdan rivi, jota vastaavaa ryhmää ollaan yhdistämässä
     * @param y Laudan kohdan sarake, jota vastaavaa ryhmää ollaan yhdistämässä
     */
    
    public void yhdistaRyhmaYmparoiviinRyhmiin(int x, int y) {
        int ryhmanNumero1 = lauta[x][y];
        for (int i = 0; i<naapurit.length; i++) {
            int naapurix = x + naapurit[i][0];
            int naapuriy = y + naapurit[i][1];
            if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                int ryhmanNumero2 = lauta[naapurix][naapuriy];
                if (ryhmanNumero2 != 0) {
                    Ryhma ryhma = ryhmat.get(ryhmanNumero2);
                    if (ryhmanNumero1 * ryhmanNumero2 > 0) {
                        yhdistaKaksiRyhmaa(ryhmanNumero1, ryhmanNumero2);
                    }
                }
            }
        }
    }
    
    /**
     * Metodi laittaa syötekoordinaatteina annettuun laudan kohtaan syötteenä annetun
     * värin värisen kiven, jos se on laillista
     * 
     * @param x Laudan kohdan rivi, johon kiveä ollaan laittamassa
     * @param y Laudan kohdan sarake, johon kiveä ollaan laittamassa
     * @param vari Laitettavan kiven väri
     */
    
    public void laitaSiirto(int x, int y, int vari) {
        if (siirtoOnLaillinen(x,y,vari)) {
            if (siirtoSyoRyhmia(x,y,vari)) {
                if (siirtoAloittaaKon(x,y,vari)) {
                    viimeisinSiirtoOliKonSyonti = true;
                    viimeisimmanKonSyonninXKoordinaatti = x;
                    viimeisimmanKonSyonninYKoordinaatti = y;
                }
                syoVastustajanRyhmatYmparilta(x,y,vari);
            }
            if  (yhdistaKiviJohonkinYmparoivaanRyhmaan(x,y,vari)) {
            yhdistaRyhmaYmparoiviinRyhmiin(x,y);
            }
            else luoUusiRyhma(x,y,vari);
            if (viimeisimmanKonSyonninXKoordinaatti != x || viimeisimmanKonSyonninYKoordinaatti != y) viimeisinSiirtoOliKonSyonti = false;
            int[] siirto = {x,y,vari};
            siirrot.add(siirto);
        }
        
    }
    
    /**
     * Metodi merkitsee sen ryhmän, jonka avain hashMapissa ryhmat on syötteena annettu luku,
     * kuolleeksi
     * 
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, jota ollaan merkkaamassa kuolleeksi
     */
    
    public void merkitseRyhmaKuolleeksi(int ryhmanNumero) {
        kuolleeksiMerkitytRyhmat.add(ryhmanNumero);
    }
    
    /**
     * Metodi merkitsee sen ryhmän, jonka avain hashMapissa ryhmat on syötteena annettu luku,
     * eläväksi poistamalla sen kuolleeksi merkityistä ryhmistä
     * 
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, jota ollaan merkkaamassa eläväksi
     */
    
    public void merkitseRyhmaElavaksi(int ryhmanNumero) {
        kuolleeksiMerkitytRyhmat.remove(ryhmanNumero);
    }
    
    /**
     * Metodi merkitsee kaikki ryhmät eläviksi
     */
    
    public void merkitseKaikkiRyhmatElaviksi() {
        kuolleeksiMerkitytRyhmat.clear();
    }
    
    /**
     * Metodi tarkistaa onko se ryhmä, jonka avain hashMapissa ryhmat on syötteenä annettu luku,
     * merkitty kuolleeksi
     * 
     * @param ryhmanNumero Sen ryhmän avain hashMapissa ryhmat, jota ollaan tutkimassa
     * @return Boolean, joka kertoo onko ryhmä merkitty kuolleeksi
     */
    
    public boolean onMerkittyKuolleeksi(int ryhmanNumero) {
        return kuolleeksiMerkitytRyhmat.contains(ryhmanNumero);
    }
    
    /**
     * Metodi laittaa seuraavaksi siirroksi passauksen
     */
    
    public void passaa() {
        int[] siirto = {-1,-1,0};
        siirrot.add(siirto);
        viimeisinSiirtoOliKonSyonti = false;
    }
    
    /**
     * Metodi palauttaa Pelilaudan, jonka tilanne on sama kuin ennen viimeisintä
     * siirtoa
     * 
     * @return Pelilauta, jonka tilanne on sama kuin ennen viimeisintä siirtoa
     */
    
    public Pelilauta palautaEdellisenSiirronTilanne() {
        Pelilauta edellisenSiirronTilanne = new Pelilauta(pituus,leveys);
        for (int i = 0; i<siirrot.size()-1; i++) {
            int[] siirto = siirrot.get(i);
            int siirronRivi = siirto[0];
            int siirronSarake = siirto[1];
            int siirronVari = siirto[2];
            if (siirronRivi == -1) edellisenSiirronTilanne.passaa();
            else edellisenSiirronTilanne.laitaSiirto(siirronRivi,siirronSarake,siirronVari);
        }
        return edellisenSiirronTilanne;
    }
    
   
}
        
        

    
    
    

