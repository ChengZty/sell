<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<script type="text/javascript">
		$(document).ready(function() {
			var chart;
			$.ajax({
				type : "POST",
				url : "reportController.do?getSmsReportById&reportType=${reportType}&smsId=${smsId}",
				success : function(jsondata) {
					data = eval(jsondata);
// 					console.log(data);
					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'containerCol',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : "消息推送"
						},
						xAxis : {
							categories : [ '推送量','送达量', '点击量','购买量' ]
						},
						tooltip : {
							 percentageDecimals : 1,
							 formatter: function() {
								 return '<b>' + this.point.name + '</b>: '+  Highcharts.numberFormat(this.percentage, 1) +'%';
         					}

						},
						exporting:{  
			                filename:'column',  
			                url:'${ctxPath}/reportController.do?export'//
			            },
						plotOptions : {
							column : {
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
			});
		});
</script>
<div id="containerCol" style="width: 80%; height: 80%"></div>


