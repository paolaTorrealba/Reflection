package main;


import clases.Persona;
import servicios.Servicio;

public class main {

	public static void main(String[] args) {	
		
		Persona p = new Persona(1L, 31660603,"Paola", "Torrealba");	
		Servicio.guardar(p);
		
		Persona p2 = new Persona(2L, 32456435,"Marcos", "Vilas");
		Servicio.guardar(p2);
		
		Servicio.eliminar(p2);
		
		Persona p3 = new Persona(1L, 99999999,"Esteban", "Lopez");
		Servicio.modificar(p3);
		
		Object obj = Servicio.obtenerPorId(p.getClass(), p);
		
		System.out.println(obj);
	}

}
