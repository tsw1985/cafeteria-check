package com.gabrielglez.services.facade;

import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;

public class ReplacementFacade {


	public boolean delete(int id){
		if ( DataBaseHelperManager.getReplacementDAO().delete(id) ){
			return true;
		}else{
			return false;
		}
	}
	

	public boolean create(String nameReplacement, String obsevationReplacement) {
		
		Replacement replacement = new Replacement();
		replacement.setName(nameReplacement);
		replacement.setDeleted("NO");
		replacement.setObservation(obsevationReplacement);
		
		if ( DataBaseHelperManager.getReplacementDAO().create(replacement)){
			return true;
		}else{
			return false;
		}
	}


	public boolean update(String nameReplacement, String observationReplacement, int idReplacement) {
		
		Replacement replacement = new Replacement();
		replacement.setId(idReplacement);
		replacement.setName(nameReplacement);
		replacement.setObservation(observationReplacement);
				
		if ( DataBaseHelperManager.getReplacementDAO().update(replacement, idReplacement)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean setLikeUsed(int idReplacement){
		
		if ( DataBaseHelperManager.getReplacementDAO().setLikeUsed(idReplacement)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean setLikeNotUsed(int idReplacement){
		
		if ( DataBaseHelperManager.getReplacementDAO().setLikeUsed(idReplacement)){
			return true;
		}else{
			return false;
		}
	}
}