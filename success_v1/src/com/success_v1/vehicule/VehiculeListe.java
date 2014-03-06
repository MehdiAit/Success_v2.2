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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
	private String idAgence;
	private String dateDepart;
	private String dateRetour;
	private String ville;


	private ListView lv;
	private AdapterVehicule ad;

	private static String url_all = config.getURL()+"get_vehicule.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_vehicule";
	private static final String TAG_ID = "id";
	private static final String TAG_MARQUE = "marque";
	private static final String TAG_MODELE = "modele";
	private static final String TAG_MOTORISATION = "motorisation";
	private static final String TAG_TARIF = "tarifJour";
	private static final String TAG_IMG = "imageVehicule";
	private static final String TAG_ville_param = "ville_agence";
	//private static final String TAG_IMG = "imageVehicule";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicule_list);

		Intent result = getIntent();
		dateDepart = result.getStringExtra("dateDepart");
		dateRetour=result.getStringExtra("dateRetour");
		ville =result.getStringExtra("ville");
		cat = result.getStringExtra("nom_cat");
		getActionBar().setTitle("");

		Log.i("Date depart", dateDepart);
		Log.i("Date retour", dateRetour);
		Log.i("cat",cat);
		Log.i("ville",ville);

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
			pDialog.setMessage("Loading .Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			int success;
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cat_vehicule", cat));
				params.add(new BasicNameValuePair(TAG_ville_param, ville));
				params.add(new BasicNameValuePair("dateDebLoc_reservation", dateDepart));
				params.add(new BasicNameValuePair("dateFinLoc_reservation", dateRetour));
				JSONObject json = jsonParser.makeHttpRequest(url_all, "GET", params);

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
						Vehicule vehicule = new Vehicule(id,mark,model,motor,tarif,image);
						//Log.i("mark",vehicule.);

						vehiculelist.add(vehicule);
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
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  menu.findItem(R.id.action_search).setVisible(true);
	  return true;
	 }
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch(item.getItemId())
			{
			//case R.id.SubMenuLogOut: session.logoutUser();Toast.makeText(getApplicationContext(), "Deconnexion", Toast.LENGTH_SHORT).show();break;
			case R.id.SubMenuNote:Toast.makeText(this, "Notez nous", Toast.LENGTH_SHORT).show();
			case R.id.SubMenuAbout:Toast.makeText(this, "Qui suis-je?", Toast.LENGTH_SHORT).show();
			case R.id.action_search:
				Intent searchActivity= new Intent(this,VehiculeSearch.class);
				startActivity(searchActivity);
				break;
			}
			return super.onOptionsItemSelected(item);
		}
}
