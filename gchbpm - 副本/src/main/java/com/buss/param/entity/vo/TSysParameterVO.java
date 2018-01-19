package com.buss.param.entity.vo;


/**   
 * @Title: Entity
 * @Description: 系统参数
 * @author onlineGenerator
 * @date 2017-02-15 15:57:12
 * @version V1.0   
 *
 */
public class TSysParameterVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	/**主键*/
	private java.lang.String id;
	/**参数编码*/
	private java.lang.String paraCode;
	/**参数值*/
	private java.lang.String paraValue;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getParaCode() {
		return paraCode;
	}
	public void setParaCode(java.lang.String paraCode) {
		this.paraCode = paraCode;
	}
	public java.lang.String getParaValue() {
		return paraValue;
	}
	public void setParaValue(java.lang.String paraValue) {
		this.paraValue = paraValue;
	}
	
	
}
