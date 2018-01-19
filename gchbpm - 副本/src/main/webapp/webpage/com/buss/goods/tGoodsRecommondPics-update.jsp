<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品推荐</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
	<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script><!-- 自动补全控件 -->
	<link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/divView/css/bootstrap.min2.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="plug-in/gridly/jquery.gridly.js" ></script><!-- 拖动控件 -->
	<link href="plug-in/gridly/css/jquery.gridly.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
  <t:formvalid formid="formobj_gu" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tGoodsController.do?doUpdateRecommondPics" tiptype="5" beforeSubmit="setPics()"  callback="backList">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
								<td align="right" width="100px">
									<label class="Validform_label">
										系统推荐:
									</label>
								</td>
								<td class="value" colspan="5" >
									<div>
		                               <input type="button" value="选择推荐商品" onclick="toGoodsList()" class="uploadify-button " style="width: 120px;height: 25px;"/>
		                            </div>	
		                            <div class="gridly2" id="sys_recommond_pics" style="height: 160px;margin-top: 5px;margin-bottom: -10px">
		                            <c:forEach items="${tGoodsPage.tSysRecmdPics}" varStatus="status" var="sysRecmdPic"> 
									    	 <div class='brick3' >	
									    	 	<input type='hidden' class='s_r_g_v' value=''/>
									    	 	<input type='hidden' class='s_r_p_v' value=''/>
									    	 	<input type='hidden' class='s_r_g_n_v' value=''/>
									    	 	<input type='hidden' class='s_r_t_v' value=''/>
									    	 	<input type='hidden' class='s_r_c_v' value=''/>
									    	 	<input type='hidden' class='s_r_id_v' value=''/>
									    	 	<input type='hidden' class='s_r_id' value='${sysRecmdPic.id }'/>
									    	 	<input type='hidden' class='s_r_c' value='${sysRecmdPic.currentPrice }'/>
									    	 	<input type='hidden' class='s_r_g_n' value='${sysRecmdPic.goodsName }'/>
									    	 	<input type='hidden' class='s_r_t' value='${sysRecmdPic.goodsType }'/>
									    	 	<input type='hidden' class='s_r_g' value='${sysRecmdPic.recmdGoodsId }'/>
									    	 	<a class='delete2' onclick='delSysRecmdPic(this)' href='#'>×</a>
									    	 	<img src='${sysRecmdPic.picUrl }' width='140' height='140' ></img>
												<script>$('#sys_recommond_pics').gridly()</script>
											</div>
									</c:forEach>
	        						</div>
	        						<div><span>最多可推荐6件商品</span></div>
								</td>
							</tr>
					<tr>
								<td align="right">
									<label class="Validform_label">
										管家秀:
									</label>
								</td>
								<td class="value" colspan="5" >
									<div>
		                               <input  type="file"  id="guide_recmd_upLoad" />
		                            </div>	
		                            <div class="gridly2" id="guide_recommond_pics" style="height: 160px;">
		                            	<c:forEach items="${tGoodsPage.tGuideRecmdPics}" varStatus="status" var="guideRecmdPic"> 
									    	 <div class='brick4' >	
									    	 	<input type='hidden' class='g_id_v' value=''/>
									    	 	<input type='hidden' class='p_u_v' value=''/>
									    	 	<input type='hidden' class='u_i_v' value=''/>
									    	 	<input type='hidden' class='u_n_v' value=''/>
									    	 	<input type='hidden' class='u_p_v' value=''/>
									    	 	<input type='hidden' class='g_id'  value='${guideRecmdPic.id }'/>
									    	 	<input type='hidden' class='u_i' id='${guideRecmdPic.userId }-i' value='${guideRecmdPic.userId }'/>
									    	 	<input type='hidden' class='u_n' id='${guideRecmdPic.userId }-n' value='${guideRecmdPic.userName }'/>
									    	 	<input type='hidden' class='u_p' id='${guideRecmdPic.userId }-p'  value='${guideRecmdPic.mobilePhone }'/>
									    	 	<a class='delete2' onclick='delGuidePic(this)' href='#'>×</a>
												<img src='${guideRecmdPic.picUrl }' width='110' height='140' ></img>
												拍摄人：<input type='text' onclick='focus();' id='${guideRecmdPic.userId }-id'  autocomplete='off' class='ac_input' style='width:50px;height: 20px;' value="${guideRecmdPic.userName }"/>
<%-- 												<input type='text' id='${guideRecmdPic.id }'  autocomplete='off' class='ac_input' style='width: 50px; height: 20px;' value='${guideRecmdPic.userName }' onclick='focus();'/> --%>
												<script>$('#guide_recommond_pics').gridly()</script>
											</div>
									</c:forEach>
	        						</div>
	        						<div style="margin-top: 10px"><span>最多可推荐6张照片</span></div>
								</td>
							</tr>
				</table>
				
		<div style="padding: 10px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >提交</a> 
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
		
 </body>
<script type="text/javascript">
  //编写自定义JS代码
  function goback(){
	  window.location="tGoodsController.do?retailerList&type=${type}";
	  return false;
  }
  
  function sub(){
		$("#formobj_gu").submit();
  }
  
	function backList(data) {
			 if(data.success){
				document.location="tGoodsController.do?retailerList";
			}else{
				$.messager.alert('错误提示', data.msg);
			}
		}
	var guide_pic_num = ${guideRecmdPicsNum};//已经上传的导购推荐图片的张数	
	$(function () {
		 //上传导购推荐图片	
	    $('#guide_recmd_upLoad').uploadify({buttonText:'浏览图片',
	    	queueID:'progress_bar_m',
	        progressData:'speed',
	        height:25,
	        overrideEvents:['onDialogClose'],
	        fileTypeDesc:'文件格式:',
	        fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
	        fileSizeLimit:'210KB',
	        swf:'plug-in/uploadify/uploadify.swf',
	        uploader:'tGoodsController.do?uploadGuideRecomdPics&sessionId=${pageContext.session.id}',
	        auto:true,
	        multi:true,
	        queueSizeLimit:6,
	        onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            if(d.success){
	            	guide_pic_num++;
//	                 var n = $("#guide_recommond_pics").find("div").length;
//	                 var left_ = n==0?0:n*(200+40);//初始化图片位置，用于让图片在一行显示
					var id = new Date().getTime()+ parseInt(Math.random()*1000);
					var id_p = id+"_p";
					var id_n = id+"_n";
					var id_id = id+"_id";
	                var div_imgs = "<div class='brick4' ><input type='hidden' class='p_u_v' value=''/><input type='hidden' class='u_i_v' value=''/><input type='hidden' class='u_n_v' value=''/><input type='hidden' class='u_p_v' value=''/><a class='delete2' onclick='delGuidePic(this)' href='#'>×</a><img src='"
	                +d.msg+"' width='110' height='140' ></img>" +"拍摄人：<input type='text' id='"+id+"'  autocomplete='off' class='ac_input' style='width: 50px; height: 20px;' value='' onclick='focus();'/><input type='hidden' id='"+id_n
	                +"'  class='u_n' value=''/><input type='hidden' id='"+id_id+"'  class='u_i' value=''/><input type='hidden' id='"+id_p+"' class='u_p'/></div>";
					$("#guide_recommond_pics").append(div_imgs);
	                $('#guide_recommond_pics').gridly().css("height","160px");
					bindAutoComplete(id,id_n,id_p,id_id); 
				    if(guide_pic_num>=6){
				    	$("#guide_recmd_upLoad").hide();
				    }else{
				    $('#guide_recmd_upLoad').uploadify('settings','queueSizeLimit',6-guide_pic_num);
				    }
				    };
	            },
	        onFallback : function() {
				tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
			},
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -100:
					$.messager.alert('错误提示',
							'上传图片总共不能超过6个');
					break;
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的210KB大小');
					break;
				case -120:
					$.messager.alert('错误提示', '文件大小异常');
					break;
				case -130:
					$.messager.alert('错误提示', '文件类型不正确!');
					break;
				}
			}
	    });
	    initGuideRecmdPic();
	});
	
	//选择系统推荐商品
	function toGoodsList() {
		  var url = "tGoodsController.do?toGoodsList";
			 $.dialog.setting.zIndex = 300;
			 if(typeof(windowapi) == 'undefined'){
				 $.dialog({
						content: "url:"+url,
						lock : true,
						title:"选择商品",
						width:950,
						height: 700,
						cache:false,
					    ok: function(){
					    	iframe = this.iframe.contentWindow;
					    	var selected = iframe.getSelectRows();
					    	if (selected == '' || selected == null ){
					    		alert( '请选择商品');
					    		return false;
						    }else {
						    	var n = $("#sys_recommond_pics img").length;
						    	if(n+selected.length>6){
						    		alert('单品不能超过6个');
						    		return false;
						    	}
						    	var goodsIds = $("#sys_recommond_pics input[class='s_r_g']");
						    	var arrsGoodsId= [];//goodsId的数组
						    	$(goodsIds).each(function(idx,item){
						    		arrsGoodsId.push($(this).val());
						    	});
//								  selected是要遍历的集合
//								  index就是索引值
//								  domEle 表示获取遍历每一个dom对
							    	$.each( selected, function(index, domEle){
							    		arrsGoodsId.push(domEle.id);
							    		if(!isRepeat(arrsGoodsId)){//不重复
							    			var div_imgs = "<div class='brick3'><input type='hidden' class='s_r_g_v' value=''/><input type='hidden' class='s_r_g_n_v' value=''/><input type='hidden' class='s_r_c_v' value=''/>"
							    				+"<input type='hidden' class='s_r_p_v' value=''/><input type='hidden' class='s_r_t_v' value=''/><a class='delete2' onclick='delSysRecmdPic(this)' href='#'>×</a><img src='"
							    				+domEle.smallPic	+"' width='140' height='140' ></img><div><input type='hidden' class='s_r_g' value='"+domEle.id
							    				+"' /><input type='hidden' class='s_r_c' value='"+domEle.currentPrice+"' /><input type='hidden' class='s_r_g_n' value='"+domEle.goodsName+"' /><input type='hidden' class='s_r_t' value='"+domEle.goodsType+"' /></div></div>"
							    				$("#sys_recommond_pics").append(div_imgs);
							    			$('#sys_recommond_pics').gridly();
							    		}
							    	});
						    }
							 
					    },
					    cancelVal: '关闭',
					    cancel: true /*为true等价于function(){}*/
					}).zindex();
				} else{
		  			$.dialog({
						content: "url:"+url,
						lock : true,
						title:"选择商品",
						width:950,
						height: 700,
						parent:windowapi,
						cache:false,
					     ok: function(){
					    	 iframe = this.iframe.contentWindow;
						    	var selected = iframe.getSelectRows();
						    	if (selected == '' || selected == null ){
						    		alert( '请选择商品');
						    		return false;
							    }else {
							    	var n = $("#sys_recommond_pics img").length;
							    	if(n+selected.length>6){
							    		alert('单品不能超过6个');
							    		return false;
							    	}
							    	var goodsIds = $("#sys_recommond_pics input[class='s_r_g']");
							    	var arrsGoodsId= [];//goodsId的数组
							    	$(goodsIds).each(function(idx,item){
							    		arrsGoodsId.push($(this).val());
							    	});
//									  selected是要遍历的集合
//									  index就是索引值
//									  domEle 表示获取遍历每一个dom对
								    	$.each( selected, function(index, domEle){
								    		arrsGoodsId.push(domEle.id);
								    		if(!isRepeat(arrsGoodsId)){//不重复
								    			var div_imgs = "<div class='brick3'><input type='hidden' class='s_r_g_v' value=''/><input type='hidden' class='s_r_g_n_v' value=''/><input type='hidden' class='s_r_c_v' value=''/>"
								    				+"<input type='hidden' class='s_r_p_v' value=''/><input type='hidden' class='s_r_t_v' value=''/><a class='delete2' onclick='delSysRecmdPic(this)' href='#'>×</a><img src='"
								    				+domEle.smallPic	+"' width='140' height='140' ></img><div><input type='hidden' class='s_r_g' value='"+domEle.id
								    				+"' /><input type='hidden' class='s_r_c' value='"+domEle.currentPrice+"' /><input type='hidden' class='s_r_g_n' value='"+domEle.goodsName+"' /><input type='hidden' class='s_r_t' value='"+domEle.goodsType+"' /></div></div>"
								    				$("#sys_recommond_pics").append(div_imgs);
								    			$('#sys_recommond_pics').gridly();
								    		}
								    	});
							    }
					    },
					    cancelVal: '关闭',
					    cancel: true 
					}).zindex();
		  		}
		}

	/** 判断数组中是否有重复项*/
	function isRepeat(arr){
	    var hash = {};
	    for(var i in arr) {
	        if(hash[arr[i]])
	             return true;
	        hash[arr[i]] = true;
	    }
	    return false;
	}
	
	//设置推荐图片
	function setPics(){
		var flag = true;
		//系统推荐排序
		 var $pics_dtl = $("#sys_recommond_pics div[class='brick3']");
		 var left_d = "";
		 var pic_url = "";
		 var goods_id = "";
		 var goods_name = "";
		 var current_price = "";
		 var id = "";
		 $.each( $pics_dtl, function(index, domEle){
				 left_d = (index%6)*160+"px";//
				 var $divs = $("#sys_recommond_pics div[class='brick3']");
				 var n = $divs.length;
				 for(var i=0;i<n;i++){
					 var div = $divs.get(i);
					 if($(div).css("left")==left_d){
						 pic_url =  $(div).find("img").attr("src");
						 goods_id =  $(div).find("input[class='s_r_g']").val();
						 goods_name =  $(div).find("input[class='s_r_g_n']").val();
						 goods_type =  $(div).find("input[class='s_r_t']").val();
						 current_price =  $(div).find("input[class='s_r_c']").val();
						 id =  $(div).find("input[class='s_r_id']").val();
						 break;
					 }
				 }
				 $(domEle).find("input[class='s_r_g_v']").attr("name","tSysRecmdPics["+index+"].recmdGoodsId").val(goods_id);
				 $(domEle).find("input[class='s_r_g_n_v']").attr("name","tSysRecmdPics["+index+"].goodsName").val(goods_name);
				 $(domEle).find("input[class='s_r_t_v']").attr("name","tSysRecmdPics["+index+"].goodsType").val(goods_type);
				 $(domEle).find("input[class='s_r_p_v']").attr("name","tSysRecmdPics["+index+"].picUrl").val(pic_url);
				 $(domEle).find("input[class='s_r_c_v']").attr("name","tSysRecmdPics["+index+"].currentPrice").val(current_price);
				 $(domEle).find("input[class='s_r_id_v']").attr("name","tSysRecmdPics["+index+"].id").val(id);
			});
		 
		 //导购推荐排序
		 var $guide_pics_dtl = $("#guide_recommond_pics div[class='brick4']");
		 var g_left_d = "";
		 var g_id = "";
		 var g_pic_url = "";
		 var g_user_id = "";
		 var g_user_name = "";
		 var g_mobile_phone = "";
		 var g_mobile_phone = "";
		 $.each( $guide_pics_dtl, function(idx, domEle){
			 left_d = (idx%6)*160+"px";//
			 var $divs = $("#guide_recommond_pics div[class='brick4']");
			 var n = $divs.length;
			 for(var i=0;i<n;i++){
				 var div = $divs.get(i);
				 if($(div).css("left")==left_d){
					 g_pic_url =  $(div).find("img").attr("src");
					 g_id =  $(div).find("input[class='g_id']").val();
					 g_user_id =  $(div).find("input[class='u_i']").val();
					 g_user_name =  $(div).find("input[class='u_n']").val();
					 g_mobile_phone =  $(div).find("input[class='u_p']").val();
					 break;
				 }
			 }
			 $(domEle).find("input[class='g_id_v']").attr("name","tGuideRecmdPics["+idx+"].id").val(g_id);
			 $(domEle).find("input[class='p_u_v']").attr("name","tGuideRecmdPics["+idx+"].picUrl").val(g_pic_url);
			 $(domEle).find("input[class='u_i_v']").attr("name","tGuideRecmdPics["+idx+"].userId").val(g_user_id);
			 $(domEle).find("input[class='u_n_v']").attr("name","tGuideRecmdPics["+idx+"].userName").val(g_user_name);
			 $(domEle).find("input[class='u_p_v']").attr("name","tGuideRecmdPics["+idx+"].mobilePhone").val(g_mobile_phone);
		 });
		 return flag;
	}
	
	//删除推荐明细图
	function delSysRecmdPic(obj){
	   $(obj).parent().remove();
	   $('#sys_recommond_pics').css("height","160px");
	} 

	//删除导购推荐主图 
	function delGuidePic(obj){
		$(obj).parent().remove();
		$("#guide_recmd_upLoad").show();
		guide_pic_num--;
		$('#guide_recmd_upLoad').uploadify('settings','queueSizeLimit',6-guide_pic_num);
		$('#guide_recommond_pics').css("height","160px");
	} 
	/**自动完成插件start*/
	//自动完成绑定
	function bindAutoComplete(id,id_n,id_p,id_id) {
		$("#"+id).autocomplete(
				"userController.do?getGuideAutoList",
				{
					max : 5,
					minChars : 1,
					width : 200,
					scrollHeight : 100,
					matchContains : true,
					autoFill : false,
					extraParams : {
						featureClass : "P",
						style : "full",
						maxRows : 10,
						labelField : "realName,mobilePhone",
						valueField : "id",
						searchField : "realName,mobilePhone",
						entityName : "TSUser"
//							trem : getTremValueuser(id)
					},
					parse : function(data) {
						//每次输入的时候初始化值
						$("#"+id_n).val("");
					   	$("#"+id_id).val("");
					   	$("#"+id_p).val("");
						return parse.call(this, data);
					},
					formatItem : function(row, i, max) {
						return formatItem.call(this, row,
								i, max);
					}
				}).result(function(event, row, formatted) {
			callBack.call(this, row,id,id_n,id_p,id_id);
		})
	};

	   function parse(data){
	       	var parsed = [];
		        	$.each(data.rows,function(index,row){
		        		parsed.push({data:row,result:row,value:row.id});
		        	});
	   				return parsed;
	   }
	   /**
	    * 选择后回调 
	    * 
	    * @param {Object} data
	    */
	   function callBack(data,id,id_n,id_p,id_id) {
	   	$("#"+id).val(data.realName);
	   	$("#"+id_n).val(data.realName);
	   	$("#"+id_id).val(data.id);
	   	$("#"+id_p).val(data.mobilePhone);
	   }
	   
	    /**
	     * 每一个选择项显示的信息
	     * 
	     * @param {Object} data
	     */
	   function formatItem(data) {
	   	return data.realName + "-->" + " " + data.mobilePhone;
	   }
	   /**自动完成插件end*/

	   function initGuideRecmdPic(){
		var $divs = $("#guide_recommond_pics div");
		if($divs.length>0){
			$divs.each(function(idx,item){
				var id = $(this).find("input[class='ac_input']").attr("id");
				var id_n = $(this).find("input[class='u_n']").attr("id");
				var id_id = $(this).find("input[class='u_i']").attr("id");
				var id_p = $(this).find("input[class='u_p']").attr("id");
				bindAutoComplete(id,id_n,id_p,id_id); 
	    	});
		}
	}
  </script>