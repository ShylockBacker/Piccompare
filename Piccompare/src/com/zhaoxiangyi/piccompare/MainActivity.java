package com.zhaoxiangyi.piccompare;

import com.zhaoxiangyi.piccompare.utils.PictureContrast;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "Piccomparemain";
//	private String url1 = null;
//	private String url2 = null;
//	private String intent1 = null;
//	private String intent2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		intent1 = getIntent().getCharSequenceExtra("url1").toString();
//		intent2 = getIntent().getCharSequenceExtra("url2").toString();
//    	Log.i(LOG_TAG, "url1= " + intent1);
//    	Log.i(LOG_TAG, "url2= " + intent2);
    	
		Button compare = (Button) findViewById (R.id.compare);
		final EditText picurl1 = (EditText) findViewById (R.id.url1);
		final EditText picurl2 = (EditText) findViewById (R.id.url2);
		final TextView result = (TextView) findViewById (R.id.TextView);
		
		compare.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
//				url1 = intent1;
//				url2 = intent2;
				String url1 = picurl1.getText().toString();
				String url2 = picurl2.getText().toString();
		    	Log.i(LOG_TAG, "url1= " + url1);
		    	Log.i(LOG_TAG, "url2= " + url2);
				if (url1.equals("") && url2.equals("")){
					Log.i(LOG_TAG, "url1 and url2 is all null");
					url1 = "/mnt/sdcard/test/arrow.png";
					url2 = "/mnt/sdcard/test/bg_audio.png";
				}
				String percentage = compare(url1, url2);
				result.setText(percentage);
			}
		});
	}
	
	protected String compare(String url1, String url2){
		String percentage = PictureContrast.similarity(url1, url2);
		
		return percentage;
	}

}
