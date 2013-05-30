/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import pelilogiikka.*;
import java.util.Scanner;

/**
 * Luokka on tekstipohjainen käyttöliittymä gon pelaamiseen kahden ihmispelaajan
 * välillä.
 *
 * @author Prod
 */
public class Pelikayttoliittyma {
    private int pituus;
    private int leveys;
    private Pelilauta pelilauta;
    private int[][] lauta;
    private int vuorossaOlevanPelaajanVari;
    private String viimeisinSiirto = "";
    private int siirtojenMaara = 0;
    private int tasoitusKivet;
    private Scanner input;
    
    public Pelikayttoliittyma() {
        pituus = 0;
        leveys = 0;
        input = new Scanner(System.in);
        System.out.println("Anna laudan pituus: ");
        pituus = palautaKaypaPelaajanSyote(2,19, "Pituuden");
        System.out.println("Anna laudan leveys: ");
        leveys = palautaKaypaPelaajanSyote(2,19, "Leveyden");
        pelilauta = new Pelilauta(pituus,leveys);
        System.out.println("Anna tasoituskivien määrä: ");
        tasoitusKivet = palautaKaypaPelaajanSyote(0,9, "Tasoituskivien määrän");
        if (tasoitusKivet > 0) vuorossaOlevanPelaajanVari = -1;
        else vuorossaOlevanPelaajanVari = 1;
        lauta = pelilauta.getLauta();
        pelaa();
    }
    
    public int palautaKaypaPelaajanSyote(int alaraja, int ylaraja, String syotteenTyyppi) {
        Scanner input = new Scanner(System.in);
        
        while (true) {
            String syote = input.nextLine();
            if (onKokonaisluku(syote)) {
               int luku = Integer.parseInt(syote);
                if (luku < alaraja || luku > ylaraja) {
                    System.out.println(syotteenTyyppi + " tulee olla kokonaisluku väliltä " + alaraja + "-" + ylaraja + "!");
                }
                else return luku;
            }
            else System.out.println(syotteenTyyppi + " tulee olla kokonaisluku väliltä " + alaraja + "-" + ylaraja + "!");
        }
    }
    
    public boolean onKokonaisluku(String syote) {
        try { 
        Integer.parseInt(syote); 
    } catch(NumberFormatException e) { 
        return false; 
    }
        return true;
    }
    
    public void tulostaLauta() {
        tulostaKirjaimet();
        for (int i = 0; i<pituus; i++) {
            tulostaRivi(i);
        }
        tulostaKirjaimet();
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
    
    public void tulostaRivi(int rivi) {
        if (pituus < 10 || pituus-rivi >= 10) System.out.print(pituus-rivi);
        else System.out.print(" " + (pituus-rivi));
        for (int i = 0; i<leveys; i++) {
            if (lauta[rivi][i] > 0) System.out.print(" X");
            else if (lauta[rivi][i] < 0) System.out.print(" O");
            else System.out.print(" .");
        }
        System.out.print(" " + (pituus-rivi));
        System.out.println();
    }
    
    public void pelaa() {
        while (true) {
            tulostaLauta();
            if (tasoitusKivet > 0) {
                System.out.println("Aseta tasoituskivi: ");
                String siirto = input.nextLine();
                if (koitaLaittaaSiirto(siirto,1)) tasoitusKivet--;
            }
            else {
                int mustanVangit = pelilauta.getMustanVangit();
                int valkeanVangit = pelilauta.getValkeanVangit();
                System.out.println("Mustan vangit: " + mustanVangit + " Valkean vangit: " + valkeanVangit);
                System.out.println("\"pass\" passaa.");
                if (viimeisinSiirto.length() > 0) System.out.println("Viimeisin siirto: " + viimeisinSiirto);
                if (vuorossaOlevanPelaajanVari == 1) System.out.print("Mustan vuoro: ");
                else System.out.print("Valkean vuoro: ");
                String siirto = input.nextLine();
                if (siirto.equals("pass")) {
                    pelilauta.passaa();
                    vuorossaOlevanPelaajanVari = -vuorossaOlevanPelaajanVari;
                    if (viimeisinSiirto.equals("pass")) {
                        pisteytaPeli();
                        break;
                    } 
                    else {
                        viimeisinSiirto = siirto;
                    }
                }
                else {
                if (koitaLaittaaSiirto(siirto,vuorossaOlevanPelaajanVari)) vuorossaOlevanPelaajanVari = -vuorossaOlevanPelaajanVari;
                else System.out.println("Siirron tulee olla muotoa kirjain-numero, esimerkiksi a3 tai b5");
                }
            }
        }
    }
    
    public void pisteytaPeli() {
        while (true) {
            tulostaLauta();
            System.out.println("\"pisteyta\" pisteyttää pelin ja \"peruuta\" palaa takaisin peliin.");
            System.out.print("Anna kuolleeksi tai eläväksi merkittävän ryhmän jonkun kiven koordinaatti: ");
            String syote = input.nextLine();
            if (syote.equals("pisteyta")) {
                Pisteyttaja pisteyttaja = luoPisteyttaja(pelilauta);
                pisteyttaja.pisteyta();
                int mustanPisteet = pisteyttaja.getMustanPisteet();
                int valkeanPisteet = pisteyttaja.getValkeanPisteet();
                System.out.println("Mustan pisteet: " + mustanPisteet + " Valkean pisteet: " + valkeanPisteet);
                if (mustanPisteet > valkeanPisteet) System.out.println("Musta voitti " + (mustanPisteet-valkeanPisteet) + " pistettä.");
                else if (mustanPisteet < valkeanPisteet) System.out.println("Valkea voitti " + (valkeanPisteet-mustanPisteet) + "pistettä.");
                else System.out.println("Tasapeli!");
                break;
            }
            if (syote.equals("peruuta")) {
                pelaa();
                break;
            }
            if (koitaMerkitaRyhmaKuolleeksi(syote)) { 
                System.out.println("Merkitty ryhmä " + syote + " kuolleeksi.");
            }
            else if (koitaMerkitaRyhmaElavaksi(syote)) System.out.println("Merkitty ryhmä " + syote + " eläväksi");
            else System.out.println("Koordinaatin tulee olla muotoa kirjain-numero, esim a3 tai b5.");
        }
}



    
    public boolean koitaLaittaaSiirto(String siirto, int vari) {
        if (siirto.length() < 2 || siirto.length() > 3) return false;
        int siirronXKoordinaatti = (int) Character.toUpperCase(siirto.charAt(0)) - 65;
        if ((int) Character.toUpperCase(siirto.charAt(0)) >=  73) siirronXKoordinaatti--;
        if (!onKokonaisluku(siirto.substring(1))) return false;
        int siirronYKoordinaatti = pituus - Integer.parseInt(siirto.substring(1));
        pelilauta.laitaSiirto(siirronYKoordinaatti,siirronXKoordinaatti,vari);
        int uusiSiirtojenMaara = pelilauta.getSiirtojenMaara();
        if (uusiSiirtojenMaara > siirtojenMaara) {
            siirtojenMaara = uusiSiirtojenMaara;
            viimeisinSiirto = siirto;
            return true;
        }
        else return false;
    }
    
    public boolean koitaMerkitaRyhmaKuolleeksi(String syote) {
        if (syote.length() < 2 || syote.length() > 3) return false;
        int syotteenXKoordinaatti = (int) Character.toUpperCase(syote.charAt(0)) - 65;
        if (syotteenXKoordinaatti < 0 || syotteenXKoordinaatti >= leveys) return false;
        if (!onKokonaisluku(syote.substring(1))) return false;
        int syotteenYKoordinaatti = pituus - Integer.parseInt(syote.substring(1));
        if (syotteenYKoordinaatti < 0 || syotteenYKoordinaatti >= pituus) return false;
        int ryhmanNumero = lauta[syotteenYKoordinaatti][syotteenXKoordinaatti];
        if (ryhmanNumero == 0) return false;
        if (pelilauta.onMerkittyKuolleeksi(ryhmanNumero)) return false;
        pelilauta.merkitseRyhmaKuolleeksi(lauta[syotteenYKoordinaatti][syotteenXKoordinaatti]);
        return true;
        
    }
    
    public boolean koitaMerkitaRyhmaElavaksi(String syote) {
        if (syote.length() < 2 || syote.length() > 3) return false;
        int syotteenXKoordinaatti = (int) Character.toUpperCase(syote.charAt(0)) - 65;
        if (syotteenXKoordinaatti < 0 || syotteenXKoordinaatti >= leveys) return false;
        if (!onKokonaisluku(syote.substring(1))) return false;
        int syotteenYKoordinaatti = pituus - Integer.parseInt(syote.substring(1));
        if (syotteenYKoordinaatti < 0 || syotteenYKoordinaatti >= pituus) return false;
        int ryhmanNumero = lauta[syotteenYKoordinaatti][syotteenXKoordinaatti];
        if (ryhmanNumero == 0) return false;
        pelilauta.merkitseRyhmaElavaksi(lauta[syotteenYKoordinaatti][syotteenXKoordinaatti]);
        return true;
    }
    
    public Pisteyttaja luoPisteyttaja(Pelilauta pelilauta) {
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.poistaKuolleetRyhmat();
        pisteyttaja.alustaLasketutKohdat();
        return pisteyttaja;
    }
   


}
