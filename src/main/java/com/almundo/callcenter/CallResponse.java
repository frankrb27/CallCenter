package com.almundo.callcenter;

import org.apache.log4j.Logger;

import com.almundo.dispatcher.impl.DispatcherImpl;
import com.almundo.model.Employee;

/**
 * @author Frank Rodriguez
 */
public class CallResponse extends CallCenter implements Runnable {

	//Logger
	private static final Logger logger = Logger.getLogger(DispatcherImpl.class);
	private static String CLASS_ = "[CallResponse]";
	//
	private Employee employee;

	/**
	 * Constructor
	 * @param Employee employee available
	 */
	public CallResponse(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the Employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * Take call
	 */
	public void run() {
		String METHOD_ = "[run]";	
		try {
			logger.info(CLASS_+METHOD_+"[Execute run]");
			dispatcher.takeCall(employee);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}catch(InterruptedException ie) {
			logger.info(CLASS_+METHOD_+"[Fail "+ie.getMessage()+"]");
		}catch(Exception e) {
			logger.info(CLASS_+METHOD_+"[Fail "+e.getMessage()+"]");
		}
	}
}
