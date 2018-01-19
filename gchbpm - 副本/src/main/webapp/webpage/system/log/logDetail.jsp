<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>日志详情</title>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true">
	<fieldset class="step"><legend> <%-- <t:mutiLang langKey="common.loginfo"/> --%> 日志详情</legend>
	
	<div class="form"><label class="form"> <%-- 用户名 --%>操作人:${tsLog.TSUser.userName } </label>
	 </div>
	<div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.logtype"/> --%>日志类型: </label>
	   <!-- 以后再统一由数据字典维护  fangwenrong-->
	   <c:if test="${tsLog.operatetype==1}">登录</c:if>
	   <c:if test="${tsLog.operatetype==2}">注销</c:if>
	   <c:if test="${tsLog.operatetype==3}">插入</c:if>
	   <c:if test="${tsLog.operatetype==4}">删除</c:if>
	   <c:if test="${tsLog.operatetype==5}">更新</c:if>
	   <c:if test="${tsLog.operatetype==6}">上传</c:if>
	   <c:if test="${tsLog.operatetype==7}">其他</c:if>
	</div>
	<div class="form"><label class="form"> 日志内容: </label> ${tsLog.logcontent }</div>
	<div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.loglevel"/> --%>日志等级: </label>
	   <!-- 以后再统一由数据字典维护  fangwenrong-->
	   <c:if test="${tsLog.loglevel==1}">INFO</c:if>
	   <c:if test="${tsLog.loglevel==2}">WARRING</c:if>
	   <c:if test="${tsLog.loglevel==3}">ERROR</c:if>
	</div>

	<div class="form"><label class="form"> 操作IP: </label> ${tsLog.note}</div>
	<div class="form"><label class="form"> 操作时间: </label>
	  <fmt:formatDate value="${tsLog.operatetime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
	 </div>
	<div class="form"><label class="form"> 浏览器: </label> ${tsLog.broswer}</div>
	

	</fieldset>
	</form>
</t:formvalid>
</body>
</html>


