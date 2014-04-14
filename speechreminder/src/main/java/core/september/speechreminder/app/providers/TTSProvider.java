package core.september.speechreminder.app.providers;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

import core.september.speechreminder.app.SpeechReminder;

/**
 * Created by christian on 09/04/14.
 */
public class TTSProvider extends HashMap<String, String> implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Context context;
    private boolean needDownloadData = false;

    public boolean isNeedDownloadData() {
        return needDownloadData;
    }

    public void init(Context context) {
        if (tts == null) {
            tts = new TextToSpeech(context, this);
        }
        this.context = context;
    }

    private String message;

    public void say(String sayThis) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,sayThis);
       // textToSpeech.speak(tts,TextToSpeech.QUEUE_FLUSH,params);
        tts.speak(sayThis, TextToSpeech.QUEUE_FLUSH,params);
    }


    
    public void stop() {
		tts.stop();
	}

    @Override
    public void onInit(int status) {
        //Locale loc = new Locale("de", "", "");


        Locale correctLocale = tts.isLanguageAvailable(context.getResources().getConfiguration().locale) == TextToSpeech.LANG_NOT_SUPPORTED?
                Locale.US : context.getResources().getConfiguration().locale;

        needDownloadData = tts.isLanguageAvailable(correctLocale) == TextToSpeech.LANG_MISSING_DATA;

        if (tts.isLanguageAvailable(correctLocale) >= TextToSpeech.LANG_AVAILABLE) {
            tts.setLanguage(correctLocale);
        }

        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

            @Override
            public void onUtteranceCompleted(String utteranceId) {
                if(SpeechReminder.getInstance().loopSpeach) {
                    tts.playSilence(500,TextToSpeech.QUEUE_ADD,null);
                    TTSProvider.this.say(utteranceId);
                }


            }
        });
    }



    public void shutdown() {
        tts.shutdown();
    }
}
