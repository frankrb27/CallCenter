package com.almundo.model;

import com.almundo.constant.RoleEnum;

/**
 * @author Frank Rodriguez
 */
public class Employee {

	private Integer id;
	private String name;
	private RoleEnum role;

	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param role
	 */
	public Employee(Integer id, String name, RoleEnum role) {
		this.id = id;
		this.name = name;
		this.role = role;
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

	/**
	 * @return the role
	 */
	public RoleEnum getRole() {
		return role;
	}

	@Override
	public String toString() {
		return String.format("[Employee: {id: %d, name: %s, role: %s} ]", this.id, this.name,
				this.role.getDescription());
	}
}
