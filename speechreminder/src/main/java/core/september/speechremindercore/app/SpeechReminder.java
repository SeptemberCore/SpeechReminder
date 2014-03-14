package core.september.speechremindercore.app;

import android.app.Application;

import net.danlew.android.joda.ResourceZoneInfoProvider;

/**
 * Created by christian on 13/03/14.
 */
public class SpeechReminder extends Application{

    private static SpeechReminder instance;

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

    }


}
