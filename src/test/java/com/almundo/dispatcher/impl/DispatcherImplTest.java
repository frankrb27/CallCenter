package com.almundo.dispatcher.impl;

import java.util.Date;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

import com.almundo.constant.CallStatus;
import com.almundo.constant.RoleEnum;
import com.almundo.dispatcher.Dispatcher;
import com.almundo.model.Call;
import com.almundo.model.Customer;
import com.almundo.model.Employee;

/**
 * @author frank
 */
public class DispatcherImplTest {
	//Bundle
	static String APP_BUNDLE = "com.almundo.config.application";
	static ResourceBundle appBundle = ResourceBundle.getBundle(APP_BUNDLE);
	//Dispatcher
	Dispatcher dispatcher = new DispatcherImpl(Integer.parseInt(appBundle.getString("number.operator")),
			Integer.parseInt(appBundle.getString("number.supervisor")),
			Integer.parseInt(appBundle.getString("number.director")));

	/**
	 * create the maximum allowed calls and then assign the calls to each one of the
	 * queues according to the capacity of parameterized operators, supervisors and
	 * directors.
	 */
	@Test
	public void dispatchCallTest1() {
		//Create Calls
		for(int i=0; i < Integer.parseInt(appBundle.getString("number.concurrents.calls")); i++) {
			Call call = new Call(i+1000, new Customer(i+100, "Customer_"+i), new Date(), CallStatus.INCOMING);
			dispatcher.dispatchCall(call);
		}
		Assert.assertEquals(6, dispatcher.getOperatorCallQueue().size());
		Assert.assertEquals(3, dispatcher.getSupervisorCallQueue().size());
		Assert.assertEquals(1, dispatcher.getDirectorCallQueue().size());
		Assert.assertEquals(0, dispatcher.getGeneralCallQueue().size());
	}

	/**
	 * create more allowed calls and then assign calls to each of the queues
	 * according to the capacity of parameterized operators, supervisors and
	 * directors, but the call that exceeds the capacity will be assigned to the
	 * general queue
	 */
	@Test
	public void dispatchCallTest2() {
		//Create Calls
		for(int i=0; i < Integer.parseInt(appBundle.getString("number.concurrents.calls"))+1; i++) {
			Call call = new Call(i+1000, new Customer(i+100, "Customer_"+i), new Date(), CallStatus.INCOMING);
			dispatcher.dispatchCall(call);
		}
		Assert.assertEquals(6, dispatcher.getOperatorCallQueue().size());
		Assert.assertEquals(3, dispatcher.getSupervisorCallQueue().size());
		Assert.assertEquals(1, dispatcher.getDirectorCallQueue().size());
		Assert.assertEquals(1, dispatcher.getGeneralCallQueue().size());
	}

	/**
	 * Each of the employees takes a call
	 */
	@Test
	public void takeCall1() {
		try {
			//Create call
			dispatchCallTest1();
			dispatcher.takeCall(new Employee(10, "Operator_Test", RoleEnum.OPERATOR));
			dispatcher.takeCall(new Employee(10, "Supervisor_Test", RoleEnum.SUPERVISOR));
			dispatcher.takeCall(new Employee(10, "Director_Test", RoleEnum.DIRECTOR));
			Assert.assertEquals(5, dispatcher.getOperatorCallQueue().size());
			Assert.assertEquals(2, dispatcher.getSupervisorCallQueue().size());
			Assert.assertEquals(0, dispatcher.getDirectorCallQueue().size());
			Assert.assertEquals(0, dispatcher.getGeneralCallQueue().size());
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}	
	
	/**
	 * All employees with the OPERATOR role answer calls and take calls from the
	 * general queue
	 */
	@Test
	public void takeCall2() {
		try {
			//Create call
			dispatchCallTest2();
			Employee employee = new Employee(10, "Operator_Test10", RoleEnum.OPERATOR);
			dispatcher.takeCall(employee);
			dispatcher.takeCall(new Employee(11, "Operator_Test11", RoleEnum.OPERATOR));
			dispatcher.takeCall(new Employee(12, "Operator_Test12", RoleEnum.OPERATOR));
			dispatcher.takeCall(new Employee(13, "Operator_Test13", RoleEnum.OPERATOR));
			dispatcher.takeCall(new Employee(14, "Operator_Test14", RoleEnum.OPERATOR));
			dispatcher.takeCall(new Employee(15, "Operator_Test15", RoleEnum.OPERATOR));
			dispatcher.takeCall(employee);
			Assert.assertEquals(0, dispatcher.getOperatorCallQueue().size());
			Assert.assertEquals(3, dispatcher.getSupervisorCallQueue().size());
			Assert.assertEquals(1, dispatcher.getDirectorCallQueue().size());
			Assert.assertEquals(0, dispatcher.getGeneralCallQueue().size());
		}catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}		
}
