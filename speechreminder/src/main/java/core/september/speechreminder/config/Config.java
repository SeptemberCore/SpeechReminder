package core.september.speechreminder.config;


import android.util.SparseArray;

import org.joda.time.DateTimeConstants;

import core.september.speechreminder.activities.SignInUpActivity;
import core.september.speechreminder.activities.SpeechReminderActivity;
import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.iface.CRUDable;
import core.september.speechreminder.models.AppUser;
import core.september.speechreminder.models.Event;

/**
 * Created by christian on 19/02/14.
 */
public class Config {

    public final static String APPLICATION_ID = "7289";
    public final static String AUTHORIZATION_KEY = "HSxgbucxBk-8qvg";
    public final static String AUTHORIZATION_SECRET = "8xvwxEayHXG-X-p";
    public final static String REVMOB_APP_ID = "53677ee5b39360ed0773fc39";
    public final static int NULL = -1;
    public final static String END_OF_LINE = System.getProperty("line.separator");
    public final static Class LANDING_ACTIVITY = SignInUpActivity.class;
    public final static Class PLANNING_ACTIVITY = SpeechReminderActivity.class;
    public final static String DATE_FORMAT = "yyyy/MM/dd";
    public final static String DATE_SPLIT = "/";
    public final static String HOUR_SPLIT = ":";
    public final static String SPACE = " ";
    public final static String SHORT_DATE_FORMAT = "dd, MMMM";
    //private static List<Class<? extends CRUDable>> models = null;
    public static Class<? extends CRUDable>[] _models = new Class[]{Event.class, AppUser.class};
    //public static final String PICKED_ITEM = "PICKED_ITEM";
    public static String EXTRA_FIELD = "MODEL_ID";
    private static Config instance;
    //private final static String MODEL_PACKAGE = "core.september.speechremindercore.models";
    private static SparseArray<DaysOfWeek> _dayReference;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static SparseArray<DaysOfWeek> dayReference() {
        if (_dayReference == null) {
            _dayReference = new SparseArray<DaysOfWeek>();
            _dayReference.append(DateTimeConstants.SUNDAY, DaysOfWeek.SUNDAY);
            _dayReference.append(DateTimeConstants.MONDAY, DaysOfWeek.MONDAY);
            _dayReference.append(DateTimeConstants.TUESDAY, DaysOfWeek.TUESDAY);
            _dayReference.append(DateTimeConstants.WEDNESDAY, DaysOfWeek.WEDNESDAY);
            _dayReference.append(DateTimeConstants.THURSDAY, DaysOfWeek.THURSDAY);
            _dayReference.append(DateTimeConstants.FRIDAY, DaysOfWeek.FRIDAY);
            _dayReference.append(DateTimeConstants.SATURDAY, DaysOfWeek.SATURDAY);
        }
        return _dayReference;
    }

    public Boolean IS24HOURVIEW() {
        return SpeechReminder.getInstance().getPref().getBoolean("24hFormat", true);
    }

    public String SHORT_HOUR_FORMAT() {
        return IS24HOURVIEW() ? "HH:mm" : "hh:mm aa";
    }

    public String HOUR_FORMAT() {
        return SHORT_HOUR_FORMAT();
    }


}
