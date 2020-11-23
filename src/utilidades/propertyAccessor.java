package utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class propertyAccessor {
	public static String getProperty(String nombreProperty) {
		Properties propiedades = new Properties();
		try {
			propiedades
			 .load(new FileInputStream(
					 System.getProperty("user.dir")+"/resources/application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return propiedades.getProperty(nombreProperty);
	}
	
}
