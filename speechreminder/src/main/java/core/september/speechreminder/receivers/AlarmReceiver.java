package core.september.speechreminder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import core.september.speechreminder.config.Config;
import core.september.speechreminder.services.SpeechService;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        long modelId = intent.getLongExtra(Config.EXTRA_FIELD, -1);
        //Event event = (Event) CRUD.getInstance().selectById(Event.class, modelId);
        //START SERVICE WITH NOTIFY EVENT
        Intent serviceIntent = new Intent(context, SpeechService.class);
        serviceIntent.putExtra(Config.EXTRA_FIELD, modelId);
        context.startService(serviceIntent);

    }


}
