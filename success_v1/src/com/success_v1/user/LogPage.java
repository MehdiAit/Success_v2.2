package com.success_v1.user;

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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;

public class LogPage extends Activity{
	EditText editMail;
	EditText editMdp;
	Button btnConnect;
	Button btnRegister;
	TextView titleActionBar;
	/**********************************/
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	JSONParser jParser = new JSONParser();

	// JSON Node names
	private static String url_authentification = config.getURL()+"auth_user.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "user_tab";
	private static final String TAG_ID = "id";
	private static final String TAG_NOM = "nom";
	private static final String TAG_PRENOM = "prenom";
	private static final String TAG_MDP = "mdp";
	private static final String TAG_ADRESSE = "adresse";
	private static final String TAG_NUMPERM = "numPermis";
	private static final String TAG_DATENAIS = "dateNaissance";
	private static final String TAG_RETRAITPERM = "dateRetraitPermis";
	private static final String TAG_GENRE = "genre";
	private static final String TAG_CODEPOST = "codePost";
	private static final String TAG_VILLE = "ville";
	private static final String TAG_PAYS = "pays";
	private static final String TAG_TELEPHONE = "num";
	private static final String TAG_MAIL = "mail";


	JSONObject user_tab = new JSONObject();
	/*********************************/
	// Session Manager Class
	SessionManager session;
	/***********************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_page);
		
		/*getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Connexion");*/
		getActionBar().setTitle("");
		
		session= new SessionManager(getApplicationContext());
		editMail = (EditText)this.findViewById(R.id.editMail);
		
		editMdp = (EditText)this.findViewById(R.id.editMdp);
		btnConnect = (Button)this.findViewById(R.id.btnConnect);
		btnRegister= (Button)this.findViewById(R.id.btnRegister);

		btnConnect.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				new UserAuthentification().execute();

			}
		});
		btnRegister.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent Registration = new Intent(getBaseContext(), RegisterPage.class);     
				startActivityForResult(Registration, 10);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 10)
		{
			if(resultCode == RESULT_OK)
			{
				Toast.makeText(getApplicationContext(), "Votre inscription c'est déroulé avec succée", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	class UserAuthentification extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LogPage.this);
			pDialog.setMessage("Authentification...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int success;
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("mail_user", editMail.getText().toString()));
				params.add(new BasicNameValuePair("mdp_user", editMdp.getText().toString()));
				JSONObject json = jsonParser.makeHttpRequest(url_authentification, "GET", params);


				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray productObj = json.getJSONArray(TAG_TAB);
					user_tab = productObj.getJSONObject(0);

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
				session.createLoginSession((user_tab.getString(TAG_ID)), user_tab.getString(TAG_NOM),user_tab.getString(TAG_PRENOM),
						user_tab.getString(TAG_MDP),user_tab.getString(TAG_ADRESSE),user_tab.getString(TAG_NUMPERM),user_tab.getString(TAG_DATENAIS),user_tab.getString(TAG_RETRAITPERM)
						,user_tab.getString(TAG_GENRE),user_tab.getString(TAG_CODEPOST),user_tab.getString(TAG_VILLE),user_tab.getString(TAG_PAYS),
						user_tab.getString(TAG_TELEPHONE),user_tab.getString(TAG_MAIL));   
				//Log.d("Salut","C'est bon kho! roh mhenni!");
				//Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
				Intent result = getIntent();
				setResult(RESULT_OK, result);
				finish();
			} catch (JSONException e) {
				e.printStackTrace();
			}                      
			pDialog.dismiss();           
		}
	} 

}
