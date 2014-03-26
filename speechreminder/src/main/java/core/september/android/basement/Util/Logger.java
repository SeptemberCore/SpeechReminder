package core.september.android.basement.Util;

/**
 * Created by ilgrac on 23/03/14.
 */
public class Logger {

    public static void error(Object input,Throwable t) {
        android.util.Log.e(input.getClass().getSimpleName(),t.getMessage(),t);
    }

    public static  void debug(Object input,Throwable t) {
        android.util.Log.d(input.getClass().getSimpleName(),t.getMessage(),t);
    }

}
