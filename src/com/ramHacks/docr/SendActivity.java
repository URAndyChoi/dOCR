package com.ramHacks.docr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.commons.net.ftp.FTPClient;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountInfo;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SendActivity extends ActionBarActivity {

	private DbxAccountManager mDbxAcctMgr;
	
	public void sendFinal(View view) throws IOException{
		
		RadioButton ftp;
		ftp = (RadioButton) findViewById(R.id.radioFTP);
		
		if( ftp.isChecked() ){
			boolean isConnected = false;
			FTPClient client = new FTPClient();
			
			client.connect(FTPInfo.address);
			
		    if(client.isConnected())
		    {
		        isConnected = client.login(FTPInfo.acctName, FTPInfo.acctPW);
		        
		        if( isConnected )
		         {	
//		        	  Calendar c = Calendar.getInstance();
//		        	  int seconds = c.get(Calendar.SECOND);
//		        	  FileOutputStream fOut = new FileOutputStream("out_"+seconds+".html");
//		        	  final String s = Holder.node.getHtmlText();
//		        	  
//		        	  fOut.write(s.getBytes());
//		        	  fOut.flush();
//		        	  fOut.close();
//		        	  // FileInputStream in = new FileInputStream(new File(""));
		              OutputStream s = client.storeUniqueFileStream(Holder.node.getHtmlText());
		              s.flush();
		              s.close();
		              client.logout();
		              Toast toast = Toast.makeText(getApplicationContext(), "Document Sent!", 3000);
		              toast.show();
		         }
		        client.disconnect();
		    }
		}
		
		RadioButton dropBox; 
		
		dropBox = (RadioButton) findViewById(R.id.radioDropbox);
		if( dropBox.isChecked() ){
			if( mDbxAcctMgr.getLinkedAccount().isLinked() ){
				Calendar c = Calendar.getInstance();
	        	int seconds = c.get(Calendar.SECOND);
				
				DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
				DbxPath newPath = new DbxPath("out_"+seconds+".html");
				
				
				DbxFile curFile = dbxFs.create(newPath);
				try{
					curFile.writeString(Holder.node.getHtmlText());
				}
				finally{
					curFile.close();
					Toast toast = Toast.makeText(getApplicationContext(), "Document Sent!", 3000);
					toast.show();
				}
			}
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		TextView v = (TextView) findViewById(R.id.editText1);
		v.setText(Holder.node.getPlainText());
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), getString( R.string.app_key ), getString( R.string.app_secret ) );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
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
