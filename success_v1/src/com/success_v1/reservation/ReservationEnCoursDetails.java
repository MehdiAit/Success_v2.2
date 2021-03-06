package com.success_v1.reservation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.success_v1.res.JSONParser;
import com.success_v1.res.config;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class ReservationEnCoursDetails extends Activity {
	
    private TextView txtNumReservation;
    private TextView txtDatReservation;
    private TextView txtDebutReservation;
    private TextView txtFinReservation;
    private TextView txtMarqueReservation;
    private TextView txtModelReservation;
    private TextView txtCategorieReservation;
    private TextView txtNomAgenceReservation;
    private TextView txtEtatReservation;
    private TextView txtPrixReservation;
    private TextView txtMotorReservation;
    private Button btnAnnulerReservation;
    private TextView genreUser;
    private TextView prenomUser;
    private TextView nomUser;
    private TextView mailUser;
    private TextView numeroUser;
    private TextView titleActionBar;
    private TextView txtNbreJour;
    private ImageView imgLogoCar;
    private SessionManager session;
    
    private String pid_user;
	private DateTime dtfin;
	private DateTime dtdeb;
    private String pid;
    private String url_imageReserv;
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParser jParser = new JSONParser();
    // JSON Node names
    
    private static String url_detail = config.getURL()+"get_reservation_encours_detail.php";
    private static String url_deleteReservation = config.getURL()+"delete_reservation.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_TAB = "tab_detailReservation";
    private static final String TAG_ID = "id";
    private static final String TAG_DATERESERV = "date_reservation";
    private static final String TAG_DEBRESERV ="debut_reservation";
    private static final String TAG_FINRESERV = "fin_reservation";
    private static final String TAG_ETATRESERV = "etat_reservation";
    private static final String TAG_MARKRESERV = "marque_vehicule";
    private static final String TAG_MODELERESERV = "modele_vehicule";
    private static final String TAG_PRIXRESERV = "prix_total";
    private static final String TAG_NBJOUR = "nombre_jour";
    
    private static final String TAG_MOTORRESERV = "motorisation_vehicule";
    private static final String TAG_NOMAGENCE = "nom_agence";
       
    JSONObject detail_tab = new JSONObject();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicule_detail);
        
        /*getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Détails de la réservation");*/
        getActionBar().setTitle("");
  session = new SessionManager(getApplicationContext());
        
        HashMap<String, String> user = session.getUserDetails();
        pid_user = user.get(SessionManager.KEY_ID);
        Intent result = getIntent();
		pid = result.getStringExtra("id_get");
        url_imageReserv = result.getStringExtra("url_image");
        txtNbreJour = (TextView) findViewById(R.id.nbre_jours_reserv);//
		txtNumReservation = (TextView) findViewById(R.id.txtTitreResume);//
		//txtDatReservation = (TextView) findViewById(R.id.txtDatReservation);
		txtDebutReservation = (TextView) findViewById(R.id.date_depart_recup);//
		txtFinReservation = (TextView) findViewById(R.id.date_retour_recup);//
		txtEtatReservation = (TextView) findViewById(R.id.txtEtatReservation);
		txtMarqueReservation = (TextView) findViewById(R.id.marqueVehicule);//
		txtModelReservation = (TextView) findViewById(R.id.nom_vehicule_recup);//
		//txtCategorieReservation= (TextView) findViewById(R.id.txtCategorieReservation);
		txtNomAgenceReservation = (TextView) findViewById(R.id.nomAgenceResume);//
		txtNomAgenceReservation.setVisibility(View.VISIBLE);
		txtPrixReservation = (TextView)findViewById(R.id.prixVehicule);//
		txtMotorReservation = (TextView) findViewById(R.id.motorVehicule);
		btnAnnulerReservation= (Button)findViewById(R.id.btntestreseravation);
		genreUser= (TextView) findViewById(R.id.txtGenreResume);
        nomUser= (TextView) findViewById(R.id.txtNomResume);
        prenomUser = (TextView) findViewById(R.id.txtPrenomResume);
        mailUser = (TextView) findViewById(R.id.txtMailResume);
        numeroUser = (TextView) findViewById(R.id.txtPhoneResume);
        imgLogoCar = (ImageView) findViewById(R.id.imgLogoCar);
        prenomUser.setText(user.get(SessionManager.KEY_PRENOM));
		nomUser.setText(user.get(SessionManager.KEY_NOM));
		mailUser.setText(user.get(SessionManager.KEY_MAIL));
		numeroUser.setText(user.get(SessionManager.KEY_NUM));

		btnAnnulerReservation.setText("Annuler ma réservation");
		btnAnnulerReservation.setOnClickListener(new View.OnClickListener(){
  	      @Override
  	      public void onClick(View v) {
  	        
  	    	Dialog();
  	    	//new DelReservation().execute();
  	    	
  	      }
  	    });

        //Log.d("value", a);
        
        new GetReservationDetails().execute(); 
 
    }
    
    public void Dialog()
    {
    	AlertDialog.Builder confirm = new AlertDialog.Builder(this);
    	confirm.setIcon(android.R.drawable.ic_dialog_alert);
    	confirm.setTitle("Supression");
    	confirm.setMessage("Voulez vous vraiment annuler votre reservation ?");
    	
    	confirm.setPositiveButton("Oui", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new DelReservation().execute();
				
			}
		});
    	
    	confirm.setNegativeButton("Non", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	
    	confirm.show();
    	
    }

    class DelReservation extends AsyncTask<String, String, String> {
     	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReservationEnCoursDetails.this);
            pDialog.setMessage("Annulation en cours ...");
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
            params.add(new BasicNameValuePair("id_reservation", txtNumReservation.getText().toString()));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_deleteReservation, "POST", params);
 
            // check log cat fro response
            //Log.d("Test reservation", json.toString());
 
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
                        //Log.d("Detail", json.toString() + pid);
 
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
            	//Log.d("Url image", url_imageReserv);
            	Picasso.with(getApplicationContext()).load(url_imageReserv).into(imgLogoCar);
	            txtNumReservation.setText(detail_tab.getString(TAG_ID));
	            //txtDatReservation.setText(detail_tab.getString(TAG_DATERESERV));
	            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	            dtdeb = formatter.parseDateTime(detail_tab.getString(TAG_DEBRESERV));
	    		dtfin = formatter.parseDateTime(detail_tab.getString(TAG_FINRESERV));
	            txtDebutReservation.setText(dtdeb.toString("dd-MM-yyyy"));
	            txtFinReservation.setText(dtfin.toString("dd-MM-yyyy"));
	            //txtEtatReservation.setText(detail_tab.getString(TAG_ETATRESERV));
	            txtMarqueReservation.setText(detail_tab.getString(TAG_MARKRESERV));
	            txtModelReservation.setText(detail_tab.getString(TAG_MODELERESERV));
	            //txtCategorieReservation.setText(detail_tab.getString(TAG_NOMAGENCE));
	            txtNomAgenceReservation.setText(detail_tab.getString(TAG_NOMAGENCE));
	            txtPrixReservation.setText(detail_tab.getString(TAG_PRIXRESERV));
	            txtMotorReservation.setText(detail_tab.getString(TAG_MOTORRESERV));
	            txtNbreJour.setText(detail_tab.getString(TAG_NBJOUR));
			} catch (JSONException e) {
				e.printStackTrace();
			}                      
            pDialog.dismiss();           
        }
    } 
    
}
