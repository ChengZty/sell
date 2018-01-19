<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
	<link href="plug-in/divView/css/bootstrap.min2.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  //编写自定义JS代码
  function goback(){
	  window.location="tGoodsController.do?storeList&type=${type}";
	  return false;
  }
  
//校验规格和库存 
  function checkSpec(){
  	  var store_trs =  $("#add_TgoodsStoreDetail_table tr:visible");
  	  var store_th =  $("#TgoodsStoreDetail_table thead tr").eq(0);
  	  var nullNum = 0;
  	  $(store_trs).each(function(){
  		  var $num = $(this).find("td").eq(5).find("input[name$='store']");
  		  var num = $.trim($num.val());
  		  var $textOne = $(this).find("td").eq(2).find("input[name$='specificationOne']");//规格1不能为空
  		  var $textTwo = $(this).find("td").eq(3).find("input[name$='specificationTwo']");//规格2不能为空
//   		  var $textThree = $(this).find("td").eq(4).find("input[name$='specificationThree']");//规格3不能为空
  		  var textOne = $.trim($textOne.val());
  		  var textTwo = $.trim($textTwo.val());
//   		  var textThree = $.trim($textThree.val());
  		  if(textOne==""){
  			  nullNum++;
  			  var headOne = $(store_th).find("td").eq(2).find("input").val();
  			  if(headOne==undefined){
  				  headOne = $(store_th).find("td").eq(2).text();
  			  }
  			  $.messager.alert('提示信息',"请填写"+headOne,"",function(){
  				  $textOne.focus();
  			  });
  			  return false;
  		  }else{
  			  /* if(textTwo==""){
  				  nullNum++;
  				  var headTwo = $(store_th).find("td").eq(3).find("input").val();
  				  if(headTwo==undefined){
  					  headTwo = $(store_th).find("td").eq(3).text();
  				  }
  				  $.messager.alert('提示信息',"请填写"+headTwo,"",function(){
  					  $textTwo.focus();
  				  });
  				  return false;
  			  } */
  			  if(num==""||num=="0"){
  				  nullNum++;
  				  $.messager.alert('提示信息',"请填写库存","",function(){
  					  $num.focus();
  				  });
  				  return false;
  			  }
  		  }
  	  });
  	  return nullNum;
  }
  
  function sub(){
	  var nullNum = checkSpec();
	  if(nullNum==0){
	  	$("#formobj_gu").submit();
	  }
  }
  
	function backList(data) {
			 if(data.success){
				document.location="tGoodsController.do?storeList&change=1&type=${type}";
			}else{
				$.messager.alert('错误提示', data.msg);
			}
		}
	$(function () {
		$("#view_tb input[type!='hidden'],select").attr("disabled","disabled");
		
	});
	
	//计算库存
	function calStore(){
		var trs = $("#add_TgoodsStoreDetail_table tr:visible");
		var storeAmount =0;
		$(trs).each(function(i,obj){
			var status = $(this).find("input[name$='.status']").val();
			var store = $(this).find("input[name$='.store']").val();
			if(status="A"){
				storeAmount = store-0+storeAmount;
			}
		})
		$("#goodsStock").val(storeAmount);
// 		$("#goodsStock_1").val(storeAmount);//修改库存的时候用到
	}

	//初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select,button,a', this).each(function(){
				var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
				if(id!=null){
					if (id.indexOf("#index#") >= 0){
						$this.attr("id",id.replace('#index#',i));
					}else{
						var s = id.indexOf("[");
						var e = id.indexOf("]");
						var new_id = id.substring(s+1,e);
						$this.attr("id",id.replace(new_id,i));
					}
				}
				if(onclick_str!=null){
					if (onclick_str.indexOf("#index#") >= 0){
						$this.attr("onclick",onclick_str.replace(/#index#/g,i));
					}else{
					}
				}
			});
			$(this).find('div[name=\'xh\']').html(i+1);
		});
	}


	//初始化下标
	function resetTdNum(tableId) {
		$tbody = $("#"+tableId);
		$tbody.find('input[type=text]').each(function(i){
				var $this = $(this), name = $this.attr('name'),id=$this.attr('id');
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
		});
	}
	//更新这条记录标识的为已修改
	function changeFlag(obj){
		$(obj).parent().parent().find("input[name$='.changed']").val("Y");
	}
  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj_gu" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tGoodsController.do?doUpdateStore" tiptype="5"  callback="backList">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<input id="type" name="type" type="hidden" value="${type}">
					<input id="goodsStock" name="goodsStock" type="hidden" value="${tGoodsPage.goodsStock}">
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" id="view_tb">
							<tr>
								<td colspan="6" height="40px" style="padding-left: 10px">
									<h2>基础资料</h2>
								</td>
							</tr>
							<tr>
								<td align="right" width="100px">
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value">
										<span>${thridCategory}</span>
								</td>
								<%-- <td align="right">
									<label class="Validform_label">
										场景:
									</label>
								</td>
								<td class="value">
										  <t:dictSelect field="sceneType" type="list"
												typeGroupCode="sceneType" defaultVal="${tGoodsPage.sceneType}" hasLabel="false"  title="场景"></t:dictSelect>     
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">场景</label>
									</td> --%>
								<td align="right">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" >
								</td>	
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" datatype="*">
								</td>
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value">
							     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*">
								</td>
							</tr>
							<tr>
								<%-- <td align="right">
									<label class="Validform_label">
										现价:
									</label>
								</td>
								<td class="value">
								     	 <input id="currentPrice" name="currentPrice" type="text" style="width: 150px" value="${tGoodsPage.currentPrice}" datatype="d">
									</td> --%>
								<td align="right">
									<label class="Validform_label">
										原价:
									</label>
								</td>
								<td class="value">
							     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" value="${tGoodsPage.originalPrice}" >
								</td>
								<td align="right">
									<label class="Validform_label">
										最低价:
									</label>
								</td>
								<td class="value">
								     	 <input id="lowestPrice" name="lowestPrice" type="text" style="width: 150px" value="${tGoodsPage.lowestPrice}" datatype="d">
									</td>
								<%-- <td align="right">
									<label class="Validform_label">
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock_1"  value="${tGoodsPage.goodsStock}" type="text" style="width: 150px;"  >
								</td> --%>
							</tr>
					</table>
			<div style="width: auto;min-height: 200px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:200px;height:1px;"></div>
				<t:tabs id="t_store" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tGoodsController.do?tGoodsStoreDetailsForUpdate&id=${tGoodsPage.id}" icon="icon-search" title="库存情况" id="tGoodsStoreDetailsForUpdate"></t:tab>
				</t:tabs>
			</div>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >提交</a> 
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
			<!-- 添加 库存明细 模版 -->
	<table style="display:none">
		<tbody id="add_TgoodsStoreDetail_table_template">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh"></div>
				 </td>
				 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				 <input name="tGoodsStoreDetails[#index#].status" type="hidden" value="A"/>
				 <input name="tGoodsStoreDetails[#index#].changed" type="hidden" value="Y"/>
					  <td align="left">
						  	<input name="tGoodsStoreDetails[#index#].specificationOne" maxlength="50" 
						  		type="text"   >
					  </td>
					  <td align="left">
						  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
						  		type="text"   >
					  </td>
					 <!--  <td align="left">
						  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
						  		type="text"   >
					  </td> -->
					  <td align="left">
						  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onchange="changeFlag(this)" onkeypress="Public.input.numberInput()"	type="text" >
					  </td>
					  <!-- <td align="left">
						  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onchange="changeFlag(this)" onkeypress="Public.input.numberInput()" 
						  		type="text"   >
						  <label class="Validform_label" style="display: none;">库存</label>
					  </td> -->
				</tr>
		 </tbody>
	</table>
 </body>
 
