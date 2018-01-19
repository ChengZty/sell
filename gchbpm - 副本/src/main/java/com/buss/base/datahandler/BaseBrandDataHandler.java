package com.buss.base.datahandler;



import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;

public class BaseBrandDataHandler extends ExcelDataHandlerDefaultImpl{
	@Override
	public Object exportHandler(Object obj, String name, Object value) {
		if("排序".equals(name)){
			value = Float.valueOf(value+"").intValue();	
		}
		if("品牌编码".equals(name)){
			String val = value.toString();
			int n = val.indexOf(".");
			if(-1!=n){
				value = val.substring(0, n);
			}
		}
		return super.exportHandler(obj, name, value);
	}
	@Override
	public Object importHandler(Object obj, String name, Object value) {
		if("排序".equals(name)){
			value = Float.valueOf(value+"").intValue();	
		}
		if("品牌编码".equals(name)){
			String val = value.toString();
			int n = val.indexOf(".");
			if(-1!=n){
				value = val.substring(0, n);
			}
		}
		return super.importHandler(obj, name, value);
	}
}
