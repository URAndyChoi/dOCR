package com.ramHacks.docr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class LoadActivity extends Activity {
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    
    public void skipLoad(View view){
    	Context context = getApplicationContext();
    	CharSequence text = "You impatient motherfucker!";
    	int duration = 2000;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    	startActivity(new Intent( getApplicationContext(), MainActivity.class ));
    	finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_screen);
        // Delay needs to be checked for input sanitization, i.e. if the user presses a button
        // while we are delayed, what happens? Does the app respond? Crash? etc.
        handler.postDelayed(new Runnable() {
            @Override
         
            public void run() {
                // Do something after 10s = 10000ms
                startActivity(new Intent( getApplicationContext(), MainActivity.class ));
                finish();
                // You could do this call if you wanted it to be periodic:
            }
        }, 7500);   // Temporary 1 second delay. Change to 3 seconds in the future.
    }
    
}
