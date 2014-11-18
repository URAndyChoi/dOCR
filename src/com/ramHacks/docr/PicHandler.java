package com.ramHacks.docr;

import java.io.File;
import java.io.IOException;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PicHandler extends Activity {
	final int PIC_CROP = 2;
	private ImageView view;
	private int exif_data;
	private ExifInterface exif;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic_handler);
		view = (ImageView) findViewById(R.id.imgHandler);
		onImageCapture();
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		finish();
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PIC_CROP) {
			if (data != null && resultCode == RESULT_OK) {
				// get the data from cropping
				Bundle extras = data.getExtras();
				// turn it into a bitmap
				Bitmap selectedBitmap = extras.getParcelable("data");
				// Set the view as that bitmap. Remove? maybe.
				view.setImageBitmap(selectedBitmap);
				performOCR();
			}
			else	{
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));
				finish();
			}

		}

	}

	protected void performOCR() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap bit = BitmapFactory.decodeFile(Helper.IMAGE_PATH, options);
		
		Log.v("EXIF:", "Orient: " + exif_data);
		int rotate = 0;

		/*
		 * switch (exifOrientation) { case ExifInterface.ORIENTATION_ROTATE_90:
		 * rotate = 90; break; case ExifInterface.ORIENTATION_ROTATE_180: rotate
		 * = 180; break; case ExifInterface.ORIENTATION_ROTATE_270: rotate =
		 * 270; break; }
		 */
		Log.v("EXIF:", "Rotation: " + rotate);

		if (rotate != 0) {
			int w = bit.getWidth();
			int h = bit.getHeight();

			// Setting pre rotate
			Matrix mtx = new Matrix();
			mtx.preRotate(rotate);

			// rotate bitmap
			bit = Bitmap.createBitmap(bit, 0, 0, w, h, mtx, false);
		}

		bit = bit.copy(Bitmap.Config.ARGB_8888, true);

		Log.v("OCR:", "Before tess");

		TessBaseAPI tess = new TessBaseAPI();
		tess.setDebug(true);
		tess.init(Helper.DATA_PATH, "eng");
		tess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
		tess.setImage(bit);
		String html_text = tess.getHOCRText(0);
		String plain_text = tess.getUTF8Text();

		tess.end();

		Log.v("OCR:", "PLAIN TEXT: " + plain_text);
		Log.v("OCR:", "HTML TEXT: " + html_text);

		plain_text = plain_text.trim();

		TextView _field = (TextView) findViewById(R.id.youaregod);
		if (plain_text.length() != 0) {
			_field.setText(_field.getText().toString().length() == 0 ? plain_text
					: _field.getText() + " " + plain_text);
			
			Holder.node = new DataNode(bit, plain_text, html_text);
			startActivity(new Intent(getApplicationContext(),
					SendActivity.class));
			
		}
		else	{
			Log.v("OCR:", "No text recognized.");
		}
	}

	protected void onImageCapture() {
		File file = new File(Helper.IMAGE_PATH);
		Uri uri = Uri.fromFile(file);

		// Save current image rotation data prior to cropping.
		try {
			exif = new ExifInterface(Helper.IMAGE_PATH);
			exif_data = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		cropIntent.setDataAndType(uri, "image/*");
		cropIntent.putExtra("crop", "true");
		cropIntent.putExtra("output", uri);
		cropIntent.putExtra("return-data", true);
		startActivityForResult(cropIntent, PIC_CROP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pic_handler, menu);
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
