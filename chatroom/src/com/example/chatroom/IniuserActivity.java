package com.example.chatroom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IniuserActivity extends Activity{
    private EditText name;
    private Button ok;
    private SQLiteDatabase db;
    
    private String nameString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuser);
        
        name = (EditText)findViewById(R.id.name);
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new okButtonListenner());
        
        
        db = SQLiteDatabase.openOrCreateDatabase(config.f, null);
        try {
            Cursor cursor = db.query("config", new String[]{"name"},null,null, null, null, null);
            while(cursor.moveToNext()){
                nameString = cursor.getString(cursor.getColumnIndex("name"));
            }
            name.setText(nameString);
            cursor.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
        db.close();
    }
    
    class okButtonListenner implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String getname = name.getText().toString().trim();
            if(getname==""){
                Toast.makeText(IniuserActivity.this, "請輸入您的稱呢", Toast.LENGTH_SHORT).show();
                name.setFocusable(true);
            }else{            
                try {
                    //生成ContentValues对象
                    ContentValues values = new ContentValues();
                    //想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
                    values.put("name", getname);
                    db = SQLiteDatabase.openOrCreateDatabase(config.f, null); 
                    db.update("config",values,null,null);
                    Toast.makeText(IniuserActivity.this, "設置完成", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IniuserActivity.this,SocketmsgActivity.class);
                    startActivity(intent);
                    IniuserActivity.this.finish();
                    db.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    showDialog("設置失敗，數據庫不可用",IniuserActivity.this);
                }
            }
        }
        
    }
    
    /**
     * define a dialog for show the message
     * @param mess
     * @param activity
*/
    public void showDialog(String mess,Activity activity){
      new AlertDialog.Builder(activity).setTitle("信息")
       .setMessage(mess)
       .setNegativeButton("確定",new DialogInterface.OnClickListener()
       {
         public void onClick(DialogInterface dialog, int which)
         {          
         }
       })
       .show();
    }
}