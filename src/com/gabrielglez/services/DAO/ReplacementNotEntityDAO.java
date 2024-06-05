package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.ReplacementNotEntity;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.j256.ormlite.dao.Dao;

public class ReplacementNotEntityDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<ReplacementNotEntity, Integer> dao;
	
	
	public ReplacementNotEntityDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO() {
		dao = dataBaseHelper.getReplacementNotEntityDao();	
	}
	
	public boolean create(ReplacementNotEntity replacementNotEntity){
		
		try {
			dao.create(replacementNotEntity);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public ReplacementNotEntity get(Integer id){
		try{
			ReplacementNotEntity replacementNotEntity = dao.queryForId(id);
			return replacementNotEntity;
		}catch(SQLException ex){
			Log.e("SQL","Error al obtener repuesto not entity" + ex.toString() );
			return null;
		}
	}
	
	public List<ReplacementNotEntity> getAllReplacementNotEntity(){
		
		try{
			List<ReplacementNotEntity> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos not entity" + ex.toString() );
			return null;
		}
	}
	
	
	public List<ReplacementNotEntity> getAllReplacementNotEntitySelected(){
		
		try{
			List<ReplacementNotEntity> list = dao.queryForEq("used", "YES");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos seleccionados" + ex.toString() );
			return null;
		}
	}
	
	
	public List<ReplacementNotEntity> getAllReplacementNoDeleted(){
		
		try{
			List<ReplacementNotEntity> list = dao.queryForEq("deleted", "NO");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos not entity no eliminados" + ex.toString() );
			return null;
		}
	}
	
	
	public List<ReplacementNotEntity> getAllReplacementDeleted(){
		
		try{
			List<ReplacementNotEntity> list = dao.queryForEq("deleted", "YES");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos not entity eliminados" + ex.toString() );
			return null;
		}
	}
	
	
		
	public boolean delete(Integer id){
		try{
			
			ReplacementNotEntity replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setDeleted("YES");
			dao.update(replacementToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar repuesto not entity" + ex.toString() );
			return false;
		}
	}
	
	public boolean update(ReplacementNotEntity replacementNotEntity , Integer id) {
		
		try{
			
			ReplacementNotEntity replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setName(replacementNotEntity.getName());
			replacementToUpdate.setObservation(replacementNotEntity.getObservation());
			dao.update(replacementToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar repuesto" + ex.toString() );
			return false;
		}
	}
	
	
	
	public boolean setLikeUsed(Integer id){
		
		try{
			ReplacementNotEntity replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setUsed("YES");
			dao.update(replacementToUpdate);
			Log.e("SQL","Repuesto asignado");
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar como usado " + ex.toString() );
			return false;
		}
	}
	
	
	public boolean setLikeNotUsed(Integer id){
		
		try{
			ReplacementNotEntity replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setUsed("NO");
			dao.update(replacementToUpdate);
			Log.e("SQL","Repuesto NO asignado");
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar como NO " + ex.toString() );
			return false;
		}
	}
}