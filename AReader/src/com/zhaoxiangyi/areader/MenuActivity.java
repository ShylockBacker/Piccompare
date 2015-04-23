package com.zhaoxiangyi.areader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.zhaoxiangyi.areader.MenuRes.ResideMenu;
import com.zhaoxiangyi.areader.MenuRes.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

	private final static String LOG_TAG = "areader";
    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemBookshelf;
//    private ResideMenuItem itemProfile;
//    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemScan;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new BookshelfFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemBookshelf = new ResideMenuItem(this, R.drawable.icon_bookshelf, "Bookshelf");
        itemScan = new ResideMenuItem(this, R.drawable.icon_scan, "Scan");

        itemBookshelf.setOnClickListener(this);
        itemScan.setOnClickListener(this);

        resideMenu.addMenuItem(itemBookshelf, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemScan, ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "Click menu!");
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemBookshelf){
            changeFragment(new BookshelfFragment());
//        }else if (view == itemProfile){
//            changeFragment(new ProfileFragment());
//        }else if (view == itemCalendar){
//            changeFragment(new CalendarFragment());
        }else if (view == itemScan){
            changeFragment(new ScanFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
    	Log.i(LOG_TAG, "Change Fragment!");
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
