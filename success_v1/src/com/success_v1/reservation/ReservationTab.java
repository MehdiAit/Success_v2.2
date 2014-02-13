package com.success_v1.reservation;


import com.success_v1.successCar.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;


public class ReservationTab extends FragmentActivity {
	  
	 ActionBar actionabar;
	 ViewPager viewpager;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.reservation_tab);
	   
	        actionabar = getActionBar();
	        actionabar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	         
	        viewpager = (ViewPager) findViewById(R.id.pager);
	        FragmentManager fm = getSupportFragmentManager();
	         
	        /** Defining a listener for pageChange */
	        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
	         @Override
	         public void onPageSelected(int position) {         
	          super.onPageSelected(position);
	          actionabar.setSelectedNavigationItem(position);         
	         }        
	        };
	         
	        /** Setting the pageChange listner to the viewPager */
	        viewpager.setOnPageChangeListener(pageChangeListener);
	         
	        /** Creating an instance of FragmentPagerAdapter */
	        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(fm);
	         
	        viewpager.setAdapter(fragmentPagerAdapter);
	        actionabar.setDisplayShowTitleEnabled(true);
	         
	        /** Defining tab listener */
	        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	    
	   @Override
	   public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
	     
	   }
	 
	   @Override
	   public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
	    viewpager.setCurrentItem(tab.getPosition());
	     
	   }
	 
	   @Override
	   public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	    
	   }
	  };
	 
	  /** Creating Android Tab */
	       // Tab tab = actionabar.newTab().setText("My Friends").setIcon(R.drawable.myfriends).setTabListener(tabListener);
	  Tab tab = actionabar.newTab().setText("Reservations En Cours").setTabListener(tabListener);
	        actionabar.addTab(tab);
	        tab = actionabar.newTab().setText("Reservations Validées").setTabListener(tabListener);                              
	        actionabar.addTab(tab);      
	 
	 
	 }
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  return true;
	 }
	 
	}