<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tIpadAuthorizeList" checkbox="false" fitColumns="false" title="ipad授权表" actionUrl="tIpadAuthorizeController.do?datagrid&retailerId=${retailerId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  queryMode="single" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="零售商名称"  field="retailerName"   hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="使用状态"  field="useStatus"  dictionary="ipadStatus"  queryMode="single"  width="80" ></t:dgCol>
   <t:dgCol title="设备号"  field="deviceNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="授权码"  field="authorizeCode"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="授权日期"  field="authorizeDate"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt title="启用"  funname="activeStatus(id)"  />
   <t:dgFunOpt title="停用"  funname="stopStatus(id)"  />
   <t:dgDelOpt title="删除" url="tIpadAuthorizeController.do?doDel&id={id}" />
   <t:dgToolBar title="生成授权码" icon="icon-add" url="" onclick="genCode()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
<!--
function genCode() {
	$.ajax({
			type : "POST",
			url : 'tIpadAuthorizeController.do?doAdd',
			data : {'retailerId':'${retailerId}'},
			dataType : "json",
			success : function(data){
				if(data.success){
					tip(data.msg);
					$("#tIpadAuthorizeList").datagrid("reload");
				}else{
					tip(data.msg);
				}
			},
			error : function(err) {
				tip('操作失败了哦，请检查您的网络链接！');
			}
	});
}

function activeStatus(id){
	changStatus(id,'1');
}

function stopStatus(id){
	changStatus(id,'2');
}

function changStatus(id,useStatus) {
	$.ajax({
		type : "POST",
		url : 'tIpadAuthorizeController.do?doUpdate',
		data : {'id':id,'useStatus':useStatus},
		dataType : "json",
		success : function(data){
			if(data.success){
				tip(data.msg);
				$("#tIpadAuthorizeList").datagrid("reload");
			}else{
				tip(data.msg);
			}
		},
		error : function(err) {
			tip('操作失败了哦，请检查您的网络链接！');
		}
	});
}
//-->
</script>