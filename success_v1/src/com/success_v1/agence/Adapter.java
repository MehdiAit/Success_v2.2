package com.success_v1.agence;

import java.util.ArrayList;

import com.success_v1.successCar.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter extends BaseAdapter {
	
	private ArrayList<Agence> agence;
	private LayoutInflater fl;
	
	public Adapter(Context context, ArrayList<Agence> agen)
	{
		fl = LayoutInflater.from(context);
		agence = agen;
	}

	@Override
	public int getCount() {
		return agence.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return agence.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class ViewHolder{
		TextView id_agence;
		TextView nom_agence;
		TextView adr_agence;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (arg1 == null)
		{
			arg1 = fl.inflate(R.layout.agence_list_item, null);
			holder = new ViewHolder();
			
			holder.id_agence = (TextView)arg1.findViewById(R.id.id_agence);
			holder.nom_agence = (TextView)arg1.findViewById(R.id.nom_agence);
			holder.adr_agence = (TextView)arg1.findViewById(R.id.adr_agence);
			
			
			arg1.setTag(holder);
		}else
		{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.id_agence.setText(agence.get(arg0).id);
		holder.nom_agence.setText(agence.get(arg0).nom);
		holder.adr_agence.setText(agence.get(arg0).adresse);
		
		return arg1;
	}

}
