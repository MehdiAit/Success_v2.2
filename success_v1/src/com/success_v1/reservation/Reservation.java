package com.success_v1.reservation;

public class Reservation {
	
	String id;
	String date;
	String dateDeb;
	String dateFin;
	String image;

	public Reservation (String identifiant, String dateReserv, String DateDebutReserv, String DateFinReserv, String imageVehicule)
	{
		id = identifiant;
		date= dateReserv;
		dateDeb= DateDebutReserv;
		dateFin= DateFinReserv;
		image = imageVehicule;
	}
	public Reservation()
	{
		
	}

}
