package com.success_v1.vehicule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

	final int PAGE_COUNT = 3;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);

	}
	@Override
	public Fragment getItem(int arg0) {
		Bundle data = new Bundle();
		switch(arg0){

		case 0:
			VehiculeListe vehiculeUrbain = new VehiculeListe();  
			vehiculeUrbain.cat = "Citadine";
			data.putInt("current_page", arg0+1);
			vehiculeUrbain.setArguments(data);
			return vehiculeUrbain;

		case 1:
			VehiculeListe vehiculeCompacte = new VehiculeListe();
			vehiculeCompacte.cat = "Compacte";
			data.putInt("current_page", arg0+1);
			vehiculeCompacte.setArguments(data);
			return vehiculeCompacte;

		case 2:
			VehiculeListe vehiculeSportive = new VehiculeListe();
			vehiculeSportive.cat = "Sportive";
			data.putInt("current_page", arg0+1);
			vehiculeSportive.setArguments(data);
			return vehiculeSportive;

		}



		return null;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() { 
		return PAGE_COUNT;
	}

}