package com.hitema.rest;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hitema.model.Demande;
import com.hitema.model.DemandeFront;
import com.hitema.model.Description;
import com.hitema.model.User;
import com.hitema.util.PostUtils;


@Component
@Path("/inspecteur")
public class inspecteurServiceRest {

	@GET
	@Produces("application/json")
	@Path("/test")
	public String findAllDemandes() {
		return "boudons";
	}

	@GET
	@Path("/getDemandeByRoleName/{roleName}")
	@Consumes("application/json")
	@Produces("application/json")
	public String findByRole(@PathParam("roleName") String roleName) {
		//appel du get et recuperation de la reponse json
		String response = PostUtils.appelGet("http://localhost:8080/mizemplyCRUD/rest/demandeDao/recupDemandeParRoleName/"+roleName);
		System.out.println(response);
		
		//transformation d'un json en une list<obj>
		Type listType = new TypeToken<ArrayList<Demande>>(){}.getType();
		List<Demande> demandes = new Gson().fromJson(response, listType);
		//mapping de list<demande> en list<demandeFront>
		List<DemandeFront> listDemandeFront=mappingListDemande(demandes);
		
		//transformation de la list<DemandeFront> en json
		response=PostUtils.convertionObjetEnJson(listDemandeFront);
		System.out.println("hahahhahahahha");
		return response;
	}
	
	@GET
	@Produces("application/json")
	@Path("/saveDemande/{login}/{idDemande}/{description}")
	public void saveDemande(@PathParam("login") String login
			,@PathParam("idDemande") String idDemande,@PathParam("description") String descriptionTxt) {
		
		//recuperation de la demande
		String response = PostUtils.appelGet("http://localhost:8080/mizemplyCRUD/rest/demandeDao/recupDemandeParId/"+idDemande);
		Demande demande= PostUtils.convertionJsonEnObject(response, Demande.class);
		System.out.println(demande.getDate());
		
		//recuperation de l'user
		response = PostUtils.appelGet("http://localhost:8080/mizemplyCRUD/rest/userDao/findByLogin/"+login);
		User user= PostUtils.convertionJsonEnObject(response, User.class);
		System.out.println(user.getPassword());
		
		//creation de la descritpion
		Description description= new Description();
		description.setUser(user);
		description.setDescription(descriptionTxt);
		
		//ajout de la description dans la demande
		demande.getDescriptions().add(description);
		demande.setOpen(false);
		
		//mise a jour de la description en base
		String url="http://localhost:8080/mizemplyCRUD/rest/demandeDao/save/";
		PostUtils.appelPost(url, demande);
		
	}
	
	
	private List<DemandeFront> mappingListDemande(List<Demande> listDemande){
		
		List<DemandeFront> listDemandeFront = new ArrayList<DemandeFront>();
		for(Demande demande:listDemande){
			listDemandeFront.add(new DemandeFront(demande));
		}
		return listDemandeFront;
	}

}
