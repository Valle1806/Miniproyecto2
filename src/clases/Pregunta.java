package clases;

public class Pregunta {
	private String pregunta;
	private Respuesta[] conjuntoRespuestas;
	
	public Pregunta(String pregunta,Respuesta[] conjuntoRespuestas) {
		this.pregunta=pregunta;
		this.conjuntoRespuestas=conjuntoRespuestas;
	}
	public String getPregunta() {
		return this.pregunta;
	}
	public Respuesta[] getRespuestas() {
		return this.conjuntoRespuestas;
	}
	
}
