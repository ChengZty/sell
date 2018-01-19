var leipiEditor = UE.getEditor('content',{
    toolleipi:true,//是否显示，设计器的 toolbars
    //textarea: 'desc.goodsDesc',   
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
    'simpleupload', 'insertimage', 'emotion',
    'map',  'insertframe',  'pagebreak',  'background', '|',
    'horizontal',  'spechars',  '|',
    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', '|',
    'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|','charts', '|',
    'print', 'preview', 'searchreplace', //'help', 'drafts'
    ]],
	wordCount:true,
    maximumWords:5000,
    autoHeightEnabled:false,
//    initialFrameWidth:1000,
    initialFrameHeight:400,
    elementPathEnabled:false
});

function setContent(){
    if(leipiEditor.queryCommandState( 'source' ))
    {
    	leipiEditor.execCommand('source');
    }
            
    if(leipiEditor.hasContents()){
        leipiEditor.sync();
	    $("#goodsDesc").val(leipiEditor.getContent());
	}
}


//预览     
function view(){
	$("#preview-msg-content").html(leipiEditor.getContent());
	$("#content_view").show();
}
     
//关闭预览
function closeDiv(){
	$("#content_view").hide();
}

//校验规格和库存 
function checkSpec(){
	  var store_trs =  $("#add_TgoodsStoreDetail_table tr:visible");
	  var store_th =  $("#TgoodsStoreDetail_table thead tr").eq(0);
	  var nullNum = 0;
	  $(store_trs).each(function(){
		  var $num = $(this).find("td").eq(6).find("input[name$='store']");
		  var num = $.trim($num.val());
		  var $textOne = $(this).find("td").eq(2).find("input[name$='specificationOne']");//规格1不能为空
		  var $textTwo = $(this).find("td").eq(3).find("input[name$='specificationTwo']");//规格2不能为空
		  var $textThree = $(this).find("td").eq(4).find("input[name$='specificationThree']");//规格3不能为空
		  var textOne = $.trim($textOne.val());
		  var textTwo = $.trim($textTwo.val());
		  var textThree = $.trim($textThree.val());
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
			  if(textTwo==""&&textThree!=""){
				  nullNum++;
				  var headTwo = $(store_th).find("td").eq(3).find("input").val();
				  if(headTwo==undefined){
					  headTwo = $(store_th).find("td").eq(3).text();
				  }
				  $.messager.alert('提示信息',"请填写"+headTwo,"",function(){
					  $textTwo.focus();
				  });
				  return false;
			  }
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
	var url = "tBrandShowController.do?findBrandList";
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
						 	brandId = $(sel_tr).find("td[field='brandId'] div").text();
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
//					    		$.messager.alert('错误提示', '请选择品牌');
				    			alert("请选择品牌");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
//						    		$.messager.alert('错误提示', '只能选择一个品牌');
						    		alert("只能选择一个品牌");
						    		return false;
						    }
							 var brandId = $(sel_tr).find("td[field='brandId'] div").text();
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
function setVisibleCatgs(goodsId){
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
//						cnodes += $(node[i].target).attr("node-id")+"-"+i + ',';
						cnodes = $(node[i].target).attr("node-id")+ ',';
						categries +=cnodes;
						pnode = $(root_node).tree('getParent', node[i].target); //获取当前节点的父节点
						while (pnode!=null) {//添加全部父节点
//							pnodes += $(pnode.target).attr("node-id")+"-"+i + ',';
							pnodes = $(pnode.target).attr("node-id")+ ',';
							categries +=  pnodes;
							pnode = $(root_node).tree('getParent', pnode.target); 
						}
					}
					
				}
//				cnodes = cnodes.substring(0, cnodes.length - 1);
//				pnodes = pnodes.substring(0, pnodes.length - 1);
//				var categries = cnodes + "," + pnodes;
				var n = categries.substring(0, categries.length - 1).split(",").length;//选择类目的总的ID个数
				if(n%2!=0){
					alert("请选到对应的二级类目");
					return false;
				}else{
					$("#categries").val(categries);
				}
			},
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
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
			cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
}

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

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

//拖动图片
$('.gridly').gridly({
    base: 60, // px 
    gutter: 20, // px
    columns: 15
  });
$('.gridly').css("height","200px");
//拖动图片
/*
$('.gridly2').gridly({
	base: 20, // px 
	gutter: 10, // px
	columns: 45
});
$('.gridly2').css("height","200px");
*/

//删除主图 
function delPic(obj){
   $(obj).parent().remove();
   	$("#templatePic_main").show();
   	m_pic_num--;
   	$('#templatePic_main').uploadify('settings','queueSizeLimit',5-m_pic_num);
    $('#main_pics').css("height","200px");
} 


//初始化图片的name,按顺序排 (拖动的时候div位置未变，只是left和top值会变，单行的top为0，攒不考虑) 
function initPicsOrder(){
var flag = true;
var pics_small_num = $("#main_pics_small img").length;
if(pics_small_num==0){
	alert("请上传商品小图");
	return false;
}
var main_pics_num = $("#main_pics img").length;
if(main_pics_num==0){
	alert("请上传商品主图");
	return false;
}
	//主图排序
 var $pics_main = $("#main_pics input[type='hidden']");
 var order = "";
 var left_ = "";
 var url = "";
 $.each( $pics_main, function(index, domEle){
	 if(0==index){
		 order = "One";
	 }else if(1==index){
		 order = "Two";
	 }else if(2==index){
		 order = "Three";
	 }else if(3==index){
		 order = "Four";
	 }else{
		 order = "Five";
	 }
	 left_ = index*240+"px";
	 var $divs = $("#main_pics div");
	 $.each( $divs, function(i, n){
		 if($(this).css("left")==left_){
			 url =  $(this).find("img").attr("src");
		 }
	 });
	 $(this).attr("name","pic"+order);
	 $(this).attr("value",url);
	});
 
 setContent();
 
 return flag;
}

function fillCurrentPrice(price){
	$("#currentPrice").val(price);
}

