package com.success_v1.main;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.success_v1.agence.ReservationStep1;
import com.success_v1.reservation.ReservationTab;
import com.success_v1.successCar.R;
import com.success_v1.user.LogPage;
import com.success_v1.user.ProfilPage;
import com.success_v1.user.SessionManager;

public class Main extends Activity implements OnClickListener{
	private Button btnAgences;
	private Button btnReservation;
	private Button btnCompte;
	private Button btnTestPref;
    // Session Manager Class
    SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acceuil);
		// Je vais rajouter du code ici!
        // Session class instance
        session = new SessionManager(getApplicationContext());
        
		btnAgences = (Button)this.findViewById(R.id.btnAgences);
		btnReservation= (Button)this.findViewById(R.id.btnReservations);
		btnCompte = (Button)this.findViewById(R.id.btnCompte);
		btnTestPref = (Button)this.findViewById(R.id.btnTestPref);
		btnAgences.setOnClickListener(this);
		btnReservation.setOnClickListener(this);
		btnCompte.setOnClickListener(this);
		btnTestPref.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.btnAgences:
			Intent agenceActivity= new Intent(this,ReservationStep1.class);
			startActivity(agenceActivity);
			break;
		case R.id.btnTestPref:
			session.logoutUser();
			Toast.makeText(getApplicationContext(), "Deconexion", Toast.LENGTH_LONG).show();
			break;
		case R.id.btnReservations:
			startActivity(new Intent(this, ReservationTab.class));
			break;
		case R.id.btnCompte:
			if(session.isLoggedIn())
			{
				Intent profilActivity= new Intent(this,ProfilPage.class);
				startActivity(profilActivity);
			}else
			{
				Intent compteActivity = new Intent(this,LogPage.class);
				startActivity(compteActivity);
			}
			
			break;
		}
		
	}
}
