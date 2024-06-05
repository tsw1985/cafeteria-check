package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class ReplacementDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Replacement, Integer> dao;
	
	
	public ReplacementDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO() {
		dao = dataBaseHelper.getReplacementDao();	
	}
	
	public boolean create(Replacement replacement){
		
		try {
			dao.create(replacement);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Replacement get(Integer id){
		try{
			Replacement replacement = dao.queryForId(id);
			return replacement;
		}catch(SQLException ex){
			Log.e("SQL","Error al obtener repuesto" + ex.toString() );
			return null;
		}
	}
	
	public List<Replacement> getAllReplacement(){
		
		try{
			List<Replacement> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos " + ex.toString() );
			return null;
		}
	}
	
	
	public List<Replacement> getAllReplacementSelected(){
		
		
		try{
			QueryBuilder<Replacement, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("name", true).where().eq("used", "YES").and().eq("deleted", "NO");

			PreparedQuery<Replacement> preparedQuery = queryBuilder.prepare();
			List<Replacement> list = dao.query(preparedQuery);
			
			Log.i("SQL","Query " + preparedQuery.getStatement() );
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos seleccionados  " + ex.toString() );
			return null;
		}
	}
	
	
	public List<Replacement> getAllReplacementNoDeleted(){
		
		try{
			QueryBuilder<Replacement, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.orderBy("name", true).where().eq("deleted", "NO");

			PreparedQuery<Replacement> preparedQuery = queryBuilder.prepare();
			List<Replacement> list = dao.query(preparedQuery);
			
			Log.i("SQL","Query " + preparedQuery.getStatement() );
			
			return list;
			
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos seleccionados  " + ex.toString() );
			return null;
		}
		
		
		
		
		/*try{
			List<Replacement> list = dao.queryForEq("deleted", "NO");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos no eliminados" + ex.toString() );
			return null;
		}*/
	}
	
	
	public List<Replacement> getAllReplacementDeleted(){
		
		try{
			List<Replacement> list = dao.queryForEq("deleted", "YES");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los repuestos eliminados" + ex.toString() );
			return null;
		}
	}
	
	
		
	public boolean delete(Integer id){
		try{
			
			Replacement replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setDeleted("YES");
			dao.update(replacementToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar repuesto" + ex.toString() );
			return false;
		}
	}
	
	public boolean update(Replacement replacement , Integer id) {
		
		try{
			
			Replacement replacementToUpdate = dao.queryForId(id);
			replacementToUpdate.setName(replacement.getName());
			replacementToUpdate.setObservation(replacement.getObservation());
			dao.update(replacementToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar repuesto" + ex.toString() );
			return false;
		}
	}
	
	
	
	public boolean setLikeUsed(Integer id){
		
		try{
			Replacement replacementToUpdate = dao.queryForId(id);
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
			Replacement replacementToUpdate = dao.queryForId(id);
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