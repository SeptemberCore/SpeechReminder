package core.september.speechreminder.services;

import android.app.IntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;

import java.util.Locale;

import core.september.speechreminder.R;
import core.september.speechreminder.activities.SpeechReminderActivity;
import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;


public class SpeechService extends IntentService {


    private long modelId;
    private Event event;

    public SpeechService() {
        super("SpeechService");
    }



    //@SuppressWarnings("deprecation")
    private void notifyOnBar() {

        Intent intent = new Intent(this, SpeechReminderActivity.DetailsActivity.class);
        intent.putExtra(Config.EXTRA_FIELD,modelId);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle(event.getTitle())
                .setContentText(event.getStartHour())
                .setSmallIcon(R.drawable.ic_stat_device_access_alarms)
                .setContentIntent(pIntent).build();
                /*.addAction(R.drawable.icon, "Call", pIntent)
                .addAction(R.drawable.icon, "More", pIntent)
                .addAction(R.drawable.icon, "And more", pIntent).build();*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        noti.defaults |= Notification.DEFAULT_LIGHTS; // LED
        noti.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        noti.defaults |= Notification.DEFAULT_SOUND; // Sound

        notificationManager.notify(Long.valueOf(modelId).intValue(), noti);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Locale locale = this.getResources().getConfiguration().locale;
        if (intent != null) {
            modelId = intent.getExtras().getLong(Config.EXTRA_FIELD);
            event = (Event) CRUD.getInstance().selectById(Event.class, modelId);

            notifyOnBar();
            SpeechReminder.getInstance().getTTSProvider().say(event.getDescription());
        }
    }


}
