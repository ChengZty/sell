<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#node_root').tree({
			checkbox : true,
// 			url : 'categoryController.do?combotree&goodsId=${goodsId}',
			url : 'tPersonTagsController.do?setVisibleCategs&tagId=${tagId}',
			onLoadSuccess : function(node) {
				expandAll();
			},
			onClick: function(node){
				var isRoot =  $('#node_root').tree('getChildren', node.target);
				if(isRoot==''){
					var tagId = $("#gid").val();
				}else {
				}
			}
		});
	});
	
	function expandAll() {
		var node = $('#node_root').tree('getSelected');
		if (node) {
			$('#node_root').tree('expandAll', node.target);
		} else {
			$('#node_root').tree('expandAll');
		}
	}
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel"><input type="hidden" name="tagId" value="${tagId}" id="gid">
<input type="hidden" id="categries" value="" >
<ul id="node_root"></ul>
</div>
</div>

