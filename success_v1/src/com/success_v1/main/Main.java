package com.success_v1.main;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
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
    // Session Manager Class
    SessionManager session;
    HashMap<String, String> user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acceuil);
		getActionBar().setTitle(null);
		getActionBar().setDisplayShowHomeEnabled(false);

        session = new SessionManager(getApplicationContext());
        
		btnAgences = (Button)this.findViewById(R.id.btnAgences);
		btnReservation= (Button)this.findViewById(R.id.btnReservations);
		btnCompte = (Button)this.findViewById(R.id.btnCompte);
		
		btnAgences.setOnClickListener(this);
		btnReservation.setOnClickListener(this);
		btnCompte.setOnClickListener(this);
		
		TranslateAnimation trans1 = new TranslateAnimation (0,0,800,0);
		trans1.setStartOffset(600);
		trans1.setFillAfter(true);
		trans1.setDuration(800);
		btnAgences.startAnimation(trans1);
		TranslateAnimation trans2 = new TranslateAnimation (320,0,0,0);
		trans2.setStartOffset(320);
		trans2.setFillAfter(true);
		trans2.setDuration(800);
		btnReservation.startAnimation(trans2);
		TranslateAnimation trans3 = new TranslateAnimation (0,0,400,0); /*gauche, droite, haut, bas */
		trans3.setStartOffset(400);
		//trans3.setFillAfter(true);
		trans3.setDuration(800);
		btnCompte.startAnimation(trans3);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1)
		{
			if(resultCode == RESULT_OK)
			{
				user = session.getUserDetails();
				Toast.makeText(getApplicationContext(), "Bienvenue a vous : "+ user.get(SessionManager.KEY_NOM), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.SubMenuLogOut: session.logoutUser();Toast.makeText(getApplicationContext(), "Deconnexion", Toast.LENGTH_SHORT).show();break;
		case R.id.SubMenuNote:Toast.makeText(this, "Notez nous", Toast.LENGTH_SHORT).show();
		case R.id.SubMenuAbout:Toast.makeText(this, "Qui suis-je?", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
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
		case R.id.btnReservations:
			startActivity(new Intent(this, ReservationTab.class));
			break;
		case R.id.btnCompte:			
			if(session.isLoggedIn())
			{
				Intent profilActivity= new Intent(this,ProfilPage.class);
				startActivityForResult(profilActivity,2);
			}else
			{
				Intent compteActivity = new Intent(this,LogPage.class);
				startActivityForResult(compteActivity,1);
			}
			
			break;
		}
		
	}
}
