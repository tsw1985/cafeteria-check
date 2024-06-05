package com.gabrielglez.services.DAO;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.util.DataBaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class OperatorDAO{
	
	private DataBaseHelper dataBaseHelper;
	private Dao<Operator, Integer> dao;
	
	
	public OperatorDAO(DataBaseHelper dataBaseHelper){
		this.dataBaseHelper = dataBaseHelper;
		initDAO();
	}
	
	private void initDAO() {
		dao = dataBaseHelper.getOperatorDao();	
	}

	
	public boolean create(Operator operator){
		try {
			dao = dataBaseHelper.getOperatorDao();
			dao.create(operator);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Operator get(Integer id){
		try{
			Operator operator = dao.queryForId(id);
			return operator;
		}catch(SQLException ex){
			Log.e("SQL","Error al obtener operador" + ex.toString() );
			return null;
		}
	}
	
	public List<Operator> getAllOperator(){
		
		try{
			List<Operator> list = dao.queryForAll();
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los operarios " + ex.toString() );
			return null;
		}
	}
	
	public List<Operator> getAllOperatorNoDeleted(){

		try{
			List<Operator> list = dao.queryForEq("deleted", "NO");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los operadores " + ex.toString() );
			return null;
		}
	}

	
	public List<Operator> getAllOperatorDeleted(){

		try{
			List<Operator> list = dao.queryForEq("deleted", "YES");
			return list;
		}catch(SQLException ex){
			Log.e("SQL", "error al listar todos los operadores " + ex.toString() );
			return null;
		}
	}

	public Operator getOperatorByUserName(String userName){
		
		try{
			
			QueryBuilder<Operator, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq("user", userName ).and().eq("deleted", "NO");

			PreparedQuery<Operator> preparedQuery = queryBuilder.prepare();
			List<Operator> operatorList = dao.query(preparedQuery);
			
			
			//List<Operator> operatorList = dao.queryForEq("user", userName);
			
			for ( Operator op : operatorList){
				Log.v("Operator" , "Operador USER ->" + op.getUser() + "Operator NAME-> " + op.getName() );
			}
			
			if ( operatorList.size() > 0){
				return operatorList.get(0);
			}else{
				return null;
			}
		}catch(SQLException ex){
			Log.e("SQL", "error al obtener el operador por nombre de usuario " + ex.toString() );
			return null;
		}
	}
	
	
	public Operator operatorLogin(String userName , String password){
		
		try{
			QueryBuilder<Operator, Integer> queryBuilder = dao.queryBuilder();
			queryBuilder.where().eq("user", userName ).and().eq("deleted", "NO").and().eq("password", password);

			PreparedQuery<Operator> preparedQuery = queryBuilder.prepare();
			List<Operator> list = dao.query(preparedQuery);
			
			Log.i("Salida","Query " + preparedQuery.getStatement() );
			
			if ( list.size() > 0 ){
				return list.get(0);
			}else{
				return null;
			}
			
			
		}catch(SQLException ex){
			Log.e("SQL", "error al loguear cliente " + ex.toString() );
			return null;
		}
		
		
		
		/*try{
			List<Operator> operator = dao.queryForEq("user", userName);
			
			for ( Operator op : operator){
				Log.v("Operator" , "Operador USER ->" + op.getUser() + "Operator NAME-> " + op.getName() );
			}
			
			return operator.get(0);
		}catch(SQLException ex){
			Log.e("SQL", "error al obtener el operador por nombre de usuario " + ex.toString() );
			return null;
		}*/
	}
	
	
	
	
	
	
	
	
	public boolean delete(Integer id){
		
		try{
			
			Operator operatorToUpdate = dao.queryForId(id);
			operatorToUpdate.setDeleted("YES");
			dao.update(operatorToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al borrar operario" + ex.toString() );
			return false;
		}

	}
	
	public boolean update(Operator operator , Integer id) {
		
		try{
			
			Operator operatorToUpdate = dao.queryForId(id);
			operatorToUpdate.setName(operator.getName());
			operatorToUpdate.setDni(operator.getDni());
			operatorToUpdate.setUser(operator.getUser());
			operatorToUpdate.setPassword(operator.getPassword());
			dao.update(operatorToUpdate);
			return true;
			
		}catch(SQLException ex){
			Log.e("SQL","Error al actualizar operario" + ex.toString() );
			return false;
		}
	}
}