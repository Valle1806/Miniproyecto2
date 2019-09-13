package controles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.microedition.io.StreamConnection;

import com.jfoenix.controls.JFXButton;

import clases.Bluetooth;
import clases.Pregunta;
import clases.Principal;
import clases.Respuesta;
import clases.TTS;
import clases.Util;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlPregunta {

	private Stage escenarioPrincipal;
	private double x, y;
	private String correcta, puntos = " ", respuesta = " ", colorFondo;

	@FXML
	private Label pregunta;
	@FXML
	private Pane panelRaiz;
	@FXML
	private JFXButton botonMinimizar;
	@FXML
	private JFXButton botonCerrar;
	@FXML
	private JFXButton botonInicio;
	@FXML
	private JFXButton respuestaA;
	@FXML
	private JFXButton respuestaC;
	@FXML
	private JFXButton respuestaB;
	@FXML
	private JFXButton respuestaD;

	private int contadorAux;
	private int contador2;

	private Pregunta[] preguntas = new Pregunta[16];

	private int cultura;
	private TTS voz = new TTS();
	private InputStream in;

	@FXML
    void teclaPresionada(KeyEvent event) {
		if(event.getText().equals("f")){
			voz.speak(preguntas[contadorAux]);
		}
    }
	
	public void inicio(int cultura, int contador,int contador2) {
		iniciarPreguntas();
		
		contadorAux = contador;
		this.contador2 = contador2;
		this.cultura = cultura;

		pregunta.setWrapText(true);
		pregunta.setText(preguntas[contador].getPregunta());
		pregunta.setCenterShape(true);

		// Conversion de arreglo a lista
		List<Respuesta> respuestasList = new ArrayList<Respuesta>();
		Respuesta respuestas[] = preguntas[contador].getRespuestas();
		respuestasList = Arrays.asList(respuestas);

		java.util.Collections.shuffle(respuestasList);
		for (int i = 0; i < respuestas.length; i++) {
			if (respuestasList.get(i).getCorrecto()) {
				correcta = respuestasList.get(i).getRespuesta();
			}
		}

		voz.speak(preguntas[contador]);
		initBluetooth();

		respuestaA.setWrapText(true);
		respuestaA.setText(respuestasList.get(0).getRespuesta());
		respuestaA.setTextFill(Color.WHITE);
		respuestaA.setFont(new Font("Cambria", 18));

		respuestaB.setWrapText(true);
		respuestaB.setText(respuestasList.get(1).getRespuesta());
		respuestaB.setFont(new Font("Cambria", 18));
		respuestaB.setTextFill(Color.WHITE);

		respuestaC.setWrapText(true);
		respuestaC.setText(respuestasList.get(2).getRespuesta());
		respuestaC.setFont(new Font("Cambria", 18));
		respuestaC.setTextFill(Color.WHITE);

		respuestaD.setWrapText(true);
		respuestaD.setText(respuestasList.get(3).getRespuesta());
		respuestaD.setFont(new Font("Cambria", 18));
		respuestaD.setTextFill(Color.WHITE);

	}

	void iniciarPreguntas() {
		preguntas[0] = new Pregunta("¿De donde provino Bachué y su joven acompañante?",
				new Respuesta[] { new Respuesta("De la laguna de Iguaque", true),
						new Respuesta("De un rayo del cielo", false), new Respuesta("De una planta de maíz ", false),
						new Respuesta("Ninguna de las anteriores", false) });

		preguntas[1] = new Pregunta("¿Quién es Bachué?",
				new Respuesta[] { new Respuesta("Una Cacique", false), new Respuesta("La diosa de trigo", false),
						new Respuesta("La diosa de la luna", false),
						new Respuesta("La madre del pueblo Muisca", true) });

		preguntas[2] = new Pregunta("¿Quienes son los hijos de Bachué y su joven acompañante?",
				new Respuesta[] { new Respuesta("Los Tierra adentro", false), new Respuesta("Los Muiscas", true),
						new Respuesta("Los Incas", false), new Respuesta("Los Mayas", false) });

		preguntas[3] = new Pregunta("¿Qué habilidades NO enseñaron al pueblo Muisca Bachué y su joven acompañante?",
				new Respuesta[] { new Respuesta("A trabajar la piedra", false),
						new Respuesta("A manipular el fuego", false), new Respuesta("Navegar en el mar", true),
						new Respuesta("Cazar sus alimentos", false) });

		preguntas[4] = new Pregunta("¿Cómo regaban los cultivos los Zinues?",
				new Respuesta[] { new Respuesta("Por medio de una red de canales", true),
						new Respuesta("Usando recipientes de barro", false),
						new Respuesta("Conocían los calendarios de lluvia", false),
						new Respuesta("Los zinues no eran agricultores", false) });

		preguntas[5] = new Pregunta("Para qué servían los canales de agua que construyeron los zenúes",
				new Respuesta[] { new Respuesta("Solamente para regar sus cultivos", false),
						new Respuesta("Para hacer laberintos", false),
						new Respuesta("Para transportarse y comunicarse", true),
						new Respuesta("Ninguna de las anteriores", false) });

		preguntas[6] = new Pregunta("¿Qué artesanias hacían los zenúes?",
				new Respuesta[] { new Respuesta("Aretes y Collares", false), new Respuesta("Lanzas y arcos", false),
						new Respuesta("Pectorales y faldas", false), new Respuesta("Cerámicas y textiles", true) });

		preguntas[7] = new Pregunta("¿Qué hacían los zenúes en sus ceremonias fúnebres?",
				new Respuesta[] { new Respuesta("Hacían una procesión por el río", true),
						new Respuesta("Festejaban junto al río", false), new Respuesta("Danzaban juntamente", false),
						new Respuesta("Comían hasta el amanecer", false) });

		preguntas[8] = new Pregunta("¿Qué aprendían los niños mientras le enseñaban a tejer?",
				new Respuesta[] { new Respuesta("Cuentos y leyendas", true), new Respuesta("Los números", false),
						new Respuesta("A leer", false), new Respuesta("A cazar", false) });

		preguntas[9] = new Pregunta(
				"¿ Que material usaron para construir plataformas sobre las que levantaron sus casas?",
				new Respuesta[] { new Respuesta("Ladrillo", false), new Respuesta("Piedras", true),
						new Respuesta("Arena", false), new Respuesta("Yeso", false) });

		preguntas[10] = new Pregunta("¿En qué localización geográfica se ubicaron los Tairona?",
				new Respuesta[] { new Respuesta("Cali", false), new Respuesta("Bogotá", false),
						new Respuesta("Sierra nevada de Santa Marta", true), new Respuesta("Medellín", false) });

		preguntas[11] = new Pregunta(
				"Una de estas opciones No es una actividad económica  a la que se dedicaban los Tairona:",
				new Respuesta[] { new Respuesta("Agricultura", false),
						new Respuesta("Fabricación de motocicletas", true), new Respuesta("Artesanía", false),
						new Respuesta("Alfarería", false) });

		preguntas[12] = new Pregunta("¿Qué nombre reciben los indígenas que habitan en el territorio de Tierradentro?",
				new Respuesta[] { new Respuesta("Mayas", false), new Respuesta("Paeces", true),
						new Respuesta("Persas", false), new Respuesta("Kichwa", false) });

		preguntas[13] = new Pregunta("¿En qué cordillera se encuentra Tierradentro?",
				new Respuesta[] { new Respuesta("Cordillera Occidental", false),
						new Respuesta("Cordillera Oriental", false), new Respuesta("Cordillera Central", true),
						new Respuesta("Cordillera de la Costa", false) });

		preguntas[14] = new Pregunta("¿Qué son los hipogeos?",
				new Respuesta[] { new Respuesta("Tumbas subterráneas", true),
						new Respuesta("Parte fundamental de la gastronomía nasa", false),
						new Respuesta("Seres espirituales", false),
						new Respuesta("Los gobernadores de la comunidad nasa.", false) });

		preguntas[15] = new Pregunta("¿En dónde nació Juan Tama?",
				new Respuesta[] { new Respuesta("En el mar", true), new Respuesta("En la luna", false),
						new Respuesta("En una laguna", false), new Respuesta("En un árbol", false) });

	}

	private void initBluetooth() {
		Thread thread = new Thread(() -> {
			try {
				StreamConnection conn = Bluetooth.connect();
				OutputStream out = conn.openOutputStream();
				out.flush();
				in = conn.openInputStream();
				Boolean accepted = true;
				int last = -1;
				//voz.stop();
				PauseTransition delay = new PauseTransition(Duration.seconds(3));
				while(accepted){
					int value = Character.getNumericValue(in.read());
					System.out.println("Button: " + value);
					switch (value) {
					case 1:
						if(last==value){
							voz.speak("Opción A confirmada");
							delay.setOnFinished(actionEvent -> {
								if (respuestaA.getText().equals(correcta)) {
									puntos = "10pts";
									respuesta = "Respuesta Correcta";
									colorFondo = "#3FF500";
									voz.speak("Respuesta Correcta");
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									puntos = "0pts";
									respuesta = "Respuesta Incorrecta";
									colorFondo = "#851A1A";
									voz.speak("Respuesta Incorrecta"+ "la respuesta correcta es "+ correcta);
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								cargarInterfazRespuesta();
							});
							delay.play();
						}else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							delay = new PauseTransition(Duration.seconds(2));
							delay.setOnFinished(event -> voz.speak("Opción A seleccionada, presione de nuevo para confirmar"));
							delay.play();
						}
						last=value;
						break;
					case 2:
						if(last==value) {
							voz.speak("Opción B confirmada");
							delay.setOnFinished(actionEvent -> {
								if (respuestaB.getText().equals(correcta)) {
									puntos = "10pts";
									respuesta = "Respuesta Correcta";
									colorFondo = "#3FF500";
									voz.speak("Respuesta Correcta");
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									puntos = "0pts";
									respuesta = "Respuesta Incorrecta";
									colorFondo = "#851A1A";
									voz.speak("Respuesta Incorrecta"+ "la respuesta correcta es "+ correcta);
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								cargarInterfazRespuesta();
							});
							delay.play();
						}else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							delay = new PauseTransition(Duration.seconds(2));
							delay.setOnFinished(event -> voz.speak("Opción B seleccionada, presione de nuevo para confirmar"));
							delay.play();
						}
						last=value;
						break;
					case 3:
						if(last==value) {
							voz.speak("Opción C confirmada");
							delay.setOnFinished((actionEvent) -> {
								if (respuestaC.getText().equals(correcta)) {
									puntos = "10pts";
									respuesta = "Respuesta Correcta";
									colorFondo = "#3FF500";
									voz.speak("Respuesta Correcta");
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									puntos = "0pts";
									respuesta = "Respuesta Incorrecta";
									colorFondo = "#851A1A";
									voz.speak("Respuesta Incorrecta"+ "la respuesta correcta es "+ correcta);
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								cargarInterfazRespuesta();
							});
							delay.play();
						}else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							delay = new PauseTransition(Duration.seconds(2));
							delay.setOnFinished(event -> voz.speak("Opción C seleccionada, presione de nuevo para confirmar"));
							delay.play();
						}
						last=value;
						break;
					case 4:
						if(last==value){
							voz.speak("Opción D confirmada");
							delay.setOnFinished(actionEvent -> {
								if (respuestaD.getText().equals(correcta)) {
									puntos = "10pts";
									respuesta = "Respuesta Correcta";
									colorFondo = "#3FF500";
									voz.speak("Respuesta Correcta");
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									puntos = "0pts";
									respuesta = "Respuesta Incorrecta";
									colorFondo = "#851A1A";
									voz.speak("Respuesta Incorrecta"+ "la respuesta correcta es "+ correcta);
									try {
										conn.close();
										in.close();
										out.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								cargarInterfazRespuesta();
							});
							delay.play();
						}else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							delay = new PauseTransition(Duration.seconds(2));
							delay.setOnFinished(event -> voz.speak("Opción D seleccionada, presione de nuevo para confirmar"));
							delay.play();
						}
						last=value;
						break;
					default:
						break;

					}
				}
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
		
	
	
	@FXML
	void verificarRespuestaA(ActionEvent event) {
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opción A seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaA.getText().equals(correcta)) {
				System.out.println("CorrectaA");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	void verificarRespuestaB(ActionEvent event) {

		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opción B seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaB.getText().equals(correcta)) {
				System.out.println("CorrectaB");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	void verificarRespuestaC(ActionEvent event) {
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opción C seleccionada");
		delay.setOnFinished((actionEvent) -> {
			if (respuestaC.getText().equals(correcta)) {
				System.out.println("CorrectaC");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();
		cargarInterfazRespuesta();

	}

	@FXML
	void verificarRespuestaD(ActionEvent event) {

		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opción D seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaD.getText().equals(correcta)) {
				System.out.println("CorrectaD");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	public void cargarInterfazRespuesta() {
		try {
			voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/respuesta.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlRespuesta control = cargador.getController();
			control.setStage(escenarioPrincipal);
			control.cargar(puntos, respuesta, colorFondo, contadorAux, cultura,this.contador2,correcta);
			panelRaiz.getChildren().clear();
			panelRaiz.getChildren().add(raiz);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void regresarInicio(ActionEvent event) {
		try {
			//voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlVPrincipal control = cargador.getController();
			control.setStage(escenarioPrincipal);
			Pane panelCentral = (Pane) ((Button) event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ___________________________________________________________________________________________________

	public void setStage(Stage escenario) {
		this.escenarioPrincipal = escenario;
	}

	@FXML
	void copiarCoordenadas(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}

	@FXML
	void moverPanel(MouseEvent event) {
		escenarioPrincipal.setX(event.getScreenX() - x);
		escenarioPrincipal.setY(event.getScreenY() - y);
	}

	// Minimizar la aplicación
	@FXML
	void minimizar(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

//Cerrar la aplicación
	@FXML
	void cerrar(ActionEvent event) {
		voz.stop();
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Está a punto de cerrar la aplicación");
		alert.setContentText("¿Está seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

}