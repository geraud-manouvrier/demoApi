package cl.geraud.demoApi.rs.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DetalleResponse {

	public static final DetalleResponse RESPUESTA_OK = new DetalleResponse();
	public static final DetalleResponse RESPUESTA_ERROR = new DetalleResponse();
	
	private int codigo;
	private String mensaje;
	@JsonIgnore
	private String detalle;
	
	static {
		RESPUESTA_OK.setCodigo(HttpStatus.OK.value());
		RESPUESTA_OK.setMensaje("OK");
		RESPUESTA_OK.setDetalle("OK");
		
		RESPUESTA_ERROR.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		RESPUESTA_ERROR.setMensaje("Error al generar consulta");
	}
	
	public DetalleResponse() {}
	
	public DetalleResponse(int codigo, String mensajeDetalle) {
		this.codigo = codigo;
		this.mensaje = mensajeDetalle;
		this.detalle = mensajeDetalle;
	}

	public DetalleResponse(DetalleResponse detalleResponse) {
		this.codigo = detalleResponse.getCodigo();
		this.mensaje = detalleResponse.getMensaje();
		this.detalle = detalleResponse.getDetalle();
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	
	@Override
	public String toString() {
		return "Detalle Response [codigo=" + codigo + ", mensaje=" + mensaje + ", detalle=" + detalle + "]";
	}
}
