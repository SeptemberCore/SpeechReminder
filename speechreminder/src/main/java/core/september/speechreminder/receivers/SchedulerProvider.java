package core.september.speechreminder.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.models.Event;

public class SchedulerProvider extends BroadcastReceiver {
    public SchedulerProvider() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        List<Event> eventList = CRUD.getInstance().select(Event.class);
        for(Event event: eventList) {
            event.assign();
        }

        //Context context = SpeechReminder.getInstance();
        AlarmManager am=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(context, this.getClass());
        //intent.putExtra(Config.EXTRA_FIELD, get_id());

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.cancel(pi);

        DateTime date = new DateTime(DateTimeZone.getDefault()).toLocalDate().toDateTimeAtStartOfDay();
        DateTime tomorrow = date.plusDays(1);

        am.set(AlarmManager.RTC_WAKEUP, tomorrow.toDateTime().toDate().getTime(), pi);
    }
}
