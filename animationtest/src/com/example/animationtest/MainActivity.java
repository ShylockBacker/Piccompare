package com.example.animationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private Boolean fillAfter = true;
	
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
    
    private Button xmlrotateButton = null;
    private Button xmlscaleButton = null;
    private Button xmlalphaButton = null;
    private Button xmltranslateButton = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        rotateButton = (Button)findViewById(R.id.rotateButton);
        scaleButton = (Button)findViewById(R.id.scaleButton);
        alphaButton = (Button)findViewById(R.id.alphaButton);
        translateButton = (Button)findViewById(R.id.translateButton);
        image = (ImageView)findViewById(R.id.image);
        
        xmlrotateButton = (Button) findViewById(R.id.xmlrotateButton);
        xmlscaleButton = (Button) findViewById(R.id.xmlscaleButton);
        xmlalphaButton = (Button) findViewById(R.id.xmlalphaButton);
        xmltranslateButton = (Button) findViewById(R.id.xmltranslateButton);
     
        rotateButton.setOnClickListener(new RotateButtonListener());
        scaleButton.setOnClickListener(new ScaleButtonListener());
        alphaButton.setOnClickListener(new AlphaButtonListener());
        translateButton.setOnClickListener(new TranslateButtonListener());
        
        xmlalphaButton.setOnClickListener(new RotateButtonListener() {
        	public void onClick(View v) {
 	           // ʹ��AnimationUtilsװ�ض��������ļ�
 	           Animation animation = AnimationUtils.loadAnimation(
 	                  MainActivity.this, R.anim.alpha);
 	           // ��������
 	           image.startAnimation(animation);
            }
        });     
        xmlrotateButton.setOnClickListener(new ScaleButtonListener() {
        	public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(
                        MainActivity.this, R.anim.rotate);
                 image.startAnimation(animation);
             }
         });
        xmlscaleButton.setOnClickListener(new AlphaButtonListener() {
        	public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(
                        MainActivity.this, R.anim.scale);
                 image.startAnimation(animation);
        	}
        });
        xmltranslateButton.setOnClickListener(new TranslateButtonListener() {
        	public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
                image.startAnimation(animation);
            }
        });
    }
    class AlphaButtonListener implements OnClickListener{
       public void onClick(View v) {
           //����һ��AnimationSet���󣬲���ΪBoolean�ͣ�
           //true��ʾʹ��Animation��interpolator��false����ʹ���Լ���
           AnimationSet animationSet = new AnimationSet(true);
           //����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��
           AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
           //���ö���ִ�е�ʱ��
           alphaAnimation.setDuration(500);
           //��alphaAnimation������ӵ�AnimationSet����
           animationSet.addAnimation(alphaAnimation);
           //ʹ��ImageView��startAnimation����ִ�ж���
           image.startAnimation(animationSet);
           animationSet.setFillAfter(fillAfter);
       }
    }
    class RotateButtonListener implements OnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //����1�����ĸ���ת�Ƕȿ�ʼ
           //����2��ת��ʲô�Ƕ�
           //��4��������������Χ������ת��Բ��Բ��������
           //����3��ȷ��x����������ͣ���ABSOLUT�������ꡢRELATIVE_TO_SELF������������ꡢRELATIVE_TO_PARENT����ڸ��ؼ�������
           //����4��x���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
           //����5��ȷ��y�����������
           //����6��y���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
           RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           rotateAnimation.setDuration(1000);
           animationSet.addAnimation(rotateAnimation);
           image.startAnimation(animationSet);
           animationSet.setFillAfter(fillAfter);
       }
    }
    class ScaleButtonListener implements OnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //����1��x��ĳ�ʼֵ
           //����2��x���������ֵ
           //����3��y��ĳ�ʼֵ
           //����4��y���������ֵ
           //����5��ȷ��x�����������
           //����6��x���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
           //����7��ȷ��y�����������
           //����8��y���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
           ScaleAnimation scaleAnimation = new ScaleAnimation(
                  0, 1.0f,0,1.0f,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           scaleAnimation.setDuration(1000);
           animationSet.addAnimation(scaleAnimation);
           image.startAnimation(animationSet);
           animationSet.setFillAfter(fillAfter);
       }
    }
    class TranslateButtonListener implements OnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //����1��2��x��Ŀ�ʼλ��
           //����3��4��y��Ŀ�ʼλ��
           //����5��6��x��Ľ���λ��
           //����7��8��x��Ľ���λ��
           TranslateAnimation translateAnimation =
              new TranslateAnimation(
                  Animation.RELATIVE_TO_SELF,0f,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           translateAnimation.setDuration(1000);
           animationSet.addAnimation(translateAnimation);
           image.startAnimation(animationSet);
           animationSet.setFillAfter(fillAfter);
       }
    }
}

