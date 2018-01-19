<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<script type="text/javascript">
		var chart;
		$(document).ready(function() {
			$.ajax({
				type : "POST",
// 				async: true,
				url : "reportController.do?getSmsReportById&reportType=${reportType}&smsId=${smsId}",
				success : function(jsondata) {
					data = eval(jsondata);
					window.setTimeout(function(){ fillChart(data);},200); //初次渲染会慢，导致图形不出来
				}
			});
		});
function fillChart(data){
	console.log(data);
	chart = new Highcharts.Chart({
		chart : {
			renderTo : 'containerline',
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : "消息推送"
		},
		xAxis : {
			categories : [ '推送量','送达量',  '点击量','购买量' ]
		},
		tooltip : {
			shadow: false,
			percentageDecimals : 1,
			formatter: function() {
				return  '<b>'+this.point.name + '</b>:'+  Highcharts.numberFormat(this.percentage, 1) +'%';
				}

		},
			exporting:{  
            filename:'pie',  
             url:'${ctxPath}/reportController.do?export'  
        },  
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				showInLegend : true,
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					formatter : function() {
						return '<b>' + this.point.name + '</b>: '+  Highcharts.numberFormat(this.percentage, 1) +'%';
					}
				}
			}
		},
		series : data
	});
}		
</script>
<div id="containerline" style="width: 80%; height: 80%"></div>

