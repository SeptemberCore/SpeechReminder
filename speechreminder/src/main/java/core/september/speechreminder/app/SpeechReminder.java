package core.september.speechreminder.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import java.util.List;

import core.september.speechreminder.R;
import core.september.speechreminder.app.providers.TTSProvider;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;
 import org.acra.*;
    import org.acra.annotation.*;

    @ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        formUri = "http://www.bugsense.com/api/acra?api_key=2b923508",
        mode = ReportingInteractionMode.NOTIFICATION,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
        resNotifTickerText = R.string.crash_notif_ticker_text,
        resNotifTitle = R.string.crash_notif_title,
        resNotifText = R.string.crash_notif_text,
        resNotifIcon = android.R.drawable.stat_notify_error, // optional. default is a warning sign
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
        resDialogOkToast = R.string.crash_dialog_ok_toast
    )
/**
 * Created by christian on 13/03/14.
 */
public class SpeechReminder extends Application {

    public static boolean loopSpeach = true;
    private static SpeechReminder instance;
    //private AlarmReceiver alarm;
    public boolean offLine;
    public boolean signedIn;
    public boolean initialized = false;
    private TTSProvider ttsProvider;
    private SharedPreferences pref;// = PreferenceManager.getDefaultSharedPreferences(SpeechReminder.getInstance());

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
    
    public boolean needDownloadData() {
			return ttsProvider.isNeedDownloadData();
		}

    protected void initialize() {
		ACRA.init(this);
        ResourceZoneInfoProvider.init(this);
        ttsProvider = new TTSProvider();
        ttsProvider.init(this);
		
		pref = PreferenceManager.getDefaultSharedPreferences(SpeechReminder.getInstance());
        List<Event> eventList = CRUD.getInstance().select(Event.class);
        if(eventList!= null) {
			for (Event event : eventList) {
				event.assign();
				}
			}
        
        initialized = true;
        //alarm = new AlarmReceiver();
    }

    public SharedPreferences getPref() {
        return pref;
    }
    
    public boolean isInitialized() {
			return initialized;
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

	private void notifyOnBar(Class input, String title, String text) {
	
		if(pref.getBoolean("scheduleNotify" , true)) {
					Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher).build();
                //.setContentIntent(pIntent).build();
                /*.addAction(R.drawable.icon, "Call", pIntent)
                .addAction(R.drawable.icon, "More", pIntent)
                .addAction(R.drawable.icon, "And more", pIntent).build();*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        noti.defaults |= Notification.DEFAULT_LIGHTS; // LED
        noti.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        noti.defaults |= Notification.DEFAULT_SOUND; // Sound
        

        //SpeechReminder.getInstance().loopSpeach = true;
        notificationManager.notify(input.getName().hashCode(), noti);
			}


    }


}
