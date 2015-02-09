package com.example.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class IniActivity extends Activity{

    private EditText ip,port;
    private Button nextButton;
    private String getip,getport;
    private ProgressDialog progressDialog;
    private InetSocketAddress isa = null; 
    private SQLiteDatabase db;
    private String ipstring=null,portString=null;
    private int row=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        
        ip = (EditText)findViewById(R.id.ip);
        port = (EditText)findViewById(R.id.port);
        nextButton = (Button)findViewById(R.id.next);
        
        
        db = SQLiteDatabase.openOrCreateDatabase(config.f, null);
        try {
            Cursor cursor = db.query("config", new String[]{"ip","port"},null,null, null, null, null);
            while(cursor.moveToNext()){
                ipstring = cursor.getString(cursor.getColumnIndex("ip"));
                portString = cursor.getString(cursor.getColumnIndex("port"));
                row++;
            }
            ip.setText(ipstring);
            port.setText(portString);
            cursor.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
        db.close();
        
        nextButton.setOnClickListener(new nextButtonListenner());
    }
    
    class nextButtonListenner implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            getip = ip.getText().toString().trim();
            getport = port.getText().toString().trim();
            if(getip=="" || getip==null || getip.equals("")){
                Toast.makeText(IniActivity.this, "請輸入IP", Toast.LENGTH_SHORT).show();
                ip.setFocusable(true);
            }else if(getport=="" || getport==null || getport.equals("")){
                Toast.makeText(IniActivity.this, "請輸入端口", Toast.LENGTH_SHORT).show();
                port.setFocusable(true);
            }else{
            //progressDialog = ProgressDialog.show(IniActivity.this, "", "請稍後...", true, false);
//new Thread() {
//@Override
//public void run() {
                    try {
                        Socket s = new Socket();
                        isa = new InetSocketAddress(getip,Integer.parseInt(getport)); 
                        s.connect(isa,5000); 
                        //showDialog("連接成功",IniActivity.this);
                        try {
                            //生成ContentValues对象
                            ContentValues values = new ContentValues();
                            //想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
                            values.put("ip", getip);
                            values.put("port",getport);
                            db = SQLiteDatabase.openOrCreateDatabase(config.f, null); 
                            if(row==0){
                                db.insert("config", null, values);
                            }else{
                                db.update("config", values ,null,null);
                            }
                            Toast.makeText(IniActivity.this, "連接成功", Toast.LENGTH_SHORT);
                            s.close();
                            Intent intent = new Intent(IniActivity.this,IniuserActivity.class);
                            startActivity(intent);
                            IniActivity.this.finish();
                            db.close();
                        } catch (Exception e) {
                            // TODO: handle exception
                            showDialog("設置失敗，數據庫不可用",IniActivity.this);
                        }
                        
                        
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        showDialog("連接失敗，IP或者端口不可用",IniActivity.this);
                    }catch (SocketTimeoutException  e) {
                          System.out.println("連接超時，服務器未開啟或IP錯誤");
                          showDialog("連接超時，服務器未開啟或IP錯誤",IniActivity.this);
                          e.printStackTrace();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        showDialog("連接失敗，IP或者端口不可用",IniActivity.this);
                    }
                    //progressDialog.dismiss();
//finish();
//}
//}.start();
            }
            
        }
        
    }
    
    /**
     * define a dialog for show the message
     * @param mess
     * @param activity
*/
    public void showDialog(String mess,Activity activity){
      new AlertDialog.Builder(activity).setTitle("信息").setMessage(mess)
       .setNegativeButton("確定",new DialogInterface.OnClickListener()
       {
         public void onClick(DialogInterface dialog, int which)
         {          
         }
       })
       .show();
    }
}