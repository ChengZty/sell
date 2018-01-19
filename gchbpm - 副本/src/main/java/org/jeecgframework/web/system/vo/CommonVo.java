package org.jeecgframework.web.system.vo;

import java.io.Serializable;

public class CommonVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 824171689596222785L;
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
