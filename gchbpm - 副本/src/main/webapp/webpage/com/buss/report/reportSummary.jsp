<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- context path -->
<t:base type="jquery,easyui"></t:base>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="plug-in/echarts/dist/echarts.js"></script>

    <style type="text/css">
    	.main_div
    	{
    		height:90%;border:1px solid #ccc;width: 90%;display: inline-block;margin: 5%;
    	}
    </style>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
//路径配置
require.config({
    paths: {
        echarts: 'plug-in/echarts/dist'
    }
});
// 使用
require(
    [
        'echarts',
        'echarts/chart/radar', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar',
        'echarts/chart/line',
        'echarts/chart/pie',
        'echarts/chart/map',
        'echarts/chart/funnel'
    ],
   chartInit
);
function chartInit(ec){
	// 总体短信效果--转化率-发送量-成功送达-点击人数-购买人数-漏斗图
	var smsSendConversionChart = smsSendConversionChartFun(ec);
	window.onresize = function(){
		smsSendConversionChart.resize();
   	 }
}
function smsSendConversionChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('sms_conversion'));
	// 过渡---------------------
	myChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	$.ajax({
		type : "POST",
		url : "reportController.do?getSmsReportSummary&reportType=${reportType}",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '短信转化情况'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b}"
				},
				toolbox : {
					show : true,
					feature : {
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				series : [ {
					name : '短信转化情况',
					type : 'funnel',
					left : '10%',
					top : 60,
					// x2: 80,
					bottom : 60,
					width : '70%',
					// height: {totalHeight} - y - y2,
					min : 0,
					max : 100,
					minSize : '0%',
					maxSize : '100%',
					sort : 'descending',
					gap : 2,
					label : {
						normal : {
							show : true,
							position : 'inside'
						},
						emphasis : {
							textStyle : {
								fontSize : 20
							}
						}
					},
					labelLine : {
						normal : {
							length : 10,
							lineStyle : {
								width : 1,
								type : 'solid'
							}
						}
					},
					itemStyle : {
						label : {
							show : true,
							formatter : function(params) {
								return params.value;
							}
						},
						normal : {
							borderColor : '#fff',
							borderWidth : 1
						}
					},
					data : result.obj
				} ]
			};
			// 过渡控制，隐藏loading（读取中）
			myChart.hideLoading();
			// 为echarts对象加载数据
			myChart.setOption(option);
			return myChart;
		}
	});
}
</script>

<t:tabs id="tt" iframe="false">
	<div style="width: 700px;height:560px;margin:0px;">
		<div id="sms_conversion"  class="main_div"></div>
	</div>
</t:tabs>
