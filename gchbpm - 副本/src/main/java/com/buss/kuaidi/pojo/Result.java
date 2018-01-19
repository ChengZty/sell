package com.buss.kuaidi.pojo;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.buss.kuaidi.postOrder.JacksonHelper;

public class Result {

	private String message = "";
	@JsonIgnore
	private String nu = "";
	@JsonIgnore
	private String ischeck = "0";
	@JsonIgnore
	private String com = "";
	private String status = "0";
	@JsonIgnore
	private ArrayList<ResultItem> data = new ArrayList<ResultItem>();
	@JsonIgnore
	private String state = "0";
	@JsonIgnore
	private String condition = "";
	
	/**
    * 物流状态
    * 
    *
    */
    public static String getBackState(String state) {
    	String msg = null;
    	if("0".equals(state)){
    		msg = "在途中";
    	}else if("1".equals(state)){
    		msg = "已揽收";
    	}else if("2".equals(state)){
    		msg = "疑难";
    	}else if("3".equals(state)){
    		msg = "已签收";
    	}else if("4".equals(state)){
    		msg = "退签";
    	}else if("5".equals(state)){
    		msg = "派件";
    	}else if("6".equals(state)){
    		msg = "退回";
    	}else{
    		msg = "未知状态"+state;
    	}
    	return msg;
    }
	   

	@SuppressWarnings("unchecked")
	public Result clone() {
		Result r = new Result();
		r.setCom(this.getCom());
		r.setIscheck(this.getIscheck());
		r.setMessage(this.getMessage());
		r.setNu(this.getNu());
		r.setState(this.getState());
		r.setStatus(this.getStatus());
		r.setCondition(this.getCondition());
		r.setData((ArrayList<ResultItem>) this.getData().clone());

		return r;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<ResultItem> getData() {
		return data;
	}

	public void setData(ArrayList<ResultItem> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return JacksonHelper.toJSON(this);
	}
}
