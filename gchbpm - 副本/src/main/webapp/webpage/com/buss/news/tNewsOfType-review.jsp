<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>话题信息表</title>
    <t:base type="jquery,easyui,tools"></t:base>
	<style>
		#content_view p{
			margin: 0;
		}
	</style>
 </head>
 <body>
 <div style="margin: auto;max-width: 1280px">
   <t:formvalid formid="formobj" dialog="false" beforeSubmit="setContent()"  layout="table" action="tNewsController.do?doUpdate" callback="backList2" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tNewsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tNewsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tNewsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tNewsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tNewsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tNewsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tNewsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="shopkeeper" name="shopkeeper" type="hidden" value="${tNewsPage.shopkeeper}">
					<input id="upLoaded" name="upLoaded" type="hidden" value="${tNewsPage.upLoaded}" >
					<input id="tags" name="tags" type="hidden"  value="${tNewsPage.tags}">
					<input id="picMapContent" name="picMapContent" type="hidden"  value='${tNewsPage.picMapContent}'>
					<input id="goodNum" name="goodNum" type="hidden" value="${tNewsPage.goodNum}" >
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="${tNewsPage.noSenseNum}" >
					<input id="clickNum" name="clickNum" type="hidden" value="${tNewsPage.clickNum}" >
					<input id="newsType" name="newsType" type="hidden" value="${newsType }" ><!-- 1：管家课堂 2：管家故事-->
		<table style="width: 95%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6"  height="40px">
						<h2>
							<c:if test="${newsType =='1' }">管家课堂</c:if>
							<c:if test="${newsType =='2' }">管家故事</c:if>
						</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120px;">
						<label class="Validform_label">
							话题标题:
						</label>
					</td>
					<td class="value" width="450px;">
					     	 ${tNewsPage.title}
					</td>
					<td align="right" rowspan="2" width="120px;">
						<label class="Validform_label">
							顶图:
						</label>
					</td>
					<td class="value" rowspan="2">
					     	<img id="prePic2" src="${tNewsPage.titlePic}" style="background-color: rgba(68, 111, 128, 0.67)"  width="252px" height="150px" />
					</td>
					<td align="right" rowspan="2" width="120px;">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="2">
					     	<img id="prePic" src="${tNewsPage.coverPic}" style="background-color: rgba(68, 111, 128, 0.67);"  width="252px" height="150px" />
					</td>
				</tr>	
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题作者:
						</label>
					</td>
					<td class="value">
					     	 ${tNewsPage.author}
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题标签:
						</label>
					</td>
					<td class="value" colspan="5">
							${tNewsPage.tags}
					</td>
				</tr>				
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题正文:
						</label>
					</td>
					<td class="value" colspan="5">
					${tNewsPage.newsContext == NULL || tNewsPage.newsContext == '' ? '' : tNewsPage.newsContext}
					</td>
				</tr>
			</table>
		</div>
	</t:formvalid>
	</div>
 </body>
