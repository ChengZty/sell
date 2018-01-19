//通过pid获取下级分类
function getSubList(parentId,level){
	if(parentId != ""){
		if(level==1){
			if(parentId=="101"){ //商品话术
				//$(".w_t").hide();
				$(".w_t select option[value='2']").remove();
				$("#trade_tr").css("display","none");
			}else{
				//$(".w_t").show();
				$(".w_t select option[value='2']").remove();
				$(".w_t select").append('<option value="2">图片话术</option>');
				$("#trade_tr").css("display","");
			}
			$("#subTypeId").empty().append("<option value=''>---所有---</option>");
			$("#thridTypeId").empty().append("<option value=''>---所有---</option>");
		}else if(level==2){
			$("#thridTypeId").empty().append("<option value=''>---所有---</option>");
		}
		$.ajax({
		    type: 'POST',
		    url : 'tTemplateTypeController.do?getSubTypeList',
		    dataType: 'json',
		    data : {'pid' :parentId},
		    success : function(data){
		    	/*data = JSON.parse(data);*/
				if(data.success == true){
					if(data.msg == "2"){
				    	var optionsStr = "<option value=''>---所有---</option>";
				    	var temStr="";
				    	var len = data.obj.length;
				    	for(var i=0;i<len;i++){
				    		optionsStr += "<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>";
				    	}
				    	$("#subTypeId").html(optionsStr);
				    	$("#tb tbody tr.t").remove();
						$("#type").val("");
					}else if(data.msg == "3"){
				    	var optionsStr = "<option value=''>---所有---</option>";
				    	var temStr="";
				    	var len = data.obj.length;
				    	for(var i=0;i<len;i++){
				    		optionsStr += "<option value='"+data.obj[i].id+"'>"+data.obj[i].name+"</option>";
				    	}
				    	$("#thridTypeId").html(optionsStr);
				    	$("#tb tbody tr.t").remove();
						$("#type").val("");
					}else{
				    	$("#thridTypeId").html("<option value=''>---所有---</option>");
				    	$("#tb tbody tr.t").remove();
						$("#type").val("");
					}
				}else{
			    	$("#thridTypeId").html("<option value=''>---所有---</option>");
			    	$("#tb tbody tr.t").remove();
					$("#type").val("");
				}
		    }
		});
	}
}

/**显示文字话术或者图片话术下拉框*/
function changeTypeDisplay(subTypeId){
	if(subTypeId!=""){//默认显示文字话术
		$("#tb tbody tr.t").remove();
		var trStr = $("#div_tb tr:eq(1)").clone();
		$("#tb tbody").append(trStr);
	}else{
		$("#tb tbody tr.t").remove();
	}
}
/**显示内容，文字话术或者图片话术*/
function changeCotentDisplay(type){
	$("#tb tbody tr.t").remove();
	if(type=="2"){//图片
		var trStr = $("#div_tb tr:eq(2)").clone();
		$(trStr).find("a").attr("id","pickfiles_1");
		$(trStr).find("div#container_").attr("id","container_1");
		$(trStr).find("div#container_div_").attr("id","container_div_1");
		$("#tb tbody").append(trStr);
		//初始化图片上传七牛
		initPicsUpload();
		//初始化拖拽对象
		initSortablePics();
	}else{//文字
		//判断是否是商品话术和是否是平台录入
		if($("#topTypeId").val()==="101" && (null==retailerId || ""==retailerId || undefined ==retailerId)){
			var trStr0 = $("#div_tb tr:eq(0)").clone();
			$("#tb tbody").append(trStr0)
			$(".th-origin-clone").attr("id", "input-btn");
			changeColor();
		    sayHi();
		    addEvaluation();
		}
		var trStr = $("#div_tb tr:eq(1)").clone();
		$("#tb tbody").append(trStr);
	}
	$("#type").val(type);
}

/**初始化拖拽对象*/
function initSortablePics(){
	var bar = document.getElementById("container_div_1");
	Sortable.create(bar, { group: "omega" });
}

/**初始化图片上传七牛*/
function initPicsUpload(){
	var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//            return uptoken;
//         },
        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
        // downtoken_url: '/downtoken',
        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
        domain: domain,     // bucket域名，下载资源时用到，必需
        container: 'container_1',             // 上传区域DOM ID，默认是browser_button的父元素
        // max_file_size: '1m',             // 最大文件体积限制
        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
        max_retries: 3,                     // 上传失败最大重试次数
        dragdrop: true,                     // 开启可拖曳上传
        drop_element: 'container_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
        chunk_size: '4mb',                  // 分块上传时，每块的体积
		auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
		resize: {
			quality: 90,  //需要指定这个参数
			compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
		},
        filters : {
            // max_file_size : '1m',
            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
            // Specify what files to browse for
            mime_types: [
                {title : "Image files", extensions : "jpg,png,jpeg,bmp,gif"} // 限定jpg,jpeg,png等后缀上传
            ]
        },
        init: {
            'FilesAdded': function(up, files) {
                plupload.each(files, function(file) {
                    // 文件添加进队列后，处理相关的事情
                });
            },
            'BeforeUpload': function(up, file) {
                   // 每个文件上传前，处理相关的事情
            },
            'UploadProgress': function(up, file) {
                   // 每个文件上传时，处理相关的事情
            },
            'FileUploaded': function(up, file, info) {
                   // 每个文件上传成功后，处理相关的事情
                   // 其中info是文件上传成功后，服务端返回的json，形式如：
                   // {
                   //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
                   //    "key": "gogopher.jpg"
                   //  }
                   // 查看简单反馈
                   var domain = up.getOption('domain');
                   var res = $.parseJSON(info);
                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
                   var div_imgs = "<div class='brick' ><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img>"
                   +"<input type='hidden' name='content' value='"+sourceLink+"' class='picUrl'><input type='hidden' class='id'/><input type='hidden' class='custWordsId'/></div>"
//  					$("#container_div_1").append(div_imgs);
                   $("#container_div_1").html(div_imgs);
            },
            'Error': function(up, err, errTip) {
                   //上传出错时，处理相关的事情
            	alert(errTip);
            },
            'UploadComplete': function() {
                   //队列文件处理完毕后，处理相关的事情
            },
            'Key': function(up, file) {
                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
                // 该配置必须要在unique_names: false，save_key: false时才生效
            	var name = (new Date()).Format("yyyyMMddhhmmss")+getRandomStr();
//            	if(file.type=="image/gif"){
//					name +=".gif";
//                }
            	var n = file.name.lastIndexOf(".");
            	name += file.name.substring(n);
                var time = new Date().Format("yyyyMMdd");
                var key = directory+"wordsPics/"+time+"/"+name;
                return key;
            }
        }
    });
}

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

//删除主图 
function delPic(obj){
   $(obj).parent().remove();
} 

//判断并初始化图片下标
function checkAndInitPics(){
	var type = $("#type").val();
	if(type=="2"){//图片话术
//		var idx = 0;
		var container_div = $("#container_div_1").find("div");
		if($(container_div).length>0){
//			$(container_div).each(function(){
//				$(this).find("input[class='id']").attr("name","picList["+idx+"].id");
//				$(this).find("input[class='picUrl']").attr("name","picList["+idx+"].picUrl");
//				$(this).find("input[class='custWordsId']").attr("name","picList["+idx+"].custWordsId");
//				idx++;
//			})
		}else{
			alert("请上传图片");
			return false;
		}
	}
	return true;
}

//判断是否显示图片
function checkPicShow(value,row,index){
	 var type = row.type;//1:文字，2：图片
	 if("2"==type){
		 if(value!=""){
			 return '<img width="70" height="70" border="0" src="'+value+'">';
		 }else{
			 return '';
		 }
	 }else{
		 return value;
	 }
}

function randomColor() {	//16进制方式表示颜色0-F	
	var arrHex = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"];	
	var strHex = "#";	
	var index;	
	for(var i = 0; i < 6; i++) {	//取得0-15之间的随机整数	
		index = Math.round(Math.random() * 15);		
		strHex += arrHex[index];
	}	
	return strHex;
}


function changeColor(){
	var links = $("#tag_div a");
	for(var i=0;i<links.length;i++){
		var linkClass = $(links[i]).attr("class");
		if(linkClass != 'easyui-linkbutton l-btn')
		{
			var bgColor = randomColor();//改变背景色的代码，根据修去修改。
			links[i].style.backgroundColor = bgColor;	
		}
	}
}
function sayHi(){
	var scolor,links = document.querySelector("#tag_div").querySelectorAll("a");
	for(var i=0;i<links.length;i++){
		links[i].onmouseover = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				scolor = this.style.backgroundColor;
				this.style.color = scolor;
				this.style.borderColor = scolor;
				this.style.backgroundColor = "white";
			}
		}
		links[i].onmouseout = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				this.style.color = "white";
				this.style.borderColor = "";
				this.style.backgroundColor = scolor;
			}	
		}
		links[i].ondblclick = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				var tagVal = $("#tags").val();
				var arr = new Array();
				var newArr = new Array();
				arr = tagVal.split(",");
				var flag = false;
				var count = 0;
				for (i=0;i<arr.length ;i++ )   
				{   
					if(this.childNodes[0].nodeValue != arr[i])
					{
						newArr[count] = arr[i];
						count++;
					} 
				}   
				tagVal = newArr.join(",");
				$("#tags").val(tagVal);
				$(this).parent().remove();
			}
		}
	}
}
function addEvaluation(){
	var txt = document.getElementById("input-txt");
	var btn = document.getElementById("input-btn");
	var divs = document.getElementById("tag_div");
	if(!txt) return false;
	if(!btn) return false;
	var texts,links,spans;
	btn.onclick = function(){
		if(txt.value == "") {
			$.messager.alert("提示","请输入一个标签");
			return false
		};
		var tagVal = $("#tags").val();
		var arr = new Array();  
		arr = tagVal.split(",");
		var flag = false;
		for (i=0;i<arr.length ;i++ )   
		{   
			if(txt.value == arr[i])
			{
				flag =  true;
				break;
			} 
		}   
		
		if(flag){
			$.messager.alert("提示","已经存在标签"+txt.value);
			return false;
		}
		
		$("#tags").val(tagVal+txt.value+",");
		texts = document.createTextNode(txt.value);
		links = document.createElement("a");
		spans = document.createElement("span");
		links.appendChild(texts);
		links.style.backgroundColor = randomColor();
		spans.appendChild(links);
		divs.appendChild(spans);
		sayHi();
		txt.value="";
	}
}

