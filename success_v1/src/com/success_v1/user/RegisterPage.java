package com.success_v1.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;

public class RegisterPage extends Activity{
	Spinner spinGenre;
	EditText editPrenom;
	EditText editNom;
	Button btnDateNais;
	EditText editMailRegistration;
	EditText editCnfirmMail;
	EditText editMdpRegistration;
	EditText editCnfirmMdp;
	EditText editNumPerm;
	Button btnDatePerm;
	EditText editAdress;
	EditText editCodePost;
	EditText editVille;
	EditText editPhone;
	Button btnCreateCount;
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

	/****************************************/
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
 
    // JSON Node names
   	private static final String TAG_SUCCESS = "success";
    //private static String url_user = "http://10.0.3.2/Success2i_V1/add_user.php";
	private static String url_user = "http://192.168.1.99/Success2i_V1/add_user.php";
    JSONObject registration_tab = new JSONObject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_page);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Inscription");
		spinGenre = (Spinner)findViewById(R.id.spinGenre);

		editPrenom = (EditText)findViewById(R.id.editPrenom);
		editNom = (EditText)findViewById(R.id.editNom);
		editMailRegistration = (EditText)findViewById(R.id.editMailRegistration);
		editCnfirmMail = (EditText)findViewById(R.id.editCnfirmMail);
		editMdpRegistration = (EditText)findViewById(R.id.editMdpRegistration);
		editCnfirmMdp = (EditText)findViewById(R.id.editCnfirmMdp);
		editNumPerm = (EditText)findViewById(R.id.editNumPerm);
		editAdress = (EditText)findViewById(R.id.editAdress);
		editCodePost= (EditText)findViewById(R.id.editCodePost);
		editVille = (EditText)findViewById(R.id.editVille);
		editPhone = (EditText)findViewById(R.id.editPhone);


		btnDateNais = (Button)findViewById(R.id.btnDateNais);
		btnDatePerm = (Button)findViewById(R.id.btnDatePerm);
		btnCreateCount = (Button)findViewById(R.id.btnCreateCount);

		
		btnDateNais.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
	   	    	nbBtn=1;
	   	    	showDialog(DATE_DIALOG_NAISSANCE);
			}
		});
		btnDatePerm.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
	   	    	nbBtn =2;
	   	    	showDialog(DATE_DIALOG_PERMIS);
			}
		});
		btnCreateCount.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// Parse the input date
	   			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
	   			Date inputDate = null;
	   			try {
	   				inputDate = fmt.parse(btnDateNais.getText().toString());
	   			} catch (ParseException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   			// Create the MySQL datetime string
	   			fmt = new SimpleDateFormat("yyyy-MM-dd");
	   			dateNaissance = fmt.format(inputDate);
	   			Log.d("dateNaissance",dateNaissance);
	   			/*************/
	   			// Parse the input date
	   			SimpleDateFormat fmtt = new SimpleDateFormat("dd-MM-yyyy");
	   			Date inputDatePermis = null;
	   			try {
	   				inputDatePermis = fmtt.parse(btnDatePerm.getText().toString());
	   			} catch (ParseException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
	   			// Create the MySQL datetime string
	   			fmtt = new SimpleDateFormat("yyyy-MM-dd");
	   			datePermis= fmtt.format(inputDatePermis);
	   			Log.d("datePermis",datePermis);
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
							btnDateNais.setText(new StringBuilder().append(dayNaissance).append("-").append(monthNaissance + 1).append("-").append(yearNaissance).append(" "));
				break;
			case 2:
				yearPermis = selectedYear;
				monthPermis = selectedMonth;
				dayPermis = selectedDay;
				// set selected date into textview
							btnDatePerm.setText(new StringBuilder().append(dayPermis).append("-").append(monthPermis + 1).append("-").append(yearPermis).append(" "));
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
    class AddUser extends AsyncTask<String, String, String> {
      	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterPage.this);
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
            params.add(new BasicNameValuePair("nom_user", editNom.getText().toString()));
            params.add(new BasicNameValuePair("prenom_user", editPrenom.getText().toString()));
            params.add(new BasicNameValuePair("mdp_user", editMdpRegistration.getText().toString()));
            params.add(new BasicNameValuePair("adr_user", editAdress.getText().toString()));
            params.add(new BasicNameValuePair("numPermis_user", editNumPerm.getText().toString()));
            params.add(new BasicNameValuePair("dateNais_user", dateNaissance));
            params.add(new BasicNameValuePair("numPhone_user", editPhone.getText().toString()));
            params.add(new BasicNameValuePair("mail_user", editMailRegistration.getText().toString()));
            params.add(new BasicNameValuePair("dateRetraitPermis_user",datePermis));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_user, "POST", params);
 
            // check log cat fro response
            Log.d("Test user", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	
                    // successfully created product
        	    	Intent result = getIntent();
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
