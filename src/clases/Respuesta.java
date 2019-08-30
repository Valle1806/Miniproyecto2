package clases;

public class Respuesta {
	private String respuesta;
	private boolean correcto;
	
	public Respuesta(String respuesta,boolean correcto) {
		this.correcto=correcto;
		this.respuesta=respuesta;
	}
	public String getRespuesta() {
		return this.respuesta;
	}
	public boolean getCorrecto() {
		return this.correcto;
	}
}
