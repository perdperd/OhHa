/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import pelilogiikka.*;
import tiedostonkasittely.*;
import java.util.Scanner;

/**
 *
 * @author Prod
 */
public class Lapikayntikayttoliittyma {
    
    private int pituus;
    
    private int leveys;
    
    private int siirronNumero = 0;
    
    private String[][] laudanTilanne;
    
    private Lapikayntilauta lapikayntilauta;
    
    private Scanner input;
    
    
    
    public Lapikayntikayttoliittyma() {
        input = new Scanner(System.in);
        Pelitiedostonkasittelija kasittelija = new Pelitiedostonkasittelija();
        while (true) {
            System.out.print("Anna ladattavan pelin tiedoston nimi: ");
            String syote = input.nextLine();
            if (kasittelija.lataaTallennettuPeli(syote)) {
                lapikayntilauta = new Lapikayntilauta(kasittelija);
                if (lapikayntilauta.luoLapikaytavanPelinLaudanTilanteet()) {
                pituus = lapikayntilauta.getPituus();
                leveys = lapikayntilauta.getLeveys();
                lapikay();
                break;
                }
            }
            System.out.println("Virhe tiedoston lataamisessa");
        }
    }
    
    public void tulostaLauta() {
        tulostaKirjaimet();
        for (int i = 0; i<pituus; i++) {
            tulostaRivi(i);
        }
        tulostaKirjaimet();
    }
    
    public void tulostaRivi(int rivi) {
        if (pituus < 10 || pituus-rivi >= 10) System.out.print(pituus-rivi);
        else System.out.print(" " + (pituus-rivi));
        for (int i = 0; i<leveys; i++) {
            System.out.print(" "  + laudanTilanne[rivi][i]);
        }
        System.out.print(" " + (pituus-rivi));
        System.out.println();
    }
    
    public void tulostaKirjaimet() {
        int valilyontienMaara = 0;
        if (pituus >= 10) valilyontienMaara = 3;
        else valilyontienMaara = 2;
        for (int i = 0; i<valilyontienMaara; i++) {
            System.out.print(" ");
        }
        System.out.print("A");
        for (int i = 1; i<this.leveys; i++) {
            int kirjaimenNumero = (int) ('A' + i);
            if (i >= 8) kirjaimenNumero++;
            System.out.print(" " + (char) kirjaimenNumero);
        }
        System.out.println();
    }
    
    public boolean onKokonaisluku(String syote) {
        try { 
        Integer.parseInt(syote); 
    } catch(NumberFormatException e) { 
        return false; 
    }
        return true;
    }
    
    public int[] palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(String syote) {
        int[] syotettaVastaavatKoordinaatit = {-1,-1};
        if (syote.length() < 2 || syote.length() > 3) return syotettaVastaavatKoordinaatit;
        int syotteenXKoordinaatti = (int) Character.toUpperCase(syote.charAt(0)) - 65;
        if (syotteenXKoordinaatti == 8 ) return syotettaVastaavatKoordinaatit;
        else if (syotteenXKoordinaatti > 8) syotteenXKoordinaatti--;
        if (syotteenXKoordinaatti < 0 || syotteenXKoordinaatti >= leveys) return syotettaVastaavatKoordinaatit;
        if (!onKokonaisluku(syote.substring(1))) return syotettaVastaavatKoordinaatit;
        int syotteenYKoordinaatti = pituus - Integer.parseInt(syote.substring(1));
        if (syotteenYKoordinaatti < 0 || syotteenYKoordinaatti >= pituus) return syotettaVastaavatKoordinaatit;
        syotettaVastaavatKoordinaatit[0] = syotteenYKoordinaatti;
        syotettaVastaavatKoordinaatit[1] = syotteenXKoordinaatti;
        return syotettaVastaavatKoordinaatit;
    }
    
    public void lapikay() {
        while (true) {
            laudanTilanne = lapikayntilauta.getLaudanTilanne(siirronNumero);
            tulostaLauta();
            System.out.println("Siirto " + siirronNumero);
            System.out.println("\"s\" siirtyy seuraavaan siirtoon, \"e\" edelliseen ja \"lopeta\" lopettaa");
            String syote = input.nextLine();
            if (syote.equals("lopeta")) break;
            if (syote.equals("s")) {
                if (siirronNumero < lapikayntilauta.getSiirtojenMaara()-1) siirronNumero++;
            }
            else if (syote.equals("e")) {
                if (siirronNumero > 0) siirronNumero--;
            }
            else if (onKokonaisluku(syote)) {
                int syotettyLuku = Integer.parseInt(syote);
                siirronNumero = syotettyLuku;
                if (siirronNumero <= 0) siirronNumero = 0;
                if (siirronNumero >= lapikayntilauta.getSiirtojenMaara()-1) siirronNumero = lapikayntilauta.getSiirtojenMaara()-1;
            }
            else {
                int[] koordinaatit = palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(syote);
                if (koordinaatit[0] == -1) continue;
                int ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin = lapikayntilauta.getSiirronNumero(koordinaatit);
                if (ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin != -1) siirronNumero = ensimmainenKertaJolloinKoordinaattejaVastaavaSiirtoPelattiin;
            }
        }
    }
    
}
