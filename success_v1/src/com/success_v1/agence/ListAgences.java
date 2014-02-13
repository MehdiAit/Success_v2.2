package com.success_v1.agence;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;

public class ListAgences extends Activity {
	
	private ProgressDialog pDialog;
	
	JSONParser jParser = new JSONParser();		
    ArrayList<Agence> agencelist;
    JSONArray jsonTab = null;
		
	private ListView lv;
	
	private static String url_all = "http://10.0.3.2/Success2i_V1/get_agence.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "tab_agence";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_ADRESSE = "adresse";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agence_list);
		
		agencelist = new ArrayList<Agence>();	
		new LoadAll().execute();
		
		lv = (ListView)findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Agence idtest = new Agence();
				idtest = (Agence) lv.getAdapter().getItem(arg2);
				
				String id = idtest.id.toString();
				
				Intent intent = new Intent(getApplicationContext(), Detail.class);
				intent.putExtra("id_get", id);
				startActivityForResult(intent,10);
								}
							}
				);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 10) {
		      if (resultCode == RESULT_OK) {
		        Toast.makeText(this, "Votre resérvation s'est déroulée avec success! " + data.getStringExtra("re_id"), Toast.LENGTH_SHORT).show();
		      }
		    }
	}
		
    class LoadAll extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListAgences.this);
            pDialog.setMessage("Loading .Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_all, "GET", params);
            Log.d("Tab resultat", json.toString()); 
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	
                    jsonTab = json.getJSONArray(TAG_TAB);
                    for (int i = 0; i < jsonTab.length(); i++) {
                        JSONObject c = jsonTab.getJSONObject(i);
 
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOM);
                        String adress = c.getString(TAG_ADRESSE);
                        
                        
                        Agence agence = new Agence(id,name,adress);
                        Log.i("nom",agence.nom);
 
                        agencelist.add(agence);
                    }
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
                Adapter ad = new Adapter(ListAgences.this, agencelist);
               	lv.setAdapter(ad);               	
               	//Log.i("Thread","Hello thread");
                    }
                    });
        }
 
    }

}
