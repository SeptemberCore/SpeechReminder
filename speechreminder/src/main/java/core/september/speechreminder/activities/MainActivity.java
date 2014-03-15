package core.september.speechreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.TimeUnit;

import core.september.speechreminder.R;
import core.september.speechreminder.config.Config;
import core.september.speechreminder.helpers.DaoHelper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Class routing = (DaoHelper.getAppUser() == null) ? SignInUpActivity.class : Config.LANDING_ACTIVITY;
                startActivity(new Intent(MainActivity.this,routing));
                MainActivity.this.finish();
            }
        }, TimeUnit.SECONDS.toMillis(3));

    }


//    @Override

//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
