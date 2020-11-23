package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class UBean {

	/**
	 * Devuelve un ArrayList<Field> con todos los atributos que posee el parámetro
	 * Object.
	 * 
	 * @param obj
	 * @return
	 */
	public static ArrayList<Field> obtenerAtributos(Object obj) {

		Class<? extends Object> c = obj.getClass();

		ArrayList<Field> arrayListFields = new ArrayList<Field>(Arrays.asList(c.getDeclaredFields()));

		return arrayListFields;
	}

	/**
	 * Se debe ejecutar el método Setter del String dentro del Object.
	 * 
	 * @param o
	 * @param att
	 * @param valor
	 */
	public static void ejecutarSet(Object obj, String atributo, Object valor) {
		Object p = null;
		Class<? extends Object> c = obj.getClass();

		String nombreAttASettear = "set" + atributo;

		Method[] metodos = c.getDeclaredMethods();

		for (Method m : metodos) {
			if (m.getName().equalsIgnoreCase(nombreAttASettear)) {
				Object[] params = new Object[1];
				params[0] = valor;

				try {
					m.invoke(obj, params);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * devolverá el valor del atributo pasado por parámetro, ejecutando el getter
	 * dentro del objeto.
	 * 
	 * @param o
	 * @param att
	 * @return
	 */

	public static Object ejecutarGet(Object obj, String atributo) {
		Object p = null;
		Class<? extends Object> c = obj.getClass();

		String nombreAttAGettear = "get" + atributo;

		Method[] metodos = c.getDeclaredMethods();
		Object getObtenido = null;
		for (Method m : metodos) {
			if (m.getName().equalsIgnoreCase(nombreAttAGettear)) {
				try {
					getObtenido = m.invoke(atributo, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}

		return getObtenido;
	}

}
