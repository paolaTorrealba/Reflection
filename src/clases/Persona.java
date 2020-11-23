package clases;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre = "persona")
public class Persona implements Comparable<Persona> {

	@Id
	@Columna(nombre = "id")
	private Long id;
	
	@Columna(nombre = "nombre")
	private String nombre;

	@Columna(nombre = "apellido")
	private String apellido;

	@Columna(nombre = "dni")
	private Integer dni;


	public Persona(Long id, Integer dni, String nombre, String apellido) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public Persona() {
		super();
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public Integer getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Persona o) {
		int result = 0;
		if ((this.getNombre().compareToIgnoreCase(o.getNombre()) == 0)
				&& (this.getApellido().compareToIgnoreCase(o.getApellido()) == 0)) {
			result = 0;
		} else if (this.getApellido().compareToIgnoreCase(o.getApellido()) < 0) { // a>b
			result = -1;

		} else if (this.getApellido().compareToIgnoreCase(o.getApellido()) > 0) {// a<b
			result = 1;
		}

		return result;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Persona other = (Persona) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
//		return this.nombre;
		return this.nombre + " " + this.apellido + " " + this.dni.toString();
	}
}
