package Util;

import java.util.UUID;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Log4jListener implements ServletRequestListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(Log4jListener.class);
	
	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		MDC.put("RequestId", String.valueOf(UUID.randomUUID()));
		LOGGER.debug("++++++++++++ REQUEST INITIALIZED ++++++++++++");
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		LOGGER.debug("------------ REQUEST DESTROYED ------------");
		MDC.clear();
	}
	
	
	
}
