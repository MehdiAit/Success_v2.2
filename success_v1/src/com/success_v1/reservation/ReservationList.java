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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.success_v1.res.JSONParser;
import com.success_v1.successCar.R;
import com.success_v1.user.SessionManager;

public class ReservationList extends Fragment{
	private ProgressDialog pDialog;

	JSONParser jParser = new JSONParser();		
	ArrayList<Reservation> reservationlist;
	JSONArray jsonTab = null;
	SessionManager session;
	private ListView lv;
	View rootView = null;

	//private static String url_all = "http://10.0.3.2/Success2i_V1/get_reservations_encours.php";
	private static String url_all = "http://192.168.1.74/Success2i_V1/get_reservation.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB = "tab_reservation";
	private static final String TAG_ID = "id";
	private static final String TAG_DATE = "date_reservation";
	private static final String TAG_DATEDEB = "dateDebLoc_reservation";
	private static final String TAG_DATEFIN = "dateFinLoc_reservation";
	private static final String TAG_PRIX = "prix_total";
	private static final String TAG_IMG = "photo_vehicule";

	String etat;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.reservation_en_cours_list, container, false);

		// Session class instance
		session = new SessionManager(getActivity().getApplicationContext());
		reservationlist = new ArrayList<Reservation>();	
		new LoadAllReservations().execute();

		lv = (ListView) rootView.findViewById(R.id.lstReservationEnCours);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Reservation idtest = new Reservation();
				idtest = (Reservation) lv.getAdapter().getItem(arg2);

				String id = idtest.id.toString();

				if(etat.equals("En cours"))
				{				
					Intent intent = new Intent(getActivity(), ReservationEnCoursDetails.class);
					intent.putExtra("id_get", id);
					intent.putExtra("url_image", idtest.image.toString());
					startActivityForResult(intent,10);
				}
				else
				{
					Intent intent = new Intent(getActivity(), ReservationValideDetails.class);
					intent.putExtra("id_get", id);
					intent.putExtra("url_image", idtest.image.toString());
					startActivityForResult(intent,10);
				}
			}
		}
				);
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 10)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				reservationlist = new ArrayList<Reservation>();	
				new LoadAllReservations().execute();
			}
		}
	}

	class LoadAllReservations extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading .Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_user", session.getIdUser()));
			params.add(new BasicNameValuePair("etat", etat));
			JSONObject json = jParser.makeHttpRequest(url_all, "GET", params);

			Log.d("idUser",session.getIdUser());
			Log.d("Tab resultat", json.toString()); 
			try {
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					jsonTab = json.getJSONArray(TAG_TAB);
					for (int i = 0; i < jsonTab.length(); i++) {
						JSONObject c = jsonTab.getJSONObject(i);

						String id = c.getString(TAG_ID);
						String date = c.getString(TAG_DATE);
						String dateDeb = c.getString(TAG_DATEDEB);
						String dateFin = c.getString(TAG_DATEFIN);
						String prix = c.getString(TAG_PRIX);
						String photo = c.getString(TAG_IMG);

						Reservation reservationEnCours = new Reservation(id,date,dateDeb,dateFin,photo,prix);

						reservationlist.add(reservationEnCours);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			getActivity().runOnUiThread(new Runnable() {                 
				public void run() {                 	
					Adapter ad = new Adapter(getActivity(), reservationlist);
					lv.setAdapter(ad);               	
					Log.i("Thread","Hello thread");
				}
			});
		}

	}

}
