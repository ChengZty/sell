<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_job</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<style type="text/css">
	input{
		text-align: center;
	}
	#content_table{
		border-left:1px solid;
		border-top:1px solid;
	}
	#content_table tr td{
		text-align:center;
		border-right:1px solid;
		border-bottom:1px solid;
	}
	</style>
 </head>
 <body>
 <div>
 	<div id="top_div" style="margin:20px;">
	 	<!-- 开始时间:<input id="start_time" class="easyui-datebox" data-options="sharedCalendar:'#cc'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	截至时间:<input id="end_time" class="easyui-datebox" data-options="sharedCalendar:'#cc'"> -->
	 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	开始时间:<input type="text" id="start_time" name="start_time" style="height:20px;width:90px;" class="Wdate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		截至时间:<input type="text" id="end_time" name="end_time" style="height:20px;width:90px;" class="Wdate"></span>
	 	<span id="top_button" style="float:right">
		 	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="exportExl()">导出报表</a>&nbsp;&nbsp;&nbsp;
		 	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="check()">查询</a>
	 	</span>
 	</div>
 </div>
 <div id="content_div" style="margin:0 20px;width:10000px;">
 	<table id="content_table" border="0" cellspacing="0" cellpadding="0">  
        <tr>
        	<td rowspan="2" width="50px">序号</td>
        	<td rowspan="2" colspan="2" width="260px">项目</td>
        	<td colspan="3" id="Total">合计</td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td colspan="3">&nbsp;${store.name}&nbsp;</td>
			</c:forEach>
        </tr>
        <tr>
        	<td width="80px" >目标</td>
        	<td width="80px" >实际</td>
        	<td width="80px" >比例</td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td width="80px" >目标</td>
	        	<td width="80px" >实际</td>
	        	<td width="80px" >比例</td>
			</c:forEach>
        </tr>
        <tr >
        	<td>1</td>
        	<td rowspan="7">导购</td>
        	<td>实体总导购数</td>
        	<td></td>
        	<td id="allGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="allGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>2</td>
        	<td>使用g+导购数</td>
        	<td></td>
        	<td id="useGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="useGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>3</td>
        	<td>新增导购数</td>
        	<td></td>
        	<td id="addGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="addGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>4</td>
        	<td>退出导购数</td>
        	<td></td>
        	<td id="outGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="outGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>5</td>
        	<td>每日在线点击导购数(多日为平均)</td>
        	<td></td>
        	<td id="clickGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="clickGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>6</td>
        	<td>每日在线完成任务导购数(多日为平均)</td>
        	<td></td>
        	<td id="jobGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="jobGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>7</td>
        	<td>在线成交导购数</td>
        	<td></td>
        	<td id="payGuideTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="payGuide${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>8</td>
        	<td rowspan="5">成交</td>
        	<td>成交单数</td>
        	<td></td>
        		<td id="payOrderTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="payOrder${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>9</td>
        	<td>在线销售总件数</td>
        	<td></td>
        	<td id="onlineNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="onlineNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>10</td>
        	<td>在线销售总金额</td>
        	<td></td>
        	<td id="onlineMoneyTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="onlineMoney${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>11</td>
        	<td>在线导购平均产出</td>
        	<td></td>
        	<td id="orderAverTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="orderAver${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>12</td>
        	<td>非实体营业时间导购成交单数(21点到10点付款)</td>
        	<td></td>
        	<td id="outTimePayTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="outTimePay${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>  
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>13</td>
        	<td rowspan="8">顾客</td>
        	<td>g+顾客数量</td>
        	<td></td>
        	<td id="clientNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="clientNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>14</td>
        	<td>待发展顾客数</td>
        	<td></td>
        	<td id="nopayClientNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="nopayClientNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>15</td>
        	<td>交易顾客数</td>
        	<td></td>
        	<td id="payClientNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="payClientNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>16</td>
        	<td>顾客资料完成度（0%≤X≤20%）</td>
        	<td></td>
        	<td id="zeroToTwentyTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="zeroToTwenty${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>17</td>
        	<td>顾客资料完成度（20%&lt;X≤40%）</td>
        	<td></td>
        	<td id="TwentyToFourtyTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="TwentyToFourty${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>18</td>
        	<td>顾客资料完成度（40%&lt;X≤60%）</td>
        	<td></td>
        	<td id="fourtyToSixtyTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="fourtyToSixty${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>19</td>
        	<td>顾客资料完成度（60%&lt;X≤80%）</td>
        	<td></td>
        	<td id="sixtyToEightyTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="sixtyToEighty${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>20</td>
        	<td>顾客资料完成度（80%&lt;X≤100%）</td>
        	<td></td>
        	<td id="eightyToHundredTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="eightyToHundred${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>21</td>
        	<td rowspan="4">商品</td>
        	<td>待上架商品总数</td>
        	<td></td>
        	<td id="waitPublishTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="waitPublish${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>22</td>
        	<td>出售中商品总数</td>
        	<td></td>
        	<td id="hasPublishTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="hasPublish${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>23</td>
        	<td>待上架+出售中商品总数</td>
        	<td></td>
        	<td id="allGoodsTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="allGoods${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>24</td>
        	<td>有商品话术(好在哪里)的商品占比</td>
        	<td></td>
        	<td id="hasWordsPercentTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="hasWordsPercent${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>25</td>
        	<td rowspan="3">话题</td>
        	<td>在线话题数量</td>
        	<td></td>
        	<td id="newsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="newsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>26</td>
        	<td>有点击话题数量</td>
        	<td></td>
        	<td id="clickNewsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="clickNewsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>27</td>
        	<td>有推送话题数量</td>
        	<td></td>
        	<td id="pushNewsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushNewsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>28</td>
        	<td rowspan="2">话术</td>
        	<td>撩客话术总量</td>
        	<td></td>
        	<td id="chartWordsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="chartWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>29</td>
        	<td>有推送的撩客话术总量</td>
        	<td></td>
        	<td id="pushWordsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>30</td>
        	<td rowspan="13">任务</td>
        	<td>在线任务数</td>
        	<td></td>
        	<td id="onLineJobTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="onLineJob${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>31</td>
        	<td>上架商品</td>
        	<td></td>
        	<td id="total_21" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_21" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>32</td>
        	<td>点评商品</td>
        	<td></td>
        	<td id="total_22" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_22" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>33</td>
        	<td>点评话题</td>
        	<td></td>
        	<td id="total_23" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_23" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>34</td>
        	<td>拉新</td>
        	<td></td>
        	<td id="total_19" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_19" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>35</td>
        	<td>撩客</td>
        	<td></td>
        	<td id="total_11" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_11" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>36</td>
        	<td>推送话题</td>
        	<td></td>
        	<td id="total_12" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_12" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>37</td>
        	<td>推送商品</td>
        	<td></td>
        	<td id="total_14" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_14" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>38</td>
        	<td>推送券</td>
        	<td></td>
        	<td id="total_16" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_16" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>39</td>
        	<td>售后维护</td>
        	<td></td>
        	<td id="total_17" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_17" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>40</td>
        	<td>节日问候</td>
        	<td></td>
        	<td id="total_24" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_24" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>41</td>
        	<td>生日祝福</td>
        	<td></td>
        	<td id="total_18" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_18" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>42</td>
        	<td>完善VIP</td>
        	<td></td>
        	<td id="total_20" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="${store.id}_20" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
<%--         <tr >
        	<td>43</td>
        	<td>导购任务完成比例</td>
        	<td></td>
        	<td id="Total" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="id20"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>44</td>
        	<td>任务完成是否有激励</td>
        	<td></td>
        	<td id="Total" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>45</td>
        	<td>获奖比例</td>
        	<td></td>
        	<td id="Total" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>46</td>
        	<td>任务不完成是否有处罚</td>
        	<td></td>
        	<td id="Total" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>47</td>
        	<td>任务处罚及处罚比例</td>
        	<td></td>
        	<td id="Total" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr><tr >
        	<td>48</td>
        	<td rowspan="3">活动</td>
        	<td>导购销售激励及获奖比例</td>
        	<td></td>
        	<td id="chartWordsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="chartWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>49</td>
        	<td>导购销售处罚及处罚比例</td>
        	<td></td>
        	<td id="pushWordsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr >
        	<td>50</td>
        	<td>有无顾客促销活动</td>
        	<td></td>
        	<td id="pushWordsNumTotal" class ="checkValue"></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td id="pushWordsNum${status.index}" class ="checkValue"></td>
        		<td></td>
			</c:forEach>
        </tr> --%>
        <tr>
        	<td colspan="2">分析</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">点评</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
        <tr>
        	<td colspan="2">总结</td>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td></td>
			<c:forEach var="store" items="${storeList}" varStatus="status">
        		<td></td>
        		<td></td>
        		<td></td>
			</c:forEach>
        </tr>
    </table>  
 </div>
 
</body>

<script src="webpage/com/buss/count/clientDataPage.js?v=1.0.0"></script>
<!-- 导出为excl -->
<!-- <script src="webpage/com/buss/count/clientBlob.js?v=1.0.0"></script> -->
<script src="webpage/com/buss/count/clientFileSaver.js?v=1.0.0"></script>
<script src="webpage/com/buss/count/clientTableExport.js?v=1.0.0"></script>

<script type="text/javascript">
$(document).ready(function(){
	//给时间控件加上样式
	$("#top_div").find("input[name='start_time']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${start_time}")
	.attr("id","start_time").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_time\')}'});});
	$("#top_div").find("input[name='end_time']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${end_time}")
	.attr("id","end_time").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_time\')}'});});
	
	/* start_time = $("#top_div #start_time").val();
	end_time = $("#top_div #end_time").val();
	getGuideCount(start_time,end_time);
	setTimeout("getOrderCount(start_time,end_time)","300");
	setTimeout("getCustomerCount(start_time,end_time)","600");
	setTimeout("getGoodsCount(start_time,end_time)","900");
	setTimeout("getNewsCount(start_time,end_time)","1200");
	setTimeout("getWordsCount(start_time,end_time)","1500");
	setTimeout("getJobsCount(start_time,end_time)","1800"); */
	
});
function check(){
	$("#content_table .checkValue").html("");
	start_time = $("#top_div #start_time").val();
	end_time = $("#top_div #end_time").val();
	getGuideCount(start_time,end_time);
	setTimeout("getOrderCount(start_time,end_time)","300");
	setTimeout("getCustomerCount(start_time,end_time)","600");
	setTimeout("getGoodsCount(start_time,end_time)","900");
	setTimeout("getNewsCount(start_time,end_time)","1200");
	setTimeout("getWordsCount(start_time,end_time)","1500");
	setTimeout("getJobsCount(start_time,end_time)","1800");
}

function exportExl(){
	tableExport('content_table', '客户数据报表汇总', 'xls');
}

</script>