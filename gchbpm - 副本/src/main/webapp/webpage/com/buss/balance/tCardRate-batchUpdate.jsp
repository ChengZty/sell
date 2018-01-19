<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>G+卡提成比例设置</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function goBackList(){
	  window.location.href="tCardRateController.do?list";
  }
  </script>
 </head>
 <%try{ %>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" btnsub="btn" layout="table" action="tCardRateController.do?doBatchUpdate" 
  tiptype="4" callback="goBackList">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6" style="height: 40px">
						<h3>G+卡提成比例设置</h3>  
					</td>
				</tr>
				<tbody>
					<c:forEach var="po" items="${cardRateList }" varStatus="sts">
						<tr>
							<td align="right" width="100">
								<label class="Validform_label">
									零售商:
								</label>
							</td>
							<td class="value">
								<input type="hidden" name="rateList[${sts.index }].id" value="${po.id}"/>
								<input type="hidden" name="rateList[${sts.index }].retailerId" value="${po.retailerId}"/>
							     <input  name="rateList[${sts.index }].retailerName" type="text" style="width: 150px" class="inputxt"
							      value="${po.retailerName}" readonly="readonly">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">零售商</label>
							</td>
							<td align="right" width="120">
								<label class="Validform_label">
									零售商比例:
								</label>
							</td>
							<td class="value" width="30%">
						     	 <input  name="rateList[${sts.index }].retailerRate" type="text" style="width: 100px" class="inputxt" 
						     	 datatype="d" value="${po.retailerRate}" >
						     	 <span>%</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">零售商比例</label>
							</td>
							<td align="right" width="120">
								<label class="Validform_label">
									管家比例:
								</label>
							</td>
							<td class="value" width="30%">
							     	 <input  name="rateList[${sts.index }].guideRate" type="text" style="width: 100px" class="inputxt" 
							     	 datatype="d" value="${po.guideRate}" >
							     	 <span>%</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">管家比例</label>
								</td>
							</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr height="40">
					<td  colspan="6" align="center">
						<a href="#" class="easyui-linkbutton" id="btn" iconCls="icon-save">保存</a>
						&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" onclick="goBackList()" iconCls="icon-back">返回</a>
					</td>
				</tfoot>
			</table>
		</t:formvalid>
 </body>
<%}catch(Exception e){e.printStackTrace();}%>