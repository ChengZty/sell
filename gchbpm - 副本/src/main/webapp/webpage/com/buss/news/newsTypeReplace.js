/*
 * 替换code为名称
 * value 所有资讯分类code_retailerId,用逗号连接
 * text 对应所有资讯分类name,用逗号连接
 */
 /*function replaceCode(code,row,index){
	 var name = "";
	 var valArray = value.split(',');
	 var nameArray = text.split(',');
	 if(code.indexOf(",")!=-1){
		 var codeArray = code.split(',');
		 var fullCode = "";
		 for(var i=0; i<codeArray.length; i++){
			 fullCode+=codeArray[i]+"_"+row.shopkeeper+",";
		 }
		 code = fullCode.slice(0,-1);
	 }else{
		 code = code+"_"+row.shopkeeper;
	 }
	 if(code.indexOf(",")!=-1){
		 var fullCodeArray = code.split(',');
		 var fullName = "";
		 for(var i=0; i<fullCodeArray.length; i++){
			 for(var k=0; k<valArray.length; k++){
				 if(fullCodeArray[i]==valArray[k]){
					 fullName+=nameArray[k]+",";
					 break;
				 }
			 }
		 }
		 name = fullName.slice(0,-1);
	 }else{
		 for(var k=0; k<valArray.length; k++){
			 if(code==valArray[k]){
				 name = nameArray[k];
				 break;
			 }
		 }
	 }
	 
	 return name;
 }*/
 
//获取资讯分类的下拉框	 
 function getCodeSelectHtml(){
// 	 var valArray = value.split(',');
// 	 var nameArray = text.split(',');
	 
// 	var html = '<span style="display:-moz-inline-box;display:inline-block;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " >资讯分类：</span><select name="newsTypeRetailerId" width="100" style="width: 104px">';
 	var html = '<span style="display:-moz-inline-box;display:inline-block;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " >话题分类：</span><select name="news_Type" width="100" style="width: 104px">';
 	html+="<option value=''>---请选择---</option>";
 	html+="<option value='3'>热门话题</option>";
 	html+="<option value='4'>品牌&商品</option>";
 	html+="<option value='5'>活动话题</option>";
// 	 for(var k=0; k<valArray.length; k++){
// 		 html+="<option value='"+valArray[k]+"'>"+nameArray[k]+"</option>";
// 	 }
 	 html+="</select></span>";
 	 return html;
 }