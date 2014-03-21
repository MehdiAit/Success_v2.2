package com.success_v1.main;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.success_v1.agence.List_Ville;
import com.success_v1.successCar.R;

public class ReservationStep1 extends Activity{
	private int year;
	private int month;
	private int day;
	
	private String dateDepart;
	private String dateRetour;
	private String date;
	private String ville;
	
	private Button btnDateDepart;
	private Button btnDateRetour;
	private Button btnVilleDepart;
	private Button btnSearchCar;
	private TextView location;
	
	
	private int yearDepart;
	private int monthDepart;
	private int dayRetour;
	private int yearRetour;
	private int monthRetour;
	private int dayDepart;
	private int nbBtn;
	private TextView titleActionBar;
	private ImageView logoEtape;

	static final int DATE_DIALOG_DEPART = 999;
	static final int DATE_DIALOG_RETOUR = 899;

	JSONObject detail_tab = new JSONObject();
	ConnectivityManager wf;
	NetworkInfo info;


	public void ConvertDate(String format, Button btndate)
	{
		date = new SimpleDateFormat(format).format(new Date());
		btndate.setText(date);	
	}

	public Date ConvertDate2(String format, Button btndate)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		Date inputDate = null;
		try {
			inputDate = fmt.parse(btndate.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return inputDate;
	}
	
	public void toast(String txt, int leng)
	{
		Toast.makeText(this, txt, leng).show();
	}
	
	public static Date getZeroTimeDate(Date dateinsert) {
	    Date res = dateinsert;
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( dateinsert );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    res = calendar.getTime();

	    return res;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reservation_step1);
		/*getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		logoEtape = (ImageView)findViewById(R.id.logoEtape1);
		titleActionBar.setText("Création de la réservation (1/3)");
		logoEtape.setVisibility(ImageView.VISIBLE);*/
		getActionBar().setTitle("");
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		Intent intt = getIntent();
		String comune_recupe = intt.getStringExtra("comune");
		String ville_recupe = intt.getStringExtra("ville");

		btnDateDepart = (Button) findViewById(R.id.btnDateDeb);
		btnDateRetour = (Button) findViewById(R.id.btnDateRetour);
		btnVilleDepart = (Button) findViewById(R.id.btnVilleDepart1);
		btnSearchCar = (Button) findViewById(R.id.btnSearchCar);
		location = (TextView) findViewById(R.id.txtLocalMap);
		
		wf = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
		info = wf.getActiveNetworkInfo();
		
		location.setText(" "+comune_recupe+", "+ville_recupe);
		ConvertDate("dd-MM-yyyy", btnDateDepart);
		ConvertDate("dd-MM-yyyy", btnDateRetour);
		
		btnDateDepart.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				nbBtn=1;
				showDialog(DATE_DIALOG_DEPART);
			}
		});
		btnDateRetour.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				nbBtn =2;
				showDialog(DATE_DIALOG_RETOUR);
			}
		});

		btnSearchCar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent listCarActivity = new Intent(getBaseContext(), com.success_v1.vehicule.VehiculeTab.class);

				// Parse the input date
				Date inputDate = ConvertDate2("dd-MM-yyyy", btnDateDepart);
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				dateDepart = fmt.format(inputDate);

				// Parse the input date
				Date inputDate2 = ConvertDate2("dd-MM-yyyy", btnDateRetour);
				SimpleDateFormat fmtt = new SimpleDateFormat("yyyy-MM-dd");
				dateRetour= fmtt.format(inputDate2);

				Date today = new Date();
				
				Integer a = inputDate.compareTo(inputDate2);
				Integer b = today.compareTo(inputDate);
				
				today = getZeroTimeDate(today);
				
				if((inputDate.compareTo(inputDate2) == 1) || (inputDate.compareTo(today) == -1) || (btnVilleDepart.getText().toString().equals("Ville de départ")))
				{
					toast("Dates incohérentes ou Ville non selectionnée !", Toast.LENGTH_LONG);				
				}
				else
				{
					listCarActivity.putExtra("dateDepart", dateDepart);
					listCarActivity.putExtra("dateRetour", dateRetour);
					listCarActivity.putExtra("ville", ville);
					
					if(info != null)
					{
						Log.d("wifi state","Connected");
						startActivity(listCarActivity);
					}else
					{
						Toast.makeText(getApplicationContext(), "Vous devez être connecté >.<", Toast.LENGTH_LONG).show();
						Log.d("wifi state","Deconnected");
					}					
				}


			}
		});	
		
		
		btnVilleDepart.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),List_Ville.class);
				startActivityForResult(i, 1);			
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
	        if (resultCode == RESULT_OK) {	        		        	
	        	btnVilleDepart.setText(data.getCharSequenceExtra("nomVille"));
	        	ville = btnVilleDepart.getText().toString();
	        }
	    }

	}
	
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_DEPART:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,day);
		case DATE_DIALOG_RETOUR:
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
				yearDepart = selectedYear;
				monthDepart = selectedMonth;
				dayDepart = selectedDay;
				// set selected date into textview
				btnDateDepart.setText(new StringBuilder().append(dayDepart).append("-").append(monthDepart + 1).append("-").append(yearDepart).append(" "));
				break;
			case 2:
				yearRetour = selectedYear;
				monthRetour = selectedMonth;
				dayRetour = selectedDay;
				// set selected date into textview
				btnDateRetour.setText(new StringBuilder().append(dayRetour).append("-").append(monthRetour + 1).append("-").append(yearRetour).append(" "));
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
}
