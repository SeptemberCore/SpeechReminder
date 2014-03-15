package core.september.speechreminder;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.CRUD;
import core.september.speechreminder.helpers.DaoHelper;
import core.september.speechreminder.iface.CRUDable;
import core.september.speechreminder.models.Event;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        long modelId = intent.getExtras().getLong(Config.EXTRA_FIELD);
        Event event = (Event) CRUD.getInstance().selectById(Event.class, modelId);
        //START SERVICE WITH NOTIFY EVENT

    }


}
