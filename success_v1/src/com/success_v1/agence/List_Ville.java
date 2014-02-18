package com.success_v1.agence;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.success_v1.successCar.R;

public class List_Ville extends Activity{
	
	ArrayList<Ville> ville_list = new ArrayList<Ville>();
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ville_list);
				
		ville_list.add(new Ville("01","Adrar"));
		ville_list.add(new Ville("31","Oran"));
		ville_list.add(new Ville("16","Alger"));
		ville_list.add(new Ville("09","Blida"));
		
		list = (ListView)findViewById(R.id.listVille);
		Ville_Adapter adpV = new Ville_Adapter(this, ville_list);
		
		list.setAdapter(adpV);
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Ville vl = new Ville();
				vl = (Ville) list.getAdapter().getItem(arg2);
				String nom = vl.nom_ville;
				
				Intent result = getIntent();
    	        result.putExtra("nomVille", nom);
    	        setResult(RESULT_OK, result);
    	        finish();
				
			}
			
			
		});
		
	}

}
