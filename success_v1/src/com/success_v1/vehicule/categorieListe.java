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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.internal.cn;
import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class categorieListe extends Fragment {
	
	private ProgressDialog pDialog;

	private JSONParser jsonParser = new JSONParser();
	private ArrayList<CategorieVehicule> cat_vehiculelist;
	private JSONArray jsonTab = null;
	
	//mettre un acc/mut pour la variable cat
	String cat_type;
	private String idAgence;
	private String dateDepart;
	private String dateRetour;
	private String ville;
	private String latitude;
	private String longitude;
	
	
	private ListView lv;
	private AdapterCategorie ad;
	private View rootView = null;

	private static String url_all = config.getURL()+"get_categorie.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_categorie";
	private static final String TAG_ID = "id";
	private static final String TAG_NOM = "nom";
	private static final String TAG_DESCRIPTION = "description";
	//private static final String TAG_TYPE = "type";
	
	//private static final String TAG_IMG = "imageVehicule";
	//private static final String TAG_ville_param = "ville_agence";
	//private static final String TAG_IMG = "imageVehicule";
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.categorie_list, container, false);

		Intent result = getActivity().getIntent();
		dateDepart = result.getStringExtra("dateDepart");
		dateRetour=result.getStringExtra("dateRetour");
		
		
		ville =result.getStringExtra("ville");
		latitude =result.getStringExtra("latitude");
		longitude =result.getStringExtra("longitude");

		
		Log.i("Date depart", dateDepart);
		Log.i("Date retour", dateRetour);

		cat_vehiculelist = new ArrayList<CategorieVehicule>();	
		new LoadAll().execute();

		lv = (ListView)rootView.findViewById(R.id.listCategorie);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				CategorieVehicule idtest = new CategorieVehicule();
				idtest = (CategorieVehicule) lv.getAdapter().getItem(arg2);

				String nom = idtest.getName().toString();
				
				Intent intent = new Intent(getActivity().getApplicationContext(), com.success_v1.vehicule.VehiculeListe.class);
				intent.putExtra("nom_cat", nom);
				intent.putExtra("id_agence", idAgence);
				intent.putExtra("dateDepart", dateDepart);
				intent.putExtra("dateRetour", dateRetour);
				
				/********* it depande , if approximate agence location is checked then ville param's dosn't existe ********/
				intent.putExtra("ville", ville);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				
				
				startActivityForResult(intent,1);				
				
				
			}
		}
				);		
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1)
		{
			getActivity();
			if(resultCode == Activity.RESULT_OK)
			{
				cat_vehiculelist = new ArrayList<CategorieVehicule>();
				new LoadAll().execute();
				Toast.makeText(getActivity().getApplicationContext(), "Test intent" , Toast.LENGTH_LONG).show();
			}
		}
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
				params.add(new BasicNameValuePair("type", cat_type));
				JSONObject json = jsonParser.makeHttpRequest(url_all, "GET", params);

				Log.d("cate", json.toString() + idAgence);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					jsonTab = json.getJSONArray(TAG_TAB);
					for (int i = 0; i < jsonTab.length(); i++) {
						JSONObject c = jsonTab.getJSONObject(i);

						String id = c.getString(TAG_ID);
						String mark = c.getString(TAG_NOM);
						String model = c.getString(TAG_DESCRIPTION);
						//String motor = c.getString(TAG_TYPE);

						//String image = c.getString(TAG_IMG);
						CategorieVehicule categorie = new CategorieVehicule(id,mark,model);
						//Log.i("mark",vehicule.);

						cat_vehiculelist.add(categorie);
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
					ad = new AdapterCategorie(getActivity(), cat_vehiculelist);
					lv.setAdapter(ad);               	
				}
			});
		}

	}

}
