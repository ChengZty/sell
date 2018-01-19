<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="templateNewsList" checkbox="true" fitColumns="false"  title="话题列表" sortName="createDate" sortOrder="desc" actionUrl="templateNewsController.do?datagrid&isRet=0" idField="id"  queryMode="group">
   <t:dgCol title="主键"  field="id" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title" query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic" queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author" query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="话题分类"  field="newsType" query="true" queryMode="single" dictionary="t_news_type,code,name,status = 'A' and platform_type='1' "  width="120"></t:dgCol> --%>
   <t:dgCol title="话题分类"  field="newsType" queryMode="single" dictionary="news_type"  width="120"></t:dgCol>
   <t:dgCol title="关联行业"  field="tradeName" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="标签"  field="tags" queryMode="single"  width="120" query="true"></t:dgCol>
<%--    <t:dgCol title="点击数"  field="clickNum"    queryMode="single"  width="50" ></t:dgCol> --%>
<%--    <t:dgCol title="点赞数"  field="goodNum"    queryMode="single"  width="50" ></t:dgCol> --%>
<%--    <t:dgCol title="无感数"  field="noSenseNum"    queryMode="single"  width="50" ></t:dgCol> --%>
   <t:dgCol title="是否已发布"  field="upLoaded"   query="true" queryMode="single" dictionary="sf_yn" width="90"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${userType !='01' }">
   <t:dgFunOpt funname="doCopy(id)"  title="复制话题"></t:dgFunOpt>
   </c:if>
   <c:if test="${userType == '01' }">
	   <t:dgDelOpt title="删除" url="tNewsController.do?doDel&id={id}" />
	   <t:dgToolBar title="录入" icon="icon-add" url=""  onclick="addbytab()" funname="add"></t:dgToolBar>
	   <t:dgToolBar title="编辑" icon="icon-edit" url=""  onclick="updatebytab()" funname="update"></t:dgToolBar>
   </c:if>
   <c:if test="${userType !='01' }">
   <t:dgToolBar title="批量复制"  icon="icon-add" url="tNewsController.do?doBatchCopy" funname="doBatchCopy"></t:dgToolBar>
<%--    <t:dgToolBar title="全部复制"  icon="icon-add" url="tNewsController.do?doAllCopy" funname="doAllCopy"></t:dgToolBar> --%>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="reviewbytab()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="话题分类">
                	话题分类：
            </span>
            <select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
					 <option value="">---所有---</option>
					 <c:forEach var="obj" items="${topTypeList}" >
							<option value="${obj.id}">${obj.name}</option>					 	
		              </c:forEach>
				</select>
        </span>
        
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="关联行业">
                	关联行业：
            </span>
            <select id="templateTrade" name="templateTrade"  WIDTH="100" style="width: 104px;height: 26px;">
					 <option value="">---所有---</option>
					 <c:forEach var="obj" items="${templateTrade}" >
							<option value="${obj.id}">${obj.name}</option>					 	
		              </c:forEach>
				</select>
        </span>
    </div>
</div>
<!--  <script src = "webpage/com/buss/news/templateNewsList.js"></script>	 -->
 <script type="text/javascript">
 $(document).ready(function(){
	$("#templateNewsListtb").find("select[name='newsType']").attr("name","news_Type");
	$("#templateNewsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });

//新增
function addbytab() {
		document.location="tNewsController.do?goAdd";
}

//查看
function reviewbytab() {
	var rowsData = $("#templateNewsList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	window.open("tNewsController.do?goReview&id="+id,"new");
}

//修改
function updatebytab(){
	var rowsData = $("#templateNewsList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="tNewsController.do?goUpdate&id="+id;
}

//复制话题
function doCopy(id){
	 var url = "tNewsController.do?doCopy&id="+id;
	 $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
// 					reloadTable();
				}
			}
		});
}

//批量复制
function doBatchCopy(title,url,gridname){
	 var ids = [];
	 var rows = $("#"+gridname).datagrid('getSelections');
	 if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			$.ajax({
				url : url,
				type : 'post',
				data : {
					ids : ids.join(',')
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						$("#"+gridname).datagrid('unselectAll');
						ids='';
					}
				}
			});
		} else {
			tip("请选择需要复制的数据");
		}
}
 </script>