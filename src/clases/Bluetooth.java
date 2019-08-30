package clases;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Bluetooth {
	
  private RemoteDevice myDevice;
  private MediaPlayer mediaPlayer;
  
  //encontrando el dispositivo deseado
  
  public void buscarDispositivo(MediaView video) throws Exception {
	 
	  final File f = new File("src/videos/Bachue.mp4");
	  mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
	

	  LocalDevice device = LocalDevice.getLocalDevice();
	  System.out.println("LocalDevice: "+device.getFriendlyName());
	  System.out.println("LocalDevice: "+device.getBluetoothAddress());
	  
	  
	  RemoteDevice[] remoteDevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
	  
	  for(RemoteDevice d: remoteDevice) {
		  if(d.getFriendlyName(false).equals("Aprendehistoria")) {
			  myDevice=d;
			  break;
		  }
	  }

	  System.out.println("Dispotivo: "+myDevice.getFriendlyName(false));
	  System.out.println("Bluetooth address: "+myDevice.getBluetoothAddress());
	  
	//coumnicandose con el dispositivo deseado
	  
	  String url = "btspp://"+myDevice.getBluetoothAddress()+":1;authenticate=false;encrypt=false;master=false";
	  
	  System.out.println("Conectando con el dispositivo...");
	  StreamConnection con = (StreamConnection) Connector.open(url);
	  OutputStream out = con.openOutputStream();
	  out.flush();
	  InputStream in = con.openInputStream();
	  
	  /*Ejemplo de enviar dato al bluetooth
	   *
	  out.write("1".getBytes()); //'1' es ON and '0' es OFF
	  out.flush();
	  
	  Thread.sleep(500);
	  
	  out.write("0".getBytes());
	  out.flush();
	  
	  
	  out.close();
	  in.close();
	  con.close();*/
	  
	  //leer datos del bluetooth
	 int aux;
	  while(true) {
		  System.out.println("oprima boton");	  
		  aux=Character.getNumericValue(in.read());
		  System.out.println(aux);
		 if(aux==1) {
			
			 Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	 System.out.println("entro");
			 video.setMediaPlayer(mediaPlayer);
			 mediaPlayer.play();
		            }
		        });
			 aux=10;
			 
		 }
		 
		  //Thread.sleep(500);
	  }
	        
  }//end buscarDispositivo method
   
}