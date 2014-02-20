package com.success_v1.vehicule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class Detail extends Activity {

	private TextView id_vehicule;
	private TextView model_vehicule;
	private TextView marque_vehicule;
	private TextView moteur_vehicule;
	private TextView couleur_vehicule;
	private TextView annee_vehicule;
	private TextView km_vehicule;
	private TextView prix_vehicule;
	private TextView genreUser;
	private TextView prenomUser;
	private TextView nomUser;
	private TextView mailUser;
	private TextView numeroUser;
	private TextView titleActionBar;
	private ImageView imageCaisse;
	private ImageView logoEtape;
	private TextView nbre_jours_reserv;


	private TextView date_depart;
	private TextView date_retour;

	private SessionManager session;

	private String pid;
	private String pid_user;
	private String date_depart_intent;
	private String date_retour_intent;
	private String url_image; 
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	private JSONParser jsonParser = new JSONParser();

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private Integer numberDays;
	private Date d1;
	private Date d2;

	// JSON Node names
	//private static String url_detail = "http://10.0.3.2/Success2i_V1/get_vehicule_detail.php";
	//private static String url_reservation = "http://10.0.3.2/Success2i_V1/add_reservation.php";

	private static String url_detail = "http://192.168.1.72/Success2i_V1/get_vehicule_detail.php";
	private static String url_reservation = "http://192.168.1.72/Success2i_V1/add_reservation.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "vehicule_tab";
	private static final String TAG_ID = "id";
	private static final String TAG_MODEL = "modele";
	//private static final String TAG_COLOR = "couleur";
	private static final String TAG_ENGINE = "motorisation";
	private static final String TAG_MARK = "marque";
	//private static final String TAG_KM = "kilometrage";
	//private static final String TAG_YEAR = "annee";
	private static final String TAG_PRICE = "tarifJour";

	JSONObject detail_tab = new JSONObject();

	public static Date stringToDate(String sDate) throws ParseException {
		return formatter.parse(sDate);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicule_detail);
		//getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);

		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Création de la réservation (3/3)");
		logoEtape = (ImageView)findViewById(R.id.logoEtape3);
		logoEtape.setVisibility(ImageView.VISIBLE);
		session = new SessionManager(getApplicationContext());

		HashMap<String, String> user = session.getUserDetails();
		pid_user = user.get(SessionManager.KEY_ID);

		Intent result = getIntent();
		pid = result.getStringExtra("id_voiture");
		date_depart_intent = result.getStringExtra("date_depart");
		date_retour_intent = result.getStringExtra("date_retour");
		url_image = result.getStringExtra("url_image");

		try {
			d1 = stringToDate(date_depart_intent);
			d2 = stringToDate(date_retour_intent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		numberDays = Days.daysBetween(new DateTime(d1), new DateTime(d2)).getDays() + 1;

		Log.d("test",numberDays.toString());

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
		
		//Log.d("value", a);

		findViewById(R.id.btntestreseravation).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				new AddReservation().execute();
			}
		});

		new GetCarDetails().execute(); 

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
					// Resultat de requete vide
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

				Integer carPrice = Integer.valueOf(detail_tab.getString(TAG_PRICE));
				carPrice = carPrice * numberDays;
				nbre_jours_reserv.setText(numberDays.toString());


				prix_vehicule.setText(carPrice.toString());
				Picasso.with(getApplicationContext()).load(url_image).into(imageCaisse);
				date_depart.setText(date_depart_intent);
				date_retour.setText(date_retour_intent);           
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
					Intent result = getIntent();
					result.putExtra("re_id", pid);
					setResult(RESULT_OK, result);
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

}
