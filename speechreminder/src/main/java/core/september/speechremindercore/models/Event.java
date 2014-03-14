package core.september.speechremindercore.models;

import com.niusounds.sqlite.Persistence;
import com.niusounds.sqlite.PrimaryKey;

import java.util.Date;

import core.september.speechremindercore.iface.CRUDable;

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

}
