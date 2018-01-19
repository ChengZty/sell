<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
/*
	$('#addTOrderDetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTOrderDetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTOrderDetailBtn').bind('click', function(){   
 		 var tr =  $("#add_tOrderDetail_table_template tr").clone();
	 	 $("#add_tOrderDetail_table").append(tr);
	 	 resetTrNum('add_tOrderDetail_table');
	 	 return false;
    });  
	$('#delTOrderDetailBtn').bind('click', function(){   
      	$("#add_tOrderDetail_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tOrderDetail_table'); 
        return false;
    }); 
*/
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tOrderDetail_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTOrderDetailBtn" href="#">添加</a> <a id="delTOrderDetailBtn" href="#">删除</a> 
</div>
<table border="0" width="100%" cellpadding="2" cellspacing="0" id="tOrderDetail_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						商品名称
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						件数
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						原价
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						现价
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						运费
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						折扣
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						优惠价
				  </td>
	</tr>
	<tbody id="add_tOrderDetail_table">	
	<c:if test="${fn:length(tOrderDetails)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tOrderDetails[0].id" type="hidden"/>
					<input name="tOrderDetails[0].createName" type="hidden"/>
					<input name="tOrderDetails[0].createBy" type="hidden"/>
					<input name="tOrderDetails[0].createDate" type="hidden"/>
					<input name="tOrderDetails[0].updateName" type="hidden"/>
					<input name="tOrderDetails[0].updateBy" type="hidden"/>
					<input name="tOrderDetails[0].updateDate" type="hidden"/>
					<input name="tOrderDetails[0].status" type="hidden" value="A"/>
					<input name="tOrderDetails[0].orderId" type="hidden"/>
					<input name="tOrderDetails[0].subOrderNo" type="hidden"/>
					<input name="tOrderDetails[0].goodsId" type="hidden"/>
					<input name="tOrderDetails[0].priceCombination" type="hidden"/>
					<input name="tOrderDetails[0].payTime" type="hidden"/>
					<input name="tOrderDetails[0].orderStatus" type="hidden"/>
				  <td align="left">
					  	<input name="tOrderDetails[0].goodsName" maxlength="50" 
					  		type="text"   >
					  <label class="Validform_label" style="display: none;">商品名称</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].quantity" maxlength="12" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">件数</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].priceOriginal" maxlength="12" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">原价</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].priceNow" maxlength="12" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">现价</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].fare" maxlength="12" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">运费</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].discount" maxlength="5" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">折扣</label>
					</td>
				  <td align="left">
					  	<input name="tOrderDetails[0].pricePreferential" maxlength="12" 
					  		type="text"   style="width:120px;" >
					  <label class="Validform_label" style="display: none;">优惠价</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tOrderDetails)  > 0 }">
		<c:forEach items="${tOrderDetails}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tOrderDetails[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tOrderDetails[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tOrderDetails[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tOrderDetails[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tOrderDetails[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tOrderDetails[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tOrderDetails[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tOrderDetails[${stuts.index }].status" type="hidden" value="${poVal.status }"/>
					<input name="tOrderDetails[${stuts.index }].orderId" type="hidden" value="${poVal.orderId }"/>
					<input name="tOrderDetails[${stuts.index }].subOrderNo" type="hidden" value="${poVal.subOrderNo }"/>
					<input name="tOrderDetails[${stuts.index }].goodsId" type="hidden" value="${poVal.goodsId }"/>
					<input name="tOrderDetails[${stuts.index }].priceCombination" type="hidden" value="${poVal.priceCombination }"/>
					<input name="tOrderDetails[${stuts.index }].payTime" type="hidden" value="${poVal.payTime }"/>
					<input name="tOrderDetails[${stuts.index }].orderStatus" type="hidden" value="${poVal.orderStatus }"/>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].goodsName" maxlength="50" 
					  		type="text"   readonly="readonly" value="${poVal.goodsName }">
					  <label class="Validform_label" style="display: none;">商品名称</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].quantity" maxlength="12" 
					  		type="text"   style="width:40px;" readonly="readonly" value="${poVal.quantity }">
					  <label class="Validform_label" style="display: none;">件数</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].priceOriginal" maxlength="12" 
					  		type="text"   style="width:80px;" readonly="readonly" value="${poVal.priceOriginal }">
					  <label class="Validform_label" style="display: none;">原价</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].priceNow" maxlength="12" 
					  		type="text"   style="width:80px;" readonly="readonly" value="${poVal.priceNow }">
					  <label class="Validform_label" style="display: none;">现价</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].fare" maxlength="12" 
					  		type="text"   style="width:40px;"  value="${poVal.fare }">
					  <label class="Validform_label" style="display: none;">运费</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].discount" maxlength="5" 
					  		type="text"   style="width:40px;"  value="${poVal.discount }">
					  <label class="Validform_label" style="display: none;">折扣</label>
				   </td>
				   <td align="left">
					  	<input name="tOrderDetails[${stuts.index }].pricePreferential" maxlength="12" 
					  		type="text"   style="width:80px;"  value="${poVal.pricePreferential }">
					  <label class="Validform_label" style="display: none;">优惠价</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
