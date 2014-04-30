package core.september.speechreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import core.september.speechreminder.R;
import core.september.speechreminder.app.SpeechReminder;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //findViewById(android.R.id.title).setColo

        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        Boolean needData = SpeechReminder.getInstance().needDownloadData();


        
        if(needData) {

            TextView disclaimerText = (TextView) findViewById(R.id.disclaimerText);
            final String NEW_LINE = System.getProperty("line.separator");

            StringBuilder builder = new StringBuilder();
            builder.append("DISCLAIMER :");
            builder.append(NEW_LINE);
            builder.append("It appear you have no valid TTS data installed on device");
            builder.append(NEW_LINE);
            builder.append("please TOUCH HERE to download TTS data and after restart this app");


            disclaimerText.setText(builder.toString());

            disclaimerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent installIntent = new Intent();
                    installIntent.setAction(
                            TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installIntent);
                    finish();
                }
            });
			}

		else {

            findViewById(R.id.disclaimerText).setVisibility(View.GONE);

            (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Class routing = (DaoHelper.getAppUser() == null) ? SignInUpActivity.class : Config.LANDING_ACTIVITY;
                Class routing = SpeechReminderActivity.class;
                startActivity(new Intent(MainActivity.this, routing));
                MainActivity.this.finish();
            }
        }, TimeUnit.SECONDS.toMillis(3));
			
			
			}


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
