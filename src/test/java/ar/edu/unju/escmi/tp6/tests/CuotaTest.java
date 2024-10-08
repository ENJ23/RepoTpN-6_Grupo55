package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CuotaTest {

	private Credito credito;
	
	@BeforeEach
    public void setUp() {
        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        Factura factura = new Factura(); 
        List<Cuota> cuotas = new ArrayList<>();

        credito = new Credito(tarjetaCredito, factura, cuotas);
    }

    @Test
    public void testListaCuotasNoEsNull() {
    	
        assertNotNull(credito.getCuotas());
        
    }
    
    @Test
    public void testNumeroCuotas() {
    	
        assertEquals(30, credito.getCuotas().size());

    }
    
    @Test 
    public void testMaxCuotas() {
    	
    	assertTrue(credito.getCuotas().size() < 31);
    }
}