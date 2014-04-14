package com.success_v1.reservation;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.success_v1.successCar.R;

public class Adapter extends BaseAdapter {
	
	private ArrayList<Reservation> reservation;
	private LayoutInflater fl;
	private DateTime dtreserv;
	private DateTime dtfin;
	private DateTime dtdeb;
	
	public Adapter(Context context, ArrayList<Reservation> reserv)
	{
		fl = LayoutInflater.from(context);
		reservation = reserv;
	}
	

	@Override
	public int getCount() {
		return reservation.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return reservation.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class ViewHolder{
		TextView id_reservation;
		TextView date_reservation;
		TextView date_Debreservation;
		TextView date_Finreservation;
		TextView photo_vehicule;
		TextView price;
		ImageView image_vehicule;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (arg1 == null)
		{
			arg1 = fl.inflate(R.layout.reservation_list_item, null);
			holder = new ViewHolder();
			
			holder.id_reservation = (TextView)arg1.findViewById(R.id.id_reservationEnCours);
			holder.date_reservation = (TextView)arg1.findViewById(R.id.date_reservationEnCours);
			holder.date_Debreservation = (TextView)arg1.findViewById(R.id.dateDeb_reserv);
			holder.date_Finreservation = (TextView)arg1.findViewById(R.id.dateFin_reserv);
			holder.photo_vehicule = (TextView)arg1.findViewById(R.id.photo_reservEnCours);
			holder.image_vehicule = (ImageView)arg1.findViewById(R.id.img_reservEnCours);
			holder.price = (TextView)arg1.findViewById(R.id.total_reservation);
			arg1.setTag(holder);
		}else
		{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.id_reservation.setText(reservation.get(arg0).id);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		dtreserv = formatter.parseDateTime(reservation.get(arg0).date);
		dtdeb = formatter.parseDateTime(reservation.get(arg0).dateDeb);
		dtfin = formatter.parseDateTime(reservation.get(arg0).dateFin);
		holder.date_reservation.setText(dtreserv.toString("dd-MM-yyyy"));
		holder.date_Debreservation.setText(dtdeb.toString("dd-MM-yyyy"));
		holder.date_Finreservation.setText(dtdeb.toString("dd-MM-yyyy"));
		holder.photo_vehicule.setText(reservation.get(arg0).image);
		holder.price.setText(reservation.get(arg0).prix);
		Picasso.with(arg1.getContext()).load(holder.photo_vehicule.getText().toString()).into(holder.image_vehicule);
		//holder.date_reservation.setText(reservation.get(arg0).date);
		return arg1;
	}

}
