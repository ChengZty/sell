<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#node_root').tree({
			checkbox : false,
// 			url : 'categoryController.do?combotree&goodsId=${goodsId}',
			url : 'categoryController.do?setVisibleCategs&goodsId=${goodsId}',
			onLoadSuccess : function(node) {
				if("${goodsId}"!=""){
					//expandAll();
				}
			},
			onClick: function(node){
				var isRoot =  $('#node_root').tree('getChildren', node.target);
				if(isRoot==''){
					var goodsId = $("#gid").val();
				}else {
				}
			}
		});
		/*
		$("#functionListPanel").panel(
				{
					title :'菜单列表',
					tools:[{iconCls:'icon-save',handler:function(){mysubmit();}}]
				}
		);
		$("#operationListpanel").panel(
				{
					title :'页面控件权限配置',
					tools:[{iconCls:'icon-save',handler:function(){submitOperation();}}]
				}
		);
		
			$("#dataRuleListpanel").panel(
				{
					title :'数据规则权限',
					tools:[{iconCls:'icon-save',handler:function(){submitDataRule();}}]
				}
		);
			*/
	});
	function mysubmit() {
		var goodsId = $("#gid").val();
		var s = GetNode();
		$("#categries").val(s);
		doSubmit("categoryController.do?updateVisibleCategs&categries=" + s + "&goodId=" + goodsId);
	}
	function GetNode() {
		var node = $('#node_root').tree('getChecked');
		var cnodes = '';
		var pnodes = '';
		var pnode = null; //保存上一步所选父节点
		for ( var i = 0; i < node.length; i++) {
			if ($('#node_root').tree('isLeaf', node[i].target)) {
				cnodes += node[i].id + ',';
				pnode = $('#node_root').tree('getParent', node[i].target); //获取当前节点的父节点
				while (pnode!=null) {//添加全部父节点
					pnodes += pnode.id + ',';
					pnode = $('#node_root').tree('getParent', pnode.target); 
				}
			}
		}
		cnodes = cnodes.substring(0, cnodes.length - 1);
		pnodes = pnodes.substring(0, pnodes.length - 1);
		return cnodes + "," + pnodes;
	};
	
	function expandAll() {
		var node = $('#node_root').tree('getSelected');
		if (node) {
			$('#node_root').tree('expandAll', node.target);
		} else {
			$('#node_root').tree('expandAll');
		}
	}
// 	function selecrAll() {
// 		var node = $('#node_root').tree('getRoots');
// 		for ( var i = 0; i < node.length; i++) {
// 			var childrenNode =  $('#node_root').tree('getChildren',node[i].target);
// 			for ( var j = 0; j < childrenNode.length; j++) {
// 				$('#node_root').tree("check",childrenNode[j].target);
// 			}
// 	    }
// 	}
// 	function reset() {
// 		$('#node_root').tree('reload');
// 	}

// 	$('#selecrAllBtn').linkbutton({	}); 
// 	$('#resetBtn').linkbutton({	});   
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel"><input type="hidden" name="goodsId" value="${goodsId}" id="gid">
<input type="hidden" id="categries" value="" >
<ul id="node_root"></ul>
</div>
</div>

