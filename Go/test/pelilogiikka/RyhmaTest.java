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

/**
 *
 * @author Prod
 */
public class RyhmaTest {
    
    Ryhma ryhma1;
    Ryhma ryhma2;
    
    public RyhmaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ryhma1 = new Ryhma(1);
        ryhma2 = new Ryhma(1);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void RyhmaanVoiLisataSamanKivenVainKerran() {
        ryhma1.lisaaKivi(1,1);
        ryhma1.lisaaKivi(1,1);
        int kivienMaara = ryhma1.getKivienMaara();
        assertEquals(1,kivienMaara);
    }
    
    @Test
    public void RyhmaanVoiLisataSamanVapaudenVainKerran() {
        ryhma1.lisaaVapaus(1,1);
        ryhma1.lisaaVapaus(1,1);
        int vapauksienMaara = ryhma1.getVapauksienMaara();
        assertEquals(1,vapauksienMaara);
    }
    
    
    
    @Test
    public void kahdenRyhmanYhdisteessaOnOikeaMaaraKivia() {
        ryhma1.lisaaKivi(0,0);
        ryhma1.lisaaKivi(0, 1);
        ryhma2.lisaaKivi(1,1);
        ryhma2.lisaaKivi(1,0);
        ryhma1.yhdistaToiseenRyhmaan(ryhma2);
        int kivienMaara = ryhma1.getKivienMaara();
        assertEquals(4,kivienMaara);
    }
    
    @Test
    public void kahdenRyhmanYhdisteessaOnOikeaMaaraVapauksiaJosKummallakaanRyhmallaEiOleYhteisiaVapauksia() {
        ryhma1.lisaaKivi(0,1);
        ryhma1.lisaaVapaus(1, 1);
        ryhma1.lisaaVapaus(0,2);
        ryhma1.lisaaVapaus(0,0);
        ryhma2.lisaaKivi(1,1);
        ryhma2.lisaaVapaus(0, 1);
        ryhma2.lisaaVapaus(1,2);
        ryhma2.lisaaVapaus(2,1);
        ryhma2.lisaaVapaus(1,0);
        ryhma1.yhdistaToiseenRyhmaan(ryhma2);
        int vapauksienMaara = ryhma1.getVapauksienMaara();
        assertEquals(5,vapauksienMaara);
    }
    
    @Test
    public void kahdenRyhmanYhdisteessaOnOikeaMaaraVapauksiaJosRyhmillaOnYhteisiaVapauksia() {
        ryhma1.lisaaKivi(0,0);
        ryhma1.lisaaVapaus(0,1);
        ryhma1.lisaaVapaus(1, 0);
        ryhma2.lisaaKivi(0,1);
        ryhma2.lisaaKivi(1,1);
        ryhma2.lisaaVapaus(1,0);
        ryhma2.lisaaVapaus(0,2);
        ryhma2.lisaaVapaus(1,2);
        ryhma2.lisaaVapaus(2,1);
        ryhma1.yhdistaToiseenRyhmaan(ryhma2);
        int vapauksienMaara = ryhma1.getVapauksienMaara();
        assertEquals(4,vapauksienMaara);
    }
    
}
