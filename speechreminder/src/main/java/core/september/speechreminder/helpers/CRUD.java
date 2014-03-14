package core.september.speechreminder.helpers;

import android.util.Log;

import com.niusounds.sqlite.SQLiteDAO;

import java.util.List;

import core.september.speechreminder.app.SpeechReminder;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.iface.CRUDable;


/**
 * Created by christian on 19/02/14.
 */
public class CRUD {
    private static CRUD instance;
    private static SQLiteDAO dao;
    private final static String TAG = CRUD.class.getSimpleName();

    private CRUD() {
        //ClassPath.getTopLevelClasses("core.september.android.limboo.models");
        //Set<Class<? extends CRUDable>> classes = reflections.getSubTypesOf(CRUDable.class);
        dao = SQLiteDAO.getInstance(SpeechReminder.getInstance(), Config._models);
    }

    public static CRUD getInstance() {
        if (instance == null) {
            instance = new CRUD();
        }
        return instance;
    }

    public void insert(CRUDable crud) {
        dao.insert(crud);
    }

    public List<? extends CRUDable> select(Class<? extends CRUDable> clazz) {
        return dao.get(clazz);
    }

    public void update(CRUDable item, String whereClause, String whereArgs) {
        //   Item afterItem = new Item();
        //   afterItem.setTitle("after title");
        //   dao.update(afterItem, "title=?", "beforeTitle"); // UPDATE Item SET ... WHERE title='beforeTitle'
        dao.update(item, whereClause, whereArgs);
    }

    public void delete(CRUDable item, String whereClause, String whereArgs) {
        try {
            dao.delete(item.getClass(), whereClause, whereArgs);
        }
        catch (Throwable t) {
            Log.e(TAG,t.getMessage(),t);
        }
    }
}
