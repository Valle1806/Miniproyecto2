package clases;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class Bluetooth {
	private static RemoteDevice myDevice;
	private static final String deviceName = "mapaInterac";

	public static StreamConnection connect() {
		try {
			LocalDevice device = LocalDevice.getLocalDevice();
			// System.out.println("LocalDevice: " + device.getFriendlyName());
			// System.out.println("LocalDevice: " + device.getBluetoothAddress());

			RemoteDevice[] remoteDevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
			for (RemoteDevice d : remoteDevice) {
				if (d.getFriendlyName(false).equals(deviceName)) {
					myDevice = d;
					break;
				}
			}

			if (myDevice != null) {
				System.out.println("Dispotivo: " + myDevice.getFriendlyName(false));
				System.out.println("Bluetooth address: " + myDevice.getBluetoothAddress());

				// coumnicandose con el dispositivo deseado

				String url = "btspp://" + myDevice.getBluetoothAddress()
						+ ":1;authenticate=false;encrypt=false;master=false";

				System.out.println("Connecting...");
				StreamConnection con = (StreamConnection) Connector.open(url);
				return con;
			}

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public static void close(StreamConnection conn){
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static void search() {
		Thread thread = new Thread(() -> {
			try {
				LocalDevice device = LocalDevice.getLocalDevice();
				// System.out.println("LocalDevice: " + device.getFriendlyName());
				// System.out.println("LocalDevice: " + device.getBluetoothAddress());

				RemoteDevice[] remoteDevice = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
				for (RemoteDevice d : remoteDevice) {
					if (d.getFriendlyName(false).equals(deviceName)) {
						myDevice = d;
						break;
					}
				}

				if (myDevice != null) {
					System.out.println("Dispotivo: " + myDevice.getFriendlyName(false));
					System.out.println("Bluetooth address: " + myDevice.getBluetoothAddress());

					// coumnicandose con el dispositivo deseado

					String url = "btspp://" + myDevice.getBluetoothAddress()
							+ ":1;authenticate=false;encrypt=false;master=false";

					System.out.println("Connecting...");
					StreamConnection con = (StreamConnection) Connector.open(url);
					OutputStream out = con.openOutputStream();
					out.flush();
					InputStream in = con.openInputStream();

					int consecutivo = 0;
					int last = -1;

					while (true) {
						System.out.print("waiting bottom: ");
						int value = Character.getNumericValue(in.read());
						System.out.println(value);
						Util.play("src/sonidos/sound" + String.valueOf(value) + ".wav");
						if (last == value) {
							consecutivo++;
						} else {
							consecutivo = 0;
						}
						last = value;
						if (consecutivo == 5) {
							TTS voz;
							voz = new TTS();
							voz.speak("Llendo al menú principal");
						}

					}
				} else {
					System.out.println("Device not found !");
				}
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		});
		thread.setDaemon(false);
		thread.start();

	}

}
