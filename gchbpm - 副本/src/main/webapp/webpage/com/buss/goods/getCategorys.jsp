<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html style="height: 100%">
 <head>
  <link rel="stylesheet" href="plug-in/goods/css/member.css" />
  <link rel="stylesheet" href="plug-in/goods/css/member_store.css" />
  <t:base type="jquery"></t:base>
 </head>
 <body>
	

<div>
    <!--S 搜索结果-->
    <!--S 分类选择区域-->
    <div>
        <div class="wp_sort" id="categorys_div" style="position:relative;height: 290px;width: 740px">
            <div id="class_div" class="wp_sort_block2">
                <div class="sort_list">
                    <div class="wp_category_list">
                        <div id="class_div_1" class="category_list">
                            <ul>
		                          <c:forEach var="obj" items="${catList}" >
											 <li class=""  onclick="selClass(this);" id="${obj.id}|${obj.level}">
				                                <a class=""  href="javascript:void(0)">
				                                	<span class="has_leaf">${obj.name}</span>
				                                </a>
			                                </li>	                          
		                          </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="sort_list">
                    <div class="wp_category_list blank">
                        <div id="class_div_2" class="category_list">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="sort_list">
                    <div class="wp_category_list blank">
                        <div id="class_div_3" class="category_list">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tips_choice"  style="display: block; clear:both;width: 775px"><span class="tips_zt"></span>
            <dl class="hover_tips_cont">
                <dt id="commodityspan"><span style="color:#F00;">请选择商品类别</span></dt>
                <dt id="commoditydt" style="display: none;" class="current_sort">您当前选择的商品类别是：</dt>
                <dd id="commoditydd"></dd>
            </dl>
        </div>
    </div>
 </body>  
</html>
<script type="text/javascript">
<!--
function selClass(obj){
	$('.wp_category_list').css('background','');
	$("#commodityspan").hide();
	$("#commoditydt").show();
	$("#commoditydd").show();
	$(obj).siblings('li').children('a').attr('class','');
	$(obj).children('a').attr('class','classDivClick');
	tonextClass(obj.id);
}

function tonextClass(id){
	var valarray = id.split('|');
// 	if(1 == parseInt(valarray[1])){
// 		$('#topCategoryId').val(valarray[0]);
// 	}else if(2 == parseInt(valarray[1])){
// 		$('#subCategoryId').val(valarray[0]);
// 	}else if(3 == parseInt(valarray[1])){
// 		$('#thridCategoryId').val(valarray[0]);
// 	}
	$('#dataLoading').show();
	$.getJSON('${webRoot}/tGoodsController.do?findChildClass&id='+valarray[0],
		function(data){
    		if(data != null && data != "null" && data != ""){
    			var jsonObj = eval("(" + data + ")");
    			$('#button_next_step').attr('disabled',true);
				var a='';
				var class_div_id = parseInt(valarray[1])+1;
				for (i=0; i<jsonObj.length; i++) {
					if(jsonObj[i] != null){
						a+='<li onclick="selClass(this);" id="'+ jsonObj[i][0] +'|'+class_div_id+'"><a href="javascript:void(0)"><span class="has_leaf">'+jsonObj[i][1]+'</span></a></li>';
					}
					
				}
				$('#class_div_'+class_div_id).parents('.wp_category_list').removeClass('blank');
				for (j=class_div_id; j<=4; j++) {
					$('#class_div_'+(j+1)).parents('.wp_category_list').addClass('blank');
				}
				$('#class_div_'+class_div_id).children('ul').empty();
				$('#class_div_'+class_div_id).children('ul').append(a);
				$('#class_div_'+class_div_id).nextAll('div').children('ul').empty();
				var str="";
				$.each($('a[class=classDivClick]'),function(i){
					str+=$(this).html()+"&nbsp;&gt;&nbsp;";
				});
				str=str.substring(0,str.length-16);
				$('#commoditydd').html(str);
				$('#commoditya').hide();	
				$('#dataLoading').hide();
    		}else {
    			for(var i= parseInt(valarray[1]); i<4; i++){
    				$('#class_div_'+(i+1)).children('ul').empty();
    			}
    			var str="";
    			$.each($('a[class=classDivClick]'),function(i){
    				str+=$(this).html()+"&nbsp;&gt;&nbsp;";
    			});
    			str=str.substring(0,str.length-16);
    			$('#commoditydd').html(str);
    			$('#commoditya').show();	//添加到常用分类
    			$('#dataLoading').hide();
    		}
		}
	);
}


//-->
</script>		