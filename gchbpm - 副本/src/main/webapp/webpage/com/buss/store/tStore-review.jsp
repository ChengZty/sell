<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>实体店</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
   <style type="text/css">
  .pic_div{
    float: left;
    position: relative;
    margin-left: 20px;
  }
  .clearfix{display:block; *zoom:1;/*IE/7/6*/}
  .clearfix:after{visibility:hidden; font-size:0; content: ""; display: block; clear: both; height:0;}
  .fl{float: left;}
  .store-img{
	height: 320px;
	padding-top: 15px;
	padding-right: 20px;
  }
</style>
  <script type="text/javascript">
  //编写自定义JS代码
  var m_pic_num = "${picNums}";//已经上传的图的张数
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tStoreController.do?doUpdate"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${tStorePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tStorePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tStorePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tStorePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tStorePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tStorePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tStorePage.updateDate }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tStorePage.retailerId }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								店铺名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 250px" class="inputxt"  
										       value='${tStorePage.name}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								店铺编号:
							</label>
						</td>
						<td class="value">
					     	<input id="storeCode" name="storeCode" type="text" style="width: 250px" class="inputxt"
								   value='${tStorePage.storeCode}' maxlength="30"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="address" name="address" type="text" style="width: 250px" class="inputxt"  
										       value='${tStorePage.address}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
						</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt"  
										       value='${tStorePage.phoneNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排序:
							</label>
						</td>
						<td class="value">
					     	 <input id="sortNum" name="sortNum" type="text" style="width: 150px" class="inputxt" value='${tStorePage.sortNum}' >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value" min-height="200">
	        			<div id="aaaaaaa" style="height: 350px;position: relative;" class="clearfix">
	        				<c:forEach items="${storePics}" varStatus="status" var="poVal">
	        					<div class='pic_div'>
	        					<img class="store-img" src='${poVal.picUrl }'></img>
	        					</div>
	        				</c:forEach>
	        			</div>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/store/tStore.js"></script>		