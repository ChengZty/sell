$(function () {
	//上传小图
	/** 用七牛插件上传  start */
// 	    var P = new QiniuJsSDK();
// 	    var uploaderPic = P.uploader({
// 	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
// 	        browse_button: 'templatePic_main_small',         // 上传选择的点选按钮，必需
// 	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
// 	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
// 	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
// //	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
// 	         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
// //	         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
// //	            // do something
// //	            return getUptoken();
// //	         },
// 	        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
// 	        // downtoken_url: '/downtoken',
// 	        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
// 	        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
// 	        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
// 	        domain: domain,     // bucket域名，下载资源时用到，必需
// 	        container: 'container_pic',             // 上传区域DOM ID，默认是browser_button的父元素
// 	        // max_file_size: '100kb',             // 最大文件体积限制
// 	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
// 	        max_retries: 3,                     // 上传失败最大重试次数
// 	        dragdrop: true,                     // 开启可拖曳上传
// 	        drop_element: 'container_pic',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
// 	        chunk_size: '4mb',                  // 分块上传时，每块的体积
// 	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
// 	        //x_vars : {
// 	        //    查看自定义变量
// 	        //    'time' : function(up,file) {
// 	        //        var time = (new Date()).getTime();
// 	                  // do something with 'time'
// 	        //        return time;
// 	        //    },
// 	        //    'size' : function(up,file) {
// 	        //        var size = file.size;
// 	                  // do something with 'size'
// 	        //        return size;
// 	        //    }
// 	        //},
// 	        resize: {
// 				quality: 90,  //需要指定这个参数
// 				compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
// 			},
// 	        filters : {
// 	            // max_file_size : '100kb',
// 	            prevent_duplicates: false,  // 是否允许选取prevent_duplicates: false的文件,为true时表示不允许,为false时表示允许,默认为false
// 	            // Specify what files to browse for
// 	            mime_types: [
// 	                {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
// 	            ]
// 	        },
// 	        init: {
// 	            'FilesAdded': function(up, files) {
// 	                plupload.each(files, function(file) {
// 	                    // 文件添加进队列后，处理相关的事情
// 	                });
// 	            },
// 	            'BeforeUpload': function(up, file) {
// 	                   // 每个文件上传前，处理相关的事情
// 	            },
// 	            'UploadProgress': function(up, file) {
// 	                   // 每个文件上传时，处理相关的事情
// 	            },
// 	            'FileUploaded': function(up, file, info) {
// 	                   // 每个文件上传成功后，处理相关的事情
// 	                   // 其中info是文件上传成功后，服务端返回的json，形式如：
// 	                   // {
// 	                   //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
// 	                   //    "key": "gogopher.jpg"
// 	                   //  }
// 	                   // 查看简单反馈
// 	                   var domain = up.getOption('domain');
// 	                   var res = $.parseJSON(info);
// 	                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
// 	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img></div>"
// 		   				$("#main_pics_small").html(div_imgs);
// 		   				$("#smallPic").val(sourceLink);
// 	            },
// 	            'Error': function(up, err, errTip) {
// 	                   //上传出错时，处理相关的事情
// 	            	alert(errTip);
// 	            },
// 	            'UploadComplete': function() {
// 	                   //队列文件处理完毕后，处理相关的事情
// 	            },
// 	            'Key': function(up, file) {
// 	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
// 					// 该配置必须要在unique_names: false，save_key: false时才生效
// 					//如果是png，则将png转成jpg上传，此处修改后缀名
// 					var tempFileName = file.name;
// 					if(tempFileName.indexOf('.png') > -1){
// 						tempFileName = tempFileName.replace('.png', '.jpg');
// 					}
//  	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
//  	                var n = file.name.lastIndexOf(".");
//  	            	name += file.name.substring(n);
// 	                var time = new Date().Format("yyyyMMdd");
// 	                var key = directory + "smallPic/"+time+"/"+name;
// 	                return key
// 	            }
// 	        }
// 		});
		/** 用七牛插件上传  end */

		//参数
		var paramsJson = {
			cropperWidth: 100, //裁剪宽度
			cropperHeight: 100, //裁剪高度
			moduleName: 'smallPic',  //模块名称
			dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
				var div_imgs = "<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img></div>"
				$("#main_pics_small").html(div_imgs);
				$("#smallPic").val(sourceLink);
			}
		}
		var btnSelectFileVo = new BtnSelectFileVo('#templatePic_main_small', paramsJson);
		
})

//获取随机ID
function getRandomStr(){
	  var str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	  var n = 8, s = "";
	  for(var i = 0; i < n; i++){
	      var rand = Math.floor(Math.random() * str.length);
	      s += str.charAt(rand);
	  }
	  return s;
}


//校验规格和库存  20170609改为不校验库存
function checkSpec(){
	  var store_trs =  $("#add_TgoodsStoreDetail_table tr:visible");//录入规格的明细tr
	  var store_th =  $("#TgoodsStoreDetail_table thead tr").eq(0);
	  var n = $(store_trs).length;//规格明细条数
	  var hasStoreNum = 0;//录入库存的规格数量
	  var nullNum = 0;
	  $(store_trs).each(function(){
		  var $num = $(this).find("td").eq(5).find("input[name$='store']");//库存
		  var num = $.trim($num.val());
		  var $textOne = $(this).find("td").eq(2).find("input[name$='specificationOne']");//规格1不能为空
		  var $textTwo = $(this).find("td").eq(3).find("input[name$='specificationTwo']");//规格2不能为空
//		  var $textThree = $(this).find("td").eq(4).find("input[name$='specificationThree']");//规格3不能为空
		  var textOne = $.trim($textOne.val());
		  var textTwo = $.trim($textTwo.val());
//		  var textThree = $.trim($textThree.val());
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
			  /*if(textTwo==""){
				  nullNum++;
				  var headTwo = $(store_th).find("td").eq(3).find("input").val();
				  if(headTwo==undefined){
					  headTwo = $(store_th).find("td").eq(3).text();
				  }
				  $.messager.alert('提示信息',"请填写"+headTwo,"",function(){
					  $textTwo.focus();
				  });
				  return false;
			  }*/
			  if(num!=""&&num!="0"){//规格如果录入了库存则必须规格都要录入
				  hasStoreNum++;
			  }
		  }
	  });
	  if($(store_trs).length==0){
		  $.messager.alert('提示信息',"请添加规格明细");
		  nullNum++;
	  }
	  if(hasStoreNum>0&&hasStoreNum!=n){//只填写了部分库存
		  $.messager.alert('提示信息',"请统一填写库存明细");
		  nullNum++;
	  }
	  return nullNum;
}


//选择运费（包邮，定额）
function changeFareType(type){
	if("0"==type){//包邮
		$("#fare_td_2 input").attr("disabled",true);
		$("#fare_2").empty();
		$("#fare_3").empty();
		$("#fare").val("0");
		$("#fare_1").hide();
		$("#fare_td_2 input[type='radio'][name='farePreferentialType'][value='0']").attr("checked",true);
	}else if("1"==type){//定额
		$("#fare_td_2 input").attr("disabled",false);
		$("#fare_1").show();
		$("#fare").focus();
	}
}
//选择运费优惠（无，满免，满减）
function changeFarereferentialType(type){
	if("0"==type){//无
		$("#fare_2").empty();
		$("#fare_3").empty();
	}else if("1"==type){//满免
		$("#fare_2").html('满<input type="text" name="goodsFarePreferential" style="width: 30px" onkeypress="Public.input.numberInput()"/>元包邮');
		$("#fare_2 input").focus();
		$("#fare_3").empty();
	}else if("2"==type){//递减
		var fare = $("#fare").val();
		$("#fare_3").html('第一件<input type="text" id="fare_pft" style="width: 30px" value="'+fare+'" readonly="readonly"/>元，第二件开始每件<input type="text" name="goodsFarePreferential" style="width: 30px" onkeypress="Public.input.numberInput()"/>元');
		$("#fare_3 input").eq(1).focus();
		$("#fare_2").empty();
	}
}

//选择品牌
function findBrands(){
//	var url = "tBrandShowController.do?findBrandList&isSelfBrand=1";
	var url = "baseBrandController.do?list&isForChoose=1";//查询列表
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择 品牌",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
//			    	iframe = this.iframe.contentWindow;
//			    	var selected = iframe.getSelectRows();
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
//				    		$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择品牌");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
//					    		$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个品牌");
					    		return false;
					    }
						 var brandId="";
//					 	if("1"==retailer_type){//零售商
						 	brandId = $(sel_tr).find("td[field='id'] div").text();
//						}else if("2"==retailer_type){//云商
//							brandId = $(sel_tr).find("td[field='id'] div").text();
//						}
						 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
						 var brandCode = $(sel_tr).find("td[field='brandCode'] div").text();
						 $("#brandId").val(brandId);
						 $("#brandCode").val(brandCode);
						 $("#brandName").val(brandName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择 品牌",
				width:700,
				height: 500,
				zIndex:1999,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择品牌");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
						    		alert("只能选择一个品牌");
						    		return false;
						    }
							 var brandId="";
							 	brandId = $(sel_tr).find("td[field='id'] div").text();
							 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
							 var brandCode = $(sel_tr).find("td[field='brandCode'] div").text();
							 $("#brandId").val(brandId);
							 $("#brandCode").val(brandCode);
							 $("#brandName").val(brandName);
						 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
			
		}
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

//设置可见类目
/*function setVisibleCatgs(goodsId){
	var url = "tGoodsController.do?categryList&goodsId="+goodsId;
	$.dialog.setting.zIndex = 300;
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: "url:"+url,
			lock : true,
			title:"选择可见类目<font color='red' size='2'> （选到二级类目）</font>",
			width:700,
			height: 600,
			cache:false,
			ok: function(){
				var iframe_doc = this.iframe.contentWindow.document;
				var root_node = $(iframe_doc).find('#node_root');
				var node = $(root_node).tree('getChecked');
				var cnodes = '';
				var pnodes = '';
				var categries = '';
				var pnode = null; //保存上一步所选父节点
				for ( var i = 0; i < node.length; i++) {
					if ($(root_node).tree('isLeaf', node[i].target)) {
						cnodes = $(node[i].target).attr("node-id")+ ',';
						categries +=cnodes;
						pnode = $(root_node).tree('getParent', node[i].target); //获取当前节点的父节点
						while (pnode!=null) {//添加全部父节点
							pnodes = $(pnode.target).attr("node-id")+ ',';
							categries +=  pnodes;
							pnode = $(root_node).tree('getParent', pnode.target); 
						}
					}
					
				}
				var n = categries.substring(0, categries.length - 1).split(",").length;//选择类目的总的ID个数
				if(n%2!=0){
					alert("请选到对应的二级类目");
					return false;
				}else{
					$("#categries").val(categries);
				}
			},
			cancelVal: '关闭',
			cancel: true 为true等价于function(){}
		}).zindex();
	} else{
		$.dialog({
			content: "url:"+url,
			lock : true,
			title:"选择可见类目<font color='red' size='2'> （选到二级类目）</font>",
			width:700,
			height: 600,
			parent:windowapi,
			cache:false,
			ok: function(){
				var iframe_doc = this.iframe.contentWindow.document;
				var root_node = $(iframe_doc).find('#node_root');
				var node = $(root_node).tree('getChecked');
				var cnodes = '';
				var pnodes = '';
				var categries = '';
				var pnode = null; //保存上一步所选父节点
				for ( var i = 0; i < node.length; i++) {
					if ($(root_node).tree('isLeaf', node[i].target)) {
						cnodes = $(node[i].target).attr("node-id")+ ',';
						categries +=cnodes;
						pnode = $(root_node).tree('getParent', node[i].target); //获取当前节点的父节点
						while (pnode!=null) {//添加全部父节点
							pnodes = $(pnode.target).attr("node-id")+ ',';
							categries +=  pnodes;
							pnode = $(root_node).tree('getParent', pnode.target); 
						}
					}
					
				}
				var n = categries.substring(0, categries.length - 1).split(",").length;//选择类目的总的ID个数
				if(n%2!=0){
					alert("请选到对应的二级类目");
					return false;
				}else{
					$("#categries").val(categries);
				}
			},
			cancelVal: '关闭',
			cancel: true 为true等价于function(){}
		}).zindex();
	}
}
*/
//选择类目  select
function chooseCatgs(goodsId){
	var url = "tGoodsController.do?categryList&goodsId="+goodsId;
	$.dialog.setting.zIndex = 300;
		$.dialog({
			content: "url:"+url,
			lock : true,
			title:"选择类目<font color='red' size='2'> （选到二级类目）</font>",
			width:700,
			height: 600,
			cache:false,
			ok: function(){
				var iframe_doc = this.iframe.contentWindow.document;
				var root_node = $(iframe_doc).find('#node_root');
				var node = $(root_node).tree('getSelected');
				var cnodes = '';
				var pnodes = '';
				var categries = ''; //节点id，依次是第三级 第二级 第一级
				var pnode = null; //保存上一步所选父节点
				var nodeArr = [node];  //节点数组，依次是 第一级 第二级 第三级
				
				if($(root_node).tree('isLeaf', node.target)){
					cnodes = $(node.target).attr('node-id') + ',';
					categries +=cnodes;
					pnode = $(root_node).tree('getParent', node.target); //获取当前节点的父节点
					while(pnode!=null){//添加全部父节点
						nodeArr.unshift(pnode);
						pnodes = $(pnode.target).attr("node-id")+ ',';
						categries +=  pnodes;
						pnode = $(root_node).tree('getParent', pnode.target); 
					}
				}
				var n = categries.substring(0, categries.length - 1).split(",").length;//选择类目的总的ID个数
				if(n%2!=0){
					alert("请选到对应的二级类目");
					return false;
				}else{
					var categoryStr = '';
					var nodeId = '';
					$.each(nodeArr, function(index, nodeObj){
						nodeId = $(nodeObj.target).attr('node-id');
						categoryStr += nodeObj.target.textContent + ' ';
						
						/*if(index === 0){ //一级目录
							$("#topCategoryId").val(nodeId);
						}else if(index === 1){ //二级目录
							$("#subCategoryId").val(nodeId);
						}else if(index === 2){ //三级目录
							$("#thridCategoryId").val(nodeId);
						}*/
						if(index === 0){ //二级目录
							$("#subCategoryId").val(nodeId);
						}else if(index === 1){ //三级目录
							$("#thridCategoryId").val(nodeId);
						}
					});
					$('.goods-category').text(categoryStr);
					
					$("#categries").val(categries);
					
				}
			},
			cancelVal: '关闭',
			cancel: true //为true等价于function(){}
		}).zindex();
}

//计算库存
function calStore(){
	var trs = $("#add_TgoodsStoreDetail_table tr:visible");
	var storeAmount =0;
	var hasStore = false;//是否录入库存
	$(trs).each(function(i,obj){
		var store = $(this).find("input[name$='.store']").val().trim();
		if(store!=""){
			hasStore= true;
			storeAmount = store-0+storeAmount;
		}
	})
	if(hasStore){
		$("#goodsStock").val(storeAmount);
	}else{
		$("#goodsStock").val("");
	}
}

//更新这条记录标识的为已修改
function changeFlag(obj){
	$(obj).parent().parent().find("input[name$='.changed']").val("Y");
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

/**计算最低价
 * type 1:根据原价和折扣计算最低价，2：通过原价和最低价计算折扣 
 */
function fillLowestPrice(type){
	var originalPrice = $("#originalPrice").val();
	var discount = $("#lowestPriceDiscount").val()-0;
	var lowestPrice = $("#lowestPrice").val();
	if(type==1){
		if(originalPrice&&discount){
			if(discount>100){
				discount=100;
				$("#lowestPriceDiscount").val(discount)
			}
			$("#lowestPrice").val((originalPrice*discount/100).toFixed(2));
		}else{
			$("#lowestPrice").val(0);
		}
	}else if(type==2){
		if(originalPrice&&lowestPrice){
			$("#lowestPriceDiscount").val((lowestPrice*100/originalPrice).toFixed(0));
		}else{
			$("#lowestPriceDiscount").val(0);
		}
	}
}

//删除图片
function delPic(obj){
	   $(obj).parent().remove();
}
//删除小图
function delSmallPic(obj){
	$(obj).parent().remove();
	$("#smallPic").val("");
}

function goAdd(data) {
	if(data.success){
		document.location="tNewGoodsController.do?goAdd&addOK=1";
	}else{
		$.messager.alert('错误提示', data.msg);
	}
}

function goToChoose(){
	  window.location="tNewGoodsController.do?goStep1";
	  return false;
}

//修改分类
function goToChange(goodsId){
	window.location="tNewGoodsController.do?goStep1&id="+goodsId;
	return false;
}

//发布
function sub(){
	$("#publishStatus").val("1");
	doSub();
}


//存草稿
function save(){
	$("#publishStatus").val("2");
	doSub(); 
}

//提交
function doSub(){
	var thridCategoryId = $("#thridCategoryId").val();
	if(thridCategoryId==""){
		tip("请选择商品类目");
		return;
	}
	var goodsStatus = $("#goodsStatus").val();
	if(goodsStatus=="4"){//上架中的
		$("#formobj").submit();
	}else{
		var nullNum = checkSpec();
		if(nullNum==0){
			calStore();//计算库存并赋值
			$("#formobj").submit();
		}
	}
}

//待发布
/*function preSub(){
	var nullNum = checkSpec();
	if(nullNum==0){
		$("#publishStatus").val("3");
		$("#formobj").submit();
	}
}
*/


/**
 * 添加商品话术
 */
function addWords(obj){
	var url = "templateWordsGoodsController.do?goodsWordsForGoods";
	$.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择话术",
				width:800,
				height: 560,
				cache:false,
			    ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
			    	 var f = getGoodsWords(iframe_doc,obj);
				     return f;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择话术",
				width:800,
				height: 560,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
			    	 var f = getGoodsWords(iframe_doc,obj);
				     return f;
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
		}
}

//选择商品话术
function getGoodsWords(iframe_doc,obj){
	var flag = false;
	var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
	if ($(sel_tr).length ==0 ){
		alert("请选择话术");
		return flag;
	}else if ($(sel_tr).length > 1 ){
		alert("只能选择一条话术");
		return flag;
	}else{
		$(sel_tr).each(function(){
			var content = $(this).find("td[field='content'] div").text();
			$(obj).parent().prev().find("input").val(content);
		})
		flag = true;
		$(obj).parent().parent().find("input[name$='changed']").val("Y");
	}
	return flag;
}

/**初始化显示正面图和吊牌图*/
function initPicTextShow(){
	$("#container_1_div div.pic_div").each(function(idx){
		$(this).find("div.bgd_txt").remove();
		if(idx==0){
			$(this).prepend("<div class='bgd_txt'>正面图</div>");
		}
		if(idx==4){
			$(this).prepend("<div class='bgd_txt'>吊牌图</div>");
		}
	});
}