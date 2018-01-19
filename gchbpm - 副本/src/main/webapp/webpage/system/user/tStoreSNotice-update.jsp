<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>common.notice</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="noticeStore.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tSNoticePage.id }">
					<input id="id" name="storeId" type="hidden" value="${tSNoticePage.storeId}">
					<input id="id" name="isSend" type="hidden" value="${tSNoticePage.isSend}">
					<input id="id" name="status" type="hidden" value="${tSNoticePage.status}">
		<table style="width: 990px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label" >
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="noticeTitle" name="noticeTitle" type="text" style="width: 95%" class="inputxt" maxlength="20" datatype="*"  value='${tSNoticePage.noticeTitle}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">通知标题</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
							<%-- <textarea rows="10" cols="50" name="noticeContent" id="noticeContent" maxlength="150" datatype="*" style="width: 500px;">${tSNoticePage.noticeContent}</textarea> --%>
							<textarea name="noticeContent" id="noticeContent" style="width: 100%" datatype="*">${tSNoticePage.noticeContent}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">通知公告内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label" style="white-space:nowrap;">
								授权级别:
							</label>
						</td>
						<td class="value">
						     	 <input type="radio" id="noticeLevel" name="noticeLevel" value="1" datatype="*"  <c:if test="${tSNoticePage.noticeLevel=='1'}">checked="checked"</c:if> 
						     	 <c:if test="${empty tSNoticePage.noticeLevel}">checked="checked"</c:if> />全体导购
         				&nbsp;&nbsp;<input type="radio" name="noticeLevel" value="2" <c:if test="${tSNoticePage.noticeLevel=='2'}">checked="checked"</c:if> />指定导购
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">授权级别</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript">
    $(function(){
	  var leipiEditor = UE.getEditor('noticeContent',{
		    toolleipi:true,//是否显示，设计器的 toolbars
		    textarea: 'noticeContent',   
		    toolbars: [[
				'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
				'fontfamily', 'fontsize', '|', 'indent', '|',
				'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
				'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
				'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
				'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
				'customstyle', 'paragraph','|',
				'directionalityltr', 'directionalityrtl', 'indent', '|',
				'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
				'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
				//'simpleupload', 
				'insertimage','insertvideo', '|',
			//    'emotion',
			//    'map',  'insertframe',  'pagebreak',  'background', '|',
			//    'horizontal',  'spechars',  '|',
			   'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', '|',
			//    'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|','charts', '|',
			//    'print', 
				'preview',
			//    'searchreplace', //'help', 'drafts'
		    ]],
			wordCount:true,
		    autoHeightEnabled:false,
		    elementPathEnabled:false,
		    initialFrameHeight:300
		});
  })
  </script>