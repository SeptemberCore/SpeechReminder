package core.september.android.basement;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import core.september.android.basement.Util.Logger;
import core.september.speechreminder.R;

/**
 * Created by christian on 21/03/14.
 */
public abstract class AbstractNavigationDrawerActivity extends ActionBarActivity {

    protected abstract int contentView();
    protected abstract int drawerLayout();
    protected abstract int leftDrawer();
    protected abstract ArrayAdapter mDrawerListAdapter();
    protected abstract void selectItem(int position);
    protected abstract boolean handleNavigationButton(MenuItem item, FallBackDefault fallBackDefault);

    protected abstract int drawerImageRes();
    protected abstract int openDrawerContentDescRes();
    protected abstract int closeDrawerContentDescRes();

    protected abstract int drawerShadow();

    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());

        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(getResources().getColor(R.color.WhiteSmoke));
            }
        }
        //mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(drawerLayout());
        mDrawerList = (ListView) findViewById(leftDrawer());
        mDrawerLayout.setDrawerShadow(drawerShadow(), GravityCompat.START);

        // Set the adapter for the list view
        mDrawerList.setAdapter(mDrawerListAdapter());
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTitle = mDrawerTitle = getTitle();
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerImageRes(), openDrawerContentDescRes(), closeDrawerContentDescRes()) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        //return super.onOptionsItemSelected(item);
        return handleNavigationButton(item,new FallBackDefault(){
           public boolean doDefault() {
               return  AbstractNavigationDrawerActivity.super.onOptionsItemSelected(item);
            }
        });
    }

    public interface FallBackDefault {
        boolean doDefault();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        int menuSize = menu.size();
        for(int i = 0; i< menuSize; i++) {
            if(menu.getItem(i).getTitle().equals(mTitle)) {
                Logger.debug(this, new Throwable(""+menu.getItem(i).getTitle()));
                menu.getItem(i).setVisible(!drawerOpen);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
