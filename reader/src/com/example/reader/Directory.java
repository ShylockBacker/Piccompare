package com.example.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Directory extends Activity {

	private static final String LOG_TAG = "Directory";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directory);
		ListDirectory();
	}

	private void ListDirectory() {
		new Handler().post(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				File file = new File("/storage/sdcard0/QQReader/books/ÖÜÒ×.txt");
				if (!file.exists()) {
					Toast.makeText(Directory.this, "file not exist!",
							Toast.LENGTH_SHORT).show();
					return;
				}
				FileInputStream fis;
				final String RE = "([µÚ].{1,5}[ÕÂ])(.+)";
				try {
					fis = new FileInputStream(file);
					BufferedReader dr = new BufferedReader(
							new InputStreamReader(fis, "GBK"));
					String line = null;
					long offset = 0;
					while ((line = dr.readLine()) != null) {
						if (line.trim().matches(RE)) {
							Log.d(LOG_TAG, offset + "" + line.trim());
						}
						;
						offset = offset + line.length() + 2;
					}
					dr.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}