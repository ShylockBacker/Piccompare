package com.example.reader;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReaderActivity extends Activity {
	
	private static final String LOG_TAG = "reader";

	private int mNum = 0;
	public String midString = null;
	public int mResid = 0;
	
	private int[] mImageId = new int[] { R.drawable.bg_black,
			R.drawable.bg_blue, R.drawable.bg_darkblue,
			R.drawable.bg_green,R.drawable.bg_lightgreen,
			R.drawable.bg_orange,R.drawable.bg_red,
			R.drawable.bg_white,R.drawable.bg_yellow };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader);
		showbackground(mNum);
		
        setSettingButton();
		setBlackButton();
        setBlueButton();
        setDarkblueButton();
        setGreenButton();
        setLightgreenButton();
        setOrangeButton();
        setRedButton();
        setWhiteButton();
        setYellowButton();

	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	private void showbackground(int mNum) {
		ImageView imageView = (ImageView) findViewById(R.id.image_view);
		if (imageView != null){
			imageView.setBackgroundResource(mImageId[mNum]);
		} else {
			showToast("imageView is null");
		}
		
	}
	
//	private void showsettingbackground(int mNum) {
//		ImageView iv = (ImageView) findViewById(mResid);
//		if (iv != null){
//			iv.setBackgroundResource(mImageId[mNum]);
//		} else {
//			showToast("iv is null");
//		}
//		
//	}
	
	public void initlayout() {
        
        Spinner sp=new Spinner(this);

        ArrayAdapter< String> adapter = new ArrayAdapter< String>( this, 
        android.R.layout.simple_spinner_item);
        adapter.add("black");
        adapter.add("blue");
        adapter.add("darkblue");
        adapter.add("green");
        adapter.add("lightgreen");
        adapter.add("orange");
        adapter.add("red");
        adapter.add("white");
        adapter.add("yellow");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        		Log.i(LOG_TAG, "position =" + position + "id =" + id);
        		position = position + 1;
        		mNum = position / 10;
        		Log.i(LOG_TAG, "mNum =" + mNum);
        		showbackground(mNum);
        			showToast("Spinner1: position=" + position+1 + " id=" + id+1);
        		}  
        		public void onNothingSelected(AdapterView<?> parent) {
        		 
        			showbackground(0); 
        		    }
        		});
        		  

        sp.setAdapter(adapter);
        sp.setPrompt( "choose a color:"); 

        LinearLayout set=new LinearLayout(this);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
        );
        
//        final ImageView iv = new ImageView(ReaderActivity.this); 
//        set.addView(iv, new LayoutParams(60, 60));
        
        set.addView(sp,ll);
        setContentView(set);
        
//        Resources res = this.getResources(); 
//        midString = res.getResourceEntryName(iv.getId());
//        mResid = iv.getId();
//        Log.i(LOG_TAG, "midString =" + midString + "mResid =" + mResid);
        
	}
	
	private void setBlackButton() {
		TextView BlackButton = (TextView) findViewById(R.id.black);
		BlackButton.setText(R.string.black);
        if (BlackButton != null) {
        	BlackButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(0);
                }
            });            
        }
	}
	
	private void setBlueButton() {
		TextView BlueButton = (TextView) findViewById(R.id.blue);
		BlueButton.setText(R.string.blue);
        if (BlueButton != null) {
        	BlueButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(1);
                }
            });            
        }
	}
	
	private void setDarkblueButton() {
		TextView DarkblueButton = (TextView) findViewById(R.id.darkblue);
		DarkblueButton.setText(R.string.darkblue);
        if (DarkblueButton != null) {
        	DarkblueButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(2);
                }
            });            
        }
	}
	
	private void setGreenButton() {
		TextView GreenButton = (TextView) findViewById(R.id.green);
		GreenButton.setText(R.string.green);
        if (GreenButton != null) {
        	GreenButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(3);
                }
            });            
        }
	}
	
	private void setLightgreenButton() {
		TextView LightgreenButton = (TextView) findViewById(R.id.lightgreen);
		LightgreenButton.setText(R.string.lightgreen);
        if (LightgreenButton != null) {
        	LightgreenButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(4);
                }
            });            
        }
	}
	
	private void setOrangeButton() {
		TextView OrangeButton = (TextView) findViewById(R.id.orange);
		OrangeButton.setText(R.string.orange);
        if (OrangeButton != null) {
        	OrangeButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(5);
                }
            });            
        }
	}
	
	private void setRedButton() {
		TextView RedButton = (TextView) findViewById(R.id.red);
		RedButton.setText(R.string.red);
        if (RedButton != null) {
        	RedButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(6);
                }
            });            
        }
	}
	
	private void setWhiteButton() {
		TextView WhiteButton = (TextView) findViewById(R.id.white);
		WhiteButton.setText(R.string.white);
        if (WhiteButton != null) {
        	WhiteButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(7);
                }
            });            
        }
	}
	
	private void setYellowButton() {
		TextView YellowButton = (TextView) findViewById(R.id.yellow);
		YellowButton.setText(R.string.yellow);
        if (YellowButton != null) {
        	YellowButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	showbackground(8);
                }
            });            
        }
	}
	
	private void setSettingButton() {
		TextView SettingButton = (TextView) findViewById(R.id.button1);
        SettingButton.setText(R.string.action_setting);
        if (SettingButton != null) {
            SettingButton.setOnClickListener(new View.OnClickListener() {                
                public void onClick(View v) {
                	initlayout();
                }
            });            
        }
	}
	
	private void showToast(CharSequence msg) {
		  
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
