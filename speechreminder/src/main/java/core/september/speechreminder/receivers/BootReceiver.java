package core.september.speechreminder.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
/*        List<Event> eventList = CRUD.getInstance().select(Event.class);
        for(Event event: eventList) {
            event.assign();
        }*/

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(context, SchedulerProvider.class);
        //intent.putExtra(Config.EXTRA_FIELD, get_id());

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //am.cancel(pi);

        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }
}
