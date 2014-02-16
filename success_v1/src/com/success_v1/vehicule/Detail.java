package com.success_v1.vehicule;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.view.View;
import android.widget.TextView;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;
 
public class Detail extends Activity {
	
    TextView id_vehicule;
    TextView model_vehicule;
    TextView marque_vehicule;
    TextView moteur_vehicule;
    TextView couleur_vehicule;
    TextView annee_vehicule;
    TextView km_vehicule;
    TextView prix_vehicule;
    TextView genreUser;
    TextView prenomUser;
    TextView nomUser;
    TextView mailUser;
    TextView numeroUser;
    
    
    TextView date_depart;
    TextView date_retour;
    
    SessionManager session;
    
    String pid;
    String pid_user;
    String date_depart_intent;
    String date_retour_intent;
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
 
    // JSON Node names
    //private static String url_detail = "http://10.0.3.2/Success2i_V1/get_vehicule_detail.php";
    //private static String url_reservation = "http://10.0.3.2/Success2i_V1/add_reservation.php";
    
    private static String url_detail = "http://192.168.1.99/Success2i_V1/get_vehicule_detail.php";
    private static String url_reservation = "http://192.168.1.99/Success2i_V1/add_reservation.php";
    // JSON Node names
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "vehicule_tab";
    private static final String TAG_ID = "id";
    private static final String TAG_MODEL = "modele";
    private static final String TAG_COLOR = "couleur";
    private static final String TAG_ENGINE = "motorisation";
    private static final String TAG_MARK = "marque";
    private static final String TAG_KM = "kilometrage";
    private static final String TAG_YEAR = "annee";
    private static final String TAG_PRICE = "tarifJour";
       
    JSONObject detail_tab = new JSONObject();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_detail);
        
        session = new SessionManager(getApplicationContext());
        
        HashMap<String, String> user = session.getUserDetails();
        pid_user = user.get(SessionManager.KEY_ID);
        
        Intent result = getIntent();
		pid = result.getStringExtra("id_voiture");
		date_depart_intent = result.getStringExtra("date_depart");
		date_retour_intent = result.getStringExtra("date_retour");
         
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
        
        date_depart = (TextView) findViewById(R.id.date_depart_recup);//
        date_retour = (TextView) findViewById(R.id.date_retour_recup);//

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
	            prix_vehicule.setText(detail_tab.getString(TAG_PRICE));
	            
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
