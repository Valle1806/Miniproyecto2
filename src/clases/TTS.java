package clases;

import java.io.IOException;
import java.io.InputStream;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class TTS {
	//comentario
	private InputStream sinte;
	private AdvancedPlayer player;

	public TTS() {
		try {
			sinte = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw").getMP3Data("");
			player = new AdvancedPlayer(sinte);
		} catch (Exception e) {

		}

	}

	public void stop() {
		try {
			player.close();
		} catch (Exception e) {
			System.out.println("problemas para parar");
		}

	}

	private void speakAux(String text) {
		Thread thread = new Thread(() -> {
			try {
				player.close();
				sinte = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw").getMP3Data(text);
				player = new AdvancedPlayer(sinte);
				player.play();
			} catch (IOException | JavaLayerException e) {
				System.out.println("No internet, no TTS");
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public void speak(String text) {
		speakAux(text);
	}

	public void speak(Pregunta p) {
		speakAux("\nSección de preguntas, " + p.getPregunta() + "\nOpción A. " + p.getRespuestas()[0].getRespuesta()
				+ "\n" + "\nOpción B. " + p.getRespuestas()[1].getRespuesta() + "\n" + "\nOpción C. "
				+ p.getRespuestas()[2].getRespuesta() + "\n" + "\nOpción D. " + p.getRespuestas()[3].getRespuesta()
				+ "\n" + "\n Use las cartas de respuesta");
	}
}
