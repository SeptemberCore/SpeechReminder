package core.september.speechreminder.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;


public class SpeechService extends IntentService {

    private TextToSpeech speech;

    public SpeechService() {
        super("SpeechService");
    }

    @Override public void onDestroy() {
        // Don’t forget to shutdown tts!
        if (speech != null) {
            speech.stop();
            speech.shutdown();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Locale locale = this.getResources().getConfiguration().locale;
        if (intent != null) {
            long modelId = intent.getExtras().getLong(Config.EXTRA_FIELD);
            Event event = (Event) CRUD.getInstance().selectById(Event.class, modelId);
            speech = new TextToSpeech(this,new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        if (speech.isLanguageAvailable(locale) >= 0) {
                            speech.setLanguage(locale);
                        }
                        else if (speech.isLanguageAvailable(Locale.US) >= 0) {
                            speech.setLanguage(Locale.US);
                        }
                        else if (speech.isLanguageAvailable(Locale.UK) >= 0) {
                            speech.setLanguage(Locale.UK);
                        }
                        else {
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        }
                    }
                }
            });
        }
    }


}
