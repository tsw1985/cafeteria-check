package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.model.CheckSheetReplacement;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class CheckSheetReplacementDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<CheckSheetReplacement, Integer> dao;
	
	
	public CheckSheetReplacementDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO() {
		dao = dataBaseHelper.getcheckSheetReplacementDao();	
	}
	
	
	public void create(CheckSheetReplacement checkSheetReplacement){
		try {
			dao.create(checkSheetReplacement);
			Log.i("Salida","CheckSheetReplacement creada ID ->" + checkSheetReplacement.getReplacement() + " Idchecksheet ->" + checkSheetReplacement.getCheckSheet() );
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CheckSheetReplacement get(Integer id){
		
		try{
			
			CheckSheetReplacement checkSheetReplacement = dao.queryForId(id);
			return checkSheetReplacement;
			
		}catch(SQLException ex){
			
			Log.e("SQL","Error al obtener checkSheet" + ex.toString() );
			return null;
			
		}
	}
	

	
	
	
	
	public List<CheckSheetReplacement> getAllCheckSheetReplacement(){
		
		try{
			List<CheckSheetReplacement> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheetReplacement " + ex.toString() );
			return null;
		}
	}
	
	
	public List<CheckSheetReplacement> getAllCheckSheetByIdCheckSheet(Integer idCheckSheet){
		
		
		try{
			List<CheckSheetReplacement> list = dao.queryForEq("idCheckSheetFore", idCheckSheet);
			Log.i("Salida","GetAllCheckSheetByid ok");
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los checkSheetReplacement " + ex.toString() );
			return null;
		}
	}
	
	
	
	
	

	public void delete(Integer id, String delete){
		
		/*try{
			
			CheckSheet checkSheet = dao.queryForId(id);
			dao.update(checkSheet);
			
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar repuesto" + ex.toString() );
		}*/

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