package core.september.speechreminder.config;


import core.september.speechreminder.iface.CRUDable;
import core.september.speechremindercore.models.Event;

/**
 * Created by christian on 19/02/14.
 */
public class Config {

    public final static String APPLICATION_ID = "7289";
    public final static String AUTHORIZATION_KEY = "HSxgbucxBk-8qvg";
    public final static String AUTHORIZATION_SECRET = "8xvwxEayHXG-X-p";

    public final static String END_OF_LINE = System.getProperty("line.separator");

    //public final static Class LANDING_ACTIVITY = ActionBarDrawerActivity.class;

    //private final static String MODEL_PACKAGE = "core.september.speechremindercore.models";


    //private static List<Class<? extends CRUDable>> models = null;
    public static Class<? extends CRUDable>[] _models = new Class[]{Event.class};


}