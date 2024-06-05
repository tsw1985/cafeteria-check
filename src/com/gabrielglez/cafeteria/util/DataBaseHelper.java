package com.gabrielglez.cafeteria.util;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gabrielglez.cafeteria.model.CheckSheet;
import com.gabrielglez.cafeteria.model.CheckSheetReplacement;
import com.gabrielglez.cafeteria.model.Customer;
import com.gabrielglez.cafeteria.model.Operator;
import com.gabrielglez.cafeteria.model.Replacement;
import com.gabrielglez.cafeteria.model.ReplacementNotEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

	private final static String DATABASENAME = "varanini.db";
	private Dao<Customer    , Integer> customerDAO    = getCustomerDao();
	private Dao<Operator    , Integer> operatorDAO    = getOperatorDao();
	private Dao<Replacement , Integer> replacementDAO = getReplacementDao();
	private Dao<ReplacementNotEntity , Integer> replacementNotEntityDAO = getReplacementNotEntityDao();
	private Dao<CheckSheet  , Integer> checkSheetDAO  = getcheckSheetDao();
	private Dao<CheckSheetReplacement  , Integer> checkSheetReplacementDAO  = getcheckSheetReplacementDao();

	
	public Dao<Customer, Integer> getCustomerDAO() {
		return customerDAO;
	}

	public Dao<Operator, Integer> getOperatorDAO() {
		return operatorDAO;
	}

	public Dao<Replacement, Integer> getReplacementDAO() {
		return replacementDAO;
	}

	public Dao<CheckSheet, Integer> getCheckSheetDAO() {
		return checkSheetDAO;
	}
	
	
	
	public DataBaseHelper(Context context) {
		super(context, DATABASENAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		
		try {
			
			Log.i(DataBaseHelper.class.getName() , "onCreate");
			TableUtils.createTable(connectionSource, Customer.class);
			TableUtils.createTable(connectionSource, Operator.class);
			TableUtils.createTable(connectionSource, Replacement.class);
			TableUtils.createTable(connectionSource, ReplacementNotEntity.class);
			TableUtils.createTable(connectionSource, CheckSheet.class);
			TableUtils.createTable(connectionSource, CheckSheetReplacement.class);
			
		} catch (SQLException e) {
			Log.e("SQL" , "Error al crear las tablas" + e.toString() );
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,	int arg3) {
		
		try {
			TableUtils.dropTable(connectionSource, Customer.class    , true);
			TableUtils.dropTable(connectionSource, Operator.class    , true);
			TableUtils.dropTable(connectionSource, Replacement.class , true);
			TableUtils.dropTable(connectionSource, ReplacementNotEntity.class , true);
			TableUtils.dropTable(connectionSource, CheckSheet.class  , true);
			TableUtils.dropTable(connectionSource, CheckSheetReplacement.class  , true);
		} catch (SQLException e) {
			Log.e("SQL" , "Error al borrar las tablas" + e.toString() );
			e.printStackTrace();
		}
		
		
	}

	
	public Dao<Customer, Integer> getCustomerDao() {
		
		try {
			
			if (customerDAO == null){
				customerDAO = getDao(Customer.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en customerDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customerDAO;
	}
	
	public Dao<Operator, Integer> getOperatorDao() {
		
		try {
			
			if (operatorDAO == null){
				operatorDAO = getDao(Operator.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en operatorDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return operatorDAO;
	}
	
	
	public Dao<ReplacementNotEntity, Integer> getReplacementNotEntityDao() {
		
		try {
			
			if (replacementNotEntityDAO == null){
				replacementNotEntityDAO = getDao(ReplacementNotEntity.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en replacementNotEntityDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return replacementNotEntityDAO;
	}
	
	
	
	
	
	public Dao<Replacement, Integer> getReplacementDao() {
		
		try {
			
			if (replacementDAO == null){
				replacementDAO = getDao(Replacement.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en replacementDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return replacementDAO;
	}
	
	
	public Dao<CheckSheet, Integer> getcheckSheetDao() {
		
		try {
			
			if (checkSheetDAO == null){
				checkSheetDAO = getDao(CheckSheet.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en checkSheetDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return checkSheetDAO;
	}
	
	
	public Dao<CheckSheetReplacement, Integer> getcheckSheetReplacementDao() {
		
		try {
			
			if (checkSheetReplacementDAO == null){
				checkSheetReplacementDAO = getDao(CheckSheetReplacement.class);
				Log.i(DataBaseHelper.class.getName() , "Entra en checkSheetReplacementDAO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return checkSheetReplacementDAO;
	}
}