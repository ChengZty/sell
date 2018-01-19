<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script src="plug-in/Validform/js/common.js"></script>
<script type="text/javascript">
	$('#addTgoodsWordsDetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTgoodsWordsDetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTgoodsWordsDetailBtn').bind('click', function(){   
 		 var tr =  $("#add_TgoodsWordsDetail_table_template tr").clone();
	 	 $("#add_TgoodsWordsDetail_table").append(tr);
	 	 resetTrNum('add_TgoodsWordsDetail_table');
	 	 return false;
    });  
	$('#delTgoodsWordsDetailBtn').bind('click', function(){   
      	$("#add_TgoodsWordsDetail_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_TgoodsWordsDetail_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		
	    
    });

	//监听上、下、左、右，移动商品规格input的focus位置
	$('html').on('keydown', function(evt){
		var $allInputSpec = $('#add_TgoodsWordsDetail_table input[type="text"]');  //所有需要移动的input
		var keyFlag = evt.keyCode === 37 || evt.keyCode === 38 || evt.keyCode === 39 || evt.keyCode === 40;
		if($allInputSpec.is(':focus') && keyFlag){
			var $curInputFocus = $allInputSpec.filter(":focus");  //当前focus的input
			var curPos = $allInputSpec.index($curInputFocus);
			var tempPos;

			switch(evt.keyCode){
				case 37:  //左
					tempPos = (curPos - 1) > 0 ? (curPos - 1) : 0;
					break;
				case 38:  //上
					tempPos = (curPos - 5) > 0 ? (curPos - 5) : 0;
					break;
				case 39:  //右
					tempPos = (curPos + 1) < ($allInputSpec.length - 1) ? (curPos + 1) : ($allInputSpec.length - 1);
					break;
				case 40:  //下
					tempPos = (curPos + 5) <= ($allInputSpec.length - 1) ? (curPos + 5) : curPos;
					break;
				default:
			}
			$allInputSpec.get(tempPos).focus();
		}
	});
	
	//内容是否改变
	function changeVal(obj){
		$(obj).parent().parent().find("input[name$='changed']").val("Y");
	}
	
	$(function(){
		  var isView="${isView}";
		  if(isView=="1"){
			  $("#add_TgoodsWordsDetail_table input").attr("disabled","disabled");
		  }
		  
	  })
</script>
<c:if test="${isView!='1' }">
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTgoodsWordsDetailBtn" href="#">添加</a> <a id="delTgoodsWordsDetailBtn" href="#">删除</a> 
</div>
</c:if>
<table border="0" width="100%" cellpadding="2" cellspacing="0" id="TgoodsWordsDetail_table">
	<thead>
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						排序
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						话术
				  </td>
				  <c:if test="${isView!='1' }">
				  <td align="center" bgcolor="#EEEEEE">
						操作
				  </td>
				  </c:if>
	</tr>
	</thead>
	<tbody id="add_TgoodsWordsDetail_table">	
	<c:if test="${fn:length(tGoodsWordsDetails)  <= 0 }">
			<tr>
				<td align="center" width="40px"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center" width="40px"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tGoodsWordsDetails[0].id" type="hidden"/>
					<input name="tGoodsWordsDetails[0].createName" type="hidden"/>
					<input name="tGoodsWordsDetails[0].createBy" type="hidden"/>
					<input name="tGoodsWordsDetails[0].createDate" type="hidden"/>
					<input name="tGoodsWordsDetails[0].updateName" type="hidden"/>
					<input name="tGoodsWordsDetails[0].updateBy" type="hidden"/>
					<input name="tGoodsWordsDetails[0].updateDate" type="hidden"/>
					<input name="tGoodsWordsDetails[0].status" type="hidden" value="A"/>
					<input name="tGoodsWordsDetails[0].finActId" type="hidden"/>
					<input name="tGoodsWordsDetails[0].wordsType" type="hidden" value="2"/>
					<input name="tGoodsWordsDetails[0].changed" type="hidden" value="N"/>
				  <td align="left" width="60px">
					  	<input name="tGoodsWordsDetails[0].sortNum" style="width: 50px;" maxlength="5" onkeypress="Public.input.numberInput()" type="text"  >
					</td>
				  <td align="left" width="80%">
					  	<input name="tGoodsWordsDetails[0].words" style="width: 98%;"  type="text" >
				  </td>
				  <c:if test="${isView!='1' }">
				  <td align="center">
					  	<a href="#" class="easyui-linkbutton" onclick="addWords(this)" iconCls="icon-add" >选择已有话术</a>
				  </td>
				  </c:if>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tGoodsWordsDetails)  > 0 }">
		<c:forEach items="${tGoodsWordsDetails}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" width="40px"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" width="40px"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tGoodsWordsDetails[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].status" type="hidden" value="${poVal.status }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].finActId" type="hidden" value="${poVal.finActId }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].wordsType" type="hidden" value="${poVal.wordsType }"/>
					<input name="tGoodsWordsDetails[${stuts.index }].changed" type="hidden" value="N"/>
				  <td align="left" width="60px">
					  	<input name="tGoodsWordsDetails[${stuts.index }].sortNum" style="width: 50px;" maxlength="5" 
					  	onkeypress="Public.input.numberInput()" type="text" value="${poVal.sortNum }" >
					</td>
				  <td align="left" width="80%">
					  	<input name="tGoodsWordsDetails[${stuts.index }].words" style="width: 98%;" type="text" 
					  	 value="${poVal.words }" onchange="changeVal(this)">
					</td>
				<c:if test="${isView!='1' }">
				 <td align="center">
					  	<a href="#" class="easyui-linkbutton" onclick="addWords(this)" iconCls="icon-add" >选择已有话术</a>
				  </td>
				  </c:if>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
