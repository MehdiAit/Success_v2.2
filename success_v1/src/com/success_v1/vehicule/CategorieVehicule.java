package com.success_v1.vehicule;

public class CategorieVehicule {
	
	String id;
	String nom;
	String description;
	
	
	public CategorieVehicule(String id, String nom, String description)
	{
		this.id = id;
		this.nom = nom;
		this.description = description;
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
