package com.success_v1.vehicule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class Detail extends Activity {

	private TextView id_vehicule;
	private TextView model_vehicule;
	private TextView marque_vehicule;
	private TextView moteur_vehicule;
	//private TextView couleur_vehicule;
	//private TextView annee_vehicule;
	//private TextView km_vehicule;
	
	private TextView prix_vehicule;
	private TextView genreUser;
	private TextView prenomUser;
	private TextView nomUser;
	private TextView mailUser;
	private TextView numeroUser;
	//private TextView titleActionBar;
	private ImageView imageCaisse;
	//private ImageView logoEtape;
	private TextView nbre_jours_reserv;


	private TextView date_depart;
	private TextView date_retour;
	String date;
	private SessionManager session;

	private String pid;
	private String pid_user;
	private String date_depart_intent;
	private String date_retour_intent;
	private String url_image; 
	private String id_agence;
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	private JSONParser jsonParser = new JSONParser();

	private Integer numberDays;
	private Integer carPrice;
	private DateTime dt;
	private DateTime dt2;
	Intent result;

	
	
	public void ConvertDate(String format, TextView txtdate)
	{
		date = new SimpleDateFormat(format).format(new Date());
		txtdate.setText(date);	
	}

	private static String url_detail = config.getURL()+"get_vehicule_detail.php";
	private static String url_reservation = config.getURL()+"add_reservation.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "vehicule_tab";
	private static final String TAG_ID = "id";
	private static final String TAG_MODEL = "modele";
	private static final String TAG_ID_Agence = "id_agence";
	//private static final String TAG_COLOR = "couleur";
	private static final String TAG_ENGINE = "motorisation";
	private static final String TAG_MARK = "marque";
	//private static final String TAG_KM = "kilometrage";
	//private static final String TAG_YEAR = "annee";
	private static final String TAG_PRICE = "tarifJour";

	private JSONObject detail_tab = new JSONObject();
	private HashMap<String, String> user = new HashMap<String, String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicule_detail);
		//getActionBar().setDisplayShowHomeEnabled(false);
		/*getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);

		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Création de la réservation (3/3)");
		logoEtape = (ImageView)findViewById(R.id.logoEtape3);
		logoEtape.setVisibility(ImageView.VISIBLE);*/
		getActionBar().setTitle("");
		session = new SessionManager(getApplicationContext());

		user = session.getUserDetails();
		pid_user = user.get(SessionManager.KEY_ID);

		Intent result = getIntent();
		pid = result.getStringExtra("id_voiture");
		date_depart_intent = result.getStringExtra("date_depart");
		date_retour_intent = result.getStringExtra("date_retour");
		url_image = result.getStringExtra("url_image");
		
		Log.i("dt",date_depart_intent);
		Log.i("dt_rt",date_retour_intent);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		dt = formatter.parseDateTime(date_depart_intent);
		
		DateTimeFormatter formatter2 = DateTimeFormat.forPattern("yyyy-MM-dd");
		dt2 = formatter2.parseDateTime(date_retour_intent);
		
		Log.i("aze",dt.toString());

		numberDays = Days.daysBetween(dt, dt2).getDays() + 1;

		id_vehicule = (TextView) findViewById(R.id.id_vehicule_recup);
		model_vehicule = (TextView) findViewById(R.id.nom_vehicule_recup);//
		marque_vehicule = (TextView) findViewById(R.id.marqueVehicule);//
		moteur_vehicule = (TextView) findViewById(R.id.motorVehicule);
		prix_vehicule = (TextView) findViewById(R.id.prixVehicule);

		genreUser= (TextView) findViewById(R.id.txtGenreResume);
		nomUser= (TextView) findViewById(R.id.txtNomResume);
		prenomUser = (TextView) findViewById(R.id.txtPrenomResume);
		mailUser = (TextView) findViewById(R.id.txtMailResume);
		numeroUser = (TextView) findViewById(R.id.txtPhoneResume);
		imageCaisse = (ImageView) findViewById(R.id.imgLogoCar);
		date_depart = (TextView) findViewById(R.id.date_depart_recup);//
		date_retour = (TextView) findViewById(R.id.date_retour_recup);//
		nbre_jours_reserv = (TextView) findViewById(R.id.nbre_jours_reserv);//

		prenomUser.setText(user.get(SessionManager.KEY_PRENOM));
		nomUser.setText(user.get(SessionManager.KEY_NOM));
		mailUser.setText(user.get(SessionManager.KEY_MAIL));
		numeroUser.setText(user.get(SessionManager.KEY_NUM));

		findViewById(R.id.btntestreseravation).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				if(session.isLoggedIn())
				{
					new AddReservation().execute();
				}
				else
				{
					Intent intent = new Intent(getApplicationContext(), com.success_v1.user.LogPage.class);
					startActivityForResult(intent,55);
				}
			}
		});

		new GetCarDetails().execute(); 

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 55)
		{
			if(resultCode == RESULT_OK)
			{
				user = session.getUserDetails();
				pid_user = user.get(SessionManager.KEY_ID);
				
				prenomUser.setText(user.get(SessionManager.KEY_PRENOM));
				nomUser.setText(user.get(SessionManager.KEY_NOM));
				mailUser.setText(user.get(SessionManager.KEY_MAIL));
				numeroUser.setText(user.get(SessionManager.KEY_NUM));
				
			}
		}
	}

	class GetCarDetails extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail.this);
			pDialog.setMessage("Loading .Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int success;
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_get_voiture", pid));
				JSONObject json = jsonParser.makeHttpRequest(url_detail, "GET", params);

				Log.d("Detail", json.toString() + pid);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray productObj = json.getJSONArray(TAG_TAB);
					detail_tab = productObj.getJSONObject(0);
					

				}else{
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {
			try {           		            
				id_vehicule.setText(detail_tab.getString(TAG_ID));
				model_vehicule.setText(detail_tab.getString(TAG_MODEL));
				marque_vehicule.setText(detail_tab.getString(TAG_MARK));
				moteur_vehicule.setText(detail_tab.getString(TAG_ENGINE));

				carPrice = Integer.valueOf(detail_tab.getString(TAG_PRICE));
				carPrice = carPrice * numberDays;
				nbre_jours_reserv.setText(numberDays.toString());

				prix_vehicule.setText(carPrice.toString());
				Picasso.with(getApplicationContext()).load(url_image).into(imageCaisse);
				date_depart.setText(dt.toString("dd-MM-yyyy"));
				date_retour.setText(dt2.toString("dd-MM-yyyy"));  
				
				/******* Convertisseur a revoire ***********/
				//ConvertDate("dd-MM-yyyy", date_depart);
				//ConvertDate("dd-MM-yyyy", date_retour);
				
				id_agence = detail_tab.getString(TAG_ID_Agence);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}                 
			pDialog.dismiss();           
		}
	} 



	class AddReservation extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail.this);
			pDialog.setMessage("Reservation en cours ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) { 
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_vehicule", pid));
			params.add(new BasicNameValuePair("id_user", pid_user));
			params.add(new BasicNameValuePair("date_depart", date_depart_intent));
			params.add(new BasicNameValuePair("date_retour", date_retour_intent));
			params.add(new BasicNameValuePair("nb_jour", numberDays.toString()));
			params.add(new BasicNameValuePair("prix_total", carPrice.toString()));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_reservation, "POST", params);

			// check log cat fro response
			Log.d("Test reservation", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					// successfully created product
					//Intent result = getIntent();					
					//setResult(RESULT_OK, result);					
					//finish();
					
					
					// intent 2 recupe agence ID:
					
					result = new Intent(Detail.this, com.success_v1.agence.Detail.class);
					result.putExtra("id_agence", id_agence);
					startActivityForResult(result,1);
					finish();
					

				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub    	
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			setResult(RESULT_OK, result);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
