package core.september.speechreminder.helpers;

import java.util.List;

import core.september.speechremindercore.models.AppUser;

/**
 * Created by christian on 19/02/14.
 */
public class DaoHelper {
    private static AppUser user;

    public static AppUser getAppUser() {
        if (user == null) {
            List<AppUser> usersList = (List<AppUser>) CRUD.getInstance().select(AppUser.class);
            if (usersList != null && usersList.size() > 0) {
                user = usersList.get(0);
            }
            //user = CRUD.getInstance().select(AppUser.class);
        }
        return user;
    }

    public static void setAppUser(AppUser appUser) {
        CRUD.getInstance().delete(appUser, null, null);
        CRUD.getInstance().insert(appUser);
    }

}
