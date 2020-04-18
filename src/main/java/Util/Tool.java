package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class Tool {
	
	private Properties getProperties()  {
		
		Properties prop = new Properties();
		String propFileName = "src/main/resources/config.properties";
		
		try (FileInputStream inputStream = new FileInputStream(propFileName)) { 
			prop.load(inputStream);
		}catch(FileNotFoundException ex) {
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	public String getPropertiesString(String name) {	

		Properties prop = getProperties();
		return prop.getProperty(name).toString();
	
	}
	
	public int getPropertiesInt(String name) {

		Properties prop = getProperties();
		
		return Integer.parseInt(prop.getProperty(name));

	}
}
