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
			categorieListe vehiculeUrbain = new categorieListe ();  
			vehiculeUrbain.cat_type = "Tourisme";
			data.putInt("current_page", arg0+1);
			vehiculeUrbain.setArguments(data);
			return vehiculeUrbain;

		case 1:
			categorieListe  vehiculeCompacte = new categorieListe ();
			vehiculeCompacte.cat_type = "Utilitaire";
			data.putInt("current_page", arg0+1);
			vehiculeCompacte.setArguments(data);
			return vehiculeCompacte;

		case 2:
			categorieListe  vehiculeSportive = new categorieListe ();
			vehiculeSportive.cat_type = "Luxe";
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