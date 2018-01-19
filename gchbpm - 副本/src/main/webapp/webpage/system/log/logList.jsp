<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid title="日志管理" name="logList" actionUrl="logController.do?datagrid" idField="id" sortName="operatetime" sortOrder="desc" pageSize="1000" extendParams="view:scrollview,">
	<t:dgCol title="日志类型" field="loglevel" hidden="true"></t:dgCol>
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="日志内容" field="logcontent" width="200"></t:dgCol>
	<t:dgCol title="操作IP" field="note" width="200"></t:dgCol>
	<t:dgCol title="操作人ID" field="TSUser.userName" width="200"></t:dgCol>
	<t:dgCol title="操作人名" field="TSUser.realName" width="200"></t:dgCol>
	<t:dgCol title="(推荐使用IE8+,谷歌浏览器可以获得更快,更安全的页面响应速度) 技术支持：" field="broswer" width="100"></t:dgCol>
	<t:dgCol title="operate.time" field="operatetime" formatter="yyyy-MM-dd hh:mm:ss" width="100"></t:dgCol>
</t:datagrid>
<div id="logListtb" style="padding: 3px; height: 25px">
	<%--add-begin--Author:zhoujf  Date:20150531 for：日志详情按钮位置迁移 --%>
	<span style="float:left;">
		<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" icon="icon-search" onclick="detail('查看','logController.do?logDetail','logList',null,null)" id="">
		查看
		</a>
	</span>
	<%--add-end--Author:zhoujf  Date:20150531 for：日志详情按钮位置迁移--%>
    <div name="searchColums" style="float: right; padding-right: 15px;">
        日志类型: <!-- update---Author:宋双旺  Date:20130414 for：改变值进行查询 -->
        <select name="loglevel" id="loglevel" onchange="logListsearch();">
            <option value="0">选择日志类型</option>
            <option value="1">登录</option>
            <option value="2">注销</option>
            <option value="3">插入</option>
            <option value="4">删除</option>
            <option value="5">更新</option>
            <option value="6">上传</option>
            <option value="7">其他</option>
        </select>
       <%--add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
        <span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="操作时间 ">操作时间: </span>
            <input type="text" name="operatetime_begin" id="operatetime_begin" style="width: 100px; height: 24px;" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'operatetime_end\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">~
            <input type="text" name="operatetime_end" id="operatetime_end" style="width: 100px; height: 24px; margin-right: 20px;" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'operatetime_begin\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </span>
        <%--add-end--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="logListsearch();">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="clearSearch();">清空</a>
    </div>
</div>
<%--add-begin--Author:zhangguoming  Date:20140427 for：查询与清空操作--%>
<script type="text/javascript">
    $(document).ready(function(){
        $("input").css("height", "24px");
    });
    
    <%--update-begin--Author: jg_huangxg  Date:20140427 for：TASK #928 【日志查询】日志列表查询按钮无效 --%>
    function logListsearch(){
    	var loglevel = $("#loglevel").val();
    	var operatetime_begin = $("#operatetime_begin").val();
    	var operatetime_end = $("#operatetime_end").val();
    	if(jQuery.trim(operatetime_begin) != '' && jQuery.trim(operatetime_end) != ''){
    		$("#logList").datagrid('load',{
        		loglevel : loglevel,
        		operatetime_begin : operatetime_begin,
        		operatetime_end : operatetime_end
        	});
    	}else{
    		$("#logList").datagrid('load',{
        		loglevel : loglevel
        	});
    	}
    }
    
    function clearSearch(){
    	$("#loglevel").val(0);
    	$("#operatetime_begin").val("");
    	$("#operatetime_end").val("");
    	$("#logList").datagrid('load',{});
    }
    <%--update-end--Author: jg_huangxg  Date:20160229 for：TASK #928 【日志查询】日志列表查询按钮无效 --%>
</script>
<%--add-end--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
