package controles;

import java.util.Optional;

import clases.Bluetooth;
import clases.Principal;
import clases.TTS;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class ControlVPrincipal {
	private Stage escenarioPrincipal;
	private double x, y;
	@FXML
	private Pane panelRaiz;
	@FXML
	private JFXButton botonMinimizar;
	@FXML
	private JFXButton botonA;
    @FXML
    private JFXButton botonB;
    @FXML
    private JFXButton botonC;
    @FXML
    private MediaView video;
	@FXML
	private JFXButton botonCerrar;
	private TTS voz = new TTS();

	@FXML
	private void initialize() {
		voz.speak("Bienvenido al juego aprendamos de las culturas precolombinas colombianas, presiona un botón "
				);
		iniciarLecturaBluetooth(video);

	}
    void iniciarLecturaBluetooth(MediaView video) {
	 Bluetooth conectarBluetooth = new Bluetooth();
     try {
			conectarBluetooth.buscarDispositivo(video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
