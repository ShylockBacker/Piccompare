package com.zhaoxiangyi.areader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * Author: zhaoxiangyi Date: 4/14/2015 Mail: xiangyizhao1991@gmail.com
 */
public class ScanFragment extends Fragment {

	private static final String LOG_TAG = "ScanFragment";
	ArrayList<String> bookpath = new ArrayList<String>();
	ArrayList<String> bookname = new ArrayList<String>();
	private File path;
	private ListView mListView;
	private SimpleAdapter mAdapter;
	private List<HashMap<String, Object>> mHashMaps;
	private HashMap<String, Object> map;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.scan, container, false);

		Button scan = (Button) parentView.findViewById(R.id.scan);
		mListView = (ListView) parentView.findViewById(R.id.founded_book_list);

		final MenuActivity parentActivity = (MenuActivity) getActivity();
		scan.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setPath();
				File temp = setPath();
				scanFile(temp);
			}
		});
		return parentView;
	}

	private File setPath() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {			
			path = android.os.Environment.getExternalStorageDirectory();
		}
		Log.e(LOG_TAG, "file directory:" + path);
		return path;
	}

	private void UpdateAdapter() {
		int n = bookname.size();
		for (int i = 0; i <= n; i++) {
			getData(n);
		}
		mAdapter = new SimpleAdapter(this.getActivity()
				.getApplicationContext(), getData(),
				R.layout.activity_list, new String[] { "image", "title",
						"info" }, new int[] { R.id.img, R.id.title,
						R.id.info });
		mListView.setAdapter(mAdapter);
		if (bookpath.equals("")) {
			Toast.makeText(getActivity(), "No such files !", Toast.LENGTH_SHORT)
					.show();
		}
	}           
	
	private ArrayList<String> scanFile(File path) {
		
		Log.e(LOG_TAG, "path:" + path);
		File[] files = path.listFiles();
		Log.e(LOG_TAG, "files list:" + files);                                                                                            
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (file.list() != null) {
						for (String dirpath : file.list()){
							File f=new File(dirpath);
							scanFile(f);
						}
					} else{
						Log.e(LOG_TAG, "empty directory:" + file.getPath());
					}
				} else if (file.getName().endsWith(".txt")) {
					bookpath.add(file.getPath());
					bookname.add(file.getName());
				}
			}
		}
		UpdateAdapter();
			
		return bookpath;
	}

	private List<HashMap<String, Object>> getData(int n) {
		mHashMaps = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.ic_a);
		map.put("title", "bookname[n]");
		map.put("info", "bookpath[n]");
		mHashMaps.add(map);

		return mHashMaps;
	}

	private List<HashMap<String, Object>> getData() {
		mHashMaps = new ArrayList<HashMap<String, Object>>();
		// map = new HashMap<String, Object>();
		// map.put("image", R.drawable.ic_a);
		// map.put("title", "bookname[0]");
		// map.put("info", "bookpath[0]");
		// mHashMaps.add(map);
		//
		// map = new HashMap<String, Object>();
		// map.put("image", R.drawable.ic_b);
		// map.put("title", "bookname[1]");
		// map.put("info", "bookpath[1]");
		// mHashMaps.add(map);
		//
		// map = new HashMap<String, Object>();
		// map.put("image", R.drawable.ic_c);
		// map.put("title", "bookname[2]");
		// map.put("info", "bookpath[2]");
		//
		// mHashMaps.add(map);
		return mHashMaps;
	}
}
