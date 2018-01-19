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
	<t:tab href="tSmsCountInfoController.do?batchPushList"  title="批量短信发送" id="default"></t:tab>
	<t:tab href="tSmsCountInfoController.do?singlePushList"  title="通知短信发送"  id="default2"></t:tab>
</t:tabs>
