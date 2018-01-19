/*-------------------------------------------------------------------------------------------------顾客资料开始----------------------------------------------------------------------------------------*/
/**
 * 顾客资料完整度
 * 
 * @param ec
 * @returns
 */
function integrityChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var integrityChart = ec.init(document.getElementById('integrity_div'),
			'macarons');
	// 过渡---------------------
	integrityChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	integrityChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getIntegrityReport",
		dataType : "json",
		success : function(result) {

			option = {
				title : {
					text : '顾客分析',
					subtext : '顾客资料完整度'
				},

				tooltip : {
					trigger : 'axis'
				},
				legend : {
					orient : 'vertical',
					x : 'right',
					y : 'bottom',
					// backgroundColor:"#559EB1",
					// borderColor:"#559EB1",
					data : [ '资料完整度' + result.attributes.completeness + '%']
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				polar : [ {
					indicator : [ {
						text : '姓名',
						axisLabel : {
							show : true,
							textStyle : {
								fontSize : 12,
								color : '#333'
							}
						},
						max : result.attributes.maxNumber
					}, {
						text : '性别',
						max : result.attributes.maxNumber
					}, {
						text : '年龄段',
						max : result.attributes.maxNumber
					}, {
						text : '手机号码',
						max : result.attributes.maxNumber
					}, {
						text : 'VIP等级',
						max : result.attributes.maxNumber
					}, {
						text : '生日',
						max : result.attributes.maxNumber
					}, {
						text : '星座',
						max : result.attributes.maxNumber
					}, {
						text : '消费记录',
						max : result.attributes.maxNumber
					} ],
					splitNumber : 5,
				} ],
				calculable : true,
				series : [ {
					name : '资料完整度' + result.attributes.completeness + '%',
					type : 'radar',
					itemStyle : {
						normal : {
							label : {
								show : true,
								formatter : function(params) {
									return params.value;
								}
							},
							color : "#559EB1" // 图标颜色
						}
					},
					data : [ {
						value : result.obj,
						name : '人次'
					} ]
				} ]
			};
			// 为echarts对象加载数据
			integrityChart.setOption(option);
			return integrityChart;
		}
	});
}

/**
 * 顾客资料--性别比例
 * 
 * @param ec
 * @returns
 */
function sexChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var sexChart = ec.init(document.getElementById('sex_div'), 'macarons');
	// 过渡---------------------
	sexChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	sexChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getSexChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '性别'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '性别' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : result.obj.sexList
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '性别',
					type : 'bar',
					data : result.obj.numList
				} ]
			};
			// 为echarts对象加载数据
			sexChart.setOption(option);
			return sexChart;
		}
	});

}
/**
 * 顾客资料--年龄段图表
 * 
 * @param ec
 * @returns
 */
function ageChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var ageChart = ec.init(document.getElementById('age_div'));
	// 过渡---------------------
	ageChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	ageChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getAgeChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '年龄段'
				},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'value',
					boundaryGap : [ 0, 0.01 ]
				} ],
				yAxis : [ {
					type : 'category',
					data : result.obj.ageList
				} ],
				series : [ {
					name : '年龄段',
					type : 'bar',
					data : result.obj.numList
				} ]
			};
			// 为echarts对象加载数据
			ageChart.setOption(option);
			return ageChart;
		}
	});

}
/**
 * 顾客资料--vip占比
 * 
 * @param ec
 * @returns
 */
function vipChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var vipChart = ec.init(document.getElementById('vip_div'), 'macarons');
	// 过渡---------------------
	vipChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	vipChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getVipChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : 'VIP等级占比'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c}"
					//formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'right',
					y : 'bottom',
					data : result.obj.vipList
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
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
					name : 'VIP等级',
					type : 'pie',
					radius : [ '50%', '70%' ],
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						},
						emphasis : {
							label : {
								show : true,
								position : 'center',
								textStyle : {
									fontSize : '30',
									fontWeight : 'bold'
								}
							}
						}
					},
					data : result.obj.mapList,
					markPoint : {
						symbol : 'star',
						data : [ {
							name : '最大',
							value : result.obj.maxNumber,
							x : '80%',
							y : 50,
							symbolSize : 32
						} ]
					}
				} ]
			};
			// 为echarts对象加载数据
			vipChart.setOption(option);
			return vipChart;
		}
	});

}

/**
 * 顾客生日分布
 * 
 * @param ec
 * @returns
 */
function birthdayChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var birthdayChart = ec.init(document.getElementById('birthday_div'));
	// 过渡---------------------
	birthdayChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	birthdayChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getBirthdayReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '生日'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					x : 'center',
					data : [ '生日' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				polar : [
				{
					indicator : (function() {
						var res = [];
						for ( var i = 1; i <= 12; i++) {
							res.push({
								text : i + '月'
							});
						}
						return res;
					})()

				} ],
				series : [ {
					type : 'radar',
					itemStyle : {
						normal : {
							areaStyle : {
								type : 'blue'
							}
						}
					},
					data : [ {
						name : '生日',
						value : result.obj
					} ]
				} ]
			};
			// 为echarts对象加载数据
			birthdayChart.setOption(option);
			return birthdayChart;
		}
	});

}
/**
 * 顾客资料--手机号码所属运营商
 * 
 * @param ec
 * @returns
 */
function operatorChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var operatorChart = ec.init(document.getElementById('operator_div'),
			'macarons');
	// 过渡---------------------
	operatorChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	operatorChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getOperatorChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '用户所属运营商分布'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c}"
					//formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'horizontal',
					x : 'center',
					y : 'bottom',
					data : result.obj.operatorList
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
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
					name : '用户所属运营商分布',
					type : 'pie',
					radius : [ '50%', '70%' ],
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						},
						emphasis : {
							label : {
								show : true,
								position : 'center',
								textStyle : {
									fontSize : '30',
									fontWeight : 'bold'
								}
							}
						}
					},
					data : result.obj.contentMap,
					markPoint : {
						symbol : 'circle',
						data : [ {
							name : '最大',
							value : result.obj.maxNumber,
							x : '80%',
							y : 50,
							symbolSize : 32
						} ]
					}
				} ]
			};
			// 为echarts对象加载数据
			operatorChart.setOption(option);
			return operatorChart;
		}
	});
}

/**
 * 顾客资料--星座
 * 
 * @param ec
 * @returns
 */
function constellationChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var constellationChart = ec.init(document
			.getElementById('constellation_div'), 'macarons');
	// 过渡---------------------
	constellationChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	constellationChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getConstellationChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '星座'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					x : 'center',
					data : [ '星座' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				polar : [ {
					indicator : [ {
						text : '白羊座',
						axisLabel : {
							show : true,
							textStyle : {
								fontSize : 12,
								color : '#333'
							}
						}
					}, {
						text : '金牛座'
					}, {
						text : '双子座'
					}, {
						text : '巨蟹座'
					}, {
						text : '狮子座'
					}, {
						text : '处女座'
					}, {
						text : '天秤座'
					}, {
						text : '天蝎座'
					}, {
						text : '射手座'
					}, {
						text : '摩羯座'
					}, {
						text : '水瓶座'
					}, {
						text : '双鱼座'
					} ]
				} ],
				series : [ {
					type : 'radar',
					itemStyle : {
						normal : {
							areaStyle : {
								type : 'blue'
							}
						}
					},
					data : [ {
						name : '星座',
						value : result.obj
					} ]
				} ]
			};
			// 为echarts对象加载数据
			constellationChart.setOption(option);
			return constellationChart;
		}
	});
}
/**
 * 顾客资料--用户分布
 * 
 * @param ec
 * @returns
 */
function regionChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var regionChart = ec.init(document.getElementById('region_div'));
	// 过渡---------------------
	regionChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	regionChart.hideLoading();
	$.ajax({
		type : "POST",
		url : "reportController.do?getRegionChartReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '顾客分析',
					subtext : '地区分布',
					x : 'left'
				},
				tooltip : {
					trigger : 'axis'
				},
				tooltip : {
					trigger : 'item'
				},
				legend : {
					orient : 'vertical',
					x : 'center',
					data : [ '用户数' ]
				},
				toolbox : {
					show : true,
					orient : 'vertical',
					x : 'right',
					y : 'center',
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				dataRange : {
					min : 0,
					max : 2500,
					x : 'left',
					y : 'bottom',
					text : [ '高', '低' ], // 文本，默认为数值文本
					calculable : true
				},
				roamController : {
					show : true,
					x : 'right',
					mapTypeControl : {
						'china' : true
					}
				},
				series : [ {
					name : '用户数',
					type : 'map',
					mapType : 'china',
					roam : false,
					itemStyle : {
						normal : {
							label : {
								show : true
							}
						},
						emphasis : {
							label : {
								show : true
							}
						}
					},
					data : result.obj
				} ]
			};
			// 为echarts对象加载数据
			regionChart.setOption(option);
			return regionChart;
		}
	});

}
/*-------------------------------------------------------------------------------------------------顾客资料结束----------------------------------------------------------------------------------------*/
/** ****************************************************************************************************************************************************************************************************** */
/** ****************************************************************************************************************************************************************************************************** */
/** ****************************************************************************************************************************************************************************************************** */
/*-------------------------------------------------------------------------------------------------短信效果开始----------------------------------------------------------------------------------------*/
/**
 * 短信送达率
 */
function smsSendChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('sms_send_div'));
	// 过渡---------------------
	myChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});
	// ajax getting data...............
	// ajax callback
	myChart.hideLoading();

	$.ajax({
		type : "POST",
		url : "reportController.do?getSmsSendReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '过去一周短信送达情况',
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '发送数量', '成功送达人数' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : false
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : result.obj.time
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '发送数量',
					type : 'bar',
					data : result.obj.pushCount,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}, {
					name : '成功送达人数',
					type : 'bar',
					data : result.obj.reach,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				} ]
			};
			myChart.setOption(option);
			return myChart;
		}
	});

}

/**
 * 短信发送转化率
 * 
 * @param ec
 * @returns
 */
function smsConversionChartFun(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('sms_conversion_div'));
	// 过渡---------------------
	myChart.showLoading({
		text : '正在努力的读取数据中...', // loading话术
		effect : 'whirling'
	});

	// ajax getting data...............
	$.ajax({
		type : "POST",
		url : "reportController.do?getSmsConversionReport",
		dataType : "json",
		success : function(result) {
			option = {
				title : {
					text : '过去一周短信转化情况'
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
					name : '过去一周短信转化情况',
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
		}
	});
	return myChart;
}

/*-------------------------------------------------------------------------------------------------短信效果结束----------------------------------------------------------------------------------------*/

