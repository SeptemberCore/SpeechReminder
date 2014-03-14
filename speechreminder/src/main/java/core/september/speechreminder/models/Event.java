package core.september.speechreminder.models;

import com.niusounds.sqlite.Persistence;
import com.niusounds.sqlite.PrimaryKey;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

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
        DateTime dateTime = new DateTime(DateTimeZone.getDefault());
        //return new Interval( someTime.minusHours( 3 ), someTime ).contains( checkTime );
       return getRepeatBit() > 0 ? isAssignableRepeating() : isAssignableNoRepeating();
    }

    private boolean isAssignableRepeating() {
        return true;
    }

    private boolean isAssignableNoRepeating() {
        return true;
    }
}
