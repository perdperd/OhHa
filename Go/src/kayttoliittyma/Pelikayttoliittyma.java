/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import pelilogiikka.*;
import tiedostonkasittely.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Luokka on tekstipohjainen käyttöliittymä gon pelaamiseen kahden ihmispelaajan
 * välillä.
 *
 * @author Juuso Nyyssönen
 */
public class Pelikayttoliittyma extends Tekstikayttoliittyma {
    
    /**
     * Pelilauta, jolla pelataan
     */
    
    private Pelilauta pelilauta;
    
    /**
     * Taulukko, joka esittää laudan tilannetta
     */
    
    private int[][] lauta;
    
    /**
     * Luku, joka vastaa vuorossa olevan pelaajan väriä
     */
    
    private int vuorossaOlevanPelaajanVari;
    
    /**
     * Viimeksi pelattu siirto stringinä
     */
    
    private String viimeisinSiirto = "";
    
    /**
     * Tähän asti pelattujen siirtojen määrä
     */
    
    private int siirtojenMaara = 0;
    
    /**
     * Alussa laitettavien tasoituskivien määrä
     */
    
    private int tasoitusKivet;
    
    /**
     * Pelaajan syötteen lukeva Scanner
     */
    
    private Scanner input;
    
    /**
     * Metodi kysyy käyttäjältä luotavan pelilaudan pituuden, leveyden ja
     * tasoituskivien määrän, luo tämän laudan ja aloittaa sitten pelin
     */
    
    public Pelikayttoliittyma() {
        pituus = 0;
        leveys = 0;
        input = new Scanner(System.in);
        System.out.println("Anna laudan pituus: ");
        pituus = palautaKaypaPelaajanSyote(2,19, "Pituuden");
        System.out.println("Anna laudan leveys: ");
        leveys = palautaKaypaPelaajanSyote(2,19, "Leveyden");
        pelilauta = new Pelilauta(pituus,leveys);
        System.out.println("Anna tasoituskivien maara: ");
        tasoitusKivet = palautaKaypaPelaajanSyote(0,9, "Tasoituskivien maaran");
        if (tasoitusKivet > 0) vuorossaOlevanPelaajanVari = -1;
        else vuorossaOlevanPelaajanVari = 1;
        lauta = pelilauta.getLauta();
        pelaa();
    }
    
    /**
     * Metodi palauttaa sellaisen pelaajan syöttämän kokonaisluvun, joka on annettujen rajojen
     * välissä
     * 
     * @param alaraja Palautettavan luvun alaraja
     * @param ylaraja Palautettavan luvun yläraja
     * @param syotteenTyyppi Sen syötteen tyyppi, jota vastaavaa lukua ollaan palauttamassa
     * @return Pelaajan antama rajojen välissä oleva luku
     */
    
    public int palautaKaypaPelaajanSyote(int alaraja, int ylaraja, String syotteenTyyppi) {
        
        while (true) {
            String syote = input.nextLine();
            if (onKokonaisluku(syote)) {
               int luku = Integer.parseInt(syote);
                if (luku < alaraja || luku > ylaraja) {
                    System.out.println(syotteenTyyppi + " tulee olla kokonaisluku valilta " + alaraja + "-" + ylaraja + "!");
                }
                else return luku;
            }
            else System.out.println(syotteenTyyppi + " tulee olla kokonaisluku valilta " + alaraja + "-" + ylaraja + "!");
        }
    }
    
    @Override
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
    
    /**
     * Metodi ottaa pelaajilta syötteitä ja laittaa syötteitä vastaavia siirtoja
     * laudalle, jos syötteet ovat laillisia
     */
    
    public void pelaa() {
        System.out.println(pituus);
        System.out.println(leveys);
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
                System.out.println("\"pass\" passaa, \"undo\" peruu edellisen siirron ja \"luovuta\" luovuttaa.");
                if (viimeisinSiirto.length() > 0) System.out.println("Viimeisin siirto: " + viimeisinSiirto);
                if (vuorossaOlevanPelaajanVari == 1) System.out.print("Mustan vuoro: ");
                else System.out.print("Valkean vuoro: ");
                String siirto = input.nextLine();
                if (siirto.equals("luovuta")) { 
                    luovuta();
                    break;
                }
                if (siirto.equals("pass")) {
                    pelilauta.passaa();
                    vuorossaOlevanPelaajanVari = -vuorossaOlevanPelaajanVari;
                    if (viimeisinSiirto.equals("pass")) {
                        merkkaaKuolleetRyhmat();
                        break;
                    } 
                    else viimeisinSiirto = siirto;
                }
                else if (siirto.equals("undo")) {
                    peruEdellinenSiirto();
                }
                else if (koitaLaittaaSiirto(siirto,vuorossaOlevanPelaajanVari)) vuorossaOlevanPelaajanVari = -vuorossaOlevanPelaajanVari;
                else System.out.println("Siirto ei ole laillinen. Siirron tulee olla muotoa kirjain-numero, esimerkiksi a3 tai b5");    
            }
        }
    }
    
    public void luovuta() {
        if (vuorossaOlevanPelaajanVari == 1) System.out.println("Musta luovutti! Valkea voitti");
        else System.out.println("Valkea luovutti! Musta voitti");
        System.out.print("Tallennetaanko peli? k/e ");
        String syote = input.nextLine();
        if (syote.equals("k")) tallennaPeli();
        else new Alkuvalikko();
    }
    
    /**
     * Metodi ottaa pelaajilta syötteitä ja merkkaa syötteitä vastaavat laudan ryhmät kuolleiksi
     */
    
    public void merkkaaKuolleetRyhmat() {
        while (true) {
            tulostaLauta();
            System.out.println("\"pisteyta\" pisteyttaa pelin ja \"peruuta\" palaa takaisin peliin.");
            System.out.print("Anna kuolleeksi tai elavaksi merkittävän ryhman jonkun kiven koordinaatti: ");
            String syote = input.nextLine();
            if (syote.equals("pisteyta")) {
                pisteytaPeli();
                break;
            }
            if (syote.equals("peruuta")) {
                pelilauta.merkitseKaikkiRyhmatElaviksi();
                pelaa();
                break;
            }
            if (koitaMerkitaRyhmaKuolleeksi(syote)) { 
                System.out.println("Merkitty ryhma " + syote + " kuolleeksi.");
            }
            else if (koitaMerkitaRyhmaElavaksi(syote)) System.out.println("Merkitty ryhma " + syote + " elavaksi");
            else System.out.println("Syote oli virheellinen. Koordinaatin tulee olla muotoa kirjain-numero, esimerkiksi a3 tai b5.");
        }
}
    
    /**
     * Metodi pisteyttää pelilaudan tilanteen ja tulostaa voittajan ja voiton
     * määrän pisteinä
     */
    
    public void pisteytaPeli() {
        Pisteyttaja pisteyttaja = luoPisteyttaja(pelilauta);
        pisteyttaja.pisteyta();
        int mustanPisteet = pisteyttaja.getMustanPisteet();
        int valkeanPisteet = pisteyttaja.getValkeanPisteet();
        System.out.println("Mustan pisteet: " + mustanPisteet + " Valkean pisteet: " + valkeanPisteet);
        if (mustanPisteet > valkeanPisteet) System.out.println("Musta voitti " + (mustanPisteet-valkeanPisteet) + " pisteella.");
        else if (mustanPisteet < valkeanPisteet) System.out.println("Valkea voitti " + (valkeanPisteet-mustanPisteet) + " pisteella.");
        else System.out.println("Tasapeli!");
        System.out.println("Tallennetaanko peli? k/e ");
        String syote = input.nextLine();
        if (syote.equals("k")) tallennaPeli();
        else new Alkuvalikko();
    }
    
    /**
     * Metodi yrittää laittaa syötteenä annetun siirron laudalle
     * 
     * @param siirto Pelaajan syötteenä antamat siirron koordinaatit
     * @param vari Laitettavan siirron väri
     * @return true, jos siirron laittaminen onnistui, ja muuten false
     */
    
    public boolean koitaLaittaaSiirto(String siirto, int vari) {
        int[] syotettaVastaavatKoordinaatit = palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(siirto);
        if (syotettaVastaavatKoordinaatit[0] == -1) return false;
        int siirronYKoordinaatti = syotettaVastaavatKoordinaatit[0];
        int siirronXKoordinaatti = syotettaVastaavatKoordinaatit[1];
        pelilauta.laitaSiirto(siirronYKoordinaatti,siirronXKoordinaatti,vari);
        int uusiSiirtojenMaara = pelilauta.getSiirtojenMaara();
        if (uusiSiirtojenMaara > siirtojenMaara) {
            siirtojenMaara = uusiSiirtojenMaara;
            viimeisinSiirto = siirto;
            return true;
        }
        else return false;
    }
    
    /**
     * Metodi yrittää merkitä syötteenä annettuja koordinaatteja vastaavan ryhmän
     * kuolleeksi
     * 
     * @param syote Pelaajan syötteenä antamat laudan koordinaatit
     * @return true, jos kuolleeksi merkitseminen onnistui, ja false muuten
     */
    
    public boolean koitaMerkitaRyhmaKuolleeksi(String syote) {
        int[] syotettaVastaavatKoordinaatit = palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(syote);
        if (syotettaVastaavatKoordinaatit[0] == -1) return false;
        int syotteenYKoordinaatti = syotettaVastaavatKoordinaatit[0];
        int syotteenXKoordinaatti = syotettaVastaavatKoordinaatit[1];
        int ryhmanNumero = lauta[syotteenYKoordinaatti][syotteenXKoordinaatti];
        if (ryhmanNumero == 0) return false;
        if (pelilauta.onMerkittyKuolleeksi(ryhmanNumero)) return false;
        pelilauta.merkitseRyhmaKuolleeksi(lauta[syotteenYKoordinaatti][syotteenXKoordinaatti]);
        return true;
        
    }
    
    /**
     * Metodi yrittää merkitä syötteenä annettuja koordinaatteja vastaavan ryhmän eläväksi
     * 
     * @param syote Pelaajan syötteenä antamat laudan koordinaatit
     * @return true, jos eläväksi merkitseminen onnistui, ja false muuten
     */
    
    public boolean koitaMerkitaRyhmaElavaksi(String syote) {
        int[] syotettaVastaavatKoordinaatit = palautaPelaajanSyotettaVastaavatLaudanKoordinaatit(syote);
        if (syotettaVastaavatKoordinaatit[0] == -1) return false;
        int syotteenYKoordinaatti = syotettaVastaavatKoordinaatit[0];
        int syotteenXKoordinaatti = syotettaVastaavatKoordinaatit[1];
        int ryhmanNumero = lauta[syotteenYKoordinaatti][syotteenXKoordinaatti];
        if (ryhmanNumero == 0) return false;
        pelilauta.merkitseRyhmaElavaksi(lauta[syotteenYKoordinaatti][syotteenXKoordinaatti]);
        return true;
    }
    
    /**
     * Metodi luo tämänhetkistä pelilaudan tilannetta vastaavan pisteyttäjän ja
     * alustaa sen oikein
     * 
     * @param pelilauta Pelilauta, jota vastaavaa pisteyttäjää ollaan luomassa
     * @return Syötteenä annettua pelilautaa vastaava pisteyttäjä
     */
    
    public Pisteyttaja luoPisteyttaja(Pelilauta pelilauta) {
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.poistaKuolleetRyhmat();
        pisteyttaja.alustaLasketutKohdat();
        return pisteyttaja;
    }
    
    /**
     * Metodi peruu viimeksi laitetun siirron
     */
    
    public void peruEdellinenSiirto() {
        if (pelilauta.getSiirtojenMaara() > 0) {
            Pelilauta pelilaudanTilanneEdellisellaSiirrolla = pelilauta.palautaEdellisenSiirronTilanne();
            pelilauta = pelilaudanTilanneEdellisellaSiirrolla;
            lauta = pelilauta.getLauta();
            vuorossaOlevanPelaajanVari = -vuorossaOlevanPelaajanVari;
            if (pelilauta.getSiirtojenMaara() > 0) {
                int[] viimeisinSiirtoTaulukkona = pelilauta.getSiirrot().get(pelilauta.getSiirtojenMaara()-1);
                char[] viimeisimmanSiirronXKoordinaatti = Character.toChars(viimeisinSiirtoTaulukkona[1] + 65);
                if (viimeisinSiirtoTaulukkona[1] >= 8) viimeisimmanSiirronXKoordinaatti = Character.toChars(viimeisinSiirtoTaulukkona[1] + 66);
                int viimeisimmanSiirronYKoordinaatti = pituus - viimeisinSiirtoTaulukkona[0];
                viimeisinSiirto = viimeisimmanSiirronXKoordinaatti[0] + "" + viimeisimmanSiirronYKoordinaatti;
            } else viimeisinSiirto = "";
            siirtojenMaara--;
        }
    }
    
    /**
     * Metodi tallentaa pelin käyttäjän antamaan tiedostoon
     */
    
    public void tallennaPeli() {
        Pelitiedostonkasittelija tiedostonkasittelija = new Pelitiedostonkasittelija();
        while (true) {
            System.out.print("Anna tallennettavan tiedoston nimi: ");
            String syote = input.nextLine();
            if (!tiedostonkasittelija.tallennaPelattuPeli(pelilauta,syote)) {
                System.out.println("Virhe tiedoston tallentamisessa, anna toinen nimi tiedostolle");
            }
            else { 
                System.out.println("Peli tallennettu tiedostoon " + syote + "!");
                new Alkuvalikko();
                break;
            }
        }
    }
   


}
