<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>系统参数</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //提交信息
  var clickFlag = true; //可点击
  function sub(){
	  if(clickFlag){
		  $("#formobj").submit();
	  }
  }
  
  //页面校验通过后，提交前会调用该方法
  function clickDis(){
	clickFlag = false; //防止新增后ID没有回写过来，继续点击保存，会再次新增同样的数据
  }
  
//显示提示信息
  function showMsg(obj){
	   tip(obj.msg);
// 	  if(obj.success){
	   setTimeout(reloadTab,1000);//刷新页面，获取新增后的id
// 	  }
  }
  function reloadTab(){
	  location.reload();
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" layout="table" action="tSysParameterController.do?doBatchSaveOrUpdate" beforeSubmit="clickDis" callback="showMsg" tiptype="4">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="2" align="left" class="value" style="height: 40px;padding-left: 20px;">
						<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >保存</a>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							新品:
						</label>
					</td>
					<td class="value">
							<input name="paramList[0].id" type="hidden" value="${paramList[0].id }">
							<input name="paramList[0].paraCode" type="hidden" value="newGoodsDays">
							<input name="paramList[0].paraName" type="hidden" value="新品">
							<input name="paramList[0].sortNum" type="hidden" value="0"><!-- 排序 -->
							<input name="paramList[0].paraType" type="hidden" value="0"><!-- 参数类型 -->
					     	 上架<input name="paramList[0].paraValue" type="text" style="width: 30px" class="inputxt" value="${paramList[0].paraValue }" datatype="n">天（含）以内产品
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							临近:
						</label>
					</td>
					<td class="value">
							<input name="paramList[1].id" type="hidden" value="${paramList[1].id }">
							<input name="paramList[1].paraCode" type="hidden" value="nearDate">
							<input name="paramList[1].paraName" type="hidden" value="临近">
							<input name="paramList[1].sortNum" type="hidden" value="1">
							<input name="paramList[1].paraType" type="hidden" value="0">
					     	 提前<input name="paramList[1].paraValue" type="text" style="width: 30px" class="inputxt" value="${paramList[1].paraValue }" datatype="n">天（含）查询顾客生日
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
