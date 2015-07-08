package com.hitema.model;

import java.io.Serializable;
import java.util.Set;


public class DemandeFront implements Serializable {

	private int idDemande;

	
	private String adresse;


	private String date;

	
	private String titre;
	
	private String descriptions;

	public DemandeFront() {
	}
	
	public DemandeFront(Demande demande) {
		
		this.idDemande=demande.getIdDemande();
		this.adresse=demande.getAdresse();
		this.date=demande.getDate();
		this.titre=demande.getTitre();
		System.out.println("hahahhahahaha2");
		this.descriptions=getDescriptionInscpeteur(demande.getDescriptions());
	}
	
	private String getDescriptionInscpeteur(Set<Description> listDescription){
		System.out.println("lalalallalala");
		for(Description description : listDescription){
			System.out.println(description);
			System.out.println("\t\t"+description.getUser().getRole().getNom());
			if("riverain".equals(description.getUser().getRole().getNom())){
				return description.getDescription();
			}
		}
		return "";
	}

	public int getIdDemande() {
		return this.idDemande;
	}

	private static final long serialVersionUID = 1L;

	public void setIdDemande(int idDemande) {
		this.idDemande = idDemande;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescriptions() {
		return this.descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

}