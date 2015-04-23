package com.example.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectActivity extends Activity {
	TextView txt;
	private String LOG_TAG = "Select";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		txt = (TextView) findViewById(R.id.booklist);
		Button back = (Button) this.findViewById(R.id.back);

		TextView ScanButton = (TextView) findViewById(R.id.scan);
		ScanButton.setText(R.string.scan);
		if (ScanButton != null) {
			ScanButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					scanbook();
				}
			});
		}
		back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SelectActivity.this,
						ReaderActivity.class);
				startActivity(intent);
				SelectActivity.this.finish();
			}
		});
	}

	public void scanbook() {
		List<String> getFiles = GetFiles("/", ".txt", true);
		for (String string : getFiles) {
			// System.out.println(string);
			txt.append("book:" + string + "\r\n");
		}
	}

	private List<String> booklist = new ArrayList<String>();

	public List<String> GetFiles(String Path, String Extension, boolean IsIterative) {
		File[] files = null;
		files = new File(Path).listFiles();
		this.getFilesDir();
		try {
		if (files.length == 0) {
			Log.e(LOG_TAG, "empty directory:"+Path);
		} else if (files.length < 0){
			Log.e(LOG_TAG, "length < 0:"+Path);
		} else {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isFile()) {
					if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) // 判断扩展名
						booklist.add(f.getPath());
					if (!IsIterative)
						break; // 如果不进入子集目录则跳出
				} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
					GetFiles(f.getPath(), Extension, IsIterative); // 这里就开始递归了
			}
		}
		} catch (Exception e) {
			Log.e(LOG_TAG, "exception:"+Path+e);
			Log.e(LOG_TAG, "files.length:"+files.length);
		}
		return booklist;		
	}
}
