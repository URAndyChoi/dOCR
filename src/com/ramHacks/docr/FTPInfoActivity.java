package com.ramHacks.docr;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FTPInfoActivity extends ActionBarActivity {
	
	Button subButton;
	EditText addrEdit;
	EditText acctNameEdit;
	EditText acctPWEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftpinfo);
		subButton = (Button)findViewById(R.id.ftpProcess);
		addrEdit = (EditText)findViewById(R.id.ftp_address);
		acctNameEdit = (EditText)findViewById(R.id.ftpacct_name);
		acctPWEdit = (EditText)findViewById(R.id.ftpacct_pw);
		
		subButton.setOnClickListener(
			new View.OnClickListener()
			{
				public void onClick(View view)
			    {
					String address = addrEdit.getText().toString();
					String acctName = acctNameEdit.getText().toString();
					String acctPW = acctPWEdit.getText().toString();
					Intent i = new Intent(getApplicationContext(), AccountSelectActivity.class );
					i.putExtra("ftpAddr", address);
					i.putExtra("ftpName", acctName);
					i.putExtra("ftpPW", acctPW);
					startActivity(i);
			    }
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ftpinfo, menu);
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
