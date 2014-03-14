package core.september.speechremindercore.iface;

import com.quickblox.core.result.Result;

/**
 * Created by christian on 28/02/14.
 */
public interface AuthCallback {
    void onSessionSuccess(Result result);

    void onSessionError(Result result);

    void onAuthSuccess(Result result);

    void onAuthError(Result result);
}
