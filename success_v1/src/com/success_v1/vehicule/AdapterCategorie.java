package com.success_v1.vehicule;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.success_v1.successCar.R;

public class AdapterCategorie extends BaseAdapter {
	
	private ArrayList<CategorieVehicule> Categorie_vehicule;
	private LayoutInflater fl;
	
	public AdapterCategorie(Context context, ArrayList<CategorieVehicule> vehic)
	{
		fl = LayoutInflater.from(context);
		Categorie_vehicule = vehic;
	}

	@Override
	public int getCount() {
		return Categorie_vehicule.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Categorie_vehicule.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class ViewHolder{
		TextView id_cat;
		TextView nom_cat;
		TextView description_cat;
		TextView tarif_cat;
		TextView photo_cat;
		ImageView image_cat;
		//ImageView image_vehicule;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (arg1 == null)
		{
			arg1 = fl.inflate(R.layout.categorie_list_item, null);
			holder = new ViewHolder();
			
			holder.id_cat = (TextView)arg1.findViewById(R.id.id_list_categorie);
			holder.nom_cat = (TextView)arg1.findViewById(R.id.nom_list_categorie);
			holder.description_cat = (TextView)arg1.findViewById(R.id.description_list_categorie);
			holder.tarif_cat = (TextView)arg1.findViewById(R.id.tarifAPartir_categorie);
			holder.photo_cat = (TextView)arg1.findViewById(R.id.photo_categorie);
			holder.image_cat = (ImageView)arg1.findViewById(R.id.img_categorie);
			arg1.setTag(holder);
		}else
		{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.id_cat.setText(Categorie_vehicule.get(arg0).id);
		holder.nom_cat.setText(Categorie_vehicule.get(arg0).nom);
		holder.description_cat.setText(Categorie_vehicule.get(arg0).description);
		holder.tarif_cat.setText(Categorie_vehicule.get(arg0).prix);
		holder.photo_cat.setText(Categorie_vehicule.get(arg0).image);
		Picasso.with(arg1.getContext()).load(holder.photo_cat.getText().toString()).into(holder.image_cat);
		return arg1;
	}
	
	 

}
