package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class Tool {
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(Tool.class);
	
	private Properties getProperties()  {
		
		Properties prop = new Properties();
		String propFileName = "/var/lib/tomcat8/webapps/demoapi-rs/WEB-INF/classes/config.properties";
		
		try (FileInputStream inputStream = new FileInputStream(propFileName)) { 
			prop.load(inputStream);
		}catch(FileNotFoundException ex) {
			LOGGER.error(ex.getMessage());
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage());
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		return prop;
	}
	
	public String getPropertiesString(String name) {	

		Properties prop = getProperties();
		return prop.getProperty(name);
	
	}
	
	public int getPropertiesInt(String name) {

		Properties prop = getProperties();
		
		return Integer.parseInt(prop.getProperty(name));

	}
}
