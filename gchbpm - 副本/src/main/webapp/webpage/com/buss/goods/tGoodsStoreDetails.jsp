<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script src="plug-in/Validform/js/common.js"></script>
<script type="text/javascript">
	$('#addTgoodsStoreDetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTgoodsStoreDetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTgoodsStoreDetailBtn').bind('click', function(){   
 		 var tr =  $("#add_TgoodsStoreDetail_table_template tr").clone();
	 	 $("#add_TgoodsStoreDetail_table").append(tr);
	 	 resetTrNum('add_TgoodsStoreDetail_table');
	 	 return false;
    });  
	$('#delTgoodsStoreDetailBtn').bind('click', function(){   
      	$("#add_TgoodsStoreDetail_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_TgoodsStoreDetail_table'); 
        calStore();
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		
// 	    $("#TgoodsStoreDetail_table").createhftable({
// 	    	height:'auto',
// 			width:'auto',
// 			fixFooter:false
// 			});
	    
    });

	//监听上、下、左、右，移动商品规格input的focus位置
	$('html').on('keydown', function(evt){
		var $allInputSpec = $('#add_TgoodsStoreDetail_table input[type="text"]');  //所有需要移动的input
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
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTgoodsStoreDetailBtn" href="#">添加</a> <a id="delTgoodsStoreDetailBtn" href="#">删除</a> 
	<c:if test="${isY !='1' }">
		<font style="color: red;margin-left: 20px;" size="8px">可修改规格名称</font>
	</c:if>
</div>
<table border="0" width="100%" cellpadding="2" cellspacing="0" id="TgoodsStoreDetail_table">
	<thead>
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						<input name="headerOne" type="text" style="background: #D0D6D8" value="颜色" maxlength="10" <c:if test="${isY =='1' }">readonly="readonly"</c:if> />
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						<input name="headerTwo" type="text" style="background: #D0D6D8" value="尺码" maxlength="10" <c:if test="${isY =='1' }">readonly="readonly"</c:if>/>
				  </td>
				  <!-- <td align="left" bgcolor="#EEEEEE">
						<input name="headerThree" type="text" style="background: #D0D6D8" value="规格3" maxlength="10"/>
				  </td>-->
				  <td align="left" bgcolor="#EEEEEE">
						预警库存
				  </td> 
				  <td align="left" bgcolor="#EEEEEE">
						库存
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						条码
				  </td>
	</tr>
	</thead>
	<tbody id="add_TgoodsStoreDetail_table">	
	<c:if test="${fn:length(tGoodsStoreDetails)  <= 0 }">
			<tr>
				<td align="center" width="40px"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center" width="40px"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tGoodsStoreDetails[0].id" type="hidden"/>
					<input name="tGoodsStoreDetails[0].createName" type="hidden"/>
					<input name="tGoodsStoreDetails[0].createBy" type="hidden"/>
					<input name="tGoodsStoreDetails[0].createDate" type="hidden"/>
					<input name="tGoodsStoreDetails[0].updateName" type="hidden"/>
					<input name="tGoodsStoreDetails[0].updateBy" type="hidden"/>
					<input name="tGoodsStoreDetails[0].updateDate" type="hidden"/>
					<input name="tGoodsStoreDetails[0].status" type="hidden" value="A"/>
					<input name="tGoodsStoreDetails[0].goodsId" type="hidden"/>
					<input name="tGoodsStoreDetails[0].changed" type="hidden" value="Y"/>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[0].specificationOne" maxlength="50" 
					  		type="text"  ><span style="color: red">*</span>
					</td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[0].specificationTwo" maxlength="50" 
					  		type="text"  >
					</td>
				  <!-- <td align="left">
					  	<input name="tGoodsStoreDetails[0].specificationThree" maxlength="50" 
					  		type="text"  >
					</td>-->
				  <td align="left">
					  	<input name="tGoodsStoreDetails[0].alarmGoodsStock" maxlength="12"  onkeypress="Public.input.numberInput()"
					  		type="text"  >
					</td> 
				  <td align="left">
					  	<input name="tGoodsStoreDetails[0].store" maxlength="12" onblur="calStore()" onkeypress="Public.input.numberInput()"
					  		type="text"  >
					</td>
					<td align="left">
					  	<input name="tGoodsStoreDetails[0].barCode" maxlength="20" type="text" >
					</td>
   			</tr>
	</c:if>
	
	</tbody>
</table>
