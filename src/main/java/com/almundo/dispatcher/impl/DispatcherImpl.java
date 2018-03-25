package com.almundo.dispatcher.impl;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.almundo.constant.CallStatus;
import com.almundo.constant.RoleEnum;
import com.almundo.dispatcher.Dispatcher;
import com.almundo.model.Call;
import com.almundo.model.Employee;

/**
 * @author Frank Rodriguez
 */
public class DispatcherImpl implements Dispatcher {
	//Bundle
	private static String APP_BUNDLE = "com.almundo.config.application";
	private static ResourceBundle appBundle = ResourceBundle.getBundle(APP_BUNDLE);
	//Minimun and maximun time per call
	private static int min = Integer.parseInt(appBundle.getString("minimun.time"));
	private static int max = Integer.parseInt(appBundle.getString("maximun.time"));
	//Queue
	private static LinkedBlockingQueue<Call> operatorCallQueue;
	private static LinkedBlockingQueue<Call> supervisorCallQueue;
	private static LinkedBlockingQueue<Call> directorCallQueue;
	private static LinkedBlockingQueue<Call> generalCallQueue;
	//Logger
	private static final Logger logger = Logger.getLogger(DispatcherImpl.class);
	private static String CLASS_ = "[DispatcherImpl]";

	/**
	 * Constructor
	 * @param int: numberOperators
	 * @param int: numberSupervisors
	 * @param int: numberDirectors
	 */
	public DispatcherImpl(int numberOperator, int numberSupervisor, int numberDirector) {
		operatorCallQueue = new LinkedBlockingQueue<Call>(numberOperator);
		supervisorCallQueue = new LinkedBlockingQueue<Call>(numberSupervisor);
		directorCallQueue = new LinkedBlockingQueue<Call>(numberDirector);
		generalCallQueue = new LinkedBlockingQueue<Call>();
	}

	/**
	 * Dispath call to queue 
	 * @param Call call: Call that will be put in a work queue
	 */
	public void dispatchCall(Call call) {
		String METHOD_ = "[dispatchCall]";
		logger.info(CLASS_+METHOD_+"[Add call to queue]");
		assignCallToOperators(call);
		logger.info(CLASS_+METHOD_+"[Finished]");
	}

	/**
	 * Assign call to operator queue
	 * @param Call call: Call that will be put in a work queue
	 */
	private void assignCallToOperators(Call call) {
		String METHOD_ = "[assignCallToOperators]";
		try{
			//Add Call to queue
			logger.info(CLASS_+METHOD_+"[Add call to operator queue]");
			call.setStatus(CallStatus.ON_HOLD);
			operatorCallQueue.add(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}catch(IllegalStateException ise) {
			//Full queue, reassing to supervisor
			logger.info(CLASS_+METHOD_+"[Full operator queue, reassigning call {"+call.toString()+"} to the queue of supervisors]");
			assignCallToSupervisor(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}
	}

	/**
	 * Assign call to supervisor queue
	 * @param Call call: Call that will be put in a work queue
	 */
	private void assignCallToSupervisor(Call call) {
		String METHOD_ = "[assignCallToSupervisor]";
		try{
			//Add Call to queue
			logger.info(CLASS_+METHOD_+"[Add call to supervisor queue]");
			call.setStatus(CallStatus.ON_HOLD);
			supervisorCallQueue.add(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}catch(IllegalStateException ise) {
			//Full queue, reassing to supervisor
			logger.info(CLASS_+METHOD_+"[Full supervisor queue, reassigning call {"+call.toString()+"} to the queue of directors]");
			assignCallToDirector(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}
	}

	/**
	 * Assign call to director queue
	 * @param Call call: Call that will be put in a work queue
	 */
	private void assignCallToDirector(Call call) {
		String METHOD_ = "[assignCallToDirector]";
		try{
			//Add Call to queue
			logger.info(CLASS_+METHOD_+"[Add call to director queue]");
			call.setStatus(CallStatus.ON_HOLD);
			directorCallQueue.add(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}catch(IllegalStateException ise) {
			//Full queue, reassing to general
			logger.info(CLASS_+METHOD_+"[Full director queue, reassigning call {"+call.toString()+"} to the queue of general]");
			assignCallToGeneral(call);
			logger.info(CLASS_+METHOD_+"[Finished]");
		}
	}

	/**
	 * Assign call to director queue
	 * @param Call call: Call that will be put in a work queue
	 */
	private void assignCallToGeneral(Call call) {
		String METHOD_ = "[assignCallToGeneral]";
		//Add Call to queue
		logger.info(CLASS_+METHOD_+"[Add call to general queue]");
		call.setStatus(CallStatus.ON_HOLD);
		generalCallQueue.add(call);
		logger.info(CLASS_+METHOD_+"[Finished]");
	}	

	/**
	 * Take call per employee
	 * @param Employee employee: Employee who will take the call
	 * @throws InterruptedException
	 */
	public void takeCall(Employee employee) throws InterruptedException {
		String METHOD_ = "[takeCall]";
		int duration = (int)(Math.random() * ((max - min) + 1)) + min;
		Call call = null;
		//GET call from respective queue
		if(employee.getRole().equals(RoleEnum.OPERATOR)) {
			if(operatorCallQueue.isEmpty()) {
				call = getCallOfQueue(generalCallQueue);
			}else {
				call = getCallOfQueue(operatorCallQueue);
			}
		}else if(employee.getRole().equals(RoleEnum.SUPERVISOR)) {
			if(supervisorCallQueue.isEmpty()) {
				call = getCallOfQueue(generalCallQueue);
			}else {
				call = getCallOfQueue(supervisorCallQueue);
			}
		}else {
			if(directorCallQueue.isEmpty()) {
				call = getCallOfQueue(generalCallQueue);
			}else {
				call = getCallOfQueue(directorCallQueue);
			}
		}
		if(call != null) {
			call.setEmployee(employee);
			call.setStatus(CallStatus.CONNECTED);
			logger.info(CLASS_+METHOD_+"[Information of the call CONNECTED {"+call.toString()+"}]");
			Thread.sleep(duration);
			call.setEndDate(new Date());
			call.setDuration(duration);
			call.setStatus(CallStatus.FINISHED);
			logger.info(CLASS_+METHOD_+"[Information of the call FINISHED {"+call.toString()+"}]");
		}else {
			logger.info(CLASS_+METHOD_+"[There are no calls waiting]");
		}
	}

	/**
	 * Get Call object from specific queue
	 * @param LinkedBlockingQueue<Call> queue
	 * @return
	 */
	private Call getCallOfQueue(LinkedBlockingQueue<Call> queue) {
		return queue.poll();
	}

	/**
	 * @return the operatorCallQueue
	 */
	public LinkedBlockingQueue<Call> getOperatorCallQueue() {
		return operatorCallQueue;
	}

	/**
	 * @return the supervisorCallQueue
	 */
	public LinkedBlockingQueue<Call> getSupervisorCallQueue() {
		return supervisorCallQueue;
	}

	/**
	 * @return the directorCallQueue
	 */
	public LinkedBlockingQueue<Call> getDirectorCallQueue() {
		return directorCallQueue;
	}

	/**
	 * @return the generalCallQueue
	 */
	public LinkedBlockingQueue<Call> getGeneralCallQueue() {
		return generalCallQueue;
	}
}
