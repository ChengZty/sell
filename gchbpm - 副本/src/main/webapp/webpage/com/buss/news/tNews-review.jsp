<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>话题详情</title>
    <t:base type="jquery,easyui,tools"></t:base>
    <link rel="stylesheet" href="plug-in/login/css/tags.css" type="text/css"></link>
 </head>
 <body>
 <div style="margin: auto;max-width: 1280px">
   <t:formvalid formid="formobj" dialog="false" beforeSubmit="setContent()" btnsub="btn"  layout="table" action="tNewsController.do?doUpdate" >
   		<input id="tags" name="tags" type="hidden"  value="${tNewsPage.tags}">
		<table style="width: 95%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6" style="height: 40px">
						<h2>话题管理</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120px;">
						<label class="Validform_label">
							话题标题:
						</label>
					</td>
					<td class="value" width="300px;">
					     	 <input id="title" name="title" type="text" value="${tNewsPage.title}"  style="width: 250px"  disabled="disabled">
					</td>
					<td align="right" rowspan="3" width="120px;">
						<label class="Validform_label">
							顶图:
						</label>
					</td>
					<td class="value" rowspan="3">
					     	<img id="prePic2" src="${tNewsPage.titlePic}" style="background-color: rgba(68, 111, 128, 0.67)"  width="252px" height="150px" />
							<input id="titlePic" name="titlePic" type="hidden" value="${tNewsPage.titlePic}" />
					</td>
					<td align="right" rowspan="3" width="120px;">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="3">
					     	<img id="prePic" src="${tNewsPage.coverPic}" style="background-color: rgba(68, 111, 128, 0.67)"  width="252px" height="150px" />
							<input id="coverPic" name="coverPic" type="hidden" value="${tNewsPage.coverPic}" >
					</td>
				</tr>	
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题作者:
						</label>
					</td>
					<td class="value">
					     	 <input id="author" name="author" type="text" style="width: 150px"  disabled="disabled" value="${tNewsPage.author}" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话题作者</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题分类:
						</label>
					</td>
					<td class="value" id="news_type">
						<t:dictSelect field="newsType"  type="checkbox" dictField="id" dictText="name" dictTable="t_template_type"  dictCondition=" WHERE status='A' and level='1' and platform_Type='1' and template_Type='2' and id not in('6001','6002') " defaultVal="${tNewsPage.newsType}" hasLabel="false"  title="话题分类"  datatype="*" ></t:dictSelect>
<%-- 						<t:dictSelect field="newsType" type="checkbox"  id="newsType" typeGroupCode="news_type" defaultVal="${tNewsPage.newsType}" hasLabel="false"  title="话题分类"  ></t:dictSelect>      --%>
							<label class="Validform_label" style="display: none;">话题分类</label>
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
							<!-- <fieldset title="话题标签" style="min-height: 60px;">
								<div class="box" id="tag_div">
									
								</div>
							</fieldset> -->
					</td>
				</tr>				
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题正文:
						</label>
					</td>
					<td class="value" colspan="5" id="contt"><input id="newsContext" name="newsContext" type="hidden">
						${tNewsPage.newsContext == NULL || tNewsPage.newsContext == '' ? '' : tNewsPage.newsContext}
					</td>
				</tr>
			</table>
		</div>
		</t:formvalid>
</div>		
 </body>
<script>
    $(function () {
    	//添加封面图片
//     	$("#contt video").attr("poster","${tNewsPage.coverPic}");
    	
//     	checkSelected();
    	
       /*  var tags = $("#tags").val();
        if(tags != ""){
        	var tagVal = $("#tags").val();
    		var arr = new Array();  
    		arr = tagVal.split(",");
    		var flag = false;
    		var divs = document.getElementById("tag_div");
    		for (i=0;i<arr.length ;i++ )   
    		{   
    			var str = arr[i];
    			if(str != ""){
    				var texts = document.createTextNode(str);
        			var links = document.createElement("a");
        			var spans = document.createElement("span");
        			links.appendChild(texts);
        			spans.appendChild(links);
        			divs.appendChild(spans);	
    			}
    		}
        } */
        $("#formobj input,select,a").attr("disabled","disabled");
    });
    
  	/* function checkSelected(){
  		var type_codes = "${tNewsPage.newsType}";
  		var code = type_codes.split(",");
  		for(var i=0;i<code.length;i++){
  			$("#news_type input[type='checkbox'][value='"+code[i]+"']").attr("checked",true);
  		}
  	} */
</script>