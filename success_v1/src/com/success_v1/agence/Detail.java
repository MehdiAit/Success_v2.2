package com.success_v1.agence;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class Detail extends Activity {
	
    TextView id_agence;
    TextView nom_agence;
    TextView adr_agence;
    TextView tel_agence;
    TextView fax_agence;
    TextView mail_agence;
    TextView desc_agence;
    TextView latitude_agence;
    TextView longitude_agence;
    Button btnAppeler_agence;
    Button btnMap_agence;
    Button btnReserver_agence;
    
    String pid;
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
 
    // JSON Node names
    private static String url_detail = "http://10.0.3.2/Success2i_V1/get_agence_detail.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "agence_tab";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_ADRESSE ="adresse";
    private static final String TAG_TELEPHONE = "telephone";
    private static final String TAG_FAX = "fax";
    private static final String TAG_MAIL = "mail";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
       
    JSONObject detail_tab = new JSONObject();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agence_detail);
        
        Intent result = getIntent();
		pid = result.getStringExtra("id_get");
         
        id_agence = (TextView) findViewById(R.id.id_agence);
        nom_agence = (TextView) findViewById(R.id.nom_agence);
        adr_agence = (TextView) findViewById(R.id.adr_agence);
        tel_agence = (TextView) findViewById(R.id.tel_agence);
        fax_agence = (TextView) findViewById(R.id.fax_agence);
        mail_agence = (TextView) findViewById(R.id.mail_agence);
        desc_agence = (TextView) findViewById(R.id.desc_agence);
        latitude_agence = (TextView) findViewById(R.id.latitude_agence);
        longitude_agence = (TextView) findViewById(R.id.longitude_agence);
        btnMap_agence= (Button)findViewById(R.id.btnMap_agence);
        btnAppeler_agence = (Button)findViewById(R.id.btnCall_agence);
        btnReserver_agence = (Button)findViewById(R.id.btnReserver_agence);
        btnMap_agence.setOnClickListener(new View.OnClickListener(){
  	      @Override
  	      public void onClick(View v) {
  	        
  	    	Intent mapActivity = new Intent(Detail.this, com.success_v1.googlemapsv2.MapActivity.class);
  	        mapActivity.putExtra("re_id", pid);
  	        mapActivity.putExtra("latitude", latitude_agence.getText());
  	        mapActivity.putExtra("longitude", longitude_agence.getText());
  	        startActivity(mapActivity);
  	      }
  	    });
        btnAppeler_agence.setOnClickListener(new View.OnClickListener(){
    	      @Override
    	      public void onClick(View v) {
    	        /****** Mettre la fonction d'appel en reprenant le numéro de téléphone de l'agence*********/
    	      }
    	    });
        btnReserver_agence.setOnClickListener(new View.OnClickListener(){
    	      @Override
    	      public void onClick(View v) {
    	    	  Intent ReserverActivity = new Intent(getBaseContext(), ReservationStep1.class);     
    	    	  ReserverActivity.putExtra("re_id", pid);
    	    	  ReserverActivity.putExtra("nomAgence", (String)nom_agence.getText());
    	  	      startActivity(ReserverActivity);
    	      }
    	    });

        //Log.d("value", a);

		findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener(){
	      @Override
	      public void onClick(View v) {
	        
	    	Intent result = getIntent();
	        result.putExtra("re_id", pid);
	        setResult(RESULT_OK, result);
	        finish();
	      }
	    });
        
        new GetProductDetails().execute(); 
 
    }
 
    class GetProductDetails extends AsyncTask<String, String, String> {

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
                        params.add(new BasicNameValuePair("id_get_agence", pid));
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
	            id_agence.setText(detail_tab.getString(TAG_ID));
	            nom_agence.setText(detail_tab.getString(TAG_NOM));
	            adr_agence.setText(detail_tab.getString(TAG_ADRESSE));
	            tel_agence.setText(detail_tab.getString(TAG_TELEPHONE));
	            fax_agence.setText(detail_tab.getString(TAG_FAX));
	            mail_agence.setText(detail_tab.getString(TAG_MAIL));
	            desc_agence.setText(detail_tab.getString(TAG_DESCRIPTION));
	            latitude_agence.setText(detail_tab.getString(TAG_LATITUDE));
	            longitude_agence.setText(detail_tab.getString(TAG_LONGITUDE));	            
			} catch (JSONException e) {
				e.printStackTrace();
			}                      
            pDialog.dismiss();           
        }
    } 
    
}
