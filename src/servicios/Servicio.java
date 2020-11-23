package servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;
import utilidades.UBean;
import utilidades.UConection;

public class Servicio {

	/**
	 * el cual debe guardar en la base de datos el objeto. Debe armarse la query por
	 * medio de reflexión utilizando las anotaciones creadas en el punto 2 y
	 * utilizando los métodos creados en UBean.
	 * 
	 * @param o
	 */
	public static void guardar(Object o) {
		String query = "INSERT INTO ";
		String nombreTabla = o.getClass().getAnnotation(Tabla.class).nombre();

		query = query + nombreTabla + " (";
		ArrayList<Field> atributos = UBean.obtenerAtributos(o);

		for (Field attr : atributos) {
			query = query + attr.getAnnotation(Columna.class).nombre() + ",";
		}

		query = query.substring(0, query.length() - 1);
		query = query += ") VALUES (";

		for (Field atr : atributos) {
			if (atr.getType().getSimpleName().equals("String")) {
				query += " '" + UBean.ejecutarGet(o, atr.getName()).toString() + "', ";
			} else if (atr.getType().getSimpleName().equals("Long")) {
				query += UBean.ejecutarGet(o, atr.getName()).toString() + " , ";
			} else if (atr.getType().getSimpleName().equals("Integer")) {
				query += UBean.ejecutarGet(o, atr.getName()).toString() + " , ";
			}
		}

		query = query.substring(0, query.length() - 2);
		query = query += ")";

		System.out.println(query);

		UConection uConexion = UConection.getInstance();
		try {
			Connection conexion = uConexion.abrirConexion();

			PreparedStatement st = conexion.prepareStatement(query);

			st.execute();
			uConexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * El cual debe modificar todas las columnas, excepto la columna Id, la cual se
	 * va a utilizar para la restricción(where). Debe armarse la query por medio de
	 * reflexión utilizando las anotaciones creadas en el punto 2 y utilizando los
	 * métodos creados en UBean.
	 * 
	 * @param o
	 */
	public static void modificar(Object o) {
		String query = "UPDATE ";
		String nombreTabla = o.getClass().getAnnotation(Tabla.class).nombre();
		String idTabla = "";
		query = query + nombreTabla + " SET ";

		ArrayList<Field> atributos = UBean.obtenerAtributos(o);

		for (Field attr : atributos) {

			if (attr.isAnnotationPresent(Id.class)) {
				idTabla = UBean.ejecutarGet(o, attr.getName()).toString();
			} else {
				query += attr.getAnnotation(Columna.class).nombre() + "=";
				query += "'" + UBean.ejecutarGet(o, attr.getName()) + "', ";
			}
		}

		query = query.substring(0, query.length() - 2);
		query += "WHERE id=" + idTabla;

		System.out.println(query);
		UConection uConexion = UConection.getInstance();
		try {
			Connection conexion = uConexion.abrirConexion();

			PreparedStatement st = conexion.prepareStatement(query);

			st.execute();
			uConexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * el cual debe eliminar el registro de la base de datos. Debe armarse la query
	 * por medio de reflexión utilizando las anotaciones creadas en el punto 2 y
	 * utilizando los métodos creados en UBean.
	 * 
	 * @param o
	 */
	public static void eliminar(Object o) {
		String query = "DELETE FROM ";
		String nombreTabla = o.getClass().getAnnotation(Tabla.class).nombre();
		String idTabla = "";

		query = query + nombreTabla + " WHERE id=";

		ArrayList<Field> atributos = UBean.obtenerAtributos(o);

		for (Field attr : atributos) {

			if (attr.isAnnotationPresent(Id.class)) {
				idTabla = UBean.ejecutarGet(o, attr.getName()).toString();
			}
		}

		query = query + idTabla;

		System.out.println(query);

		UConection uConexion = UConection.getInstance();
		try {
			Connection conexion = uConexion.abrirConexion();

			PreparedStatement st = conexion.prepareStatement(query);

			st.execute();
			uConexion.cerrarConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * el cual debe devolver un objeto del tipo definido en el parámetro Class, con
	 * todos sus datos cargados. Debe armarse la query por medio de reflexión
	 * utilizando las anotaciones creadas en el punto 2 y utilizando los métodos
	 * creados en UBean.
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public static Object obtenerPorId(Class c, Object id) {
		String query = "SELECT * FROM ";
		String nombreTabla = ((Tabla) c.getAnnotation(Tabla.class)).nombre();
		String idTabla = "";
		String nombreCampoId = "";
		query = query + nombreTabla + " WHERE ";
		Object retorno = null;

		ArrayList<Field> atributos = UBean.obtenerAtributos(id);

		for (Field attr : atributos) {

			if (attr.isAnnotationPresent(Id.class)) {
				idTabla = UBean.ejecutarGet(id, attr.getName()).toString();
				nombreCampoId = attr.getAnnotation(Columna.class).nombre();
			}
		}

		query = query + nombreCampoId + "=" + idTabla;
		System.out.println(query);
		try {
			
			UConection uConn = UConection.getInstance();
			Connection conn = uConn.abrirConexion();
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet res = ps.executeQuery();

			while (res.next()) {

				for (Constructor cons : c.getConstructors()) {
					if (cons.getParameterCount() == 0) {
						retorno = cons.newInstance(null);
						break;
					}
				}

				ArrayList<Field> fields = UBean.obtenerAtributos(retorno);

				for (int i = 0; i < fields.size(); i++) {
					Columna columna = fields.get(i).getAnnotation(Columna.class);
					if (columna != null) {
						UBean.ejecutarSet(retorno, fields.get(i).getName(),
								res.getObject(columna.nombre(), fields.get(i).getType()));
					}
				}

				return retorno;
			}
			uConn.cerrarConexion();

		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return retorno;

	}

}
