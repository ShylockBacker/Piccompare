package com.example.reader;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SearchActivity extends Activity{
	private static final String LOG_TAG = "Search";
	private String result = ""; 
	   
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		final EditText keywordText = (EditText) this.findViewById(R.id.keyword);
		Button search = (Button) this.findViewById(R.id.search);
		Button view = (Button) this.findViewById(R.id.view);
		final TextView result = (TextView) this.findViewById(R.id.result);
		Button back = (Button) this.findViewById(R.id.back);
		
		view.setOnClickListener(new OpenFileAction());
		
		search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String keyword = keywordText.getText().toString();
				if (keyword.equals("")) {
					result.setText("Empty keyword!");
				} else {
					result.setText(searchFile(keyword,"/storage/"));
				}
			}
		});
		
		back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SearchActivity.this,
						ReaderActivity.class);
				startActivity(intent);
				SearchActivity.this.finish();
			}
		});
	}

	private String searchFile(String keyword, String path) { 

	   File[] files = new File(path).listFiles(); 
	   for (File file : files) { 
		   if (file.isDirectory()) {
			   if (file.list() != null){
				   searchFile(keyword,file.getPath());
			   }
			   Log.e(LOG_TAG, "empty directory:"+file.getPath()); 
		     } else if (file.getName().indexOf(keyword) >= 0) { 
			       result += file.getPath() + "\n"; 
		     }
	   } 
	  if (result.equals("")){
	    result = "No such file!"; 
	  }
	 return result; 
	}

	class OpenFileAction implements OnClickListener
    {

		public void onClick(View v) {
			
			Intent in = new Intent(SearchActivity.this, ViewFile.class);
        	startActivityForResult(in, 0);
		}
    	
    }
}
