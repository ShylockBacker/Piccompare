package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SocketActivity extends Activity {
	Socket socket = null;
	String buffer = "";
	EditText txt;
	Button send;
	Button server;
	EditText edt;
	String geted;
	
	public static void socketactivity() throws IOException {
		ServerSocket serivce = new ServerSocket(30000);
		while (true) {
			//�ȴ��ͻ�������
			Socket socket = serivce.accept();
			new Thread(new SocketServer(socket)).start();
		}
	}
	public Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x11) {
				Bundle bundle = msg.getData();
				txt.append("server:"+bundle.getString("msg")+"\n");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket);
		txt = (EditText) findViewById(R.id.textView1);
		send = (Button) findViewById(R.id.client);
		server = (Button) findViewById(R.id.server);
		edt = (EditText) findViewById(R.id.textView2);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				geted = edt.getText().toString();
				txt.append("client:"+geted+"\r\n");
				//�����߳� ����������ͺͽ�����Ϣ
				new MyThread(geted).start();
			}
		});
		
        	server.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	try {
						socketactivity();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });            
	}

	class MyThread extends Thread {

		public String txt;

		public MyThread(String str) {
			txt = str;
		}

		@Override
		public void run() {
			//������Ϣ
			Message msg = new Message();
			msg.what = 0x11;
			Bundle bundle = new Bundle();
			bundle.clear();
			try {
				//���ӷ����� ���������ӳ�ʱΪ5��
				socket = new Socket();
				socket.connect(new InetSocketAddress("1.1.9.35", 30000), 5000);
				//��ȡ���������
				OutputStream ou = socket.getOutputStream();
				BufferedReader bff = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				//��ȡ������������Ϣ
				String line = null;
				buffer="";
				while ((line = bff.readLine()) != null) {
					buffer = line + buffer;
				}
				
				//�������������Ϣ
				ou.write("android �ͻ���".getBytes("gbk"));
				ou.flush();
				bundle.putString("msg", buffer.toString());
				msg.setData(bundle);
				//������Ϣ �޸�UI�߳��е����
				myHandler.sendMessage(msg);
				//�رո������������
				bff.close();
				ou.close();
				socket.close();
			} catch (SocketTimeoutException aa) {
				//���ӳ�ʱ ��UI������ʾ��Ϣ
				bundle.putString("msg", "����������ʧ�ܣ����������Ƿ��");
				msg.setData(bundle);
				//������Ϣ �޸�UI�߳��е����
				myHandler.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}