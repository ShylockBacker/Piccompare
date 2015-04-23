package com.example.reader1;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends ListActivity {

	private static final String LOG_TAG = "Search";
	ArrayList<String> bookpath = new ArrayList<String>();
	ArrayList<String> bookname = new ArrayList<String>();
	
	EditText keywordText = (EditText) this.findViewById(R.id.keyword);
	Button search = (Button) this.findViewById(R.id.search);
	Button back = (Button) this.findViewById(R.id.back);	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String keyword = keywordText.getText().toString();
				if (keyword.equals("")) {
					Toast.makeText(SearchActivity.this, "Empty keyword!", Toast.LENGTH_SHORT).show();
				} else {
					searchFile(keyword,"/storage/");
				}
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event){
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				break;
			case KeyEvent.KEYCODE_HOME:
				break;
			case KeyEvent.KEYCODE_MENU:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event){
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Intent intent = new Intent(SearchActivity.this,
						BookshelfActivity.class);
				startActivity(intent);
				break;
			case KeyEvent.KEYCODE_HOME:
				SearchActivity.this.finish();
				break;
			case KeyEvent.KEYCODE_MENU:
				break;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	private ArrayList<String> searchFile(String keyword, String path) { 

	   File[] files = new File(path).listFiles(); 
	   for (File file : files) { 
		   if (file.isDirectory()) {
			   if (file.list() != null){
				   searchFile(keyword,file.getPath());
			   }
			   Log.e(LOG_TAG, "empty directory:"+file.getPath()); 
		     } else if (file.getName().indexOf(keyword) >= 0) { 
		    	 bookpath.add(file.getPath());
				 bookname.add(file.getName());		
		     }
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bookname);
			setListAdapter(adapter);
	   } 
	  if (bookpath.equals("")){
		  Toast.makeText(SearchActivity.this, "No such files !", Toast.LENGTH_SHORT).show();
	  }
	 return bookpath; 
	}	
	
}