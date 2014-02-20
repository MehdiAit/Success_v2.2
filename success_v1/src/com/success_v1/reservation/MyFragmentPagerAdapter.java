package com.success_v1.reservation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
  
 final int PAGE_COUNT = 2;
  
 public MyFragmentPagerAdapter(FragmentManager fm) {
  super(fm);
   
 }
 
 /** This method will be invoked when a page is requested to create */
 @Override
 public Fragment getItem(int arg0) {
  Bundle data = new Bundle();
  switch(arg0){
   
   /** Android tab is selected */
   case 0:
    ReservationList reservEnCours = new ReservationList();
    reservEnCours.etat = "En cours";
    data.putInt("current_page", arg0+1);
    reservEnCours.setArguments(data);
    return reservEnCours;
     
   case 1:
	ReservationList reservValide = new ReservationList();
	reservValide.etat = "Validee";
    data.putInt("current_page", arg0+1);
    reservValide.setArguments(data);
    return reservValide;
  }
   
  return null;
 }
 
 /** Returns the number of pages */
 @Override
 public int getCount() { 
  return PAGE_COUNT;
 }
  
}