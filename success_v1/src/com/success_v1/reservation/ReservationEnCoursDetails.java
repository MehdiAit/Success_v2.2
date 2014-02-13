package com.success_v1.reservation;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class ReservationEnCoursDetails extends Activity {
	
    TextView txtNumReservation;
    TextView txtDatReservation;
    TextView txtDebutReservation;
    TextView txtFinReservation;
    TextView txtMarqueReservation;
    TextView txtModelReservation;
    TextView txtCategorieReservation;
    TextView txtNomAgenceReservation;
    TextView txtEtatReservation;
    Button btnAnnulerReservation;
    
    String pid;
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
 
    // JSON Node names
    //private static String url_detail = "http://10.0.3.2/Success2i_V1/get_reservation_encours_detail.php";
    private static String url_detail = "http://192.168.1.99/Success2i_V1/get_reservation_encours_detail.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "tab_detailReservation";
    private static final String TAG_ID = "id";
    private static final String TAG_DATERESERV = "date_reservation";
    private static final String TAG_DEBRESERV ="debut_reservation";
    private static final String TAG_FINRESERV = "fin_reservation";
    private static final String TAG_ETATRESERV = "etat_reservation";
    private static final String TAG_MARKRESERV = "marque_vehicule";
    private static final String TAG_MODELERESERV = "modele_vehicule";
    private static final String TAG_NOMAGENCE = "nom_agence";
       
    JSONObject detail_tab = new JSONObject();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_en_cours_detail);
        
        Intent result = getIntent();
		pid = result.getStringExtra("id_get");
         
		txtNumReservation = (TextView) findViewById(R.id.txtNumReservation);
		txtDatReservation = (TextView) findViewById(R.id.txtDatReservation);
		txtDebutReservation = (TextView) findViewById(R.id.txtDebutReservation);
		txtFinReservation = (TextView) findViewById(R.id.txtFinReservation);
		txtEtatReservation = (TextView) findViewById(R.id.txtEtatReservation);
		txtMarqueReservation = (TextView) findViewById(R.id.txtMarqueReservation);
		txtModelReservation = (TextView) findViewById(R.id.txtModelReservation);
		txtCategorieReservation= (TextView) findViewById(R.id.txtCategorieReservation);
		txtNomAgenceReservation = (TextView) findViewById(R.id.txtNomAgenceReservation);
		btnAnnulerReservation= (Button)findViewById(R.id.btnAnnulerReservation);
		btnAnnulerReservation.setOnClickListener(new View.OnClickListener(){
  	      @Override
  	      public void onClick(View v) {
  	        
  	    	/*Intent mapActivity = new Intent(this, mapAgence.class)
  	        mapActivity.putExtra("re_id", pid);
  	        startActivity(mapActivity);*/
  	      }
  	    });

        //Log.d("value", a);
        
        new GetReservationDetails().execute(); 
 
    }
 
    class GetReservationDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReservationEnCoursDetails.this);
            pDialog.setMessage("Loading .Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        protected String doInBackground(String... args) {
                    int success;
                    try {
                        SessionManager session = new SessionManager(getApplicationContext());
                        
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id_reservation", pid));
                        params.add(new BasicNameValuePair("id_user",session.getIdUser()));
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
	            txtNumReservation.setText(detail_tab.getString(TAG_ID));
	            txtDatReservation.setText(detail_tab.getString(TAG_DATERESERV));
	            txtDebutReservation.setText(detail_tab.getString(TAG_DEBRESERV));
	            txtFinReservation.setText(detail_tab.getString(TAG_FINRESERV));
	            txtEtatReservation.setText(detail_tab.getString(TAG_ETATRESERV));
	            txtMarqueReservation.setText(detail_tab.getString(TAG_MARKRESERV));
	            txtModelReservation.setText(detail_tab.getString(TAG_MODELERESERV));
	            //txtCategorieReservation.setText(detail_tab.getString(TAG_NOMAGENCE));
	            txtNomAgenceReservation.setText(detail_tab.getString(TAG_NOMAGENCE));	            
			} catch (JSONException e) {
				e.printStackTrace();
			}                      
            pDialog.dismiss();           
        }
    } 
    
}