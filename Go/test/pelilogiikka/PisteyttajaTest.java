
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
public class PisteyttajaTest {
    
    public Pelilauta pelilauta;
    
    public PisteyttajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {  
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        pelilauta = new Pelilauta(5,5);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void kopioiLaudanTilanneKopioiLaudanTilanteenOikein() {
        pelilauta.laitaSiirto(0,0,1);
        pelilauta.laitaSiirto(0,1,1);
        pelilauta.laitaSiirto(2,2,-1);
        pelilauta.laitaSiirto(3,3,-1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        boolean kopioiOikein = true;
        int[][] lauta = pisteyttaja.getLauta();
        if (lauta[0][0] != 1 || lauta [0][1] != 1) kopioiOikein = false;
        if (lauta[2][2] != -2 || lauta[3][3] != -3) kopioiOikein = false;
        assertEquals(true,kopioiOikein);
    }
    
    @Test
    public void poistaKuolleetRyhmatPoistaaKuolleetRyhmatJaEiMuita() {
        pelilauta.laitaSiirto(0,0,1);
        pelilauta.laitaSiirto(0,1,1);
        pelilauta.laitaSiirto(0,4,-1);
        pelilauta.laitaSiirto(2,2,-1);
        pelilauta.laitaSiirto(2,3,-1);
        pelilauta.merkitseRyhmaKuolleeksi(1);
        pelilauta.merkitseRyhmaKuolleeksi(-3);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.poistaKuolleetRyhmat();
        int[][] lauta = pisteyttaja.getLauta();
        boolean poistiKuolleetRyhmatJaVainNe = true;
        if (lauta[0][0] != 0 || lauta[0][1] != 0) poistiKuolleetRyhmatJaVainNe = false;
        if (lauta[0][4] == 0) poistiKuolleetRyhmatJaVainNe = false;
        if (lauta[2][2] != 0 || lauta[2][3] != 0) poistiKuolleetRyhmatJaVainNe = false;
        assertEquals(true,poistiKuolleetRyhmatJaVainNe);
    }
    
    @Test
    public void poistaKuolleetRyhmatLisaaOikeanMaaranPisteita() {
        pelilauta.laitaSiirto(0,0,1);
        pelilauta.laitaSiirto(0,1,1);
        pelilauta.laitaSiirto(0,4,-1);
        pelilauta.laitaSiirto(2,2,-1);
        pelilauta.laitaSiirto(2,3,-1);
        pelilauta.laitaSiirto(2,4,-1);
        pelilauta.merkitseRyhmaKuolleeksi(1);
        pelilauta.merkitseRyhmaKuolleeksi(-3);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.poistaKuolleetRyhmat();
        int mustanPisteet = pisteyttaja.getMustanPisteet();
        int valkeanPisteet = pisteyttaja.getValkeanPisteet();
        boolean pisteetOikein = true;
        if (mustanPisteet != 3 || valkeanPisteet != 2) pisteetOikein = false;
        assertEquals(true,pisteetOikein);
    }
    
    @Test
    public void alustaLasketutKohdatLaittaaVainVapaidenKohtienArvoksiTrue() {
        pelilauta.laitaSiirto(0,0,1);
        pelilauta.laitaSiirto(0,1,1);
        pelilauta.laitaSiirto(2,2,-1);
        pelilauta.laitaSiirto(3,3,-1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        int[][] lauta = pisteyttaja.getLauta();
        boolean[][] lasketutKohdat = pisteyttaja.getLasketutKohdat();
        boolean alustettuOikein = true;
        for (int i = 0; i<pisteyttaja.getPituus(); i++) {
            for (int j = 0; j<pisteyttaja.getLeveys(); j++) {
                if (lauta[i][j] == 0 && lasketutKohdat[i][j] == true) { 
                    alustettuOikein = false;
                    break;
                }
                else if (lauta[i][j] != 0 && lasketutKohdat[i][j] == false) {
                    alustettuOikein = false;
                    break;
                }
            }
        }
        assertEquals(true,alustettuOikein);
    }
    
    @Test
    public void pisteytaAlueLaskeeTyhjanLaudanOikein() {
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        int alueenKoko = pisteyttaja.pisteytaAlue(0,0);
        assertEquals(0,alueenKoko);
    }
    
    @Test
    public void pisteytaAluePisteyttaaLoppuunPelatunPelinAlueetOikein() {
        pelilauta.laitaSiirto(2,2,1);
        pelilauta.laitaSiirto(3,2,-1);
        pelilauta.laitaSiirto(2,3,1);
        pelilauta.laitaSiirto(3,3,-1);
        pelilauta.laitaSiirto(2,4,1);
        pelilauta.laitaSiirto(3,4,-1);
        pelilauta.laitaSiirto(2,1,1);
        pelilauta.laitaSiirto(3,1,-1);
        pelilauta.laitaSiirto(2,0,1);
        pelilauta.laitaSiirto(3,0,-1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        int valkeanAlueenKoko = pisteyttaja.pisteytaAlue(4,0);
        int mustanAlueenKoko = pisteyttaja.pisteytaAlue(0,0);
        boolean alueetOikein = true;
        if (valkeanAlueenKoko != -5) alueetOikein = false;
        if (mustanAlueenKoko != 10) alueetOikein = false;
        assertEquals(true,alueetOikein);
    }
    
    @Test
    public void pisteytaAluePalauttaaNollaJosKyseessaEiOleKenenkaanAlue() {
        pelilauta.laitaSiirto(2,2,1);
        pelilauta.laitaSiirto(3,1,-1);
        pelilauta.laitaSiirto(3,2,1);
        pelilauta.laitaSiirto(2,1,-1);
        pelilauta.laitaSiirto(2,3,1);
        pelilauta.laitaSiirto(1,1,-1);
        pelilauta.laitaSiirto(1,3,1);
        pelilauta.laitaSiirto(0,1,-1);
        pelilauta.laitaSiirto(0,3,1);
        pelilauta.laitaSiirto(4,1,-1);
        pelilauta.laitaSiirto(4,2,1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        int alueenKoko = pisteyttaja.pisteytaAlue(0,2);
        assertEquals(0,alueenKoko);
    }
    
    @Test
    public void pisteytaPisteyttaaTyhjanLaudanOikein() {
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        pisteyttaja.pisteyta();
        int mustanPisteet = pisteyttaja.getMustanPisteet();
        int valkeanPisteet = pisteyttaja.getValkeanPisteet();
        boolean pisteetOikein = true;
        if (mustanPisteet != 0 || valkeanPisteet != 0) pisteetOikein = false;
        assertEquals(true,pisteetOikein);
    }
    
    @Test
    public void pisteytaPisteyttaaLoppuunPelatunPelinOikein() {
        pelilauta.laitaSiirto(2,2,1);
        pelilauta.laitaSiirto(3,2,-1);
        pelilauta.laitaSiirto(2,3,1);
        pelilauta.laitaSiirto(3,3,-1);
        pelilauta.laitaSiirto(2,4,1);
        pelilauta.laitaSiirto(3,4,-1);
        pelilauta.laitaSiirto(2,1,1);
        pelilauta.laitaSiirto(3,1,-1);
        pelilauta.laitaSiirto(2,0,1);
        pelilauta.laitaSiirto(3,0,-1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        pisteyttaja.pisteyta();
        int mustanPisteet = pisteyttaja.getMustanPisteet();
        int valkeanPisteet = pisteyttaja.getValkeanPisteet();
        boolean pisteetOikein = true;
        if (mustanPisteet != 10 || valkeanPisteet != 5) pisteetOikein = false;
        assertEquals(true,pisteetOikein);
    }
    
    @Test
    public void pisteytaPisteyttaaOikeinJosLaudallaOnTyhjiaKohtiaJotkaEivatOleKenenkaanAlueita() {
        pelilauta.laitaSiirto(2,2,1);
        pelilauta.laitaSiirto(3,1,-1);
        pelilauta.laitaSiirto(3,2,1);
        pelilauta.laitaSiirto(2,1,-1);
        pelilauta.laitaSiirto(2,3,1);
        pelilauta.laitaSiirto(1,1,-1);
        pelilauta.laitaSiirto(1,3,1);
        pelilauta.laitaSiirto(0,1,-1);
        pelilauta.laitaSiirto(0,3,1);
        pelilauta.laitaSiirto(4,1,-1);
        pelilauta.laitaSiirto(4,2,1);
        Pisteyttaja pisteyttaja = new Pisteyttaja(pelilauta);
        pisteyttaja.kopioiLaudanTilanne(pelilauta);
        pisteyttaja.alustaLasketutKohdat();
        pisteyttaja.pisteyta();
        int mustanPisteet = pisteyttaja.getMustanPisteet();
        int valkeanPisteet = pisteyttaja.getValkeanPisteet();
        boolean pisteetOikein = true;
        if (mustanPisteet != 7 || valkeanPisteet != 5) pisteetOikein = false;
        assertEquals(true,pisteetOikein);
    }
    
}
