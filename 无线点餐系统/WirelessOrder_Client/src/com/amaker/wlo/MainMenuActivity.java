package com.amaker.wlo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amaker.util.HttpUtil;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("主菜单");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// 上下文
        private Context mContext;
        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) {
        	// 声明图片视图
            ImageView imageView;
            if (convertView == null) {
            	// 实例化图片视图
                imageView = new ImageView(mContext);
                // 设置图片视图属性
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setPadding(7,7,7,7);
            } else {
                imageView = (ImageView) convertView;
            }
            // 设置图片视图图片资源
            imageView.setImageResource(mThumbIds[position]);
            // 为当前视图添加监听器
            switch (position) {
			case 0:
				// 添加点餐监听器
				imageView.setOnClickListener(orderLinstener);
				break;
			case 1:
				// 添加转台监听器
				imageView.setOnClickListener(changeTableLinstener);
				break;
			case 2:
				// 添加查台监听器
				imageView.setOnClickListener(checkTableLinstener);
				break;
			case 3:
				// 添加更新监听器
				imageView.setOnClickListener(updateLinstener);
				break;
			case 4:
				// 添加注销监听器
				imageView.setOnClickListener(exitLinstener);
				break;
			case 5:
				// 添加结算监听器
				imageView.setOnClickListener(payLinstener);
				break;
			
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			default:
			}
            
            return imageView;
        }
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.diancai,R.drawable.zhuantai, R.drawable.chatai,
                R.drawable.gengxin, R.drawable.zhuxiao, R.drawable.jietai,
                R.drawable.yijian,R.drawable.tuijian,R.drawable.shezhi
        };
    }
    
    // 更新监听器
    OnClickListener updateLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动更新Activity
			intent.setClass(MainMenuActivity.this, UpdateActivity.class);
			startActivity(intent);
		}
	};
    
    // 查台监听器
    OnClickListener checkTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动查台Activity
			intent.setClass(MainMenuActivity.this, CheckTableActivity.class);
			startActivity(intent);
		}
	};
    
    // 结算监听器
    OnClickListener payLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动结算Activity
			intent.setClass(MainMenuActivity.this, PayActivity.class);
			startActivity(intent);
		}
	};
    
    // 订餐监听器
    OnClickListener orderLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动订餐Activity
			intent.setClass(MainMenuActivity.this, OrderActivity.class);
			startActivity(intent);
		}
	};
	// 注销监听器
    OnClickListener exitLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logout();
		}
	};
	
	// 转台监听器
    OnClickListener changeTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			changeTable();
		}
	};
	
	
	
	
	// 换台系统
	private void changeTable(){
		// 获得LayoutInflater实例
		LayoutInflater inflater = LayoutInflater.from(this);
		// 获得LinearLayout视图实例
		View v =inflater.inflate(R.layout.change_table, null);
		// 从LinearLayout中获得EditText实例
		final EditText et1 = (EditText) v.findViewById(R.id.change_table_order_number_EditText);
		final EditText et2 = (EditText) v.findViewById(R.id.change_table_no_EditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" 真的要换桌位吗？")
		       .setCancelable(false)
		       .setView(v)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	// 获得订单号
		        	String orderId = et1.getText().toString();
		        	// 获得桌号
		       		String tableId = et2.getText().toString();
		       		// 查询参数
		       		String queryString = "orderId="+orderId+"&tableId="+tableId;
		       		// url
		       		String url = HttpUtil.BASE_URL+"servlet/ChangeTableServlet?"+queryString;
		       		// 查询返回结果
		       		String result = HttpUtil.queryStringForPost(url);
		       		// 显示结果
		       		Toast.makeText(MainMenuActivity.this,result,Toast.LENGTH_LONG).show();
		           }
		       })
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
	// 退出系统
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("真的要退出系统吗？")
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  SharedPreferences pres = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		        	  SharedPreferences.Editor editor = pres.edit();
		        	  editor.putString("id", "");
		        	  editor.putString("name", "");
		        	  Intent intent = new Intent();
		        	  intent.setClass(MainMenuActivity.this, LoginActivity.class);
		        	  startActivity(intent);
		           }
		       })
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}