package clases;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Util {
	public static void play(String path){
		try {
			Clip sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File(path)));
			sonido.start();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
				
	}
}
