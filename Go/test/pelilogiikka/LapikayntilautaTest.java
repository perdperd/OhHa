/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelilogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import tiedostonkasittely.*;
import java.util.ArrayList;

/**
 *
 * @author Prod
 */
public class LapikayntilautaTest {
    
    Lapikayntilauta lapikayntilauta;
    
    public LapikayntilautaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Pelitiedostonkasittelija kasittelija = new Pelitiedostonkasittelija();
        kasittelija.lataaTallennettuPeli("esimerkkipeli");
        lapikayntilauta = new Lapikayntilauta(kasittelija);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void konstruktoriLuoLapikayntilaudanOikein() {
        int pituus = lapikayntilauta.getPituus();
        int leveys = lapikayntilauta.getLeveys();
        ArrayList<int[]> siirrot = lapikayntilauta.getSiirrot();
        int[] siirto1 = siirrot.get(0);
        int[] siirto2 = siirrot.get(1);
        int[] siirto3 = siirrot.get(2);
        boolean loiOikein = true;
        if (pituus != 10 || leveys != 11) loiOikein = false;
        if (siirto1[0] != 2 || siirto1[1] != 2 || siirto1[2] != 1) loiOikein = false;
        if (siirto2[0] != 8 || siirto2[1] != 7 || siirto2[2] != -1) loiOikein = false;
        if (siirto3[0] != -1 || siirto3[1] != -1 || siirto3[2] != 0) loiOikein = false;
        assertEquals(true, loiOikein);  
    }
    
    @Test
    public void kopioiLaudanTilanneKopioiLaudanTilanteenOikein() {
        Pelilauta pelilauta = new Pelilauta(10,11);
        pelilauta.laitaSiirto(2,2,1);
        pelilauta.laitaSiirto(3,3,-1);
        lapikayntilauta.kopioiLaudanTilanne(0, pelilauta);
        String[][] laudanTilanne = lapikayntilauta.getLaudanTilanne(0);
        boolean tilanneKopioituOikein = true;
        for (int i = 0; i<lapikayntilauta.getPituus(); i++) {
            for (int j = 0; j<lapikayntilauta.getLeveys(); j++) {
                if (i == 2 && j == 2) {
                    if (!laudanTilanne[i][j].equals("X")) tilanneKopioituOikein = false;
                }
                else if (i == 3 && j == 3) {
                    if (!laudanTilanne[i][j].equals("O")) tilanneKopioituOikein = false;
                }
                else if (!laudanTilanne[i][j].equals(".")) tilanneKopioituOikein = false;
            }
        }
        assertEquals(true, tilanneKopioituOikein);
    }
    
    @Test
    public void luoLapikaytavanPelinLaudanTilanteetPalauttaaTrueEsimerkkipelinTapauksessa() {
        Pelilauta pelilauta = new Pelilauta(10,11);
        assertEquals(true, lapikayntilauta.luoLapikaytavanPelinLaudanTilanteet(pelilauta));
    }
    
    @Test
    public void luoLapikaytavanPelinLaudanTilanteetLuoEsimerkkipelinTilanteetOikein() {
        String[][] laudanTilanne1 = lapikayntilauta.getLaudanTilanne(0);
        String[][] laudanTilanne2 = lapikayntilauta.getLaudanTilanne(1);
        String[][] laudanTilanne3 = lapikayntilauta.getLaudanTilanne(2);
        boolean tilanteetLuotuOikein = true;
        for (int i = 0; i<lapikayntilauta.getPituus(); i++) {
            for (int j = 0; j<lapikayntilauta.getLeveys(); j++) {
                if (!laudanTilanne1[i][j].equals(".")) tilanteetLuotuOikein = false;
                if (i == 2 && j == 2) {
                    if (!laudanTilanne2[i][j].equals("X") || !laudanTilanne3[i][j].equals("X")) tilanteetLuotuOikein = false;
                }
                else if (!laudanTilanne2[i][j].equals(".")) tilanteetLuotuOikein = false;
                if (i == 8 && j == 7) {
                    if (!laudanTilanne3[i][j].equals("O")) tilanteetLuotuOikein = false;
                }
                else if (i != 2 || j != 2) {
                   if(!laudanTilanne3[i][j].equals(".")) tilanteetLuotuOikein = false;
                }
            }
        }
        assertEquals(true, tilanteetLuotuOikein);
    }
    
    @Test
    public void getSiirronNumeroPalauttaaMiinusYhdenJosSiirtoaEiPelattuLadatussaPelissa() {
        int[] koordinaatit = {4,4};
        int siirronNumero = lapikayntilauta.getSiirronNumero(koordinaatit);
        assertEquals(-1,siirronNumero);
    }
    
    @Test
    public void getSiirronNumeroPalauttaaOikeanSiirronNumeronJosSiirtoPelattiinLadatussaPelissa() {
        int[] koordinaatit = {8,7};
        int siirronNumero = lapikayntilauta.getSiirronNumero(koordinaatit);
        assertEquals(2,siirronNumero);
    }
}
