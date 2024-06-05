package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.model.modelutil.CustomerNotEntity;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.gabrielglez.cafeteria.util.DataBaseHelperManager;
import com.gabrielglez.cafeteria.util.DateUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class CustomerDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Customer, Integer> dao;
	
	
	public CustomerDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	
	private void initDAO() {
		dao = dataBaseHelper.getCustomerDao();	
	}


	public boolean create(Customer customer){
		
		try {
			dao.create(customer);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Customer get(Integer id){
		try{
			Customer customer = dao.queryForId(id);
			return customer;
		}catch(SQLException ex){
			Log.e("SQL","Error al obtener cliente" + ex.toString() );
			return null;
		}
	}

	public List<Customer> getAllCustomer(){
		
		try{
			List<Customer> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
	}
	
	public List<Customer> getAllCustomerNoDeleted(){

		try{
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("comercialName", true).where().eq("deleted", "NO");

			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
			List<Customer> list = dao.query(preparedQuery);
			
			Log.i("Salida","Query " + preparedQuery.getStatement() );
			
			if ( list.size() > 0 ){
				return  list;
			}else{
				return null;
			}
			
			
		}catch(SQLException ex){
			Log.e("SQL", "error al loguear cliente " + ex.toString() );
			return null;
		}
	
	}
	
	public List<Customer> getAllCustomerDeleted(){

		try{
			List<Customer> list = dao.queryForEq("deleted", "YES");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
	}
	
	
	public List<CustomerNotEntity> getAllCustomerToCheckInActualMonth(){

		try{
			
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("comercialName", true).where().eq("deleted", "NO");

			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
			List<Customer> customerList = dao.query(preparedQuery);
			
			List<CustomerNotEntity> listCustomerNotEntity = new ArrayList<CustomerNotEntity>();
			
			Calendar calendar  = Calendar.getInstance();
			calendar.setTime( new Date() );
			
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			int lastMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			Date startDate = new GregorianCalendar( year , month , 1 ).getTime();
			Date endDate   = new GregorianCalendar( year , month , lastMonthDay ).getTime();
	
			
				for ( Customer customer : customerList ){
					
					CustomerNotEntity customerNotEntity = new CustomerNotEntity();
					customerNotEntity.setId(  customer.getId()  );
					customerNotEntity.setAddress( customer.getAddress()  );
					customerNotEntity.setComercialName(customer.getComercialName());
					customerNotEntity.setDeleted(customer.getDeleted());
					customerNotEntity.setCif( customer.getCif() );
					customerNotEntity.setObservation(customer.getObservation());
					customerNotEntity.setPhone(customer.getPhone() );
					customerNotEntity.setName(customer.getName());
				
					if ( isCustomerWasCheckedBetweenDates(startDate, endDate, customer) ){
						customerNotEntity.setChecked(true);
					}else{		
						customerNotEntity.setChecked(false);
					}
						
					listCustomerNotEntity.add(customerNotEntity);
			
				}//for customer
			
			return listCustomerNotEntity;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
		
	}
	
	
	
	public List<CustomerNotEntity> getAllCustomerToCheckInActualMonthByNameAndOption(String name , String option ){

		try{
			
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("comercialName", true).where().eq("deleted", "NO");

			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
			List<Customer> customerList = dao.query(preparedQuery);
			
			List<CustomerNotEntity> listCustomerNotEntity = new ArrayList<CustomerNotEntity>();
			
			//----------------------
			//List<Customer> customerList = dao.queryForEq("deleted", "NO");
			
			
			Calendar calendar  = Calendar.getInstance();
			calendar.setTime( new Date() );
			
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			int lastMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			Date startDate = new GregorianCalendar( year , month , 1 ).getTime();
			Date endDate   = new GregorianCalendar( year , month , lastMonthDay ).getTime();
	
			
				for ( Customer customer : customerList ){
					
					if ( customer.getComercialName().toLowerCase().startsWith( name.toLowerCase() )){
					
						CustomerNotEntity customerNotEntity = new CustomerNotEntity();
						customerNotEntity.setId(  customer.getId()  );
						customerNotEntity.setAddress( customer.getAddress()  );
						customerNotEntity.setComercialName(customer.getComercialName());
						customerNotEntity.setDeleted(customer.getDeleted());
						customerNotEntity.setCif( customer.getCif() );
						customerNotEntity.setObservation(customer.getObservation());
						customerNotEntity.setPhone(customer.getPhone() );
						customerNotEntity.setName(customer.getName());
						
						
						if ( option.equals("all") ){
							
							if ( isCustomerWasCheckedBetweenDates(startDate, endDate, customer) ){
								customerNotEntity.setChecked(true);
								Log.i("Salida","TODOS");
								
							}else{
								
								customerNotEntity.setChecked(false);
							}
							
							listCustomerNotEntity.add(customerNotEntity);
						
						}else if(option.equals("checked") ){
							
							if ( isCustomerWasCheckedBetweenDates(startDate, endDate, customer) ){
									customerNotEntity.setChecked(true);
									listCustomerNotEntity.add(customerNotEntity);
							}
							
							Log.i("Salida","checked");
							
						}else if (option.equals("nochecked")){
							
							if ( !isCustomerWasCheckedBetweenDates(startDate, endDate, customer) ){
								customerNotEntity.setChecked(false);
								listCustomerNotEntity.add(customerNotEntity);
							}
							
							Log.i("Salida","no checked");
							
						}
						
					}
			
				}//for customer
			
			return listCustomerNotEntity;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
		
	}
	
	
	public List<Customer> getAllCustomerCheckedNotCheckedByOperator(String option , Operator operator, Date startDate , Date endDate ){

		try{
			
			List<Customer> customerListToReturn = new ArrayList<Customer>();
			List<Customer> allCustomerFromBD = DataBaseHelperManager.getCustomerDAO().getAllCustomerNoDeleted();
			//List<Customer> allCustomerFromBD = DataBaseHelperManager.getCustomerDAO().getAllCustomer();
			List<CheckSheet> listCheckSheet = DataBaseHelperManager.getCheckSheetDAO().getAllCheckSheetBetweenDates(startDate, endDate );

	
				
				 if ( option.equals("checked")){
				
					if ( operator != null ){
						
						Log.i("Customer","Entra por buscar por operator ");
						
						for (Customer customer : allCustomerFromBD ){
							for (CheckSheet checkSheet : listCheckSheet ){
								
								if ( customer.getId().equals(checkSheet.getCustomer().getId() )  &&  
									 checkSheet.getOperator().getId().equals(operator.getId()) ){
										customer.setChecked(true);
										customer.setShowBackGroundColor(true);
										Log.i("Customer", "Cliente fue revisado -> " + customer.getComercialName() );
										customerListToReturn.add(customer);
								}
							}
						}
						
					
					}else{
					
						Log.i("Customer","Entra por NO buscar por operator ");
						for (Customer customer : allCustomerFromBD ){
							for (CheckSheet checkSheet : listCheckSheet ){
								
								if ( customer.getId().equals(checkSheet.getCustomer().getId())){
									customer.setChecked(true);
									customer.setShowBackGroundColor(true);
									Log.i("Customer", "Cliente fue revisado -> " + customer.getComercialName() );
									customerListToReturn.add(customer);
								}
							}
						}//end for customer
					}
				
				}else if ( option.equals("nochecked")){
	
					
					Calendar calendar  = Calendar.getInstance();
					calendar.setTime(startDate);
					
					int month = calendar.get(Calendar.MONTH);
					int year = calendar.get(Calendar.YEAR);
					
					Date startDated = new GregorianCalendar( year , month , 1 ).getTime();

				
					return getAllCustomerNoChecked(startDated , endDate );
				
				
				}//end if
			

			
			return customerListToReturn;
			
		}catch(Exception ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
		
	}
	
	private List<Customer> getAllCustomerNoChecked(Date startDate , Date endDate){

		try{
			
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("comercialName", true).where().eq("deleted", "NO");
			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();

			List<Customer> customerList = dao.query(preparedQuery);
			List<Customer> listCustomerNotEntity = new ArrayList<Customer>();
			
				for ( Customer customer : customerList ){
					
					customer.setShowBackGroundColor(true);
					
						if ( !isCustomerWasCheckedBetweenDates(startDate, endDate, customer) ){
							customer.setChecked(false);
							listCustomerNotEntity.add(customer);
						}
						
			
				}//for customer
			
			return listCustomerNotEntity;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes NO REVISADOS " + ex.toString() );
			return null;
		}
		
	}
	
	
	
	
	public List<Customer> getAllCustomerByName(String name , String option){

		try{
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.where().like("name", "%" + name + "%").and().eq("deleted", option);

			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
			List<Customer> list = dao.query(preparedQuery);
			
			Log.i("Salida","Query " + preparedQuery.getStatement() );
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return null;
		}
	}


	
	public List<Customer> getAllCustomerByComercialName(String name , String option){

		try{
			QueryBuilder<Customer, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("comercialName", true).where().like("comercialName", "%" + name + "%").and().eq("deleted", option);

			PreparedQuery<Customer> preparedQuery = queryBuilder.prepare();
			List<Customer> list = dao.query(preparedQuery);
			
			Log.i("Salida","Query " + preparedQuery.getStatement() );
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes por comercialname " + ex.toString() );
			return null;
		}
	}

	
	private boolean isCustomerWasCheckedBetweenDates(Date startDate , Date endDate , Customer customer){
		
		Log.v("TimeCheck" , "StartTime -> " + DateUtil.getStringDateFromDate(startDate) + " EndDate-> " + DateUtil.getStringDateFromDate(endDate));
		
		List<CheckSheet> allCheckSheetBetweenDates = DataBaseHelperManager.getCheckSheetDAO().getAllCheckSheetBetweenDates( startDate , endDate );
		
		if (allCheckSheetBetweenDates != null){
		
			for (CheckSheet checkSheet : allCheckSheetBetweenDates ){
				Log.e("CheckList" , "Cliente Revisado -> " + checkSheet.getCustomer().getName() );
				
				if ( customer.getCif().equals(checkSheet.getCustomer().getCif()))
					return true;
			}
			
		}
		
		return false;
	}
	
	
	public boolean delete(int id){
		
		try{
			Customer customer = dao.queryForId(id);
			customer.setDeleted("YES");
			dao.update(customer);
			
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar cliente" + ex.toString() );
			return false;
		}

	}
	
	public boolean update(Customer customerNewData) {
		try{
			Customer customer = dao.queryForId(customerNewData.getId());
			customer.setAddress(customerNewData.getAddress());
			customer.setCif(customerNewData.getCif());
			customer.setComercialName(customerNewData.getComercialName());
			customer.setName(customerNewData.getName());
			customer.setObservation(customerNewData.getObservation());
			customer.setPhone(customerNewData.getPhone());
			dao.update(customer);
			
			return true;
		}catch(SQLException ex){
			Log.e("SQL","Error al modificar cliente" + ex.toString() );
			return false;
		}
	}
}