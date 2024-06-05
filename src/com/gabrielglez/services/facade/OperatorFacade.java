package com.gabrielglez.services.facade;

import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;

public class OperatorFacade {


	public boolean delete(int id){
		if ( DataBaseHelperManager.getOperatorDAO().delete(id) ){
			return true;
		}else{
			return false;
		}
	}
	

	public boolean create(String nameOperator, String cifOperator,String userNameOperator, String passwordOperator) {
		
		Operator operator = new Operator();
		operator.setName(nameOperator);
		operator.setDeleted("NO");
		operator.setDni(cifOperator);
		operator.setUser(userNameOperator);
		operator.setPassword(passwordOperator);
		
		
		if ( DataBaseHelperManager.getOperatorDAO().create(operator)){
			return true;
		}else{
			return false;
		}
	}


	public boolean update(String nameOperator, String cifOperator,String userNameOperator, String passwordOperator, int idOperator) {
		
		Operator operator = new Operator();
		operator.setId(idOperator);
		operator.setName(nameOperator);
		operator.setDni(cifOperator);
		operator.setUser(userNameOperator);
		operator.setPassword(passwordOperator);
		
		if ( DataBaseHelperManager.getOperatorDAO().update(operator, idOperator)){
			return true;
		}else{
			return false;
		}
	}
	
}
