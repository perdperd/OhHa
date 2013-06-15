/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiedostonkasittely;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import pelilogiikka.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Juuso Nyyssönen
 */
public class PelitiedostonkasittelijaTest {
    
    Pelitiedostonkasittelija kasittelija;
    
    public PelitiedostonkasittelijaTest() {
    }
    
    @Before
    public void setUp() {
        kasittelija = new Pelitiedostonkasittelija();
    }
    
    @After
    public void tearDown() {
        File tallennettuPeli = new File("testi");
        tallennettuPeli.delete();
    }

    @Test
    public void tallennaPelattuPeliLuoOikeanNimisenTiedoston() {
        Pelilauta pelilauta = new Pelilauta(5,5);
        pelilauta.laitaSiirto(1,1,1);
        kasittelija.tallennaPelattuPeli(pelilauta,"testi");
        File tallennettuTiedosto = new File("testi");
        boolean tiedostoOnOlemassa = tallennettuTiedosto.exists();
        assertEquals(true, tiedostoOnOlemassa);
    }
    
    @Test
    public void lataaTallennettuPeliLataaValmiinEsimerkkitiedostonPelilaudanPituudenJaLeveydenOikein() {
        kasittelija.lataaTallennettuPeli("esimerkkipeli");
        int pituus = kasittelija.getLadatunPelinLaudanPituus();
        int leveys = kasittelija.getLadatunPelinLaudanLeveys();
        boolean latasiOikein = true;
        if (pituus != 10) latasiOikein = false;
        if (leveys != 11) latasiOikein = false;
        assertEquals(true, latasiOikein);
    }
    
    @Test
    public void lataaTallennettuPeliLataaValmiinEsimerkkitiedostonSiirrotOikein() {
        kasittelija.lataaTallennettuPeli("esimerkkipeli");
        ArrayList<int[]> siirrot = kasittelija.getLadatunPelinSiirrot();
        int[] siirto1 = siirrot.get(0);
        int[] siirto2 = siirrot.get(1);
        int[] siirto3 = siirrot.get(2);
        boolean latasiOikein = true;
        if (siirrot.size() != 3) latasiOikein = false;
        if (siirto1[0] != 2 || siirto1[1] != 2 || siirto1[2] != 1) latasiOikein = false;
        if (siirto2[0] != 8 || siirto2[1] != 7 || siirto2[2] != -1) latasiOikein = false;
        if (siirto3[0] != -1 || siirto3[1] != -1 || siirto3[2] != 0) latasiOikein = false;
        assertEquals(true, latasiOikein);
    }
    
    @Test
    public void tallennaPelattuPeliTallentaaPelinOikeassaMuodossa() {
         Pelilauta pelilauta = new Pelilauta(5,4);
        pelilauta.laitaSiirto(1,1,1);
        pelilauta.laitaSiirto(2,1,-1);
        pelilauta.passaa();
        kasittelija.tallennaPelattuPeli(pelilauta,"testi");
        kasittelija.lataaTallennettuPeli("testi");
        int pituus = kasittelija.getLadatunPelinLaudanPituus();
        int leveys = kasittelija.getLadatunPelinLaudanLeveys();
        ArrayList<int[]> ladatutSiirrot = kasittelija.getLadatunPelinSiirrot();
        int[] ekaSiirto = ladatutSiirrot.get(0);
        int[] tokaSiirto = ladatutSiirrot.get(1);
        int[] kolmasSiirto = ladatutSiirrot.get(2);
        boolean tallennettuOikein = true;
        if (pituus != 5 || leveys != 4) tallennettuOikein = false;
        if (ekaSiirto[0] != 1 || ekaSiirto[1] != 1 || ekaSiirto[2] != 1) tallennettuOikein = false;
        if (tokaSiirto[0] != 2 || tokaSiirto[1] != 1 || tokaSiirto[2] != -1) tallennettuOikein = false;
        if (kolmasSiirto[0] != -1 || kolmasSiirto[1] != -1 || kolmasSiirto[2] != 0) tallennettuOikein = false;
        assertEquals(true, tallennettuOikein);
    }
}
