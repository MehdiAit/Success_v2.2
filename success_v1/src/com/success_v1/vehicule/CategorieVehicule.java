package com.success_v1.vehicule;

public class CategorieVehicule {
	
	String id;
	String nom;
	String description;
	String image;
	String prix;
	
	
	public CategorieVehicule(String id, String nom, String description, String image, String prix)
	{
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.image = image;
		this.prix = prix;
	}
	
	public CategorieVehicule()
	{
		
	}
	
	public String getId()
	{
		return this.id;
	}
	public String getName()
	{
		return this.nom;
	}

}
