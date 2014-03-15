package core.september.speechreminder.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.niusounds.sqlite.Persistence;
import com.niusounds.sqlite.PrimaryKey;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import core.september.speechreminder.AlarmReceiver;
import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.config.DaysOfWeek;
import core.september.speechreminder.iface.CRUDable;

/**
 * Created by christian on 13/03/14.
 */
public class Event implements CRUDable{
    @Persistence
    @PrimaryKey(autoIncrement = true)
    private long   _id;

    @Persistence
    private String title;

    @Persistence
    private String description;

    @Persistence
    private boolean allDay;

    @Persistence
    private Date start;

    @Persistence
    private Date end;

    @Persistence
    private int repeatBit;

    @Persistence
    private long lastFetch;

    @Persistence
    private long lastUpdate;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getRepeatBit() {
        return repeatBit;
    }

    public void setRepeatBit(int repeatBit) {
        this.repeatBit = repeatBit;
    }

    public long getLastFetch() {
        return lastFetch;
    }

    public void setLastFetch(long lastFetch) {
        this.lastFetch = lastFetch;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isAssignable() {

        //return new Interval( someTime.minusHours( 3 ), someTime ).contains( checkTime );
       return getRepeatBit() > 0 ? isAssignableRepeating() : isAssignableNoRepeating();
    }

    public void assign() {
        if(isAssignable()){
            setAlarm();
        }
    }

    private Event updateDate() {
        LocalDateTime lDateTime = new DateTime(start).toLocalDateTime();
        int hour = lDateTime.getHourOfDay();
        int minute =  lDateTime.getMinuteOfHour();

        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        start = calendar.getTime();
        return this;
    }

    private boolean isAssignableRepeating() {
        DateTime dateTime = new DateTime(DateTimeZone.getDefault());
        int today = dateTime.toLocalDateTime().dayOfWeek().get();
        DaysOfWeek todayOfWeek = Config.dayReference().get(today);
        return (DaysOfWeek.getRepeating(getRepeatBit()).contains(todayOfWeek)) &&
                dateTime.isBefore(updateDate().getStart().getTime());
    }

    private boolean isAssignableNoRepeating() {
        DateTime dateTime = new DateTime(DateTimeZone.getDefault());
        return dateTime.isBefore(getStart().getTime());
    }


    protected void setAlarm() {
        Context context = SpeechReminder.getInstance();
        AlarmManager am=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Config.EXTRA_FIELD, get_id());

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.cancel(pi);
        am.set(AlarmManager.RTC_WAKEUP, getStart().getTime(), pi);
    }
}
