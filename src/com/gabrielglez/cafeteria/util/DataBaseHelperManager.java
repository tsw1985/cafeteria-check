package com.gabrielglez.cafeteria.util;

import com.gabrielglez.services.DAO.CheckSheetDAO;
import com.gabrielglez.services.DAO.CheckSheetReplacementDAO;
import com.gabrielglez.services.DAO.CustomerDAO;
import com.gabrielglez.services.DAO.OperatorDAO;
import com.gabrielglez.services.DAO.ReplacementDAO;
import com.gabrielglez.services.DAO.ReplacementNotEntityDAO;


public class DataBaseHelperManager {

	private static CustomerDAO customerDAO;
	private static ReplacementDAO replacementDAO;
	private static ReplacementNotEntityDAO replacementNotEntityDAO;
	private static CheckSheetDAO checkSheetDAO;
	private static CheckSheetReplacementDAO checkSheetReplacementDAO;
	private static OperatorDAO operatorDAO;
	
	public DataBaseHelperManager(DataBaseHelper bd){
		customerDAO = new CustomerDAO(bd);
		replacementDAO = new ReplacementDAO(bd);
		replacementNotEntityDAO = new ReplacementNotEntityDAO(bd);
		checkSheetDAO = new CheckSheetDAO(bd);
		operatorDAO = new OperatorDAO(bd);
		checkSheetReplacementDAO = new CheckSheetReplacementDAO(bd);
	}


	public static void setReplacementDAO(ReplacementDAO replacementDAO) {
		DataBaseHelperManager.replacementDAO = replacementDAO;
	}

	public static CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public static ReplacementDAO getReplacementDAO() {
		return replacementDAO;
	}
	
	public static ReplacementNotEntityDAO getReplacementNotEntityDAO() {
		return replacementNotEntityDAO;
	}

	public static CheckSheetDAO getCheckSheetDAO() {
		return checkSheetDAO;
	}

	public static CheckSheetReplacementDAO getCheckSheetReplacementDAO() {
		return checkSheetReplacementDAO;
	}

	public static OperatorDAO getOperatorDAO() {
		return operatorDAO;
	}
}