package controles;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import javax.microedition.io.StreamConnection;

import clases.Bluetooth;
import clases.Principal;
import clases.TTS;
import clases.Util;
import com.jfoenix.controls.JFXButton;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlVPrincipal {
	private Stage escenarioPrincipal;
	private double x, y;
	@FXML
	private Pane panelRaiz;
	@FXML
	private JFXButton botonMinimizar;
	@FXML
	private JFXButton botonCerrar;
	@FXML
    private MediaView video;
	
	private MediaPlayer mediaPlayer;
	
	private TTS voz = new TTS();

	private int iterador = 1;
	
	private boolean reproducion_video = false;
	private InputStream in;
	
	@FXML
	private void initialize() {
		voz.speak("Bienvenido al juego aprendamos de las culturas precolombinas colombianas. Seleccione una cultura en el mapa");
		initBluetooth();
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
				while (accepted) {
					int value = Character.getNumericValue(in.read());
					System.out.println("Button: " + value);
					switch (value) {
					case 1:
						if (last == value) {
							conn.close();
							in.close();
							out.close();
							voz.speak("Atienda con atención y responda las preguntas");
							PauseTransition delay = new PauseTransition(Duration.seconds(4));
							delay.setOnFinished(event -> mostrarVideo("muisca",value));
							delay.play();
							
							
						} else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							PauseTransition delay = new PauseTransition(Duration.seconds(1));
							delay.setOnFinished(event -> voz.speak("Cultura Muísca seleccionada. presione de nuevo para confirmar"));
							delay.play();
						}
						last = value;
						break;
					case 2:
						if (last == value) {
							conn.close();
							in.close();
							out.close();
							voz.speak("Atienda con atención y responda las preguntas");
							PauseTransition delay = new PauseTransition(Duration.seconds(4));
							delay.setOnFinished(event -> mostrarVideo("zenu",value));
							delay.play();

						} else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							PauseTransition delay = new PauseTransition(Duration.seconds(1));
							delay.setOnFinished(event -> voz.speak("Cultura Zenú seleccionada. presione de nuevo para confirmar"));
							delay.play();
						}
						last = value;
						break;
					case 3:
						if (last == value) {
							conn.close();
							in.close();
							out.close();
							voz.speak("Atienda con atención y responda las preguntas");
							PauseTransition delay = new PauseTransition(Duration.seconds(4));
							delay.setOnFinished(event -> mostrarVideo("tairona",value));
							delay.play();
						} else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							PauseTransition delay = new PauseTransition(Duration.seconds(1));
							delay.setOnFinished(event -> voz.speak("Cultura Tairona seleccionada. presione de nuevo para confirmar"));
							delay.play();
						}
						last = value;
						break;
					case 4:
						if (last == value) {
							conn.close();
							in.close();
							out.close();
							voz.speak("Atienda con atención y responda las preguntas");
							PauseTransition delay = new PauseTransition(Duration.seconds(4));
							delay.setOnFinished(event -> mostrarVideo("tierradentro",value));
							delay.play();
						} else {
							Util.play("src/sonidos/sound"+String.valueOf(value)+".wav");
							PauseTransition delay = new PauseTransition(Duration.seconds(1));
							delay.setOnFinished(event -> voz.speak("Cultura Tierradentro seleccionada. presione de nuevo para confirmar"));
							delay.play();
						}
						last = value;
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

	private void mostrarVideo(String videoName,int cultura){
		
		voz.stop();
		reproducion_video = true;
		final File f = new File("src/videos/" + videoName + ".mp4");
		//final File f = new File("src/videos/ayuda.mp4");
		mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
		video.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();

		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				System.out.println("sección de preguntas");
				System.out.println(cultura);
				cambiarAIPregunta(cultura);
			}
		});
	}
	
	void cambiarAIPregunta(int cultura) {
		try {
			voz.stop();
			mediaPlayer.stop();
			video.setMediaPlayer(null);
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlPregunta control = cargador.getController();
			int aux = 0;
			switch(cultura){
				case 1:
					aux=0;
					break;
				case 2:
					aux=4;
					break;
				case 3:
					aux=8;
					break;
				case 4:
					aux=12;
					break;
			}
			control.inicio(cultura,aux,0);
			Scene escenario = new Scene(raiz);
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	
	// --------------------------------------------------------------------------------------

	// Minimizar la aplicación
	@FXML
	void minimizar(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	// Cerrar la aplicación
	@FXML
	void cerrar(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Está a punto de cerrar la aplicación");
		alert.setContentText("¿Está seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

	public void setStage(Stage escenario) {
		this.escenarioPrincipal = escenario;
	}

	@FXML
	public void teclas(KeyEvent e) {
		
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

}
