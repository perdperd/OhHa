/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Juuso Nyyssönen
 */
public class TekstikayttoliittymaTest {
    
    Tekstikayttoliittyma kayttoliittyma;
    
    public TekstikayttoliittymaTest() {
    }
    
    @Before
    public void setUp() {
        kayttoliittyma = new Tekstikayttoliittyma();
        kayttoliittyma.setPituus(10);
        kayttoliittyma.setLeveys(11);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void onKokonaisLukuPalauttaaTrueKunSyotteenaAnnetaanKokonaisluku() {
        boolean onKokonaisluku = kayttoliittyma.onKokonaisluku("10");
        assertEquals(true, onKokonaisluku);
    }
    
    @Test
    public void onKokonaisLukuPalauttaaFalseKunSyotteenaEiAnnetaKokonaislukua() {
        boolean onKokonaisluku = kayttoliittyma.onKokonaisluku("en oo kokonaisluku");
        assertEquals(false,onKokonaisluku);
    }
    
    @Test
    public void palautaPelaajanSyotettaVastaavatLaudanKoordinaatitPalauttaaOikeatKoordinaatitKunSyoteOnOikeassaMuodossaJaEiMeneYliLaudan() {
        int[] koordinaatit = kayttoliittyma.palautaPelaajanSyotettaVastaavatLaudanKoordinaatit("j10");
        assertEquals(0, koordinaatit[0]);
        assertEquals(8, koordinaatit[1]);
    }
    
    @Test
    public void palautaPelaajanSyotettaVastaavatLaudanKoordinaatitPalauttaaNegatiivisetLuvutKunSyoteOnOikeassaMuodossaMuttaMeneeYliLaudan() {
        int[] koordinaatit = kayttoliittyma.palautaPelaajanSyotettaVastaavatLaudanKoordinaatit("t1");
        assertEquals(-1, koordinaatit[0]);
    }
    
    @Test
    public void palautaPelaajanSyotettaVastaavatLaudanKoordinaatitPalauttaaNegatiivisetLuvutKunSyoteOnVaarassaMuodossa() {
        int[] koordinaatit = kayttoliittyma.palautaPelaajanSyotettaVastaavatLaudanKoordinaatit("3a");
        assertEquals(-1, koordinaatit[0]);
    }
    
    @Test
    public void palautaPelaajanSyotettaVastaavatLaudanKoordinaatitPalauttaaNegatiivisetLuvutKunSyotteenEnsimmainenKoordinaattiOnI() {
        int[] koordinaatit = kayttoliittyma.palautaPelaajanSyotettaVastaavatLaudanKoordinaatit("i7");
        assertEquals(-1,koordinaatit[0]);
    }
}
