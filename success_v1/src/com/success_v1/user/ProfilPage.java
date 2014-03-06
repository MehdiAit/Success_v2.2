package com.success_v1.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;
import com.success_v1.user.RegisterPage.AddUser;

public class ProfilPage extends Activity{
	RadioGroup rdioGenre;
	RadioButton radioMadameButton;
	RadioButton radioMonsieurButton;
	EditText editPrenomProfil;
	EditText editNomProfil;
	Button btnDateNaisProfil;
	EditText editMailRegistrationProfil;
	EditText editMdpRegistrationProfil;
	EditText editNumPermProfil;
	Button btnDatePermProfil;
	EditText editAdressProfil;
	EditText editCodePostProfil;
	EditText editVilleProfil;
	EditText editPaysProfil;
	EditText editPhoneProfil;
	Button btnEditCountProfil;
	TextView titleActionBar;
	/**************************************/
	int year;
	int month;
	int day;
	private int nbBtn;
	static final int DATE_DIALOG_NAISSANCE = 999;
	static final int DATE_DIALOG_PERMIS = 899;
	private int yearNaissance;
	private int monthNaissance;
	private int dayNaissance;

	private int dayPermis;
	private int yearPermis;
	private int monthPermis;

	private String dateNaissance;
	private String datePermis;
	private String dateNaissanceProfil;
	private String datePermisProfil;
	private String id_user;
	private String genreSelected ="Monsieur";

	/****************************************/
    // Session Manager Class
    SessionManager session;
	/****************************************/
	// Progress Dialog
	private ProgressDialog pDialog;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	JSONParser jParser = new JSONParser();

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static String url_edituser = config.getURL()+"edit_user.php";

	JSONObject editUser_tab = new JSONObject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profil_page);
        // Session class instance
        session = new SessionManager(getApplicationContext());
		/*getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Profil");*/
        getActionBar().setTitle("");
		rdioGenre = (RadioGroup)findViewById(R.id.rdioGenreProfil);
		radioMadameButton = (RadioButton)findViewById(R.id.rdioMadameProfil);
		radioMonsieurButton = (RadioButton)findViewById(R.id.rdioMonsieurProfil);
		/*rdioGenre.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	        {
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	                switch(checkedId){
	                    case R.id.rdioMonsieurProfil:
	                        genreSelected = "Monsieur";
	                        Log.d("TestGenre",genreSelected);
	                    break;

	                    case R.id.rdioMadameProfil:
	                    	genreSelected = "Madame";
	                    	Log.d("TestGenre",genreSelected);
	                    break;

	                }


	            }
	        });*/

		editPrenomProfil = (EditText)findViewById(R.id.editPrenomProfil);
		editNomProfil = (EditText)findViewById(R.id.editNomProfil);
		editMailRegistrationProfil = (EditText)findViewById(R.id.editMailRegistrationProfil);
		editMdpRegistrationProfil = (EditText)findViewById(R.id.editMdpRegistrationProfil);
		editNumPermProfil = (EditText)findViewById(R.id.editNumPermProfil);
		editAdressProfil = (EditText)findViewById(R.id.editAdressProfil);
		editCodePostProfil= (EditText)findViewById(R.id.editCodePostProfil);
		editVilleProfil = (EditText)findViewById(R.id.editVilleProfil);
		editPaysProfil = (EditText)findViewById(R.id.editPaysProfil);
		editPhoneProfil = (EditText)findViewById(R.id.editPhoneProfil);

		btnDateNaisProfil = (Button)findViewById(R.id.btnDateNaisProfil);
		btnDatePermProfil = (Button)findViewById(R.id.btnDatePermProfil);
		btnEditCountProfil = (Button)findViewById(R.id.btnEditCountProfil);
		
		 // get user data from session
        HashMap<String, String> user = session.getUserDetails();
         
        // id
        id_user = user.get(SessionManager.KEY_ID);
		// Parse the input date
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Date inputDate = null;
			try {
				inputDate = fmt.parse(user.get(SessionManager.KEY_DATENAIS));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Create the datetime string
			fmt = new SimpleDateFormat("dd-MM-yyyy");
			dateNaissance = fmt.format(inputDate);
			Log.d("dateNaissance",dateNaissance);
			/*************/
			// Parse the input date
			SimpleDateFormat fmtt = new SimpleDateFormat("yyyy-MM-dd");
			Date inputDatePermis = null;
			try {
				inputDatePermis = fmtt.parse(user.get(SessionManager.KEY_DATERETRAITPERMIS));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Create the datetime string
			fmtt = new SimpleDateFormat("dd-MM-yyyy");
			datePermis= fmtt.format(inputDatePermis);
			Log.d("datePermis",datePermis);

        btnDateNaisProfil.setText(dateNaissance);
        btnDatePermProfil.setText(datePermis);
		editPrenomProfil.setText(user.get(SessionManager.KEY_PRENOM));
		editNomProfil.setText(user.get(SessionManager.KEY_NOM));
		editMailRegistrationProfil.setText(user.get(SessionManager.KEY_MAIL));
		editMdpRegistrationProfil.setText(user.get(SessionManager.KEY_MDP));
		editNumPermProfil.setText(user.get(SessionManager.KEY_NUMPERMIS));
		editAdressProfil.setText(user.get(SessionManager.KEY_ADRESSE));
		genreSelected=user.get(SessionManager.KEY_GENRE);
		Log.d("genreSelected",genreSelected);
		if (genreSelected.equals("Monsieur"))
		{
			radioMonsieurButton.setChecked(true);
			radioMadameButton.setChecked(false);
			Log.d("Test","Je rentre dans la condition!");
		}
		else if (genreSelected.equals("Madame"))
		{
			radioMonsieurButton.setChecked(false);
			radioMadameButton.setChecked(true);
		}
		editCodePostProfil.setText(user.get(SessionManager.KEY_CODEPOST));
		editVilleProfil.setText(user.get(SessionManager.KEY_VILLE));
		editPaysProfil.setText(user.get(SessionManager.KEY_PAYS));
		editPhoneProfil.setText(user.get(SessionManager.KEY_NUM));
		
		btnDateNaisProfil.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
	   	    	nbBtn=1;
	   	    	showDialog(DATE_DIALOG_NAISSANCE);
			}
		});
		btnDatePermProfil.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
	   	    	nbBtn =2;
	   	    	showDialog(DATE_DIALOG_PERMIS);
			}
		});
		btnEditCountProfil.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// Parse the input date
	   			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
	   			Date inputDate = null;
	   			try {
	   				inputDate = fmt.parse(dateNaissance);
	   			} catch (ParseException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   			// Create the MySQL datetime string
	   			fmt = new SimpleDateFormat("yyyy-MM-dd");
	   			dateNaissanceProfil = fmt.format(inputDate);
	   			Log.d("dateNaissance",dateNaissanceProfil);
	   			/*************/
	   			// Parse the input date
	   			SimpleDateFormat fmtt = new SimpleDateFormat("dd-MM-yyyy");
	   			Date inputDatePermis = null;
	   			try {
	   				inputDatePermis = fmtt.parse(datePermis);
	   			} catch (ParseException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   			// Create the MySQL datetime string
	   			fmtt = new SimpleDateFormat("yyyy-MM-dd");
	   			datePermisProfil= fmtt.format(inputDatePermis);
	   			Log.d("datePermis",datePermisProfil);
		    	  new AddUser().execute();
			}
		});
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_NAISSANCE:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, year, month,day);
		case DATE_DIALOG_PERMIS:
			   // set date picker as current date
			   return new DatePickerDialog(this, datePickerListener, year, month,day);
		}
		
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			switch (nbBtn)
			{
			case 1:
				yearNaissance = selectedYear;
				monthNaissance = selectedMonth;
				dayNaissance = selectedDay;
				// set selected date into textview
							btnDateNaisProfil.setText(new StringBuilder().append(dayNaissance).append("-").append(monthNaissance + 1).append("-").append(yearNaissance).append(" "));
				break;
			case 2:
				yearPermis = selectedYear;
				monthPermis = selectedMonth;
				dayPermis = selectedDay;
				// set selected date into textview
							btnDatePermProfil.setText(new StringBuilder().append(dayPermis).append("-").append(monthPermis + 1).append("-").append(yearPermis).append(" "));
				break;
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onRadioButtonClickedProfil(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rdioMonsieurProfil:
	            if (checked)
	                genreSelected="Monsieur";
	            break;
	        case R.id.rdioMadameProfil:
	            if (checked)
	            	genreSelected="Madame";
	            break;
	    }
	}
	 class AddUser extends AsyncTask<String, String, String> {
      	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(ProfilPage.this);
	            pDialog.setMessage("Inscription en cours ...");
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
	            params.add(new BasicNameValuePair("id_user", id_user));
	            params.add(new BasicNameValuePair("nom_user", editNomProfil.getText().toString()));
	            params.add(new BasicNameValuePair("prenom_user", editPrenomProfil.getText().toString()));
	            params.add(new BasicNameValuePair("mdp_user", editMdpRegistrationProfil.getText().toString()));
	            params.add(new BasicNameValuePair("adr_user", editAdressProfil.getText().toString()));
	            params.add(new BasicNameValuePair("numPermis_user", editNumPermProfil.getText().toString()));
	            params.add(new BasicNameValuePair("dateNais_user", dateNaissanceProfil));
	            params.add(new BasicNameValuePair("numPhone_user", editPhoneProfil.getText().toString()));
	            params.add(new BasicNameValuePair("mail_user", editMailRegistrationProfil.getText().toString()));
	            params.add(new BasicNameValuePair("dateRetraitPermis_user",datePermisProfil));
	            params.add(new BasicNameValuePair("genre_user", genreSelected));
	            params.add(new BasicNameValuePair("codePost_user", editCodePostProfil.getText().toString()));
	            params.add(new BasicNameValuePair("ville_user", editVilleProfil.getText().toString()));
	            params.add(new BasicNameValuePair("pays_user", editPaysProfil.getText().toString()));
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_edituser, "POST", params);
	            
	            
	 
	            // check log cat fro response
	            Log.d("Test user", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                	
	                    // successfully created product
	        	    	Intent result = new Intent(getApplicationContext(), ProfilPage.class);
	        	    	startActivity(result);
	        	        
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
	        	session.createLoginSession(id_user, editNomProfil.getText().toString(),editPrenomProfil.getText().toString(),
	        			editMdpRegistrationProfil.getText().toString(),editAdressProfil.getText().toString(),
	        			editNumPermProfil.getText().toString(),dateNaissanceProfil,datePermisProfil,
	        			genreSelected,editCodePostProfil.getText().toString(),
	        			editVilleProfil.getText().toString(),editPaysProfil.getText().toString(),
	        			editPhoneProfil.getText().toString(),editMailRegistrationProfil.getText().toString());
	        	Toast.makeText(getApplicationContext(), "Tout a été modifié: ", Toast.LENGTH_LONG).show();
	            pDialog.dismiss();
	        }
	 
	    }
}
