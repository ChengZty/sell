<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>g+后台管理系统</title>
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
    <style type="text/css">
    #alert li{
    	font-size: 15px;
    	padding: 3px 10px;
    }
    #alert li a{
    	text-decoration: none;
    }
    #alert li span{
    	color:red;
    }
    
    
    table tr td input{
    	width:100%;
    }
    
    </style>
</head>
<body class="gray-bg">
<div id="alert">
	<ul>
		<li><a id="href_1" >发货提醒(<span id="fahuoCount">0</span>)</a></li>
		<li><a id="href_2" >退款提醒(<span id="refundCount">0</span>)</a></li>
		<!-- <li>
			<input  type="button"  value="清理首页缓存"  onclick="clearIndexCache()">
		</li> -->
	</ul>
	
	<!-- 判断是否是零售商 -->
	<c:if test="${userType == '02'}">
		<table  style="margin-left:20px; width:400px; border-collapse:collapse; "  border="1" cellpadding="0" cellspacing="0">
			<tr style="text-align:center;">
				<td>系统功能缓存</td>
				<td>清除</td>
			</tr>
<!-- 			<tr height="12px"> -->
<!-- 				<td><label>APP首页</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('app')" ></td> -->
<!-- 			</tr> -->
			<tr>
				<td><label>顾客信息</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('user')"></td>
			</tr>
			<tr>
				<td><label>所在区域</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('addr')"></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td><label>资讯分类</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('newstype')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>商品列表</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('goodslist')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>场景信息</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('SceneList')"></td> -->
<!-- 			</tr> -->
			<tr>
				<td><label>人物分类</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('Category')"></td>
			</tr>
			<tr>
				<td><label>平台专家证书</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('SpeList')"></td>
			</tr>
			<tr>
				<td><label>优惠券编码自增长</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('ticket')"></td>
			</tr>
			<tr>
				<td><label>单品与组合商品详情</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('GoodsDetail')"></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td><label>商品分类</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('ListGoodsByType')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>活动商品</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('active')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>品牌商品</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('brandgoods')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>资讯分类</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('AllNewType')"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>品牌类目</label></td> -->
<!-- 				<td><input type="button" value="清除缓存" onclick="clearIndexCache('getAllBaseBrand')"></td> -->
<!-- 			</tr> -->
			<tr>
				<td><label>根据导购Id获取导购信息</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('GuideById')"></td>
			</tr>
			<tr>
				<td><label>顾客更换绑定导购列表</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('ListGate')"></td>
			</tr>
			<tr>
				<td><label>顾客端 首页查看导购在线空间</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('OnlineSpace')"></td>
			</tr>
			<tr>
				<td><label>分页显示资讯分类的所有资讯</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('AllNews')"></td>
			</tr>
			<tr>
				<td><label>获取人物列表（即专家或达人）</label></td>
				<td><input type="button" value="清除缓存" onclick="clearIndexCache('AllListGate')"></td>
			</tr>
			
		</table>
	</c:if>

	
</div>



<script type="text/javascript">
$(function(){
	getAndFillStore();
	var a_ = $(window.top.document).find(".nav-list li a");
	$(a_).each(function(idx,dom){
		var title = $(this).attr("title");
		var href = $(this).attr("href");
		if(title=="订单列表"){
			href = href.replace("addTabs","parent.window.addTabs");
			$("#href_1").attr("href",href).attr("title",title);
		}else if(title=="退款列表"){
			href = href.replace("addTabs","parent.window.addTabs");
			$("#href_2").attr("href",href).attr("title",title);
		}
	})
})
//待发货明细数量，待退款数量
function getAndFillStore(){
	var url = "tRefundReturnController.do?getNoticeCount";
  	$.ajax({
  		async : false,
  		cache : false,
  		type : 'POST',
  		url : url,// 请求的action路径
  		error : function() {// 请求失败处理函数
  		
  		},
  		success : function(data) {
  			var d = $.parseJSON(data);
  			if (d.success) {
  				var fahuoCount = d.obj.fahuoCount;
  				var refundCount = d.obj.refundCount;
  				$("#fahuoCount").text(fahuoCount);
  				$("#refundCount").text(refundCount);
  			}
  		}
  	});
}

function clearIndexCache(cacheType){
	var url = "systemController.do?clearIndexCache";
  	$.ajax({
  		async : false,
  		cache : false,
  		data:{'cacheType':cacheType},
  		type : 'POST',
  		url : url,// 请求的action路径
  		error : function() {// 请求失败处理函数
  		
  		},
  		success : function(data) {
  			var d = $.parseJSON(data);
  			if (d.success) {
  				alert(d.msg);
  			}
  		}
  	});
}

</script>

</body>

</html>
