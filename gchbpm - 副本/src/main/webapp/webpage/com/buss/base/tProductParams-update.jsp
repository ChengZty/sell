<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>产品参数信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  $(document).ready(function(){
		var inputType = '${tProductParamsPage.inputType}';
		var type = '${tProductParamsPage.type}';
		$("#type_"+type).attr("checked","checked");
		$("#inputType_"+inputType).attr("checked","checked");
		if("2"==inputType){
			$("#paramValues_tr").hide();
			  $("#paramValues").val("").removeAttr("datatype");
			  $("#inputType_td").attr("colspan","1");
			  $("td.unit").show();
		}else if("4"==inputType){
			$("#paramValues_tr").hide();
			  $("#paramValues").val("").removeAttr("datatype");
			  $("#inputType_td").attr("colspan","3");
			  $("#inputUnit").val("");
			  $("td.unit").hide();
		}
		
		 $("#inputType_td input[type='radio']").bind("click",function(){
			  var val = $(this).val();
			  if("2"==val){//输入框
				  $("#paramValues_tr").hide();
				  $("#paramValues").val("").removeAttr("datatype");
				  $("#inputType_td").attr("colspan","1");
				  $("td.unit").show();
			  }else if("4"==val){//图片
				  $("#paramValues_tr").hide();
				  $("#paramValues").val("").removeAttr("datatype");
				  $("#inputType_td").attr("colspan","3");
				  $("#inputUnit").val("");
				  $("td.unit").hide();
			  }else{
				  $("#paramValues_tr").show();
				  $("#paramValues").attr("datatype","*");
				  $("#inputType_td").attr("colspan","1");
				  $("td.unit").show();
			  }
		  })
});
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tProductParamsController.do?doUpdate"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${tProductParamsPage.id }">
					<input id="categoryId" name="categoryId" type="hidden" value="${tProductParamsPage.categoryId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								参数名:
							</label>
						</td>
						<td class="value" width="300">
						     	 <input id="paramName" name="paramName" type="text" style="width: 150px" maxlength="10"  datatype="*" value='${tProductParamsPage.paramName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数名</label>
						</td>
						<td align="right" width="100">
							<label class="Validform_label">
								类别:
							</label>
						</td>
						<td class="value">
						     	 <input id="type_1" name="type" type="radio"  value="1" >尺码指导
						     	 <input id="type_2" name="type" type="radio"  value="2" >产品信息
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">类别</label>
							</td>
					</tr>
					
					<tr>
					<td align="right">
						<label class="Validform_label">
							值类型:
						</label>
					</td>
					<td class="value" id="inputType_td">
					     	 <input id="inputType_0" name="inputType" type="radio"  value="0" >单选
					     	 <input id="inputType_1" name="inputType" type="radio"  value="1" >多选
					     	 <input id="inputType_2" name="inputType" type="radio"  value="2" >输入框
					     	 <input id="inputType_3" name="inputType" type="radio"  value="3" >下拉框
					     	 <input id="inputType_4" name="inputType" type="radio"  value="4" >图片
					     	 <input id="inputType_5" name="inputType" type="radio"  value="5" >多选图片
						</td>
					<td align="right" class="unit">
						<label class="Validform_label">
							单位:
						</label>
					</td>
					<td class="value unit">
					     	 <input id="inputUnit" name="inputUnit" type="text" style="width: 150px" value="${tProductParamsPage.inputUnit}">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单位</label>
						</td>
				</tr>
				<tr id="paramValues_tr">
						<td align="right">
							<label class="Validform_label">
								参数值:
							</label>
						</td>
						<td class="value" colspan="3">
							<textarea id="paramValues" name="paramValues" type="text" style="width: 100%;height: 100px" datatype="*">${tProductParamsPage.paramValues}</textarea>
							<span class="Validform_checktip">多个值用逗号分割，请不要在最后面加回车</span>
							<label class="Validform_label" style="display: none;">参数值</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							行排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="rowNum" name="rowNum" type="text"  style="width: 150px" maxlength="3" datatype="n" value='${tProductParamsPage.rowNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">行排序</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							行内排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="rowIndexNum" name="rowIndexNum" type="text" style="width: 150px" value="${tProductParamsPage.rowIndexNum}" maxlength="3" datatype="n">
							<span class="Validform_checktip">同一行的排序位置</span>
							<label class="Validform_label" style="display: none;">行内排序</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
