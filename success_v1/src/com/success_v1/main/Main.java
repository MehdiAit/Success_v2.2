package com.success_v1.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.success_v1.location.GpsTrack;
import com.success_v1.res.JSONParser;
import com.success_v1.reservation.ReservationTab;
import com.success_v1.successCar.R;
import com.success_v1.user.LogPage;
import com.success_v1.user.ProfilPage;
import com.success_v1.user.SessionManager;

public class Main extends Activity implements OnClickListener{
	private Button btnsearch;
	private Button btnReservation;
	private Button btnCompte;
	private TextView txtLocate;
    // Session Manager Class
    private SessionManager session;
    private HashMap<String, String> user;
    private ConnectivityManager wf;
    private NetworkInfo info;
    
    //Location
	private ProgressDialog  pDialog;
	private JSONParser jParser = new JSONParser();
	private JSONArray results;
	private GpsTrack gps;
	
	private static String jsonUrl_google_api = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	private static String jsonUrl_param = "&sensor=true&language=ar";
	
	private String comune = "";
	private String ville = "";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acceuil);
		getActionBar().setTitle(null);
		getActionBar().setDisplayShowHomeEnabled(false);

        session = new SessionManager(getApplicationContext());
		wf = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        info = wf.getActiveNetworkInfo();
        if(info != null)
        {
        	Log.d("wifi state","Connected");
        }else
        {
        	Log.d("wifi state","Deconnected");
        }
        
		btnsearch = (Button)this.findViewById(R.id.btnsearch);
		btnReservation= (Button)this.findViewById(R.id.btnReservations);
		btnCompte = (Button)this.findViewById(R.id.btnCompte);
		txtLocate = (TextView)findViewById(R.id.txtLocalMap);
		
		btnsearch.setOnClickListener(this);
		btnReservation.setOnClickListener(this);
		btnCompte.setOnClickListener(this);
		
		gps = new GpsTrack(getApplicationContext());
		new asyncRecup().execute();
		
		TranslateAnimation trans1 = new TranslateAnimation (0,0,800,0);
		trans1.setStartOffset(600);
		trans1.setFillAfter(true);
		trans1.setDuration(800);
		btnsearch.startAnimation(trans1);
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
		case R.id.btnsearch:
			Intent agenceActivity= new Intent(this,ReservationStep1.class);
			agenceActivity.putExtra("comune", comune);
			agenceActivity.putExtra("ville", ville);
			startActivity(agenceActivity);
			break;
		case R.id.btnReservations:
			if(session.isLoggedIn())
			{
			startActivity(new Intent(this, ReservationTab.class));
			}else
			{
				Intent compteActivity = new Intent(this,LogPage.class);
				startActivityForResult(compteActivity,1);
			}
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
	
	public class asyncRecup extends AsyncTask<String, String, String>
	{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Localisation en cours, Patientez ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
		@Override
		protected String doInBackground(String... params) {

			Double a = gps.getLatitude();
			Double b = gps.getLongitude();	
			String url = jsonUrl_google_api+a.toString()+","+b.toString()+jsonUrl_param;
			
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url, "GET", param);

			try {
				results = json.getJSONArray("results");
				Log.i("JsonArray",results.toString());
				comune = results.getJSONObject(1).getJSONArray("address_components").getJSONObject(0).getString("long_name").toString();
				ville = results.getJSONObject(1).getJSONArray("address_components").getJSONObject(1).getString("long_name").toString();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		protected void onPostExecute(String file_url) {
			
			txtLocate.setText(" "+comune+", "+ville);
        	pDialog.dismiss();
    }

	}
}
