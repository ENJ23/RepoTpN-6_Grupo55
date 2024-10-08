package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

	private Credito credito;
	private Factura factura;
	private TarjetaCredito tarjetaCredito;
	
	@BeforeEach
    public void setUp() {
        Cliente cliente = new Cliente(45111222, "Mario Barca", "Alvear 120", "65454686");
        TarjetaCredito tarjetaCredito =	new TarjetaCredito(232323, LocalDate.of(2026,10,10), cliente, 800000);
        this.tarjetaCredito = tarjetaCredito;
        Producto producto = new Producto(1111, "Aire Acondicionado Split On/Off 2750W FC Hisense", 220000, "Argentina");
        List<Detalle> detalles = new ArrayList<>();
        detalles.add(new Detalle(1, producto.getPrecioUnitario(), producto));
        Factura factura = new Factura(LocalDate.now(), 1, cliente, detalles);
        this.factura = factura;
        
        List<Cuota> cuotas = new ArrayList<>();
        credito = new Credito(tarjetaCredito, factura, cuotas);
        credito.generarCuotas();
    }

    @Test
    public void testMontoTotalCreditoNoExcedeLimite() {

        double montoTotal = factura.calcularTotal();

        assertTrue(montoTotal <= 1500000);
    }
    
    @Test 
    public void testSumaImportesIgualFactura() {

    	double montoTotal = 0;
    	Factura factura = new Factura();
    	for (Detalle detalle : factura.getDetalles()) {
    		montoTotal += detalle.getImporte();
    	}
    	
    	assertTrue(montoTotal == factura.calcularTotal());
    	
    }
 
    
    @Test
    public void testMontoTotalYTotalTarjeta() {
    	
    	double montoTotal = 0;
    	Factura factura = new Factura();
    	for (Detalle detalle : factura.getDetalles()) {
    		montoTotal += detalle.getImporte();
    	}
    	
    	assertFalse(montoTotal > 1500000 || montoTotal > tarjetaCredito.getLimiteCompra());
    }
}