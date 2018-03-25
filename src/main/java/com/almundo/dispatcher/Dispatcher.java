package com.almundo.dispatcher;

import java.util.concurrent.LinkedBlockingQueue;

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
	
	/**
	 * Clear queue
	 * @return
	 */
	public void clear();	
	
	/**
	 * @return the operatorCallQueue
	 */
	public LinkedBlockingQueue<Call> getOperatorCallQueue();

	/**
	 * @return the supervisorCallQueue
	 */
	public LinkedBlockingQueue<Call> getSupervisorCallQueue();

	/**
	 * @return the directorCallQueue
	 */
	public LinkedBlockingQueue<Call> getDirectorCallQueue();

	/**
	 * @return the generalCallQueue
	 */
	public LinkedBlockingQueue<Call> getGeneralCallQueue();	
}
