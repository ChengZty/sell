<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//初始化关键词下标
function resetTdNumOfAttr(tableId) {
	$tbody = $("#"+tableId);
	var n = $tbody.find("input[name$='.id']").length;//关键词个数
	var $tr;
	if(n==0){
		$tr = $tbody.find("tr");
	}else{
		var tr_num = parseInt(n/3-1);
		$tr = $tbody.find("tr:gt("+tr_num+")");//从新添加的行开始初始化
	}
	$tr.find('input[type=text]').each(function(i){
		var $this = $(this), name = $this.attr('name'),status_name = $this.prev().attr('name');
		if(name!=null){
			if (name.indexOf("#index#") >= 0){
				$this.attr("name",name.replace('#index#',i+n));
				$this.prev().attr("name",status_name.replace('#index#',i+n));//初始化status字段下标
			}else{
// 				var s = name.indexOf("[");
// 				var e = name.indexOf("]");
// 				var new_name = name.substring(s+1,e);
// 				$this.attr("name",name.replace(new_name,i));
			}
		}
	});
}

	$('#addTgoodsAttrDetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTgoodsAttrDetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTgoodsAttrDetailBtn').bind('click', function(){   
 		 var tr =  $("#add_TgoodsAttrDetail_table_template tr").clone();
	 	 $("#add_TgoodsAttrDetail_table").append(tr);
	 	resetTdNumOfAttr('add_TgoodsAttrDetail_table');
	 	 return false;
    });  
	$('#delTgoodsAttrDetailBtn').bind('click', function(){   
		var select_tr = $("#add_TgoodsAttrDetail_table").find("input:checked").parent().parent();
		$(select_tr).find("input[name$=status]").val("I");
		$(select_tr).hide(); 
// 		resetTrNum('add_TgoodsAttrDetail_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
    	<c:if test="${fn:length(tGoodsAttrDetails)  <= 0 }">
		for(i=0;i<4;i++){
			$('#addTgoodsAttrDetailBtn').click();
		}
    	</c:if>
    	
		//将表格的表头固定
// 	    $("#TgoodsAttrDetail_table").createhftable({
// 	    	height:'300px',
// 			width:'auto',
// 			fixFooter:false
// 			});
    });
    

</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTgoodsAttrDetailBtn" href="#">添加</a> 
<!-- 	<a id="delTgoodsAttrDetailBtn" href="#">删除</a>  -->
</div>
<table border="0" width="100%" cellpadding="2" cellspacing="0" id="TgoodsAttrDetail_table">
	<tbody id="add_TgoodsAttrDetail_table">	
	<c:if test="${fn:length(tGoodsAttrDetails)  > 0 }">
		<c:forEach items="${tGoodsAttrDetails}" var="poVal" varStatus="stuts">
		<c:if test="${stuts.index % 3 == 0 }">
			<tr>
		<td align="center">
<!-- 		<div style="width: 25px;" name="xh"></div> -->
		<input style="width:20px;" type="checkbox" name="ck"/> </td>
		</c:if>
				   <td align="right">
					  	关键词：
				   </td>
				   <td align="left">
					<input name="tGoodsAttrDetails[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].status" type="hidden" value="${poVal.status }"/>
					<input name="tGoodsAttrDetails[${stuts.index }].goodsId" type="hidden" value="${poVal.goodsId }"/>
					  	<input name="tGoodsAttrDetails[${stuts.index }].careValue" maxlength="50" 
					  		type="text"   value="${poVal.careValue }">
				   </td>
   		<c:if test="${stuts.index % 3 == 3 || stuts.index == status.last}">
			</tr>
		</c:if>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
