/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 * Luokka on runko tekstipohjaiselle go-käyttöliittymälle
 * 
 * @author Juuso Nyyssönen
 */
public class Tekstikayttoliittyma {
    
    /**
     * Pelilaudan pituus
     */
    
    protected int pituus;
    
    /**
     * Pelilaudan leveys
     */
    
    protected int leveys;
    
    public Tekstikayttoliittyma() {
        
    }
    
    public int getPituus() {
        return pituus;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public void setPituus(int uusiPituus) {
        pituus = uusiPituus;
    }
    
    public void setLeveys(int uusiLeveys) {
        leveys = uusiLeveys;
    }
    
    /**
     * Metodi tulostaa tämänhetkisen pelilaudan tilanteen
     */
    
    public void tulostaLauta() {
        tulostaKirjaimet();
        for (int i = 0; i<pituus; i++) {
            tulostaRivi(i);
        }
        tulostaKirjaimet();
    }
    
    /**
     * Metodi tulostaa syötteenä annetun laudan rivin
     * 
     * @param rivi Se laudan rivi, jota ollaan tulostamassa
     */
    
    public void tulostaRivi(int rivi) {
        
    }
    
    /**
     * Metodi tulostaa laudan sarakkeiden koordinaatteja vastaavat kirjaimet
     */
    
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
    
    /**
     * Metodi tutkii, onko käyttäjän syöte kokonaisluku
     * 
     * @param syote Käyttäjän antama syöte
     * @return true, jos annettu syöte on kokonaisluku ja muuten false
     */
    
    public boolean onKokonaisluku(String syote) {
        try { 
        Integer.parseInt(syote); 
    } catch(NumberFormatException e) { 
        return false; 
    }
        return true;
    }
    
    /**
     * Metodi palauttaa pelaajan syötettä vastaavat laudan koordinaatit, jos syöte
     * ylipäätänsä vastaa jotain laudan koordinaatteja
     * 
     * @param syote Pelaajan antama syöte
     * @return Kaksipaikkainen taulukko, jonka ensimmäinen luku on syötettä vastaava laudan rivi
     * ja toinen syötettä vastaava laudan sarake. Jos syote ei vastannut mitään, kummatkin näistä
     * ovat -1
     */
    
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
    
}
