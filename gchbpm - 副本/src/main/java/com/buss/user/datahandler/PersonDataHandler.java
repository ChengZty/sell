package com.buss.user.datahandler;



import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;

public class PersonDataHandler extends ExcelDataHandlerDefaultImpl{
	@Override
	public Object exportHandler(Object obj, String name, Object value) {
		if("手机号码".equals(name)||"身份证号".equals(name)){
			String val = value.toString();
			int n = val.indexOf(".");
			if(-1!=n){
				value = val.substring(0, n);
			}
		}
		if(name.contains("生日")){
			if("".equals(value)){
				value = null;
			}
		}
		return super.exportHandler(obj, name, value);
	}
	@Override
	public Object importHandler(Object obj, String name, Object value) {
		if("品牌编码".equals(name)||"身份证号".equals(name)){
			String val = value.toString();
			int n = val.indexOf(".");
			if(-1!=n){
				value = val.substring(0, n);
			}
		}
		if(name.contains("生日")){
			if("".equals(value)){
				value = null;
			}
		}
		return super.importHandler(obj, name, value);
	}
}
