package com.ramHacks.docr;

import android.graphics.Bitmap;

public class DataNode {
	private Bitmap bit;
	public Bitmap getBitmap() {
		return bit;
	}

	public void setBitmap(Bitmap bit) {
		this.bit = bit;
	}

	public String getPlainText() {
		return plain_text;
	}

	public void setPlainText(String plain_text) {
		this.plain_text = plain_text;
	}

	public String getHtmlText() {
		return html_text;
	}

	public void setHtmlText(String html_text) {
		this.html_text = html_text;
	}

	private String plain_text;
	private String html_text;
	
	public DataNode(Bitmap _bit, String _plain, String _html)	{
		this.bit = _bit;
		this.plain_text = _plain;
		this.html_text = _html;
	}
}
