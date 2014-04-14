package com.success_v1.agence;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;

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
	TextView adrArrive_agence;

	Button btnAppeler_agence;
	
	private String pid;	
	private String latitude_recup;
	private String longitude_recup;
    private GoogleMap googleMap;
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	JSONParser jParser = new JSONParser();

	// JSON Node names
	private static String url_detail = config.getURL()+"get_agence_detail.php";
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

	Intent result;

	JSONObject detail_tab = new JSONObject();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agence_detail);
		getActionBar().setTitle("");
		result = getIntent();
		pid = result.getStringExtra("id_agence");

		id_agence = (TextView) findViewById(R.id.id_agence);
		nom_agence = (TextView) findViewById(R.id.nom_agence);
		adr_agence = (TextView) findViewById(R.id.adr_agence);
		adrArrive_agence = (TextView) findViewById(R.id.adrArrive_agence);
		tel_agence = (TextView) findViewById(R.id.tel_agence);
		fax_agence = (TextView) findViewById(R.id.fax_agence);
		mail_agence = (TextView) findViewById(R.id.mail_agence);
		desc_agence = (TextView) findViewById(R.id.desc_agence);

		latitude_agence = (TextView) findViewById(R.id.latitude_agence);
		longitude_agence = (TextView) findViewById(R.id.longitude_agence);

		btnAppeler_agence = (Button)findViewById(R.id.btnCall_agence);

		btnAppeler_agence.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				String num = "tel:"+tel_agence.getText().toString();
				Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(num));
				startActivity(call);
			}
		});
		
		new GetProductDetails().execute(); 
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
				adrArrive_agence.setText(detail_tab.getString(TAG_ADRESSE));
				tel_agence.setText(detail_tab.getString(TAG_TELEPHONE));
				fax_agence.setText(detail_tab.getString(TAG_FAX));
				mail_agence.setText(detail_tab.getString(TAG_MAIL));
				desc_agence.setText(detail_tab.getString(TAG_DESCRIPTION));
				
				latitude_agence.setText(detail_tab.getString(TAG_LATITUDE));
				longitude_agence.setText(detail_tab.getString(TAG_LONGITUDE));
				
				latitude_recup = detail_tab.getString(TAG_LATITUDE);
				longitude_recup = detail_tab.getString(TAG_LONGITUDE);
				
				try {
		            // Loading map
		        initilizeMap(latitude_recup,longitude_recup);
		 
		        } catch (Exception e) {
		            e.printStackTrace();
		       }
				
			} catch (JSONException e) {
				e.printStackTrace();
			}                      
			pDialog.dismiss();           
		}
	} 
	
	private void initilizeMap(String a,String b) {
        if (googleMap == null) {
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	        	
        	double latitude = Double.valueOf(a);
        	double longitude = Double.valueOf(b);
        	
        	LatLng ln = new LatLng(latitude, longitude);
        	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ln, 13));
        	
        	MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
        	googleMap.addMarker(marker);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
