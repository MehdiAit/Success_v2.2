package com.success_v1.vehicule;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.success_v1.successCar.R;

public class AdapterVehicule extends BaseAdapter {
	
	private ArrayList<Vehicule> vehicule;
	private LayoutInflater fl;
	
	public AdapterVehicule(Context context, ArrayList<Vehicule> vehic)
	{
		fl = LayoutInflater.from(context);
		vehicule = vehic;
	}

	@Override
	public int getCount() {
		return vehicule.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return vehicule.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class ViewHolder{
		TextView id_vehicule;
		TextView marque_vehicule;
		TextView modele_vehicule;
		TextView motorisation_vehicule;
		TextView tarifJour_vehicule;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (arg1 == null)
		{
			arg1 = fl.inflate(R.layout.vehicule_list_item, null);
			holder = new ViewHolder();
			
			holder.id_vehicule = (TextView)arg1.findViewById(R.id.id_vehicule);
			holder.marque_vehicule = (TextView)arg1.findViewById(R.id.marque_vehicule);
			holder.modele_vehicule = (TextView)arg1.findViewById(R.id.modele_vehicule);
			holder.motorisation_vehicule = (TextView)arg1.findViewById(R.id.motorisation_vehicule);
			holder.tarifJour_vehicule = (TextView)arg1.findViewById(R.id.tarifJour_vehicule);
			
			
			arg1.setTag(holder);
		}else
		{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.id_vehicule.setText(vehicule.get(arg0).getId());
		holder.marque_vehicule.setText(vehicule.get(arg0).getMarque());
		holder.modele_vehicule.setText(vehicule.get(arg0).getModele());
		holder.motorisation_vehicule.setText(vehicule.get(arg0).getMotorisation());
		holder.tarifJour_vehicule.setText(vehicule.get(arg0).getPrix());
		
		return arg1;
	}

}
