package com.example.chatroom;

import java.io.File;

public class config {
	public static String SDCARD = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static File path = new File(SDCARD + "/RunChatDatabase/"); // ���ݿ��ļ�Ŀ¼
	public static File f = new File(SDCARD + "/RunChatDatabase/config.db"); // ���ݿ��ļ�
}