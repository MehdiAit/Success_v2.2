package com.success_v1.agence;

import java.util.ArrayList;

import com.success_v1.agence.Adapter.ViewHolder;
import com.success_v1.successCar.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Ville_Adapter extends BaseAdapter{
	
	private ArrayList<Ville> list_ville;
	private LayoutInflater lf;
	
	public Ville_Adapter(Context ctx, ArrayList<Ville> ville)
	{
		this.lf = LayoutInflater.from(ctx);
		this.list_ville = ville;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list_ville.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.list_ville.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class ViewHolder
	{
		TextView num;
		TextView nom;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holde;
		
		if(arg1 == null)
		{
			arg1 = lf.inflate(R.layout.ville_list_item, null);
			holde = new ViewHolder();
			
			holde.num = (TextView)arg1.findViewById(R.id.num_ville);
			holde.nom = (TextView)arg1.findViewById(R.id.nom_ville);
			
			arg1.setTag(holde);
		}
		else
		{
			holde = (ViewHolder) arg1.getTag();
		}
		
		holde.num.setText(list_ville.get(arg0).num_ville);
		holde.nom.setText(list_ville.get(arg0).nom_ville);
		
		return arg1;
	}

}
