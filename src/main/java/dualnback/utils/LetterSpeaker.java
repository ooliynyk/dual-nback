package dualnback.utils;

import java.util.Locale;

import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import dualnback.core.Letter;

public class LetterSpeaker {

	private Synthesizer synthesizer;

	static {
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}

	public LetterSpeaker() {
		try {
			SynthesizerModeDesc desc = new SynthesizerModeDesc(Locale.US);
			synthesizer = Central.createSynthesizer(desc);
			synthesizer.allocate();
			synthesizer.resume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void speak(Letter letter) {
		synthesizer.speakPlainText(letter.name(), null);
		try {
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LetterSpeaker s = new LetterSpeaker();
		s.speak(Letter.B);
	}

}
