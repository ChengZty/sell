<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
	  <t:datagrid name="tGoodsWordsListForGoods" checkbox="false" extendParams="nowrap:false," fitColumns="true" title="商品话术" actionUrl="templateWordsGoodsController.do?datagridGoodsWords" 
		  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
		   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%-- 		   <t:dgCol title="一级分类"  field="topTypeId" dictionary="t_template_type,id,name,status='A' and level='1' and template_type='1'"  queryMode="single"  width="120"></t:dgCol> --%>
		   <t:dgCol title="二级分类"  field="subTypeId" replace="${replaceStr }"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="三级分类"  field="thridTypeId" dictionary="t_template_type,id,name,status='A' and level='3' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="关联行业"  field="tradeName" queryMode="single" width="100"></t:dgCol>
		   <t:dgCol title="话术类型"  field="type" replace="图片话术_2,文字话术_1" queryMode="single" query="true" width="100"></t:dgCol>
		   <t:dgCol title="内容"  field="content"  formatterjs="checkPicShow"  queryMode="single"  query="true" width="500"></t:dgCol>
		   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   </t:datagrid>
  </div>
 </div>
 <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	二级分类：
	            </span>
	            <select id="subTypeId" name="subTypeId" onchange="getSubList(this.value)" WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">-请选择-</option>
					<c:forEach var="obj" items="${subTypeList}" >
						<option value="${obj.id}">${obj.name}</option>					 	
	              	</c:forEach>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="三级分类">
	                	三级分类：
	            </span>
	            <select id="thridTypeId" name="thridTypeId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">-请选择-</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="关联行业">
	                	关联行业：
	            </span>
	            <select id="templateTrade" name="templateTrade"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">-请选择-</option>
						 <c:forEach var="obj" items="${templateTrade}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	    </div>
	</div>
	<script src = "webpage/com/buss/words/tGoodsWords.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tGoodsWordsListForGoodsForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
//通过pid获取下级分类
function getSubList(parentId){
	if(parentId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'tTemplateTypeController.do?getSubTypeList',
		    dataType: 'json',
		    data : {'pid' :parentId},
		    success : function(data){
		    	/*data = JSON.parse(data);*/
				if(data.success == true){if(data.msg == "3"){
				    	$("#thridTypeId").empty().append("<option value=''>-请选择-</option>");
				    	var optionsStr = "";
				    	 var temStr="";
				    	 var len = data.obj.length;
					    	for(var i=0;i<len;i++){
					    		optionsStr += "<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>";
					    	}
				    	$("#thridTypeId").append(optionsStr);
				    	$("#tb tbody tr.t").remove();
						$("#type").val("");
					}
				}
		    }
		});
	}
}
//判断是否显示图片
function checkPicShow(value,row,index){
	 var type = row.type;//1:文字，2：图片
	 if("2"==type){
		 if(value!=""){
			 return '<img width="70" height="70" border="0" src="'+value+'">';
		 }else{
			 return '';
		 }
	 }else{
		 return value;
	 }
}
 </script>