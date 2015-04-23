package com.zhaoxiangyi.areader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Author: zhaoxiangyi
 * Date: 4/14/2015
 * Mail: xiangyizhao1991@gmail.com
 */
public class BookshelfFragment extends Fragment {
	
    private View parentView;
    private ShelfAdapter mAdapter;
    private ListView shelf_list;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parentView = inflater.inflate(R.layout.bookshelf_list, container, false);
    	setUpViews();
        return parentView;
    }

    private void setUpViews() {
        final MenuActivity parentActivity = (MenuActivity) getActivity();

		shelf_list = (ListView) (parentView.findViewById(R.id.shelf_list));
        mAdapter = new ShelfAdapter ();//new adapter
        shelf_list.setAdapter ( mAdapter );
        
        shelf_list.setOnItemClickListener(new OnItemClickListener() {

            private Object toast;

			@Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	toast = Toast.makeText(parentActivity,
            			"you click"+arg2+"line", Toast.LENGTH_LONG);
            		   ((Toast) toast).setGravity(Gravity.CENTER, 0, 0);
            		   ((Toast) toast).show();
            }
        });

    }

	   public class ShelfAdapter extends BaseAdapter {

	        int[ ] size = new int[ 5 ];

	        public ShelfAdapter () {

	        }

	        @ Override
	        public int getCount () {

	            if ( size.length > 3 ) {
	                return size.length;
	            } else {
	                return 3;
	            }

	        }

	        @ Override
	        public Object getItem ( int position ) {

	            return size[ position ];
	        }

	        @ Override
	        public long getItemId ( int position ) {

	            return position;
	        }

	        @ Override
	        public View getView ( int position , View convertView , ViewGroup parent ) {
	        	final MenuActivity parentActivity = (MenuActivity) getActivity();
	            LayoutInflater layout_inflater = ( LayoutInflater ) parentActivity.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
	            View layout = layout_inflater.inflate ( R.layout.bookshelf , null );

	            return layout;
	        }
	    };

}
