package com.success_v1.vehicule;


import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.success_v1.successCar.R;

public class VehiculeSearch extends Activity{
	
	private TextView titleActionBar;
	AutoCompleteTextView autoMarqueSearch;
	AutoCompleteTextView autoModeleSearch;
	Spinner spinCouleurSearch;
	Button btnLaunchSearch;
	ArrayAdapter<String> adapterMarques;
	ArrayAdapter<String> adapterModeles;
    private static final String[] MARQUES = new String[] {
        "Audi", "BMW", "Alpha Romeo", "Wv", "Seat", "Ford", "Jeep", "Honda", "Hyundai", "Renault", "Dacia"
    };
    private static final String[] MODELES = new String[] {
        "Fiesta", "R8", "Golf", "Polo", "Ibiza", "Leon", "Accent", "Eon", "Duster","Logan"
    };
	
	JSONObject detail_tab = new JSONObject();

	
	public void toast(String txt, int leng)
	{
		Toast.makeText(this, txt, leng).show();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_vehicule_page);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.koutchy_actionbar);
		titleActionBar = (TextView)findViewById(R.id.titleActionBar);
		titleActionBar.setText("Recherche");

        autoMarqueSearch = (AutoCompleteTextView)findViewById(R.id.autoMarqueSearch);
        autoModeleSearch = (AutoCompleteTextView)findViewById(R.id.autoModeleSearch);
        spinCouleurSearch = (Spinner)findViewById(R.id.spinCouleurSearch);
        btnLaunchSearch= (Button)findViewById(R.id.btnLaunchSearch);
        
        adapterMarques = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, MARQUES);
        autoMarqueSearch.setAdapter(adapterMarques);
        adapterModeles= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, MODELES);
        autoModeleSearch.setAdapter(adapterModeles);
    	// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.color_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinCouleurSearch.setAdapter(adapter);
		
		btnLaunchSearch.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
			}
		});
    }


		

		/*btnSearchCar.setOnClickListener(new View.OnClickListener(){
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
				
				if((inputDate.compareTo(inputDate2) == 1) || (inputDate.compareTo(today) == -1))
				{
					toast("Dates incohérentes", Toast.LENGTH_LONG);				
				}
				else
				{
					listCarActivity.putExtra("dateDepart", dateDepart);
					listCarActivity.putExtra("dateRetour", dateRetour);
					listCarActivity.putExtra("ville", ville);

					if (state== "Tourisme"){
						listCarActivity.putExtra("typeVehicule", "Tourisme");
					}
					else if (state== "Utilitaire")
					{
						listCarActivity.putExtra("typeVehicule", "Utilitaire");
					}
					startActivity(listCarActivity);
				}
			}
		});	*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
	        if (resultCode == RESULT_OK) {	        		        	
	        	/*btnVilleDepart.setText(data.getCharSequenceExtra("nomVille"));
	        	ville = btnVilleDepart.getText().toString();*/
	        }
	    }

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
