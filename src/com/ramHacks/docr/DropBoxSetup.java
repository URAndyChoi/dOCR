package com.ramHacks.docr;

import com.dropbox.sync.android.DbxAccountManager;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DropBoxSetup extends ActionBarActivity {
	
	private DbxAccountManager mDbxAcctMgr;
	final static int REQUEST_LINK_TO_DBX = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), getString( R.string.app_key ), getString( R.string.app_secret ) );
		mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		setContentView(R.layout.activity_drop_box_setup);
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_LINK_TO_DBX) {
	    	
	        if (resultCode == Activity.RESULT_OK) {
	        	//startActivity(new Intent( getApplicationContext(), LoadActivity.class ));
	        } else {
	        	/*
	        	Context contextFailed = getApplicationContext();
		    	CharSequence textFailed = "Your Dropbox account connection was not successful. You may have already been connected "
		    			+ "or there may have been an issue with our process.";
		    	int durationFailed = 4000;

		    	Toast toastFailed = Toast.makeText(contextFailed, textFailed, durationFailed);
		    	toastFailed.show();
		    	
		    	// Delay for Toast message to show.
		        	(new Handler())
		            .postDelayed(
		            new Runnable() {
		            public void run() {
		            // launch your activity here
		            }
		            }, 120000);
		        	*/
	        	//startActivity(new Intent( getApplicationContext(), LoadActivity.class ));
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drop_box_setup, menu);
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
}
