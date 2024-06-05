package com.gabrielglez.services.facade;

import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;

public class CustomerFacade {


	public boolean delete(int id){
		if ( DataBaseHelperManager.getCustomerDAO().delete(id) ){
			return true;
		}else{
			return false;
		}
	}
	

	public boolean create(String nameCustomer, String comercialNameCustomer,String cifCustomer, String addresCustomer, String phoneCustomer,String observationCustomer) {

		Customer customer = new Customer();
		customer.setAddress(addresCustomer);
		customer.setCif(cifCustomer);
		customer.setComercialName(comercialNameCustomer);
		customer.setDeleted("NO");
		customer.setName(nameCustomer);
		customer.setPhone(phoneCustomer);
		customer.setObservation(observationCustomer);
		
		if ( DataBaseHelperManager.getCustomerDAO().create(customer) ){
			return true;
		}else{
			return false;
		}
	}

	public boolean update(String nameCustomer, String comercialNameCustomer,
			String cifCustomer, String addresCustomer, String phoneCustomer,
			String observationCustomer, int idCustomer) {
		
		Customer customer = new Customer();
		customer.setId(idCustomer);
		customer.setAddress(addresCustomer);
		customer.setCif(cifCustomer);
		customer.setComercialName(comercialNameCustomer);
		customer.setDeleted("NO");
		customer.setName(nameCustomer);
		customer.setPhone(phoneCustomer);
		customer.setObservation(observationCustomer);
		
		if ( DataBaseHelperManager.getCustomerDAO().update(customer)){
			return true;
		}else{
			return false;
		}
		
	}
	
}
