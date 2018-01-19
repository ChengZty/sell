<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>

<style>
.tabs-with-icon{
	padding-left: 8px;
	padding-right: 8px;
}
.badge{
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    background-color: red;
    border-radius: 10px;
    position: relative;
    top: -4px;
    left: 2px;
    }
</style>
<t:tabs id="tt" iframe="true" tabPosition="top">
<%-- 	<t:tab href="tOrderInfoController.do?list"  title="全部订单" id="default"></t:tab> --%>
	<t:tab href="tOrderDetailController.do?list"  title="全部订单" id="default"></t:tab>
	<t:tab href="tOrderDetailController.do?list&order_status=2"  title="待发货"  id="default2"></t:tab>
	<t:tab href="tOrderDetailController.do?list&order_status=1"  title="待付款"	id="default1"></t:tab>
	<t:tab href="tOrderDetailController.do?list&order_status=3"  title="已发货" id="default3"></t:tab>
	<t:tab href="tOrderDetailController.do?list&order_status=4"  title="已完成" id="default4"></t:tab>
<%-- 	<t:tab href="tOrderDetailController.do?list&order_status=8"  title="已退款" id="default8"></t:tab> --%>
	<t:tab href="tOrderDetailController.do?list&order_status=9"  title="已取消" id="default9"></t:tab>
</t:tabs>

<script type="text/javascript">
$(function(){
	if("${count}"!=""&&"${count}"!="null"){
		$("#tt a").eq(1).append("<span class='badge'>${count}<span>");
	}
})
</script>