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
                Toast.makeText(IniActivity.this, "Ոݔ��IP", Toast.LENGTH_SHORT).show();
                ip.setFocusable(true);
            }else if(getport=="" || getport==null || getport.equals("")){
                Toast.makeText(IniActivity.this, "Ոݔ��˿�", Toast.LENGTH_SHORT).show();
                port.setFocusable(true);
            }else{
            //progressDialog = ProgressDialog.show(IniActivity.this, "", "Ո����...", true, false);
//new Thread() {
//@Override
//public void run() {
                    try {
                        Socket s = new Socket();
                        isa = new InetSocketAddress(getip,Integer.parseInt(getport)); 
                        s.connect(isa,5000); 
                        //showDialog("�B�ӳɹ�",IniActivity.this);
                        try {
                            //����ContentValues����
                            ContentValues values = new ContentValues();
                            //��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��
                            values.put("ip", getip);
                            values.put("port",getport);
                            db = SQLiteDatabase.openOrCreateDatabase(config.f, null); 
                            if(row==0){
                                db.insert("config", null, values);
                            }else{
                                db.update("config", values ,null,null);
                            }
                            Toast.makeText(IniActivity.this, "�B�ӳɹ�", Toast.LENGTH_SHORT);
                            s.close();
                            Intent intent = new Intent(IniActivity.this,IniuserActivity.class);
                            startActivity(intent);
                            IniActivity.this.finish();
                            db.close();
                        } catch (Exception e) {
                            // TODO: handle exception
                            showDialog("�O��ʧ���������첻����",IniActivity.this);
                        }
                        
                        
                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        showDialog("�B��ʧ����IP���߶˿ڲ�����",IniActivity.this);
                    }catch (SocketTimeoutException  e) {
                          System.out.println("�B�ӳ��r��������δ�_����IP�e�`");
                          showDialog("�B�ӳ��r��������δ�_����IP�e�`",IniActivity.this);
                          e.printStackTrace();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        showDialog("�B��ʧ����IP���߶˿ڲ�����",IniActivity.this);
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
      new AlertDialog.Builder(activity).setTitle("��Ϣ").setMessage(mess)
       .setNegativeButton("�_��",new DialogInterface.OnClickListener()
       {
         public void onClick(DialogInterface dialog, int which)
         {          
         }
       })
       .show();
    }
}