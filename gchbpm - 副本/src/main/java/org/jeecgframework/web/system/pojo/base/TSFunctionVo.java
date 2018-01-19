package org.jeecgframework.web.system.pojo.base;

/**
 *菜单列表vo
 * 用于菜单权限校验使用
 *  @author  
 */
public class TSFunctionVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String functionId;//菜单id
	private String functionName;//菜单名称
	private String functionUrl;//菜单地址
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionUrl() {
		return functionUrl;
	}
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
}