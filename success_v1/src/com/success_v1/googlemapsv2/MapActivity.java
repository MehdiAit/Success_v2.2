package com.success_v1.googlemapsv2;

import com.success_v1.successCar.R;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity {
	 
    // Google Map
	String latitude_recup;
	String longitude_recup;
    //private GoogleMap googleMap;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
 
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {/*
        if (googleMap == null) {
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        	
        	Intent i = getIntent();
        	latitude_recup = i.getStringExtra("latitude");
        	longitude_recup = i.getStringExtra("longitude");
        	
        	double latitude = Double.valueOf(latitude_recup);
        	double longitude = Double.valueOf(longitude_recup);
        	
        	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
        	
        	MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
        	googleMap.addMarker(marker);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    */}
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
}
