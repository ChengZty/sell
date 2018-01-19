<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>话题分类</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tNewsTypeController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tNewsTypePage.id }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
					<input id="platformType" name="platformType" type="hidden" value="${platformType }">
					<input  name="isNeed" type="hidden" value="0">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<%-- 
				<tr>
					<td align="right">
						<label class="Validform_label">
							编码:
						</label>
					</td>
					<td class="value">
						<c:if test="${platformType =='1' }">
					     	 <input id="code" name="code" type="text" validType="t_news_type,status = 'A' and code" style="width: 150px"  datatype="n1-2" maxlength="2">
							<span class="Validform_checktip">编码为1-2位数字</span>
						</c:if>
						<c:if test="${platformType =='2' }">
					     	 <input id="code" name="code" type="text" validType="t_news_type,status = 'A' and code" style="width: 150px"  datatype="n3-4" maxlength="4">
							<span class="Validform_checktip">编码为3-4位数字</span>
						</c:if>
						<label class="Validform_label" style="display: none;">编码</label>
						</td>
				</tr>
				 --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" maxlength="10"  datatype="*">
							<span class="Validform_checktip">名称不能超过10个字</span>
							<label class="Validform_label" style="display: none;">名字</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							显示类别:
						</label>
					</td>
					<td class="value">
					<t:dictSelect field="showType" datatype="*" defaultVal="1" typeGroupCode="newsShowTp" hasLabel="false" 
					dictText="显示类别" type="list"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">显示类别</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderNum" name="orderNum" type="text" style="width: 150px"  datatype="n1-3" maxlength="3">
							<span class="Validform_checktip">排序编号为3位以内数字</span>
						</td>
				</tr>
				<tr>
					<td align="right" >
						<label class="Validform_label">
							小图:
						</label>
					</td>
					<td class="value"  >
						<div>
							<input type="file" name="templatePic_sm" id="templatePic_sm" />
        				</div>
        				<div id="progress_bar_sm" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
					    <img id="prePic_sm" src="" style="background-color: rgba(68, 111, 128, 0.67)"  width="150px" height="90px"    />
					    <input id="smallPic" name="smallPic" type="hidden" value="${tNewsPage.coverPic}" errormsg="请上传分类小图" checktip="请上传分类小图">
					    <span class="Validform_checktip">尺寸（250*149）</span>
						<label class="Validform_label" style="display: none;">分类图片</label>
					</td>
				</tr>
				<tr>
					<td align="right" >
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value"  >
						<div>
							<input type="file" name="templatePic_u" id="templatePic_u" />
        				</div>
        				<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
					    <img id="prePic" src="" style="background-color: rgba(68, 111, 128, 0.67)"  width="252px" height="150px"    />
					    <input id="coverPic" name="coverPic" type="hidden" value="${tNewsPage.coverPic}" datatype="*" errormsg="请上传分类图片" checktip="请上传分类图片">
					    <span class="Validform_checktip">尺寸（750*446）</span>
						<label class="Validform_label" style="display: none;">封面图片</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/news/tNewsType.js?v=1.0"></script>		