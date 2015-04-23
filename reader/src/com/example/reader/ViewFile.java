package com.example.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class ViewFile extends Activity {
	
	private String filenameString;
//	private static final String defaultCode = "UTF-8";
	private static final String defaultCode = "GB2312";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		try {
			Bundle bunde = this.getIntent().getExtras();
//			filenameString = bunde.getString("fileName");
			filenameString = "/storage/sdcard0/QQReader/books/ÖÜÒ×.txt";
			refreshGUI(defaultCode);
		} catch (Exception e) {
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.reader, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.directory:
            Intent intent1 = new Intent(ViewFile.this,Directory.class);
            startActivity(intent1);
            ViewFile.this.finish();
			break;
		case R.id.settings:
            Intent intent2 = new Intent(ViewFile.this,Settings.class);
            startActivity(intent2);
            ViewFile.this.finish();
			break;
		case R.id.about:
			doAbout();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void doAbout() {
		Dialog dialog = new AlertDialog.Builder(ViewFile.this).setTitle(
				R.string.aboutTitle).setMessage(R.string.aboutInfo)
				.setPositiveButton(R.string.aboutOK,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).create();
		dialog.show();
	}
	
	private void refreshGUI(String code){
		TextView tv = (TextView) findViewById(R.id.view_contents);
		String fileContent = getStringFromFile(code);
		tv.setText(fileContent);
	}
	
	public String getStringFromFile(String code)
	{
		try {
			StringBuffer sBuffer = new StringBuffer();
			FileInputStream fileInputStream = new FileInputStream(filenameString);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, code);
			BufferedReader in = new BufferedReader(inputStreamReader);
			if(!new File(filenameString).exists())
			{
				Toast.makeText(ViewFile.this, "file not exist!", Toast.LENGTH_SHORT).show();
				return null;
			}
			while (in.ready()) {
				sBuffer.append(in.readLine() + "\n");
			}
			in.close();
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public void readFile(){
//		StringBuffer sb = new StringBuffer();
//		File file = new File("myfile.txt");
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		String line = "";
//		while((line = br.readLine())!=null){
//		sb.append(line);
//		}
//		br.close();
//		tv.setText(sb.toString());
//
//	}
}