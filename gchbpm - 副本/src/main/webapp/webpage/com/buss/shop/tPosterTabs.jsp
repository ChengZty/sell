<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- context path -->
<t:base type="jquery,easyui"></t:base>
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
	<t:tab href="tPosterController.do?tPoster&postStatus=3"  title="已上架" id="default"></t:tab>
	<t:tab href="tPosterController.do?tPoster&postStatus=4"  title="已下架" id="default"></t:tab>
	<t:tab href="tPosterController.do?tPoster&postStatus=1"  title="草稿箱" id="default"></t:tab>
	<t:tab href="tPosterController.do?tPoster&postStatus=2"  title="已完成" id="default"></t:tab>
	<t:tab href="tPosterController.do?goAdd"  title="+新建" id="default"></t:tab>
</t:tabs>
