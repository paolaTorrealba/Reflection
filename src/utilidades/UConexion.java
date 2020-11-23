package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * implementada el patrón de diseño “Singleton”.
 * @author Paola
 *
 */
public class UConexion {
	private static UConexion uConexion;
	private Connection conn;
	
	public static UConexion getInstance() {

		if(uConexion == null) {
			uConexion = new UConexion();	
		}

		return uConexion;
	}

	/**
	 * Abrir Coneccion
	 * @return
	 */
	public Connection abrirConexion() {
		if(this.conn == null) {
			try {
				Class.forName(propertyAccessor.getProperty("com.ar.connector").toString());
				String pathConexion = propertyAccessor.getProperty("com.ar.locatedb").toString();
		
				this.conn = DriverManager.getConnection(pathConexion, propertyAccessor.getProperty("com.ar.user"), propertyAccessor.getProperty("com.ar.password"));
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
		return this.conn;
	}
	
	/**
	 * Cerrar coneccion
	 */
	public void cerrarConexion() {
		if(this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
