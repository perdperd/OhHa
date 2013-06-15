/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import pelilogiikka.*;
import tiedostonkasittely.*;
import java.util.Scanner;

/**
 * Luokka on tekstipohjainen käyttöliittymä aiemmin tallennetun pelin läpikäymiseen
 *
 * @author Juuso Nyyssönen
 */
public class Lapikayntikayttoliittyma extends Tekstikayttoliittyma {
    
    /**
     * Tämänhetkisen siirron numero
     */
    
    private int siirronNumero = 0;
    
    /**
     * Laudan tilanne tämänhetkisellä siirrolla
     */
    
    private String[][] laudanTilanne;
    
    /**
     * Läpikäyntilauta, jolla ollaan läpikäymässä peliä
     */
    
    private Lapikayntilauta lapikayntilauta;
    
    /**
     * Pelaajan syötteen lukeva Scanner
     */
    
    private Scanner input;
    
    
    /**
     * Metodi luo uuden käyttöliittymän käyttäjän antaman tiedoston pohjalta
     */
    
    public Lapikayntikayttoliittyma() {
        input = new Scanner(System.in);
        Pelitiedostonkasittelija kasittelija = new Pelitiedostonkasittelija();
        while (true) {
            System.out.println("\"peruuta\" peruuttaa");
            System.out.print("Anna ladattavan pelin tiedoston nimi: ");
            String syote = input.nextLine();
            if (syote.equals("peruuta")) {
                new Alkuvalikko();
                break;
            }
            if (kasittelija.lataaTallennettuPeli(syote)) {
                lapikayntilauta = new Lapikayntilauta(kasittelija);
                if (!lapikayntilauta.getPelinLatausOnnistui()) {
                    System.out.println("Virhe tiedoston lataamisessa");
                } else {
                    pituus = lapikayntilauta.getPituus();
                    leveys = lapikayntilauta.getLeveys();
                    lapikay();
                    break;
                }
            }
            System.out.println("Virhe tiedoston lataamisessa");
        }
    }
    
    @Override
    public void tulostaRivi(int rivi) {
        if (pituus < 10 || pituus-rivi >= 10) System.out.print(pituus-rivi);
        else System.out.print(" " + (pituus-rivi));
        for (int i = 0; i<leveys; i++) {
            System.out.print(" "  + laudanTilanne[rivi][i]);
        }
        System.out.print(" " + (pituus-rivi));
        System.out.println();
    }
    
    /**
     * Metodi ottaa käyttäjältä syötteitä ja siirtyy niitä vastaaviin laudan tilanteisiin
     * 
     */
    
    
    public void lapikay() {
        while (true) {
            laudanTilanne = lapikayntilauta.getLaudanTilanne(siirronNumero);
            tulostaLauta();
            System.out.println("Siirto " + siirronNumero);
            System.out.println("\"s\" siirtyy seuraavaan siirtoon, \"e\" edelliseen ja \"lopeta\" lopettaa");
            String syote = input.nextLine();
            if (syote.equals("lopeta")) {
                new Alkuvalikko();
                break;
            }
            if (syote.equals("s")) {
                if (siirronNumero < lapikayntilauta.getSiirtojenMaara()) siirronNumero++;
            }
            else if (syote.equals("e")) {
                if (siirronNumero > 0) siirronNumero--;
            }
            else if (onKokonaisluku(syote)) {
                int syotettyLuku = Integer.parseInt(syote);
                siirrySyotteenaAnnettuunSiirtoon(syotettyLuku);
            } else siirrySyotteenaAnnettuunSiirtoon(syote);
        }
    }
    
    /**
     * Metodi asettaa tämänhetkisen siirron numeroksi syötteenä annettun siirron numeron
     * 
     * @param syotettySiirronNumero Sen siirron numero, johon ollaan siirtymässä
     */
    
    public void siirrySyotteenaAnnettuunSiirtoon(int syotettySiirronNumero) {
        siirronNumero = syotettySiirronNumero;
        if (siirronNumero <= 0) siirronNumero = 0;
        if (siirronNumero >= lapikayntilauta.getSiirtojenMaara()) siirronNumero = lapikayntilauta.getSiirtojenMaara();
    }
    
    /**
     * Metodi asettaa tämänhetkisen siirron numeroksi sen siirron numeron, jolloin syötteenä annettu
     * siirto ensimmäisen kerran pelattiin
     * 
     * @param siirto Se siirto, jota vastaavaan numeroon ollaan siirtymässä
     */
    
    public void siirrySyotteenaAnnettuunSiirtoon(String siirto) {
        int[] koordinaatit = palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(siirto);
        if (koordinaatit[0] != -1) {
        int ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin = lapikayntilauta.getSiirronNumero(koordinaatit);
        if (ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin != -1) siirronNumero = ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin;
        }
    }
    
}
