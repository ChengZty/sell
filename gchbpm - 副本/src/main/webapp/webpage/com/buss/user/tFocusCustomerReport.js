// 路径配置
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
	            'echarts/chart/map'
	        ],
	       chartInit
	    );
	    
	    function chartInit(ec){
	       	 //顾客资料分析  雷达图
	       	 var integrityChart = integrityChartFun(ec);
	       	 //性别占比图
	       	var  sexChart = sexChartFun(ec);
	       	 //年龄段
	       	var  ageChart = ageChartFun(ec);
	       	 //vip
//	       	var vipChart =  vipChartFun(ec);
	       	 //生日按月份
	       	var birthdayChart =  birthdayChartFun(ec);
				//运营商分布
	       	var operatorChart = operatorChartFun(ec);
	       	 //星座
	       	var constellationChart =  constellationChartFun(ec);
	       	 //地区分布
	       	var regionChart = regionChartFun(ec);
	       	 window.onresize = function(){
	       		 integrityChart.resize();
	       		 sexChart.resize();
	       		 ageChart.resize();
//	       		 vipChart.resize();
	       		 birthdayChart.resize();
	       		 operatorChart.resize();
	       		 constellationChart.resize();
	       		 regionChart.resize();
	       	 }
       }