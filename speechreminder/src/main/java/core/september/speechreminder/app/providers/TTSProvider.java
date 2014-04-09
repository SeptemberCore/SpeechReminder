package core.september.speechreminder.app.providers;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by christian on 09/04/14.
 */
public class TTSProvider implements TextToSpeech.OnInitListener {
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


    public void say(String sayThis) {
        tts.speak(sayThis, TextToSpeech.QUEUE_FLUSH, null);
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
    }



    public void shutdown() {
        tts.shutdown();
    }
}
