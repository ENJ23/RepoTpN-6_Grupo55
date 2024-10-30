package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		
        CollectionCliente.precargarClientes();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        CollectionTarjetaCredito.precargarTarjetas();
        long nroFactura = 0;
        int opcion = 0;
        double precioMaximo = 1500000;
        double precioMaximoCelulares = 800000;
       
        	
        
        do { 
        	try {
        	System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta con el programa “Ahora 30” ");
            System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
            System.out.println("3- Mostrar lista de los electrodomésticos “Ahora 30” ");
            System.out.println("4- Stock de Electrodomésticos “Ahora 30”");
            System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
            System.out.println("6- Salir");

            System.out.println("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            }catch (Exception e){
				scanner.nextLine();
				opcion = 999;
            }
            switch(opcion) {
            
            case 1:
            	
            	try {
            		
            	System.out.println("Ingrese su DNI para comenzar con la compra: ");
	            	long dniComprador = scanner.nextLong();
	            	scanner.nextLine();
	            	
	            	Cliente comprador = CollectionCliente.buscarCliente(dniComprador);
	            	
	            	if (comprador != null){

	            	System.out.println("Stock Disponible en 'Ahora 30': ");
	            	CollectionStock.stockAhora30();
	            	
	            	System.out.println("Ingrese el código del producto que desea comprar: ");
	            		long codigoProducto =  scanner.nextLong();
	            		scanner.nextLine();
	            		Producto producto = CollectionProducto.buscarProducto(codigoProducto);
	            		if (producto != null) {
	            		System.out.println("Cuantos quiere comprar?");
	            		int cantidad = scanner.nextInt();
	            		scanner.nextLine();
            	
            		
	            		Stock stock = CollectionStock.buscarStock(producto);
	            		double precioTotal = producto.getPrecioUnitario()*cantidad;
	            		
	            		Detalle detalle = new Detalle(cantidad, precioTotal,producto);
	            		List<Detalle> detalles = new ArrayList<>();
	            		detalles.add(detalle);
	            		
	            		nroFactura++;
	            		Factura nuevaFactura = new Factura(LocalDate.now(),nroFactura,comprador,detalles);
	            		
	            		List<Cuota> cuotas = new ArrayList<>();
	            		
	            		TarjetaCredito tarjetaComprador = CollectionTarjetaCredito.buscarTarjetaPorCliente(comprador);
	            		
		            		if(tarjetaComprador.getLimiteCompra() < precioTotal) {
		            			System.out.println("No hay fondos suficientes para la operacion");
		            		}else {
		            			if ((nuevaFactura.calcularTotal() > precioMaximo) || (producto.getDescripcion().startsWith("Celular") && nuevaFactura.calcularTotal() > precioMaximoCelulares)) {
		            				System.out.println("El total es mayor al precio máximo disponible en el plan 'Ahora 30' (1.500.000 Para electrodomésticos y 800.000 para celulares)");
		            			}else {
		            				
		            				CollectionFactura.agregarFactura(nuevaFactura);
		            				CollectionStock.reducirStock(stock, cantidad);

				            		Credito nuevoCredito = new Credito(tarjetaComprador,nuevaFactura,cuotas);
				            		CollectionCredito.agregarCredito(nuevoCredito);
				            		
				            		System.out.println("Felicidades! Se ha realizado su compra");
				            		System.out.println("Esta es su factura: ");
				            		System.out.println(nuevaFactura);
		            				}	
		            			}
	            			}else {
	            				System.out.println("El codigo de producto no pertenece a ningún producto");
	            			}
	            	}
            		else
	            	{
	            		System.out.println("El DNI ingresado no pertenece a ningún cliente registrado. Inténtelo de nuevo");
	            	}
            	
                	}catch(InputMismatchException e) {
	            		System.out.println("Ha ocurrido un error en el ingreso de datos. Intentelo de nuevo");
	            		opcion = 999;
	            		scanner.nextLine();
                	}catch(Exception e) {
                		System.out.println("Ups, ha ocurrido un error inesperado. Intentalo de nuevo");
                		opcion = 999;
                		scanner.nextLine();
                	}
            	break;
            case 2:
            	try {
            		System.out.println("Ingrese el DNI del cliente: ");
            		long dniARevisar = scanner.nextLong();
            		scanner.nextLine();
            		Cliente clienteBuscado = CollectionCliente.buscarCliente(dniARevisar);
            		System.out.println(clienteBuscado.consultarCompras());
            	}catch(InputMismatchException e) {
            		System.out.println("Ha ocurrido un error en el ingreso de datos. Intentelo de nuevo");
            		opcion = 999;
            		scanner.nextLine();            	}
            	break;
            	
            case 3:
            	
            	try {
            		
            		CollectionProducto.mostrarProductosAhora30();
            		
            	}catch(Exception e) {
            		System.out.println("Ha ocurrido un error inesperado. Intente nuevamente más tarde.");
            		System.out.println("Error: " + e.getMessage());
            		opcion = 999;
            		scanner.nextLine();
            	}
            	break;
            case 4: 
            	
            	System.out.println("Stock Disponible en 'Ahora 30': ");
            	CollectionStock.stockAhora30();
            	
            	break;
            	
            case 5:
            	try {
	            	System.out.println("Ingrese el DNI del cliente a revisar: ");
	        		long dniBuscado = scanner.nextLong();
	        		scanner.nextLine();
	        		Cliente clienteBuscadoCredito = CollectionCliente.buscarCliente(dniBuscado);
	            	TarjetaCredito tarjetaBuscada = CollectionTarjetaCredito.buscarTarjetaPorCliente(clienteBuscadoCredito);
	            	
		            	if (tarjetaBuscada == null) {
		            		System.out.println("Este cliente no tiene ninguna tarjeta asociada");
		            	}else {
		            		
		            	CollectionCredito.listarCreditosCliente(tarjetaBuscada);
		            	}
	            	
	            	
            	}catch(InputMismatchException e) {
            		System.out.println("Ha ocurrido un error en el ingreso de datos. Intentelo de nuevo");
            		opcion = 999;
            		scanner.nextLine();
            	}
            	break;
            	
            case 6:
            	System.out.println("¡Hasta pronto! Esperamos tu regreso...");
            	break;
            
            default:
            	System.out.println("Opcion inválida. Intente nuevamente");
            	break;
            }
        }while(opcion != 6);
        scanner.close();

	}

}
