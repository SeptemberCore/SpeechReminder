package core.september.speechreminder.app;

import android.app.Application;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import core.september.speechreminder.app.providers.TTSProvider;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 13/03/14.
 */
public class SpeechReminder extends Application{

    private static SpeechReminder instance;

    //private AlarmReceiver alarm;
    public boolean offLine;
    public boolean signedIn;
    public boolean loopSpeach = true;
    private TTSProvider ttsProvider;
    public Event selectedEvent;
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
        //alarm = new AlarmReceiver();
    }

    public TTSProvider getTTSProvider() {
        return ttsProvider;
    }
    
    public void stopSpeach() {
			loopSpeach = false;
			ttsProvider.stop();
			//loopSpeach = true;
		}

    /*public void setAlarm() {

    }*/


}
