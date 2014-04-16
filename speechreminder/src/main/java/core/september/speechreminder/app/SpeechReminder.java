package core.september.speechreminder.app;

import android.app.Application;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import java.util.List;

import core.september.speechreminder.app.providers.TTSProvider;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 13/03/14.
 */
public class SpeechReminder extends Application {

    public static boolean loopSpeach = true;
    private static SpeechReminder instance;
    //private AlarmReceiver alarm;
    public boolean offLine;
    public boolean signedIn;
    private TTSProvider ttsProvider;

    //public Event selectedEvent;
    public static SpeechReminder getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        instance.initialize();
    }

    protected void initialize() {
        ResourceZoneInfoProvider.init(this);
        ttsProvider = new TTSProvider();
        ttsProvider.init(this);

        List<Event> eventList = CRUD.getInstance().select(Event.class);
        for (Event event : eventList) {
            event.assign();
        }
        //alarm = new AlarmReceiver();
    }

    public TTSProvider getTTSProvider() {
        return ttsProvider;
    }

    public void stopSpeach() {
        loopSpeach = false;
        ttsProvider.stop();
    }

    public Event getEvent(long id) {
        try {
            return ((Event) CRUD.getInstance().selectById(Event.class, id));
        } catch (Throwable t) {
            android.util.Log.e(this.getClass().getSimpleName(), t.getMessage(), t);
            return null;
        }

    }


    /*public void setAlarm() {

    }*/


}
