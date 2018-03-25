package com.almundo.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.almundo.callcenter.CallRequets;
import com.almundo.callcenter.CallResponse;
import com.almundo.constant.CallStatus;
import com.almundo.constant.RoleEnum;
import com.almundo.model.Call;
import com.almundo.model.Customer;
import com.almundo.model.Employee;

/**
 * @author Frank Rodriguez
 */
public class InitApp {

	private static String APP_BUNDLE = "com.almundo.config.application";
	private static ResourceBundle appBundle = ResourceBundle.getBundle(APP_BUNDLE);
	private List<Employee> listEmployee = new ArrayList<Employee>();
	private int numberOperator, numberSupervisor, numberDirector;
	
	/**
	 * Get number of employees per queue
	 */
	private void getNumberEmployee() {
		numberOperator = Integer.parseInt(appBundle.getString("number.operator"));
		numberSupervisor = Integer.parseInt(appBundle.getString("number.supervisor"));
		numberDirector = Integer.parseInt(appBundle.getString("number.director"));
	}
	
	/**
	 * Create employees
	 */
	private void initEmployee() {
		getNumberEmployee();
		//Create operators
		if(numberOperator > 0) {
			for(int i=0; i<numberOperator; i++) {
				listEmployee.add(new Employee(i+10, "Operator_"+i, RoleEnum.OPERATOR));
			}
		}
		//Create operators
		if(numberSupervisor > 0) {
			for(int i=0; i<numberSupervisor; i++) {
				listEmployee.add(new Employee(i+10, "Supervisor_"+i, RoleEnum.SUPERVISOR));
			}
		}
		//Create operators
		if(numberDirector > 0) {
			for(int i=0; i<numberDirector; i++) {
				listEmployee.add(new Employee(i+10, "Director_"+i, RoleEnum.DIRECTOR));
			}
		}		
	}
	
	/**
	 * Init app
	 * @param args
	 */
	public static void main(String ... args) throws Exception{
		InitApp app = new InitApp();
		app.initEmployee();		
		ExecutorService executor = Executors.newFixedThreadPool(20);
		//Create Calls
		for(int i=0; i < Integer.parseInt(appBundle.getString("number.concurrents.calls")); i++) {
			Call call = new Call(i+1000, new Customer(i+100, "Customer_"+i), new Date(), CallStatus.INCOMING);
			executor.execute(new CallRequets(call));
		}
		//TimeWait
		Thread.sleep(2000);
		//Threads generate per employee
		for(Employee employee : app.listEmployee) {
			executor.execute(new CallResponse(employee));
		}		
		executor.shutdown();
		executor.awaitTermination(60, TimeUnit.SECONDS);
	}
}
