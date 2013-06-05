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
    }
    
    @Test
    public void pelitiedostonkasittelijaLukeeAiemminTallennetunPelinSiirrotOikein() {
        Pelilauta pelilauta = new Pelilauta(5,5);
        pelilauta.laitaSiirto(1,1,1);
        pelilauta.laitaSiirto(2,1,-1);
        pelilauta.passaa();
        kasittelija.tallennaPelattuPeli(pelilauta,"testi");
        kasittelija.lataaTallennetunPelinSiirrot("testi");
        ArrayList<int[]> ladatutSiirrot = kasittelija.getLadatunPelinSiirrot();
        int[] ekaSiirto = ladatutSiirrot.get(0);
        int[] tokaSiirto = ladatutSiirrot.get(1);
        int[] kolmasSiirto = ladatutSiirrot.get(2);
        boolean ladatutSiirrotOvatOikeat = true;
        if (ekaSiirto[0] != 1 || ekaSiirto[1] != 1 || ekaSiirto[2] != 1) ladatutSiirrotOvatOikeat = false;
        if (tokaSiirto[0] != 2 || tokaSiirto[1] != 1 || tokaSiirto[2] != -1) ladatutSiirrotOvatOikeat = false;
        if (kolmasSiirto[0] != -1 || kolmasSiirto[1] != -1 || kolmasSiirto[2] != 0) ladatutSiirrotOvatOikeat = false;
        assertEquals(true, ladatutSiirrotOvatOikeat);
    }
   
}
