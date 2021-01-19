package cl.geraud.demoApi.rs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

	private static final Logger LOGGER =  LoggerFactory.getLogger(ControllerAdvice.class);
	
	@ExceptionHandler
	public ResponseEntity<Void> errorException(Throwable e) {
		LOGGER.debug("Entrando al método errorException");
		LOGGER.error("Error: "+e.toString());
		LOGGER.error("Mensaje error: "+e.getMessage());
		
		HttpHeaders responseHeaders 	= new HttpHeaders();
		
		responseHeaders.set("Content-Type", "application/json");
		
		LOGGER.debug("Saliendo del método errorException");
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}