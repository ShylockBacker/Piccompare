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
				//�ؼ������ˣ�ʹ��startActivityForResult������
				startActivityForResult(intent, 100);
			}
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data)
		{
			//���Ը��ݶ���������������Ӧ�Ĳ���
			if(20==resultCode)
			{
				String bookname=data.getExtras().getString("bookname");
				String booksale=data.getExtras().getString("booksale");
				TextView_result.setText("�鼮����:"+bookname+"�鼮��Ǯ"+booksale+"Ԫ");
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
	        builder.setMessage("��ȷ���˳���")  
	                .setCancelable(false)  
	                .setPositiveButton("ȷ��",  
	                        new DialogInterface.OnClickListener() {  
	                            public void onClick(DialogInterface dialog,  
	                                    int id) {  
	                                finish();  
	                            }  
	                        })  
	                .setNegativeButton("����",  
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
