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
	 * Crear las llamadas máximas permitidas y luego asignar las llamadas a cada una
	 * de las colas de acuerdo con la capacidad de los operadores, supervisores y
	 * directores parametrizados en las propiedades number.operator, number.supervisor, number.director.
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
	 * crear más llamadas de las permitidas y luego asignar llamadas a cada una de
	 * las colas de acuerdo con la capacidad de los operadores, supervisores y
	 * directores parametrizados, pero la llamada que excede la capacidad actual de
	 * empleados se reasignará a la cola general. De esta forma fue que solucioné el
	 * plus #1 y #2
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
	 * Plus #3: Se adiciona el test para tomar llamadas y de esta forma validar que
	 * las llamadas sean tomadas solo por los empleados que pertenecen a la cola de
	 * trabajo
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
	 * Plus #3: Se adiciona el test en elcual todos los empleados con el rol de
	 * operador atienden las llamdas pendientes en su cola de trabajo y prosiguen a
	 * contestar las llamadas que esten pendientes en la cola general de trabajo
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
