package ar.edu.unju.escmi.tp6.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class CollectionCredito {

	 public static List<Credito> creditos = new ArrayList<Credito>();

	 public static void agregarCredito(Credito credito) {
	        
	    	try {
	    		creditos.add(credito);
			} catch (Exception e) {
				System.out.println("\nNO SE PUEDE GUARDAR EL CREDITO");
			}
	    	
	    }
	 
	 public static void listarCreditosCliente(TarjetaCredito tarjeta) {
		
		if (tarjeta != null) {
		 for (Credito credito : creditos) {
			 if (credito.getTarjetaCredito().equals(tarjeta)) {
				 if(credito.getFactura() != null) {
				 //System.out.println(credito.getFactura());
				 double montoTotal = credito.getFactura().calcularTotal();	 
				 credito.mostarCredito(montoTotal);
				 }else {
				System.out.println("Este cliente no tiene créditos con su tarjeta"); 
			 	}
			 }	 
		 }
		 
		}else {
			System.out.println("Este ");
			}
		 
	 }
	
		 
	 
}
