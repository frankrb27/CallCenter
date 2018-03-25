package com.almundo.constant;

/**
 * @author Frank Rodriguez
 */
public enum RoleEnum {
	
	OPERATOR(1, "Operator of Almundo.com"),
	SUPERVISOR(2, "Supervisor of Almundo.com"),
	DIRECTOR(3, "Director of Almundo.com");
	
	private Integer id;
	private String description;
	
	/**
	 * Constructor
	 * @param id
	 * @param description
	 */
	RoleEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
