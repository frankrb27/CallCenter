package com.almundo.dispatcher;

import java.util.concurrent.LinkedBlockingQueue;

import com.almundo.model.Call;
import com.almundo.model.Employee;

/**
 * @author Frank Rodriguez
 */
public interface Dispatcher {

	/**
	 * Dispath call to queue 
	 * @param Call call: Call that will be put in a work queue
	 */
	public void dispatchCall(Call call);
	
	/**
	 * Take call per employee
	 * @param Employee employee: Employee who will take the call
	 * @throws InterruptedException
	 */
	public void takeCall(Employee employee) throws InterruptedException;
		
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
