package com.almundo.dispatcher;

import com.almundo.model.Call;
import com.almundo.model.Employee;

/**
 * @author frank
 */
public interface Dispatcher {

	/**
	 * Dispath call to employee 
	 * @param call
	 */
	public void dispatchCall(Call call);
	
	/**
	 * Take call per employee
	 */
	public void takeCall(Employee employee) throws InterruptedException;
}
