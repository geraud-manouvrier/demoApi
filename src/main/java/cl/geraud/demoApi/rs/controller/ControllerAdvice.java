package cl.geraud.demoApi.rs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.geraud.demoApi.rs.Exception.CustomRuntimeException;
import cl.geraud.demoApi.rs.model.DetalleResponse;

@RestControllerAdvice
public class ControllerAdvice {

	private static final Logger LOGGER =  LoggerFactory.getLogger(ControllerAdvice.class);
	
	@ExceptionHandler
	//@ResponseStatus(HttpStatus.OK)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public DetalleResponse errorException(Throwable e) {
		LOGGER.debug("Entrando al método errorException");
		DetalleResponse detalleResponse = new DetalleResponse(DetalleResponse.RESPUESTA_ERROR);
		detalleResponse.setDetalle(e.toString());
		
		if(e instanceof CustomRuntimeException)
			detalleResponse.setMensaje(e.getMessage());
		
		LOGGER.error("Ocurrio un error al ejecutar la consulta", e);
		LOGGER.debug("Saliendo del método errorException");
		return detalleResponse;
	}

}