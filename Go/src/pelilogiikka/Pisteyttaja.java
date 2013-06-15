/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

import java.util.HashSet;
import java.util.ArrayDeque;

/**
 * Luokka hoitaa pelin pisteyttämisen sen loputtua
 *
 * @author Juuso Nyyssönen
 */

public class Pisteyttaja {
    
    /**
     * Laudan kuolleeksi merkityt ryhmät sisältävä HashSet
     */
    
    private HashSet<Integer> kuolleetRyhmat;
    
    /**
     * Laudan leveys
     */
    
    private int leveys;
    
    /**
     * Laudan pituus
     */
    
    private int pituus;
    
    /**
     * Pisteet, jotka pisteyttäjä on laskenut mustalle
     */
    
    private int mustanPisteet;
    
    /**
     * Pisteet, jotka pisteyttäjä on laskenut valkealle
     */
    
    private int valkeanPisteet;
    
    /**
     * Taulukko, joka esittää laudan tilannetta pelin lopussa
     * 
     * Sisältää laudan ryhmien numeroita
     */
    
    private int[][] lauta;
    
    /**
     * Taulukko, jossa pidetään kirjaa niistä kohdista, jotka pisteyttäjä on jo
     * laskenut
     */
    
    private boolean[][] lasketutKohdat;
    
    /**
     * Aputaulukko kiven naapurien läpikäyntiä varten
     */
    
    private final int[][] naapurit = {{1,0}, {0, 1}, {-1, 0}, {0,-1}};
    
    /**
     * Luo annettua pelilaudan tilannetta vastaavan pisteyttäjän
     * 
     * @param pelilauta Pelilauta, jota ollaan pisteyttämässä
     */
    
    public Pisteyttaja(Pelilauta pelilauta) {
        leveys = pelilauta.getLeveys();
        pituus = pelilauta.getPituus();
        lauta = new int[pituus][leveys];
        kuolleetRyhmat = pelilauta.getKuolleeksiMerkitytRyhmat();
        mustanPisteet = pelilauta.getMustanVangit();
        valkeanPisteet = pelilauta.getValkeanVangit();
        lasketutKohdat = new boolean[pituus][leveys];
    }
    
    public int getPituus() {
        return pituus;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public int getMustanPisteet() {
        return mustanPisteet;
    }
    
    public int getValkeanPisteet() {
        return valkeanPisteet;
    }
    
    public int[][] getLauta() {
        return lauta;
    }
    
    public boolean[][] getLasketutKohdat() {
        return lasketutKohdat;
    }
    
    /**
     * Kopioi syötteenä annetun pelilaudan laudan tilanteen pisteyttäjään
     * 
     * @param pelilauta Pelilauta, jonka tilannetta ollaan kopioimassa
     */
    
    public void kopioiLaudanTilanne(Pelilauta pelilauta) {
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                lauta[i][j] = pelilauta.getRyhmanNumero(i,j);
            }
        }
    }
    
    /**
     * Poistaa laudalta kuolleeksi merkityt ryhmät ja lisää ryhmien kiviä vastaavat
     * pisteet mustan tai valkean pisteisiin
     */
    
    public void poistaKuolleetRyhmat() {
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                if (kuolleetRyhmat.contains(lauta[i][j])) {
                    if (lauta[i][j] > 0) valkeanPisteet++;
                    else mustanPisteet++;
                    lauta[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * Alustaa taulukon lasketutKohdat niin, että laudan tyhjiä kohtia taulukossa vastaa false
     * ja muita true
     */
    
    public void alustaLasketutKohdat() {
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                if (lauta[i][j] == 0) lasketutKohdat[i][j] = false;
                else lasketutKohdat[i][j] = true;
            }
        }
    }
    
    /**
     * Laskee syötteenä annettua kohtaa vastaavan alueen pisteet
     * 
     * @param i Laskemisen aloituskohdan rivi laudalla
     * @param j Laskemisen aloituskohdan sarake laudalla
     * @return Luku, joka on joko alueen pisteet, jos alue kuuluu mustalle, tai alueen pisteet negatiivisena,
     * jos alue kuuluu valkealle, ja muuten 0
     */
    
    public int pisteytaAlue(int i, int j) {
        ArrayDeque<int[]> apujono = new ArrayDeque<int[]>();
        int alueenKoko = 0;
        int alueenVari = 0;
        boolean alueEiKuuluKenellekaan = false;
        int[] alkukohta = {i,j};
        apujono.add(alkukohta);
        lasketutKohdat[i][j] = true;
        while (!apujono.isEmpty()) {
            int[] laudanKohta = apujono.poll();
            int x = laudanKohta[0];
            int y = laudanKohta[1];
            alueenKoko++;
            for (int k = 0; k<naapurit.length; k++) {
               int naapurix = x + naapurit[k][0];
               int naapuriy = y + naapurit[k][1];
               if (naapurix >= 0 && naapurix < pituus && naapuriy >= 0 && naapuriy < leveys) {
                   if (!lasketutKohdat[naapurix][naapuriy]) {
                       int[] uusiKohta = {naapurix, naapuriy};
                       apujono.add(uusiKohta);
                       lasketutKohdat[naapurix][naapuriy] = true;
                   }
                   if (!alueEiKuuluKenellekaan) {
                   if (lauta[naapurix][naapuriy] != 0) {
                       if (alueenVari == 0) alueenVari = Integer.signum(lauta[naapurix][naapuriy]);
                       else if (alueenVari * lauta[naapurix][naapuriy] < 0) alueEiKuuluKenellekaan = true;
                   }
                 }
               }
            }
        }
        if (alueEiKuuluKenellekaan) return 0;
        else return alueenVari*alueenKoko;
    }
    
    /**
     * Laskee laudan kaikkien alueiden pisteet ja lisää jokaisen alueen pisteet sen
     * omistavan pelaajan pisteisiin
     */
    
    public void pisteyta() {
        for (int i = 0; i<pituus; i++) {
            for (int j = 0; j<leveys; j++) {
                if (!lasketutKohdat[i][j]) {
                    int alueenPisteet = pisteytaAlue(i,j);
                    if (alueenPisteet > 0) mustanPisteet += alueenPisteet;
                    else if (alueenPisteet < 0) valkeanPisteet -= alueenPisteet;
                }
            }
        }
    }
    
    
}
