package com.success_v1.vehicule;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class VehiculeListe extends Fragment {

	private ProgressDialog pDialog;

	private JSONParser jsonParser = new JSONParser();
	private ArrayList<Vehicule> vehiculelist;
	private JSONArray jsonTab = null;
	
	//mettre un acc/mut pour la variable cat
	String cat;
	private String idAgence;
	private String typeVehicule;
	private String dateDepart;
	private String dateRetour;
	private ListView lv;
	private SessionManager session;
	private View rootView = null;

	//private static String url_all = "http://10.0.3.2/Success2i_V1/get_vehicule.php";
	private static String url_all = "http://192.168.1.99/Success2i_V1/get_vehicule.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_vehicule";
	private static final String TAG_ID = "id";
	private static final String TAG_MARQUE = "marque";
	private static final String TAG_MODELE = "modele";
	private static final String TAG_MOTORISATION = "motorisation";
	private static final String TAG_TARIF = "tarifJour";
		
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.vehicule_list, container, false);
        
        
		session = new SessionManager(getActivity().getApplicationContext());

		Intent result = getActivity().getIntent();
		typeVehicule = result.getStringExtra("typeVehicule");
		dateDepart = result.getStringExtra("dateDepart");
		dateRetour=result.getStringExtra("dateRetour");

		
		Log.i("Date depart", dateDepart);
		Log.i("Date retour", dateRetour);

		vehiculelist = new ArrayList<Vehicule>();	
		new LoadAll().execute();

		lv = (ListView)rootView.findViewById(R.id.listVehicule);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		        
				if(session.isLoggedIn())
				{
				Vehicule idtest = new Vehicule();
				idtest = (Vehicule) lv.getAdapter().getItem(arg2);

				String id = idtest.getId().toString();
				Log.i("id voiture",id);
				
				Intent intent = new Intent(getActivity().getApplicationContext(), com.success_v1.vehicule.Detail.class);
				intent.putExtra("id_voiture", id);
				intent.putExtra("id_agence", idAgence);
				intent.putExtra("date_depart", dateDepart);
				intent.putExtra("date_retour", dateRetour);
				startActivityForResult(intent,10);
				
				}
				else
				{
					Intent intent = new Intent(getActivity().getApplicationContext(), com.success_v1.user.LogPage.class);
					startActivity(intent);
				}
				
			}
		}
				);		
		return rootView;
	}
	

	class LoadAll extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
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
				params.add(new BasicNameValuePair("type_vehicule", typeVehicule));
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

						Vehicule vehicule = new Vehicule(id,mark,model,motor,tarif);
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
			getActivity().runOnUiThread(new Runnable() {                 
				public void run() {                 	
					AdapterVehicule ad = new AdapterVehicule(getActivity(), vehiculelist);
					lv.setAdapter(ad);               	
					//Log.i("Thread","Hello thread");
				}
			});
		}

	}

}
