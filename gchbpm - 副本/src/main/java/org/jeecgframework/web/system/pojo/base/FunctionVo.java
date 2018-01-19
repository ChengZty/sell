package org.jeecgframework.web.system.pojo.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 菜单列表vo
 * 用于登录后的菜单显示
 *  @author  
 */
public class FunctionVo implements Comparator<Object>,java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String id;//菜单id
	private String functionName;//菜单名称
	private String functionUrl;//菜单地址
	private String functionOrder;//菜单排序
	private Integer functionLevel;//菜单等级
	private String parentId;//父菜单id
	private String iconPath;//菜单icon路径
	private List<FunctionVo> subFunctionList = new ArrayList<FunctionVo>();//子菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getFunctionOrder() {
		return functionOrder;
	}
	public void setFunctionOrder(String functionOrder) {
		this.functionOrder = functionOrder;
	}
	public Integer getFunctionLevel() {
		return functionLevel;
	}
	public void setFunctionLevel(Integer functionLevel) {
		this.functionLevel = functionLevel;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public List<FunctionVo> getSubFunctionList() {
		return subFunctionList;
	}
	public void setSubFunctionList(List<FunctionVo> subFunctionList) {
		this.subFunctionList = subFunctionList;
	}
	//按排序顺序排列，顺序小的在前面
	@Override
	public int compare(Object obj1, Object obj2) {
		FunctionVo c1 = (FunctionVo) obj1;
		FunctionVo c2 = (FunctionVo) obj2;
		String o1 = c1.getFunctionOrder();
		String o2 = c2.getFunctionOrder();
		if (o1 != null && o2 != null) {
			int n1 = Integer.valueOf(o1);
			int n2 = Integer.valueOf(o2);
			return n1-n2;
		}
		return 0;
	}
	
}