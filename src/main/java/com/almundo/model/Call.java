package com.almundo.model;

import java.util.Date;

import com.almundo.constant.CallStatus;
import com.almundo.utils.StringUtils;

/**
 * @author frank
 */
public class Call {

	private Integer id;
	private Employee employee;
	private Customer customer;
	private Date initDate;
	private Date endDate;
	private Integer duration;
	private CallStatus status;

	/**
	 * Constructor
	 * @param id
	 * @param customer
	 * @param initDate
	 * @param status
	 */
	public Call(Integer id, Customer customer, Date initDate, CallStatus status) {
		this.id = id;
		this.customer = customer;
		this.initDate = initDate;
		this.status = status;
	}

	/**
	 * Constructor
	 * @param id
	 * @param customer
	 * @param employee
	 * @param initDate
	 * @param status
	 */
	public Call(Integer id, Customer customer, Employee employee, Date initDate, CallStatus status) {
		this.id = id;
		this.customer = customer;
		this.employee = employee;
		this.initDate = initDate;
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @return the initDate
	 */
	public Date getInitDate() {
		return initDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the status
	 */
	public CallStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(CallStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format(
				"[Call: {id: %d, customer: %s, employee: %s, initDate: %s, endDate: %s, duration: %d, status: %s} ]", this.id,
				this.customer.toString(), this.employee!=null?this.employee.toString():null, StringUtils.formatDate(this.initDate),
				StringUtils.formatDate(this.endDate), this.duration, this.status);
	}
}
