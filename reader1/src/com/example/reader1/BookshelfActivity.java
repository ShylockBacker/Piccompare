package com.example.reader1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.TextView;

public class BookshelfActivity extends Activity {

		private TextView TextView_result;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.bookshelf);
			TextView_result=(TextView) findViewById(R.id.result);
		}
		private class button_start_task_Listener implements OnClickListener
		{
			public void onClick(View v)
			{
				Intent intent=new Intent(BookshelfActivity.this,SearchActivity.class);
				//关键点来了，使用startActivityForResult来启动
				startActivityForResult(intent, 100);
			}
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data)
		{
			//可以根据多个请求代码来作相应的操作
			if(20==resultCode)
			{
				String bookname=data.getExtras().getString("bookname");
				String booksale=data.getExtras().getString("booksale");
				TextView_result.setText("书籍名称:"+bookname+"书籍价钱"+booksale+"元");
			}
			super.onActivityResult(requestCode, resultCode, data);
		}


	class OpenFileAction implements OnClickListener {

		public void onClick(View v) {

			Intent in = new Intent(BookshelfActivity.this,
					SearchActivity.class);
			startActivityForResult(in, 0);
		}

	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  

	    if (keyCode == KeyEvent.KEYCODE_BACK) {  
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);  
	        builder.setMessage("你确定退出吗？")  
	                .setCancelable(false)  
	                .setPositiveButton("确定",  
	                        new DialogInterface.OnClickListener() {  
	                            public void onClick(DialogInterface dialog,  
	                                    int id) {  
	                                finish();  
	                            }  
	                        })  
	                .setNegativeButton("返回",  
	                        new DialogInterface.OnClickListener() {  
	                            public void onClick(DialogInterface dialog,  
	                                    int id) {  
	                                dialog.cancel();  
	                            }  
	                        });  
	        AlertDialog alert = builder.create();  
	        alert.show();  
	        return true;  
	    }  

	    return super.onKeyDown(keyCode, event);  
	} 
}
