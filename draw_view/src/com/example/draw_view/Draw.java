package com.example.draw_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class Draw extends Activity {  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
          
        MyView mv = new MyView(this);  
        setContentView(mv);  
    }  
      
    public class MyView extends View {  
  
        MyView(Context context) {  
            super(context);  
        }  
          
        @Override  
        protected void onDraw(Canvas canvas) {  
            // TODO Auto-generated method stub   
            super.onDraw(canvas);  
              
            // ���ȶ���һ��paint    
            Paint paint = new Paint();   
  
            // ���ƾ�������-ʵ�ľ���    
            // ������ɫ    
            paint.setColor(Color.BLUE);   
            // ������ʽ-���    
            paint.setStyle(Style.FILL);   
            // ����һ������    
            canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);   
  
            // ����ľ���    
            // ������ɫ    
            paint.setColor(Color.RED);   
            // ������ʽ-���ľ���    
            paint.setStyle(Style.STROKE);   
            // ����һ������    
            canvas.drawRect(new Rect(10, 10, 100, 30), paint);   
  
            // ������    
            // ������ɫ    
            paint.setColor(Color.GREEN);   
            // ������    
            canvas.drawText("Hello", 10, 50, paint);   
  
            // ��ͼ    
            // ����Դ�ļ�������λͼ    
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);   
            // ��ͼ    
            canvas.drawBitmap(bitmap, 10, 60, paint);   
        }  
          
    }  
}  

