package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto varastoAlkusaldolla;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        varastoAlkusaldolla = new Varasto(10, 5);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivisellaTilavuudellaLuodaanKayttokelvotonVarasto() {
        Varasto negatiivinen = new Varasto(-3);
        assertEquals(0.0, negatiivinen.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void alkuSaldollisenVarastonTilavuusNollataanVirheellisellaTilavuudella() {
        Varasto virheellinen = new Varasto(-1, 1);
        assertEquals(0.0, virheellinen.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void alkuSaldollisenVarastonSaldoNollataanVirheellisellaSaldolla() {
        Varasto virheellinen = new Varasto(10, -1);
        assertEquals(0.0, virheellinen.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void alkuSaldollinenVarastoOnTaynnaJosAlkusaldoSuurempiKuinTilavuus() {
        Varasto ylitaysi = new Varasto(5, 6);
        assertEquals(5, ylitaysi.getTilavuus(), vertailuTarkkuus);
        assertEquals(5, ylitaysi.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaOikeaTilavuusJaSaldo() {
        assertEquals(10, varastoAlkusaldolla.getTilavuus(), vertailuTarkkuus);
        assertEquals(5, varastoAlkusaldolla.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toStringToimiiOikein() {
        String merkkijono = "saldo = 6, vielä tilaa 5.0";
        assertEquals(merkkijono, varastoAlkusaldolla.toString());
    }
    
    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenLisaysEiMuutaSaldoa() {
        varasto.lisaaVarastoon(-8);

        // saldon ei pitäisi muuttua
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisayksenYlittaessaTilavuudenVarastoTuleeTayteen() {
        varasto.lisaaVarastoon(20);

        // saldoksi pitäisi tulla varaston tilavuus
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenNegatiivisellaMaarallaEiMuutaSaldoa() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(-2);

        assertEquals(0, saatuMaara, vertailuTarkkuus);
    }
    
    @Test
    public void otettavanMaaranYlittaessaSaldonOtetaanKaikkiMitaVoidaan() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(20);

        assertEquals(8, saatuMaara, vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

}