package com.ramHacks.docr;

import java.io.IOException;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountInfo;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AccountSelectActivity extends ActionBarActivity {
	
	private DbxAccountManager mDbxAcctMgr;
	final static int REQUEST_LINK_TO_DBX = 0;
	
	public void dropBoxAcct(View view){
		// mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), getString( R.string.app_key ), getString( R.string.app_secret ) );
		if( mDbxAcctMgr.hasLinkedAccount() ){
			DbxAccount cur = mDbxAcctMgr.getLinkedAccount();
			DbxAccountInfo curAcct = cur.getAccountInfo();
			String name = curAcct.displayName;
			Context contextAlready= getApplicationContext();
	    	CharSequence textAlready = "You have already linked your account, "+name+"!";
	    	int durationAlready = 4000;
	    	Toast toastAlready = Toast.makeText(contextAlready, textAlready, durationAlready);
	    	toastAlready.show();
		}
		else{
			mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		}
	}
	
	public void dropBoxClear(View view){
		if( mDbxAcctMgr.hasLinkedAccount() ){
			mDbxAcctMgr.unlink();
		}
		else{
			Context contextAlready= getApplicationContext();
	    	CharSequence textAlready = "You have no account that is currently linked.";
	    	int durationAlready = 4000;
	    	Toast toastAlready = Toast.makeText(contextAlready, textAlready, durationAlready);
	    	toastAlready.show();
		}
	}
	
	public void ftpAcct(View view){
		
	}
	
	public void sendText(View view){
		startActivity(new Intent( getApplicationContext(), SendActivity.class) );
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Account manager object will allow us to reference the user's Dropbox acct. 
		setContentView(R.layout.activity_account_select);
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), getString( R.string.app_key ), getString( R.string.app_secret ) );
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0 ) {
	        if (resultCode == Activity.RESULT_OK) {
	        	Context context= getApplicationContext();
		    	CharSequence text = "Your Dropbox account connection was successful.";
		    	int duration = 4000;

		    	Toast toast = Toast.makeText(context, text, duration);
		    	toast.show();
		    	/*
		    	try {
					DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
					DbxFile testFile = dbxFs.create(new DbxPath("hello.txt"));
					try {
					    testFile.writeString("Hello Dropbox!");
					} 
					// ****** Exceptions to cover our Drobbox found below. *******
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					    testFile.close();
					}
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidPathException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DbxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	// ****** Exceptions to cover our Drobbox found above. *******
	        	*/
	        	//startActivity(new Intent( getApplicationContext(), LoadActivity.class ));
	        } else {
	        	
	        	Context contextFailed = getApplicationContext();
		    	CharSequence textFailed = "Your Dropbox account connection was not successful. You may have already been connected "
		    			+ "or there may have been an issue with our process.";
		    	int durationFailed = 4000;

		    	Toast toastFailed = Toast.makeText(contextFailed, textFailed, durationFailed);
		    	toastFailed.show();
		    	
		    	
	        }
	    } else {
	        //super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_select, menu);
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
