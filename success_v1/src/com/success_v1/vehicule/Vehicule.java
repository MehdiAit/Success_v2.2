package com.success_v1.vehicule;

public class Vehicule {
	private String id;
	private String marque;
	private String modele;
	private String motorisation;
	private String prix;
	private String urlImage;
	private String aclim;
	private String nbportes;
	private String boite;
	
	
	public Vehicule(String identif, String mark, String mod, String motor, String price, String urlPic, String aclim, String nbportes, String boite)
	{
		setId(identif);
		setMarque(mark);
		setModele(mod);
		setMotorisation(motor);
		setPrix(price);
		setUrlImage(urlPic);
		setAclim(aclim);
		setNbportes(nbportes);
		setBoite(boite);
	}
	public Vehicule()
	{
		
	}
	public String getBoite() {
		return boite;
	}
	public void setBoite(String boite) {
		this.boite = boite;
	}
	public String getNbportes() {
		return nbportes;
	}
	public void setNbportes(String nbportes) {
		this.nbportes = nbportes;
	}
	public String getAclim() {
		return aclim;
	}
	public void setAclim(String aclim) {
		this.aclim = aclim;
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
