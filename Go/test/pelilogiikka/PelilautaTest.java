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
public class PelilautaTest {
    
    public Pelilauta lauta;
    
    public PelilautaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        lauta = new Pelilauta(5,5,0);
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    @Test
    public void ryhmanLuomisenJalkeenRuudussaOnOikeaNumero() {
        lauta.luoUusiRyhma(3,3,1);
        lauta.luoUusiRyhma(1,1,1);
        int ryhmanNumero = lauta.getRyhmanNumero(1,1);
        assertEquals(2,ryhmanNumero);
    }
    
    @Test
    public void ryhmanLuomisenJalkeenRyhmallaOnOikeaMaaraVapauksia() {
        lauta.luoUusiRyhma(0,0,1);
        Ryhma ryhma = lauta.getRyhma(1);
        int vapauksienMaara = ryhma.getVapauksienMaara();
        assertEquals(2,vapauksienMaara);
    }
    
    @Test
    public void lisaaKivenVapaudetRyhmaanLisaaOikeanMaaranVapauksia() {
        lauta.luoUusiRyhma(0, 0, 1);
        int ryhmanNumero = lauta.getRyhmanNumero(0,0);
        lauta.lisaaKivenVapaudetRyhmaan(0,1,ryhmanNumero);
        Ryhma ryhma = lauta.getRyhma(ryhmanNumero);
        int vapauksienMaara = ryhma.getVapauksienMaara();
        assertEquals(4, vapauksienMaara);
    }
    
    @Test
    public void kivenRyhmaanLisaamisenJalkeenRyhmassaOnOikeaMaaraKivia() {
        lauta.luoUusiRyhma(1, 1, 1);
        lauta.luoUusiRyhma(2,2,2);
        int ryhmanNumero = lauta.getRyhmanNumero(2, 2);
        lauta.lisaaKiviRyhmaan(1,2,ryhmanNumero);
        int kivienMaara = lauta.getRyhma(ryhmanNumero).getKivienMaara();
        assertEquals(2,kivienMaara);
    }
    
    @Test
    public void kivenRyhmaanLisaamisenJalkeenRyhmallaOnOikeaMaaraVapauksia() {
        lauta.luoUusiRyhma(0,0,2);
        int ryhmanNumero = lauta.getRyhmanNumero(0, 0);
        lauta.lisaaKiviRyhmaan(0,1,ryhmanNumero);
        lauta.lisaaKiviRyhmaan(1,1,ryhmanNumero);
        int vapauksienMaara = lauta.getRyhma(ryhmanNumero).getVapauksienMaara();
        assertEquals(4,vapauksienMaara);
    }
    
    @Test
    public void yhdistetyllaRyhmallaOnOikeaMaaraVapauksiaYhdistamisenJalkeen() {
        lauta.luoUusiRyhma(1,1,1);
        lauta.luoUusiRyhma(0,0,1);
        int ryhmanNumero = lauta.getRyhmanNumero(1, 1);
        int ryhmanNumero2 = lauta.getRyhmanNumero(0,0);
        lauta.lisaaKiviRyhmaan(0,1,ryhmanNumero2);
        lauta.yhdistaKaksiRyhmaa(ryhmanNumero,ryhmanNumero2);
        Ryhma ryhma = lauta.getRyhma(ryhmanNumero);
        int vapauksienMaara = ryhma.getVapauksienMaara();
        assertEquals(4,vapauksienMaara);
    }
    
    @Test
    public void poistaRyhmaLaudaltaPoistaaRyhmanLaudalta() {
        lauta.luoUusiRyhma(1,1,1);
        lauta.luoUusiRyhma(0,0,1);
        int ryhmanNumero = lauta.getRyhmanNumero(1, 1);
        int ryhmanNumero2 = lauta.getRyhmanNumero(0,0);
        lauta.lisaaKiviRyhmaan(0,1,ryhmanNumero2);
        lauta.yhdistaKaksiRyhmaa(ryhmanNumero,ryhmanNumero2);
        lauta.poistaRyhmaLaudalta(ryhmanNumero);
        boolean poisti = true;
        for (int i = 0; i<lauta.getPituus(); i++) {
            for (int j = 0; j<lauta.getLeveys(); j++ ) {
                if (lauta.getRyhmanNumero(i,j) == ryhmanNumero) poisti = false;
            }
        }
        assertEquals(true,poisti);
    }
    
    @Test
    public void lisaaPoistettavanKivenVihollisnaapureilleVapaudeksiPoistettavaKiviLisaaNaapureidenVapaudetOikein() {
        lauta.luoUusiRyhma(1,1,1);
        lauta.luoUusiRyhma(0,0,1);
        int ryhmanNumero = lauta.getRyhmanNumero(1, 1);
        int ryhmanNumero2 = lauta.getRyhmanNumero(0,0);
        lauta.lisaaKiviRyhmaan(0,1,ryhmanNumero2);
        lauta.luoUusiRyhma(1,2,-1);
        int ryhmanNumero3 = lauta.getRyhmanNumero(1,2);
        lauta.lisaaKiviRyhmaan(0,2,ryhmanNumero3);
        lauta.yhdistaKaksiRyhmaa(ryhmanNumero,ryhmanNumero2);
        lauta.poistaRyhmaLaudalta(ryhmanNumero);
        Ryhma ryhma = lauta.getRyhma(ryhmanNumero3);
        int vapauksienMaara = ryhma.getVapauksienMaara();
        assertEquals(5,vapauksienMaara);
    }
    
    @Test
    public void siirtoSyoRyhmiaPalauttaaTrueJosSiirtoSyoRyhmia() {
        lauta.luoUusiRyhma(0,1,1);
        lauta.luoUusiRyhma(0,0,-1);
        boolean siirtoSyoRyhmia = lauta.siirtoSyoRyhmia(1,0,1);
        assertEquals(true,siirtoSyoRyhmia);
    }
    
    @Test
    public void siirtoSyoRyhmiaPalauttaaFalseJosSiirtoEiSyoRyhmia() {
        lauta.luoUusiRyhma(0,0,-1);
        boolean siirtoSyoRyhmia = lauta.siirtoSyoRyhmia(1,0,1);
        assertEquals(false,siirtoSyoRyhmia);
    }
    
    @Test
    public void siirtoEiSyoRyhmiaMuttaOnLaillinenPalauttaaTrueJosSiirtoOnLaillinen() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,2,1);
        lauta.luoUusiRyhma(1, 0, -1);
        lauta.lisaaKiviRyhmaan(1,1,3);
        boolean siirtoOnLaillinen = lauta.siirtoEiSyoRyhmiaMuttaOnLaillinen(0,1,1);
        assertEquals(true,siirtoOnLaillinen);
        
    }
    
    @Test
    public void siirtoEiSyoRyhmiaMuttaOnLaillinenPalauttaaFalseJosSiirtoEiOleLaillinen() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,2,1);
        lauta.luoUusiRyhma(1,0,-1);
        lauta.lisaaKiviRyhmaan(1,1,3);
        lauta.lisaaKiviRyhmaan(1,2,3);
        lauta.lisaaKiviRyhmaan(0,3,3);
        boolean siirtoOnLaillinen = lauta.siirtoEiSyoRyhmiaMuttaOnLaillinen(0,1,1);
        assertEquals(false,siirtoOnLaillinen);
    }
    
    @Test
    public void siirtoOnKoPalauttaaTrueJosSiirtoAiheuttaaKon() {
        lauta.luoUusiRyhma(0,1,1);
        lauta.luoUusiRyhma(1,0,1);
        lauta.luoUusiRyhma(1,1,-1);
        lauta.luoUusiRyhma(0,2,-1);
        boolean siirtoOnKo = lauta.siirtoOnKo(0,0,-1);
        assertEquals(true, siirtoOnKo);
    }
    
    @Test
    public void siirtoOnKoPalauttaaFalseJosSiirtoSyoYhdenKivenRyhmiaMuttaEiAiheutaKota() {
        lauta.luoUusiRyhma(0,1,1);
        lauta.luoUusiRyhma(1,0,1);
        lauta.luoUusiRyhma(1,1,-1);
        lauta.luoUusiRyhma(0,2,-1);
        lauta.luoUusiRyhma(2,0,-1);
        boolean siirtoOnKo = lauta.siirtoOnKo(0,0,-1);
        assertEquals(false,siirtoOnKo);
    }
    
    @Test
    public void syoVastustajanRyhmatYmpariltaSyoVastustajanRyhmatYmparilta() {
        lauta.luoUusiRyhma(0,1,1);
        lauta.luoUusiRyhma(2, 1, 1);
        lauta.luoUusiRyhma(3,0,1);
        lauta.luoUusiRyhma(1,2,1);
        lauta.luoUusiRyhma(0,0,-1);
        lauta.luoUusiRyhma(1,1,-1);
        lauta.luoUusiRyhma(2,0,-1);
        lauta.syoVastustajanRyhmatYmparilta(1,0,1);
        boolean soiVastustajanKivet = true;
        if (lauta.getRyhmanNumero(0,0) != 0) soiVastustajanKivet = false;
        if (lauta.getRyhmanNumero(1,1) != 0) soiVastustajanKivet = false;
        if (lauta.getRyhmanNumero(2,0) != 0) soiVastustajanKivet = false;
        assertEquals(true,soiVastustajanKivet);
    }
    
    @Test
    public void syoVastustajanRyhmatYmpariltaEiSyoOmiaRyhmia() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.lisaaKiviRyhmaan(0,1,1);
        lauta.luoUusiRyhma(1,1,-1);
        lauta.luoUusiRyhma(1,2,1);
        lauta.luoUusiRyhma(2,1,1);
        lauta.syoVastustajanRyhmatYmparilta(1,0,1);
        boolean soiOmatKivet = false;
        if (lauta.getRyhmanNumero(0,0) == 0) soiOmatKivet = true;
        assertEquals(false,soiOmatKivet);
    }
    
    @Test
    public void yhdistaKiviJohonkinYmparoivaanRyhmaanYhdistaaKivenJohonkinYmparoivaanRyhmaan() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,1,1);
        lauta.yhdistaKiviJohonkinYmparoivaanRyhmaan(1,1,1);
        boolean yhdisti = true;
        if (lauta.getRyhmanNumero(1,1) != 1 && lauta.getRyhmanNumero(1,1) != 2) yhdisti = false;
        assertEquals(true,yhdisti);
    }
    
    @Test
    public void yhdistaKiviJohonkinYmparoivaanRyhmaanEiYhdistaKiveaErivariseenRyhmaan() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,1,1);
        lauta.yhdistaKiviJohonkinYmparoivaanRyhmaan(1,1,-1);
        boolean yhdistiVaaraanRyhmaan = false;
        if (lauta.getRyhmanNumero(1,1) != 0) yhdistiVaaraanRyhmaan = true;
        assertEquals(false,yhdistiVaaraanRyhmaan);
    }
    
    @Test
    public void yhdistaKiviJohonkinYmparoivaanRyhmaanPalauttaaTrueJosYhdistaminenOnnistui() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,1,1);
        boolean onnistui = lauta.yhdistaKiviJohonkinYmparoivaanRyhmaan(1,1,1);
        assertEquals(true,onnistui);
    }
    
    @Test
    public void yhdistaKiviJohonkinYmparoivaanRyhmaanPalauttaaFalseJosEiYhdistettyMihinkaanRyhmaan() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,1,1);
        boolean onnistui = lauta.yhdistaKiviJohonkinYmparoivaanRyhmaan(1,1,-1);
        assertEquals(false,onnistui);
    }
    
    @Test
    public void yhdistaRyhmaYmparoiviinRyhmiinYhdistaaRyhmanYmparoiviinRyhmiin() {
        lauta.luoUusiRyhma(0,0,1);
        lauta.luoUusiRyhma(0,2,1);
        lauta.luoUusiRyhma(1,1,1);
        lauta.luoUusiRyhma(0,1,1);
        lauta.yhdistaRyhmaYmparoiviinRyhmiin(0,1,1);
        boolean yhdisti = false;
        if (lauta.getRyhmanNumero(0,0) == 4 && lauta.getRyhmanNumero(0,2) == 4 && lauta.getRyhmanNumero(1,1) == 4 && lauta.getRyhmanNumero(0,1) == 4)
            yhdisti = true;
        assertEquals(true,yhdisti);
    }
    
}
