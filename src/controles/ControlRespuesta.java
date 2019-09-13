package controles;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Pregunta;
import clases.Principal;
import clases.TTS;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlRespuesta {
	private Stage escenarioPrincipal;
	private double x, y;
	private TTS voz = new TTS();
	
    @FXML
    private Pane panelRaiz;

    @FXML
    private ImageView imagenRespuesta;
    @FXML
    private JFXButton botonMinimizar;

    @FXML
    private JFXButton botonCerrar;

    @FXML
    private Label respuesta;

    @FXML
    private JFXButton botonInicio;

    @FXML
    private Label puntos;
    private int contador;
    private int contador2;
    private int tipo_carta;
    private PauseTransition delay;

    public void cargar(String puntoss,String respuestaa,String fondo,int contadorr,int tipo_carta,int contador2,  String  respuestaC) {
    	String estilo = "-fx-background-color:"+ fondo+";";
    
    	File file = new File("src/imagenes/" + respuestaa + ".png");
    	Image image = new Image(file.toURI().toString());
   		imagenRespuesta.setImage(image);
    	
    	panelRaiz.setStyle(estilo);
    	puntos.setText(puntoss);
    	respuesta.setText(respuestaa);
    	this.contador= contadorr;
    	this.contador2 = contador2;
    	this.tipo_carta= tipo_carta;
    	this.tipo_carta++;
    	
    	
    	if(this.contador2>=3) {
    		delay = new PauseTransition(Duration.seconds(5));
    		delay.setOnFinished( event -> mostrarEsperarCarta() );
    		delay.play();
    		
    	}else { 
    		voz.stop();
    		contador++;
    		this.contador2++;
    		delay = new PauseTransition(Duration.seconds(5));
    		delay.setOnFinished( event -> cambiarAIPregunta(this.tipo_carta,contador,this.contador2));
    		delay.play();
    		
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
    //Minimizar la aplicación
    @FXML
    void minimizar(ActionEvent event) {
    	Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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
    	if (result.get() == ButtonType.OK){
    	    System.exit(1); 
    	}
    }



    @FXML
    void regresarInicio(ActionEvent event) {
    	try {
    		voz.stop();
    		delay.stop();
    		String estilo = "-fx-background-color: #fff;";
        	panelRaiz.setStyle(estilo);
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlVPrincipal control= cargador.getController();
			control.setStage(escenarioPrincipal);
			Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    void cambiarAIPregunta(int tipo_carta, int contador,int contador2 ) {
    	try {
    		voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlPregunta control =cargador.getController();
			control.inicio(tipo_carta,contador,contador2);
			Scene escenario = new Scene(raiz); 
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
   
    void mostrarEsperarCarta() {
    	try {
    		voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlVPrincipal control = cargador.getController();
			Scene escenario = new Scene(raiz); 
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
		} catch(Exception e) {
			e.printStackTrace();
		}
    	
    }

}
