package com.success_v1.vehicule;


import com.success_v1.successCar.R;
import com.success_v1.successCar.R.color;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class VehiculeTab extends FragmentActivity {
	  
	 private ActionBar actionabar;
	 private ViewPager viewpager;
	 private TextView titleActionBar;
	 private ImageView logoEtape;
	 
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.vehicule_tab);
	        actionabar = getActionBar(); 
	        //actionabar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	        actionabar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        //actionabar.setCustomView(R.layout.koutchy_actionbar);
			//titleActionBar = (TextView)findViewById(R.id.titleActionBar);
			//logoEtape = (ImageView)findViewById(R.id.logoEtape2);
			//titleActionBar.setText("Création de la réservation (2/3)");
			//logoEtape.setVisibility(ImageView.VISIBLE);


	        actionabar.setTitle("Création de la réservation (2/3)");
			int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
			    TextView title = (TextView) findViewById(actionBarTitleId);
			    if (title != null) {
			        title.setTextColor(getResources().getColor(R.color.title_color));
			    }
			}
	        /*getActionBar().setDisplayShowHomeEnabled(false);
			getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			getActionBar().setCustomView(R.layout.koutchy_actionbar);
			titleActionBar = (TextView)findViewById(R.id.titleActionBar);
			titleActionBar.setText("Création de la réservation (2/3)");*/
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
	  Tab tab = actionabar.newTab().setText("Citadine").setTabListener(tabListener);
	        actionabar.addTab(tab);
	        
	        tab = actionabar.newTab().setText("Compacte").setTabListener(tabListener);                              
	        actionabar.addTab(tab);
	        
	        tab = actionabar.newTab().setText("Sportive").setTabListener(tabListener);                              
	        actionabar.addTab(tab);
	        
	 
	 }
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  return true;
	 }
	 
	}