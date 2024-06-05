package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.model.CheckSheetReplacement;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class CheckSheetDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<CheckSheet, Integer> dao;
	
	
	public CheckSheetDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	
	private void initDAO() {
		dao = dataBaseHelper.getcheckSheetDao();	
	}
	
	
	public void create(CheckSheet checkSheet){
		try {
			dao = dataBaseHelper.getcheckSheetDao();
			dao.create(checkSheet);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e("SQL","Error al crear checkSheet" + e.toString() );
		}
	}
	
	
	public int getLastIdCheckSheetReplacement(){

		try{
			
			QueryBuilder<CheckSheet , Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("idCheckSheet", false);

			PreparedQuery<CheckSheet> preparedQuery = queryBuilder.prepare();
			List<CheckSheet> list = dao.query(preparedQuery);
			
			Log.i("Salida","Query " + preparedQuery.getStatement() );
			
			if ( list.size() > 0 ){
				return list.get(0).getId();
			}
			else{
				return 0;
			}
			
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los clientes " + ex.toString() );
			return -1;
		}
		
	}
	
	
	
	public CheckSheet get(Integer id){
		try{
		
			CheckSheet checkSheet = dao.queryForId(id);
			return checkSheet;
		}catch(SQLException ex){
			Log.e("SQL","Error al obtener checkSheet" + ex.toString() );
			return null;
		}
	}

	
	public List<CheckSheet> getAllCheckSheet(){
		
		try{
			List<CheckSheet> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheet " + ex.toString() );
			return null;
		}
	}
	
	
	public List<CheckSheet> getAllCheckSheetBetweenDates(Date startDate , Date endDate){
		
		try{
			QueryBuilder<CheckSheet, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.where().between("date", startDate, endDate);

			PreparedQuery<CheckSheet> preparedQuery = queryBuilder.prepare();
			List<CheckSheet> list = dao.query(preparedQuery);
			
			Log.e("SQL", "hojas entre fechas obtenidas con exito ");
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheet " + ex.toString() );
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<CheckSheet> getAllCheckSheetBetweenDatesByCustomer(int idCustomer , boolean revised , Date startDate , Date endDate){
		
		List<CheckSheet> list = null; 
		
		try{
			
			QueryBuilder<CheckSheet, Integer> queryBuilder = dao.queryBuilder();
			
			if ( revised == true ){
				
				queryBuilder.where().between("date", startDate, endDate).and().eq("idCustomer", idCustomer );
				PreparedQuery<CheckSheet> preparedQuery = queryBuilder.prepare();
				list = dao.query(preparedQuery);
				
			}
		
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheet and customer " + ex.toString() );
			return null;
		}
	}

	
	public List<CheckSheet> getAllCheckSheetBetweenDatesByCustomerAndOperator(int idCustomer , int idOperator , boolean revised , Date startDate , Date endDate){
		
		
	List<CheckSheet> list = null; 
		
		try{
			
			QueryBuilder<CheckSheet, Integer> queryBuilder = dao.queryBuilder();
			
			if ( revised == true ){
				
				queryBuilder.where().between("date", startDate, endDate).and()
							.eq("idCustomer", idCustomer )
							.and().eq("idOperator", idOperator);
				
				PreparedQuery<CheckSheet> preparedQuery = queryBuilder.prepare();
				list = dao.query(preparedQuery);
				
			}
			
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheet by operator" + ex.toString() );
			return null;
		}
		
		
		
	}
	
	
	

	public void delete(Integer id, String delete){
		
		try{
			
			CheckSheet checkSheet = dao.queryForId(id);
			dao.update(checkSheet);
			
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar repuesto" + ex.toString() );
		}

	}
	
	public void update(CheckSheet checkSheet , Integer id) {
		/*	
		try{
			
			CheckSheet checkSheetToUpdate = dao.queryForId(id);
			checkSheetToUpdate.setName(checkSheet.getName());
			checkSheetToUpdate.setObservation(checkSheet.getObservation());
			checkSheetToUpdate.setDeleted(checkSheet.getDeleted());
			dao.update(replacementToUpdate);
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar repuesto" + ex.toString() );
		}
		*/
	}
	
	
		
	
	
	
}
