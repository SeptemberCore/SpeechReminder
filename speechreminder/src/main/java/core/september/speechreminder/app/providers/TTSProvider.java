package core.september.speechreminder.app.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private String message;
    private Locale correctLocale;

    public boolean isNeedDownloadData() {
        return needDownloadData;
    }
    
    public Locale correctLocale() {
			return correctLocale;
		}

    public void init(Context context) {
        if (tts == null) {
            tts = new TextToSpeech(context, this);
        }
        this.context = context;
    }

    public void say(String sayThis) {
        android.util.Log.d(this.getClass().getSimpleName(), "Trying to say: " + sayThis);
        if(!needDownloadData) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, sayThis);
				// textToSpeech.speak(tts,TextToSpeech.QUEUE_FLUSH,params);
				tts.speak(sayThis, TextToSpeech.QUEUE_ADD, params);
			}
        
    }

    public void sayOne(String sayThis) {
        android.util.Log.d(this.getClass().getSimpleName(), "Trying to sayOne: " + sayThis);
        if (!needDownloadData) {
            tts.speak(sayThis, TextToSpeech.QUEUE_ADD, null);
        }

    }


    public void stop() {
        tts.stop();
    }

    public boolean isSpeaking() {
        return tts.isSpeaking();
    }

    @Override
    public void onInit(int status) {
        //Locale loc = new Locale("de", "", "");


        correctLocale = tts.isLanguageAvailable(context.getResources().getConfiguration().locale) == TextToSpeech.LANG_NOT_SUPPORTED ?
                Locale.US : context.getResources().getConfiguration().locale;
                
//        if(tts.isLanguageAvailable(context.getResources().getConfiguration().locale) == TextToSpeech.LANG_NOT_SUPPORTED) {
//				SpeechReminder.getInstance().notifyOnBar(tts.getClass(),"Locale proplem",
//					"Current locale "+context.getResources().getConfiguration().locale+
//					" is not supported, i'll try to use "+Locale.US);
//			}

        needDownloadData = tts.isLanguageAvailable(correctLocale) == TextToSpeech.LANG_MISSING_DATA;
        
//        if(needDownloadData) {
//				SpeechReminder.getInstance().notifyOnBar(TTSProvider.class,"Locale proplem",
//					"Locale data for "+context.getResources().getConfiguration().locale+
//					" are missing, try to download it"
//				);
//			}

        if (tts.isLanguageAvailable(correctLocale) >= TextToSpeech.LANG_AVAILABLE) {
            tts.setLanguage(correctLocale);
            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SpeechReminder.getInstance());
            tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

            @Override
            public void onUtteranceCompleted(String utteranceId) {
                if (SpeechReminder.getInstance().loopSpeach
                       && pref.getBoolean("loopAlarm" , true) ) {
                    tts.playSilence(500, TextToSpeech.QUEUE_ADD, null);
                    TTSProvider.this.say(utteranceId);
                }


            }
        });
        }

        
    }


    public void shutdown() {
        tts.shutdown();
    }
    
    public void reset() {
		if(tts!= null) {
			try {
				tts.shutdown();
				tts = null;
				}
			catch(Throwable e) {
				android.util.Log.e(this.getClass().getSimpleName(),e.getMessage(),e);
				}
			}
		}
}
