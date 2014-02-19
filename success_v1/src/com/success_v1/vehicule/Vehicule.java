package com.success_v1.vehicule;

public class Vehicule {
	private String id;
	private String marque;
	private String modele;
	private String motorisation;
	private String prix;
	private String urlImage;
	
	public Vehicule(String identif, String mark, String mod, String motor, String price, String urlPic)
	{
		setId(identif);
		setMarque(mark);
		setModele(mod);
		setMotorisation(motor);
		setPrix(price);
		setUrlImage(urlPic);
	}
	public Vehicule()
	{
		
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}
	public String getMotorisation() {
		return motorisation;
	}
	public void setMotorisation(String motorisation) {
		this.motorisation = motorisation;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
}
