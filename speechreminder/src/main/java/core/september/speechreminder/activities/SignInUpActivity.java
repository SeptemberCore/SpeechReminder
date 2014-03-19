package core.september.speechreminder.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import core.september.speechreminder.R;
import core.september.speechreminder.app.SpeechReminder;


public class SignInUpActivity extends ActionBarActivity {


    protected Button mBtnSignIn;
    protected Button mBtnSignUp;
    protected Button mBtnWorkOffLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in_up);

        mBtnSignIn = (Button) findViewById(R.id.signInButton);
        mBtnSignUp = (Button) findViewById(R.id.signUpButton);
        mBtnWorkOffLine = (Button) findViewById(R.id.workOffLine);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.workOffLine:
                SpeechReminder.getInstance().offLine = true;
                break;

            case R.id.signInButton:

                break;

            case R.id.signUpButton:

                break;

        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
