package core.september.speechreminder.helper;

import com.quickblox.core.QBCallback;
import com.quickblox.module.users.QBUsers;
import com.quickblox.module.users.model.QBUser;

import core.september.speechreminder.config.QBQueries;

public class AuthHelper {
	
	public static void singIn(String login, String password, QBCallback callback, QBQueries context) {
        QBUser qbUser = new QBUser(login, password);
        QBUsers.signIn(qbUser, callback, context);
    }
    
    public static void signOut(QBCallback callback, QBQueries context) {
        QBUsers.signOut(callback, context);
    }

    public static void signUp(String login, String password, QBCallback callback, QBQueries context) {
        QBUser qbUser = new QBUser();
        qbUser.setLogin(login);
        qbUser.setPassword(password);
        QBUsers.signUpSignInTask(qbUser, callback, context);
    }
	
	
	}

