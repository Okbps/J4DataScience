package ch10VisualAudio;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

public class TTSExample {
    SynthesizerModeDesc desc;
    Synthesizer synthesizer;
    Voice voice;

    public void init(String voiceName) throws EngineException, AudioException,
            EngineStateError, PropertyVetoException {
        if (desc == null) {
            //default
//			System.setProperty("freetts.voices",
//					"com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            //have to be setup
            System.setProperty("freetts.voices", "de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");
            desc = new SynthesizerModeDesc(Locale.US);
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
            SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer
                    .getEngineModeDesc();
            Voice[] voices = smd.getVoices();
            Voice voice = null;
            for (int i = 0; i < voices.length; i++) {
                if (voices[i].getName().equals(voiceName)) {
                    voice = voices[i];
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        }
    }

    public void terminate() throws EngineException, EngineStateError {
        synthesizer.deallocate();
    }

    public void doSpeak(String speakText) throws EngineException,
            AudioException, IllegalArgumentException, InterruptedException {
        synthesizer.speakPlainText(speakText, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("mbrola.base", "d:\\mbrola");
        TTSExample su = new TTSExample();

        //have to be setup on your env
        su.init("mbrola_us1");

        //default
        //su.init("kevin16");
        //su.init("kevin");
        //su.doSpeak("Hello world!");
        su.doSpeak(SAMPLE);
        su.terminate();
    }

    final static String SAMPLE = "Wiki said, Floyd Mayweather, Jr. is an American professional boxer. He is currently undefeated as a professional and is a five-division world champion, having won ten world titles and the lineal championship in four different weight classes";
}
