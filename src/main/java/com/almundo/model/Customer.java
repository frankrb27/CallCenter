package com.almundo.model;

/**
 * @author Frank Rodriguez
 */
public class Customer {

	private Integer id;
	private String name;
	
	/**
	 * Constructor
	 * @param id
	 * @param name
	 */
	public Customer(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("[Customer: {id: %d, name: %s} ]", this.id, this.name);
	}
}
