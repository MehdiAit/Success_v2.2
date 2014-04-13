package com.success_v1.vehicule;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.success_v1.main.ReservationStep1;
import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;

public class VehiculeListe extends Activity {

	private ProgressDialog pDialog;

	private JSONParser jsonParser = new JSONParser();
	private ArrayList<Vehicule> vehiculelist;
	private JSONArray jsonTab = null;

	//mettre un acc/mut pour la variable cat
	String cat;
	String test;
	private String idAgence;
	private String dateDepart;
	private String dateRetour;
	private String ville;
	private String latitude;
	private String longitude;

	private Location loc_current;
	private Location loc_i;

	private TextView trans_vehicule;
	private ImageView imgTrans_vehicule;
	private ListView lv;
	private AdapterVehicule ad;

	private static String url_all = config.getURL()+"get_vehicule.php";
	private static String url_all_location = config.getURL()+"get_vehicule_near.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_vehicule";
	private static final String TAG_ID = "id";
	private static final String TAG_MARQUE = "marque";
	private static final String TAG_MODELE = "modele";
	private static final String TAG_MOTORISATION = "motorisation";
	private static final String TAG_TARIF = "tarifJour";
	private static final String TAG_IMG = "imageVehicule";
	private static final String TAG_ville_param = "ville_agence";
	private static final String TAG_trans = "transmission";
	private static final String TAG_porte = "nbportes";
	private static final String TAG_climatisation = "climatisation";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";
	//private static final String TAG_IMG = "imageVehicule";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicule_list);
		trans_vehicule = (TextView) findViewById(R.id.trans_vehicule);
		imgTrans_vehicule = (ImageView) findViewById(R.id.img_trans);

		Intent result = getIntent();
		dateDepart = result.getStringExtra("dateDepart");
		dateRetour=result.getStringExtra("dateRetour");

		if(!ReservationStep1.locationActived)
		{
			ville =result.getStringExtra("ville");
		}
		else
		{
			latitude =result.getStringExtra("latitude");
			longitude =result.getStringExtra("longitude");

			loc_current = new Location("");
			loc_current.setLatitude(Double.valueOf(latitude));
			loc_current.setLatitude(Double.valueOf(longitude));
		}

		cat = result.getStringExtra("nom_cat");
		getActionBar().setTitle("");

		Log.i("Date depart", dateDepart);
		Log.i("Date retour", dateRetour);
		Log.i("cat",cat);

		vehiculelist = new ArrayList<Vehicule>();	
		new LoadAll().execute();

		lv = (ListView)findViewById(R.id.listVehicule);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Vehicule idtest = new Vehicule();
				idtest = (Vehicule) lv.getAdapter().getItem(arg2);

				String id = idtest.getId().toString();
				Log.i("id voiture",id);

				Intent intent = new Intent(getApplicationContext(), com.success_v1.vehicule.Detail.class);
				intent.putExtra("id_voiture", id);
				intent.putExtra("id_agence", idAgence);
				intent.putExtra("date_depart", dateDepart);
				intent.putExtra("date_retour", dateRetour);
				intent.putExtra("url_image", idtest.getUrlImage().toString());

				startActivityForResult(intent,1);

			}
		}
				);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		vehiculelist = new ArrayList<Vehicule>();	
		new LoadAll().execute();
	}

	class LoadAll extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(VehiculeListe.this);
			pDialog.setMessage("Chargement...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			int success;
			try {

				JSONObject json = new JSONObject();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cat_vehicule", cat));
				params.add(new BasicNameValuePair(TAG_ville_param, ville));
				params.add(new BasicNameValuePair("dateDebLoc_reservation", dateDepart));
				params.add(new BasicNameValuePair("dateFinLoc_reservation", dateRetour));

				if(!ReservationStep1.locationActived)
				{
					json = jsonParser.makeHttpRequest(url_all, "GET", params);
				}
				else
				{
					json = jsonParser.makeHttpRequest(url_all_location, "GET", params);
				}

				Log.d("Vehicules", json.toString() + idAgence);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					jsonTab = json.getJSONArray(TAG_TAB);
					for (int i = 0; i < jsonTab.length(); i++) {
						JSONObject c = jsonTab.getJSONObject(i);

						String id = c.getString(TAG_ID);
						String mark = c.getString(TAG_MARQUE);
						String model = c.getString(TAG_MODELE);
						String motor = c.getString(TAG_MOTORISATION);
						String tarif = c.getString(TAG_TARIF);
						String image = c.getString(TAG_IMG);
						String trans = c.getString(TAG_trans);
						String porte= c.getString(TAG_porte);
						String clim = c.getString(TAG_climatisation);
						String latitude_i = "";
						String longitude_i= "";
						if(ReservationStep1.locationActived)
						{
							latitude_i = c.getString(TAG_LATITUDE);
							longitude_i = c.getString(TAG_LONGITUDE);

							test = latitude_i;
							loc_i = new Location("");
							loc_i.setLatitude(Double.valueOf(latitude_i));
							loc_i.setLongitude(Double.valueOf(longitude_i));
						}

						Vehicule vehicule = new Vehicule(id,mark,model,motor,tarif,image,clim, porte, trans);


						if(ReservationStep1.locationActived)
						{	
							
							float distance;
							distance = (float) (Math.acos(Math.sin(Double.valueOf(latitude)) * Math.sin(Double.valueOf(latitude_i)) + Math.cos(Double.valueOf(latitude)) * Math.cos(Double.valueOf(latitude_i)) * Math.cos((Double.valueOf(longitude_i)) - (Double.valueOf(longitude_i)))) * 6371);
							distance = distance / 10;
							Log.i("dst",String.valueOf(distance));
							/********** 20 KM **************/
							if((int)distance < 20)
							{
								vehiculelist.add(vehicule);	
							}else
							{
								
							}														
								
						}else
						{
							vehiculelist.add(vehicule);
						}

					}
				}else{
					// Resultat de requete vide
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {                 
				public void run() {                 	
					ad = new AdapterVehicule(getApplicationContext(), vehiculelist);
					lv.setAdapter(ad);               	
				}
			});
		}

	}
}
