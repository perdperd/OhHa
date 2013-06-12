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
    public void lataaTallennettuPeliLataaOikeanTiedoston() {
        
    }
   
}
