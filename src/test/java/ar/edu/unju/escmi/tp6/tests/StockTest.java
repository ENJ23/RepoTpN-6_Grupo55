package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

	private Stock stock;
	
	@BeforeEach
	public void setUp() {
		
		Producto producto = new Producto(1111, "Aire Acondicionado Split On/Off 2750W FC Hisense", 220000, "Argentina");
		stock = new Stock(12, producto);
		CollectionStock.stocks.add(stock);
		
	}

	@Test
	public void testDecrementoStock() {
		
		int cantidadInicial = stock.getCantidad();
		CollectionStock.reducirStock(stock, 1);
		assertTrue(stock.getCantidad() < cantidadInicial);
		
	}
}