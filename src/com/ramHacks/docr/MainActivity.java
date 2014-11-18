package com.ramHacks.docr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	public enum STATE {
		NOTHING, PICTURED, CROPPED;
	}

	private STATE current_state = STATE.NOTHING;
	final int TAKE_PICTURE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupApp();
	}

	private void setupApp() {
		// Move language file from apk assets into the sdcard directory
		if (!(new File(Helper.DATA_PATH + "tessdata/" + "eng" + ".traineddata"))
				.exists()) {
			try {

				AssetManager asset_managers = getAssets();
				InputStream in = asset_managers.open("eng.traineddata");
				File directory = new File(Helper.DATA_PATH + "tessdata/");
				directory.mkdirs();
				OutputStream out = new FileOutputStream(Helper.DATA_PATH
						+ "tessdata/" + "eng" + ".traineddata");

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException e) {
				Log.e("ERROR", "Unable to copy english data! " + e.toString());
			}
		}

		Helper.IMAGE_PATH = Helper.DATA_PATH + "ocr.jpg";
		launchCamera();
	}

	protected void launchCamera() {
		if (current_state == STATE.NOTHING) {
			File file = new File(Helper.IMAGE_PATH);
			Uri output_uri = Uri.fromFile(file);

			final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, output_uri);
			startActivityForResult(intent, TAKE_PICTURE);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PICTURE) {
			// Call the picture handler.
			if (resultCode == RESULT_OK) {
				current_state = STATE.PICTURED;
				startActivity(new Intent(getApplicationContext(),
						PicHandler.class));
				finish();
			}
			else {
				finish();
			}

		} else {
			Log.v("CAMERA:", "User cancelled");
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("current_state", current_state.ordinal());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.i("", "onRestoreInstanceState()");
//		int restore_state = (int) savedInstanceState.get("current_state");
//		if (restore_state == STATE.NOTHING.ordinal()) {
//			current_state = STATE.NOTHING;
//			setupApp();
//		} else if (restore_state == STATE.PICTURED.ordinal()) {
//			current_state = STATE.PICTURED;
//			// onPhotoTaken(Uri.parse(Helper.IMAGE_PATH) );
//		} else if (restore_state == STATE.CROPPED.ordinal()) {
//			current_state = STATE.CROPPED;
//			startActivity(new Intent(getApplicationContext(), PicHandler.class));
//			finish();
//		}
	}

}
