<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%-- <%@ page import="java.io.*"%>  
<%@ page import="javax.swing.filechooser.*"%>  
<% 
	FileSystemView fsv = FileSystemView.getFileSystemView();      
	String upurl = fsv.getHomeDirectory().toString();     
	String filename = upurl + "/G+前传.url";  
	File file = new File(filename);
	boolean isFile = false;
	if(!file.exists()){  
		isFile = true;
	}
%> --%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>g+后台管理系统</title>
		<meta name="keywords" content="g+后台管理系统" />
		<meta name="description" content="g+后台管理系统" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="plug-in/jquery/jquery.contextmenu.css"/>
		<link href="./favicon.ico" type="image/x-icon" rel="shortcut icon" />
		<!-- basic styles -->
		<link href="plug-in/ace/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<!--  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />  -->

		<!-- ace styles -->

		<link rel="stylesheet" href="plug-in/ace/assets/css/ace.min.css" />
		<link rel="stylesheet" href="plug-in/ace/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="plug-in/ace/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="plug-in/ace/assets/css/ace_custom_oa.css" />
		<link rel="stylesheet" href="plug-in/ace/assets/css/ace_custom_newmenu.css" />


		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="plug-in/ace/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="plug-in/ace/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="plug-in/ace/assets/js/html5shiv.js"></script>
		<script src="plug-in/ace/assets/js/respond.min.js"></script>
		<![endif]-->
	<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript">
		var ctx = '${webRoot}';
		var menuObj = ${menuMap2};
	</script>
	</head>

	<body>
		<%--
		<div class="navbar navbar-default" id="navbar">
			<div id="addShortcutMSG" style="width：100%; height:30px; background:#79B471; text-align:center; font-size:16px;display:none" >
					创建快捷方式
			</div>
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}

				//按CTRL + Q 实现快捷方式的下载
				$(document).ready(function(){
					document.onkeyup=function(event){
						if (event.ctrlKey && event.keyCode === 81){ 
							//显示提示信息3s
							jQuery('#addShortcutMSG').css('display','block');
							setTimeout("jQuery('#addShortcutMSG').css('display','none')",3000);
							location.href="userController.do?addShortcut";
						} 
				    }
				});
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<!-- <i class="fa fa-leaf"></i> -->
							g+后台管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						 <!-- <li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-tasks"></i>
								<span class="badge badge-grey">4</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-ok"></i>
									还有4个任务完成
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">软件更新</span>
											<span class="pull-right">65%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:65%" class="progress-bar "></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">硬件更新</span>
											<span class="pull-right">35%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:35%" class="progress-bar progress-bar-danger"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">单元测试</span>
											<span class="pull-right">15%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:15%" class="progress-bar progress-bar-warning"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">错误修复</span>
											<span class="pull-right">90%</span>
										</div>

										<div class="progress progress-mini progress-striped active">
											<div style="width:90%" class="progress-bar progress-bar-success"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										查看任务详情
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> -->

						<!-- <li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-bell-alt icon-animated-bell"></i>
								<span class="badge badge-important" id="noticeCount">0</span>
							</a>

							<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close" id="noticeContent">
								<li class="dropdown-header" id="noticeTip">
									<i class="icon-warning-sign"></i>
									0条公告
								</li>
								
								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar navbar-pink" id="noticeContent">
										
									</ul>
								</li>

								<li>
									<a href="#" id="noticeContent">
									</a>
								</li>

								<li>
									<a href="javascript:goAllNotice();" id="noticeFooter">
										查看全部消息
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> -->
							<!-- <li class="blue" id="store_0" style="display: none">
								<a id="href0" >
									<i>零</i>
									<span class="badge badge-success" id="noticeCount-0">0</span>
								</a>
							</li> 
							<li class=purple id="store_1" style="display: none">
								<a id="href1">
									<i>断</i>
									<span class="badge badge-success" id="noticeCount-1">0</span>
								</a>
							</li> 
							<li class="red" id="store_2" style="display: none">
								<a id="href2">
									<i>警</i>
									<span class="badge badge-success" id="noticeCount-2">0</span>
								</a>
							</li>  -->
						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-envelope icon-animated-vertical"></i>
								<span class="badge badge-success" id="noticeCount">0</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close" id="noticeContent">
								<li class="dropdown-header" id="noticeTip">
									<i class="icon-envelope-alt"></i>
									0条消息
								</li>
							

								<li>
									<a href="javascript:goAllNotice();" id="noticeFooter">
										查看全部消息
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> 
						<!-- <li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-envelope icon-animated-vertical"></i>
								<span class="badge badge-success" id="messageCount">0</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header" id="messageTip">
									<i class="icon-envelope-alt"></i>
									0条消息
								</li>

								<li>
									<a href="#" id="messageContent">
										
									</a>
								</li>

								

								<li>
									<a href="javascript:goAllMessage();" id="messageFooter">
										查看所有消息
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> -->

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="plug-in/ace/avatars/avatar2.png" alt="Jason's Photo" />
								<span class="user-info">
									<small>${userName }</small>
				                    <span style="color: #666633">${roleName }</span>
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:add('修改密码','userController.do?changepassword','',550,200)">
										<i class="icon-cog"></i>
										 修改密码
									</a>
								</li>

								<li>
									<a href="javascript:openwindow('个人信息','userController.do?userinfo')">
										<i class="icon-user"></i>
										 个人信息
									</a>
								</li>
								<!-- 
								<li>
									<a href="javascript:openwindow('系统消息','tSSmsController.do?getSysInfos')">
										<i class="icon-cog"></i>
										 系统消息
									</a>
								</li>
								 -->
								<li>
									<a href="javascript:clearLocalstorage()">
										<i class="icon-cog"></i>
										清除缓存
									</a>
								</li>
								<li>
									<a href="userController.do?addShortcut"  download="G+前传.url" target="blank" id="addShortcut">
										<img src='webpage/daggerSrc/login/img/shortcut.png' style="width:15px; height:15px;" />
										创建快捷方式(Ctrl+Q)
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="javascript:logout()">
										<i class="icon-off"></i>
										 注销
									</a>
								</li>
							</ul>
						</li>
						<li class="guide_help" style="display: none;">
							<a href="helpDoc/GHelpDoc/index.html" target="_blank" style="width: 45px; height: 45px; background:url('plug-in/ace/img/bg_help.png') no-repeat center center;"></a>
						</li> 
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		--%>

		<div class="navbar navbar-default th-navbar-default " id="navbar">
			<div class="navbar-header th-header-logo">
				<a href="#" class="navbar-brand">
					<small>
						g+管家真选
					</small>
				</a>
			</div>
				<div class="th-navbar-container navbar-container clearfix" id="navbar-container">
					<div class="navbar-header pull-left th-navbar-header">
						<a href="javascript:addTabs({id:'home',title:'首页',close: false,url: 'loginController.do?acehomeStatistics'});" class="navbar-brand">
							<small style="color: #5d5d5f;">
								首页
							</small>
						</a>
					</div>
	
					<div class="navbar-header pull-right" role="navigation">
						<ul class="nav ace-nav">
							<li class="green">
								<a data-toggle="dropdown" class="dropdown-toggle" href="#" style="color: #5d5d5f;" >
									消息 <span class="th-notice">（<span id="noticeCount">0</span>）</span>
								</a>
	
								<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close" id="noticeContent">
									<li class="dropdown-header" id="noticeTip">
										<i class="icon-envelope-alt"></i>
										0条消息
									</li>
	
									<li>
										<a href="javascript:goAllNotice();" id="noticeFooter">
											查看全部消息
											<i class="icon-arrow-right"></i>
										</a>
									</li>
								</ul>
							</li> 
							<li class="light-blue">
								<a data-toggle="dropdown" href="#" class="dropdown-toggle">
									<span class="user-info">
										<small style="color: #5d5d5f;">${userName }</small>
									</span>
									<i class="icon-caret-down th-icon-caret-down"></i>
								</a>
	
								<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
									<li>
										<a href="javascript:add('修改密码','userController.do?changepassword','',550,200)">
											<i class="icon-cog"></i>
											 修改密码
										</a>
									</li>
	
									<li>
										<a href="javascript:openwindow('个人信息','userController.do?userinfo')">
											<i class="icon-user"></i>
											 个人信息
										</a>
									</li>
									<!-- 
									<li>
										<a href="javascript:openwindow('系统消息','tSSmsController.do?getSysInfos')">
											<i class="icon-cog"></i>
											 系统消息
										</a>
									</li>
									 -->
									<li>
										<a href="javascript:clearLocalstorage()">
											<i class="icon-cog"></i>
											清除缓存
										</a>
									</li>
									<li>
										<a href="userController.do?addShortcut"  download="G+前传.url" target="blank" id="addShortcut">
											<img src='webpage/daggerSrc/login/img/shortcut.png' style="width:15px; height:15px;" />
											创建快捷方式(Ctrl+Q)
										</a>
									</li>
	
									<li class="divider"></li>
	
									<li>
										<a href="javascript:logout()">
											<i class="icon-off"></i>
											 注销
										</a>
									</li>
								</ul>
							</li>
							<li class="guide_help" style="display: none;">
								<a href="helpDoc/GHelpDoc/index.html" target="_blank" style="width: 45px; height: 45px; background:url('plug-in/ace/img/bg_help.png') no-repeat center center;"></a>
							</li> 
						</ul><!-- /.ace-nav -->
					</div><!-- /.navbar-header -->
				</div><!-- /.container -->
			</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar th-sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>
					<div class="th-nav-list">
						<ul class="nav nav-list th-menu-left">
							
						</ul>
						
						<ul class="nav nav-list th-menu-right">
							
						</ul>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12" style="width: 99%;padding-left:2px;padding-right: 2px;" id="tabs">
								<div class="th-nav-tabs">
									<span class="btn-control btn-left-first"></span>
									<span class="btn-control btn-left-pre"></span>
									<div class="th-tab-list">
										<ul class="nav nav-tabs nav-scroll" role="tablist">
											<!-- <li class="active"><a href="#Index" role="tab" data-toggle="tab">首页</a></li> -->
										</ul>
									</div>
									<span class="btn-control btn-right-next"></span>
									<span class="btn-control btn-right-last"></span>
									<span class="btn-control btn-view-all"></span>
								</div>
	                            <div class="tab-content">
	                                <div role="tabpanel" class="tab-pane active" id="Index">
	                                </div>
	                            </div>
	                        </div>
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				<!--<div class="ace-settings-container" id="ace-settings-container" >
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; 选择皮肤</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
							<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl">切换到左边</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								切换窄屏
								<b></b>
							</label>
						</div>
					</div>
				</div> /#ace-settings-container -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->


<div id="changepassword" style="display:none">

<input id="id" type="hidden" value="${user.id }">
	<table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
		<tbody>
			<tr>
				<td align="right" width="20%"><span class="filedzt">原密码:</span></td>
				<td class="value"><input id="password" type="password" value="" name="password" class="inputxt" datatype="*" errormsg="请输入原密码" /> <span class="Validform_checktip"> 请输入原密码 </span></td>
			</tr>
			<tr>
				<td align="right"><span class="filedzt">新密码:</span></td>
				<td class="value"><input  type="password" value="" name="newpassword" class="inputxt" plugin="passwordStrength" datatype="*6-18" errormsg="密码至少6个字符,最多18个字符！" /> <span
					class="Validform_checktip"> 密码至少6个字符,最多18个字符！ </span> <span class="passwordStrength" style="display: none;"> <b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span> </span></td>
			</tr>
			<tr>
				<td align="right"><span class="filedzt">重复密码:</span></td>
				<td class="value"><input id="newpassword" type="password" recheck="newpassword" datatype="*6-18" errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"></span></td>
			</tr>
		</tbody>
	</table>
	

</div>
	<!-- 查看全部tab页面  -->
	<div class="view-all-tabs" style="display: none;">
		<div class="all-tabs-box clearfix">
			<div class="tab-item" tab-item-id="home">
				<div class="tab-iframe-box">
					<img class="home-page-img" src="plug-in/ace/img/home_page.jpg">
				</div>
				<div class="tab-mask"></div>
				<span class="status">当前页面</span>
				<div class="tab-title">首页</div>
			</div>
		</div>
		<div class="btn_close_all">关闭全部窗口</div>
	</div>
	<!--短信制作按钮-->
	<!--<div class="btn_make_message" style="width: 80px; height: 80px; position: fixed; left: 46px; bottom: 30px; background-color: #6fb3e0;
				cursor: pointer;border-radius: 50%; text-align: center; line-height: 80px; user-select: none; font-size: 14px;">
		制作
	</div>-->
		<!-- basic scripts -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='plug-in/ace/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='plug-in/ace/assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='plug-in/ace/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="plug-in/ace/assets/js/bootstrap.min.js"></script>
		<script src="plug-in/ace/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="plug-in/ace/assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="plug-in/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="plug-in/ace/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="plug-in/ace/assets/js/jquery.slimscroll.min.js"></script>
		<script src="plug-in/ace/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="plug-in/ace/assets/js/jquery.sparkline.min.js"></script>
		<script src="plug-in/ace/assets/js/flot/jquery.flot.min.js"></script>
		<script src="plug-in/ace/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="plug-in/ace/assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="plug-in/ace/assets/js/ace-elements.min.js"></script>
		<script src="plug-in/ace/assets/js/ace.min.js"></script>
		
		<t:base type="tools"></t:base>
		<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>
		<script type="text/javascript" src="plug-in/ace/assets/js/bootstrap-tab-oa.js"></script>
		<script src="plug-in/jquery/jquery.contextmenu.js"></script>
		<script src="plug-in/layer/layer.js"></script>
	    <script src="plug-in/ace/js/bootbox.js"></script>
	    <link rel="stylesheet" href="plug-in/ace/css/poptip.css" />
	  
		<!-- inline scripts related to this page -->
		<script>
		jQuery(function($) {
			addTabs({id:'home',title:'首页',close: false,url: 'loginController.do?acehomeStatistics'});
			$('.theme-poptit .close').click(function(){
	    		$('.theme-popover-mask').fadeOut(100);
	    		$('.theme-popover').slideUp(200);
	    	});
	    	$('#closeBtn').click(function(){
	    		$('.theme-popover-mask').fadeOut(100);
	    		$('.theme-popover').slideUp(200);
	    	});
	    	//$('#ace-settings-sidebar').click();
	    	//$('#sidebar').addClass('compact');
			$('#sidebar li').addClass('hover').filter('.open').removeClass('open').find('> .submenu').css('display', 'none');
			/* if("${userType}"!="01"){//非后台人员（零售商）
				//获取库存
				getAndFillStore();
				
				var a_ = $(".nav-list li a");
				$(a_).each(function(idx,dom){
					var title = $(this).attr("title");
					var href = $(this).attr("href");
					if(title=="零库存列表"){
						$("#store_0").show();
						$("#href0").attr("href",href).attr("title",title);
					}else if(title=="断码库存列表"){
						$("#store_1").show();
						$("#href1").attr("href",href).attr("title",title);
					}else if(title=="预警库存列表"){
						$("#store_2").show();
						$("#href2").attr("href",href).attr("title",title);
					}
				})
			} */
		});
		//获取预警，断码，零库存数量
		/* function getAndFillStore(){
			var url = "tGoodsController.do?getStore";
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
		  				var store = d.obj;
		  				$("#noticeCount-0").text(store.stockZero);
		  				$("#noticeCount-1").text(store.stockLess);
		  				$("#noticeCount-2").text(store.stockAlarm);
		  			}
		  			
		  		}
		  	});
		} */
		</script>
		
		<script type="text/javascript">

		function loadModule(title,url,target){
			//TODO addTab(title,url);
			    $("#mainTitle").text(title);
      			$("#center").attr("src",url);
      	}
		

	  	function logout(){
	  		bootbox.confirm("确定退出该系统吗 ?", function(result) {
	  			if(result){
					location.href="loginController.do?logout1";
	  			}
	  		});
  		}
		function opendialog(title,url,target){
			//$("#dialog").attr("src",url);
			bootbox.dialog({
				message:$("#changestylePanel").html(),
				title:title,
				buttons:{
				OK:{
				label: "OK", 
				callback:function(){
					    var indexStyle = $('input[name="indexStyle"]:checked').val();
					    if(indexStyle==undefined||indexStyle==""){
					    	indexStyle = "ace";
					    }
					    var cssTheme = $('input[name="cssTheme"]:checked').val();
					    if(cssTheme==undefined){
					    	cssTheme = "";
					    }
						var form = $("#formobj");//取iframe里的form
						$.ajax({
		        			url:form.attr('action'),
		        			type:form.attr('method'),
		        			data:"indexStyle="+indexStyle,//+"&cssTheme="+cssTheme,
		        			success:function(data){
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									bootbox.alert(msg);
								}else{
									bootbox.alert(d.msg);
								}
		        			},
		        			error:function(e){
		        				bootbox.alert("出错了哦");
		        			}
						});
					}
			},Cancel: {label: "CLOSE", 
				callback:function() {
						//alert('close');//$("#dialog").dialog("close");
					}
				}
			}});
  			
  	}
		function changepass(title,url,target){
			//$("#dialog").attr("src",url);
			bootbox.dialog({
				message:'<form id="formobj2"  action="userController.do?savenewpwd" name="formobj2" method="post">'
					+$("#changepassword").html()+'</form>',
				title:title,
				buttons:{
				OK:{
				label: "OK", 
				callback:function(){
					//alert('not implement');
						$.ajax({
		        			url:"userController.do?savenewpwd",
		        			type:"post",
		        			data:$('#formobj2').serialize(),// 要提交的表单 ,
		        			success:function(data){
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									bootbox.alert(msg);
								}else{
									bootbox.alert(d.msg);
									}
		        			},
		        			error:function(e){
		        				bootbox.alert("出错了哦");
		        			}
						});
					}
			},Cancel: {label: "CLOSE", 
				callback:function() {
						alert('close');//$("#dialog").dialog("close");
					}
				}
			}});
  			
  	}
		function profile(title,url,target){
			//$("#dialog").attr("src",url);
			bootbox.dialog({
				message:'<iframe width="100%" height="300px" src="'+url+'" style="border:1px #fff solid; background:#CCC;"></iframe>',
				title:title,
				buttons:{
				OK:{
				label: "OK"},Cancel: {label: "CLOSE"
				}
			}});
  			
  	}
		
	function clearLocalstorage(){
		var storage=$.localStorage;
		if(!storage)
			storage=$.cookieStorage;
		storage.removeAll();
		alertTipTop("浏览器缓存清除成功!","10%");
	}


	$(document).ready(function(){
		window.setTimeout(getNoticeList,1000);
		window.setInterval(getNoticeList,1000*15*60);
	});
	
	function getNoticeList(){
		//加载公告    ---消息
		var url = "noticeController.do?getNoticeList";
		jQuery.ajax({
    		url:url,
    		type:"GET",
    		dataType:"JSON",
    		async: false,
    		success:function(data){
    			if(data.success){
    				var noticeList = data.attributes.noticeList;
    				var noticeCount = data.obj;
    				//加载消息条数
    				if(noticeCount>99){
    					$("#noticeCount").html("99+");
    				}else{
    					$("#noticeCount").html(noticeCount);
    				}
    				//加载消息提示
    				var noticeTip = "";
    				noticeTip += "<i class='ace-icon fa fa-envelope-o'></i>";
    				noticeTip += noticeCount+"条消息";
    				$("#noticeTip").html(noticeTip);
    				$("#noticeContent li[name='msgli']").remove();
    				//加载消息条目
    				var noticeContent = "";
    				if(noticeList.length > 0){
    					for(var i=0;i<noticeList.length;i++){
    						noticeContent +="<li name='msgli'><a href='javascript:goNotice(&quot;"+noticeList[i].id+"&quot;)' class='clearfix' >";
    						noticeContent +="<img src='plug-in/ace/avatars/avatar3.png' class='msg-photo' alt='Alex’s Avatar' />";
    						noticeContent +="<span class='msg-body'><span class='msg-title' style='word-wrap:break-word;word-break:break-all;'>";
    						noticeContent +=""+noticeList[i].noticeSender+":<span class='blue'>";
    						noticeContent += noticeList[i].noticeTitle + "</span></span>";
    						noticeContent +="<span class='msg-time'><i class='ace-icon fa fa-clock-o'></i><span>"+noticeList[i].noticeSenderTime+"</span>";
    						noticeContent +="<span id='"+noticeList[i].id+"_isRead'>"+noticeList[i].isRead+"</span></span></li>";
        				}
    				}
    				$("#noticeTip").after(noticeContent);
    			}
    		}
    	});
	}
	

    function goAllNotice(){
    	var addurl = "noticeController.do?noticeList";
  		createdetailwindow("消息", addurl, 700, 500);
    }

    function goNotice(id){
  		var addurl = "noticeController.do?goNotice&id="+id;
		createdetailwindow("消息详情", addurl, 700, 500);
		$("#"+id+"_isRead").html("已读");
		var noticeCount = $("#noticeCount").html();
		if(noticeCount > 0){
			noticeCount = noticeCount-1;	
		}
		$("#noticeCount").html(noticeCount);
		$("#noticeTip").html(noticeCount+"条消息");
	}

	/** SidebarVo start */
	var SidebarVo = (function(){
        function SidebarVo(box, menuJson){
            if(this instanceof SidebarVo){
				this.$box= $(box);
				this.menuJson = menuJson;
				this.menuSecondJson = {};

                this.initView();
            }else{
                return new SidebarVo(box, menuJson);
            }
        }

        SidebarVo.prototype = {
            constructor: SidebarVo,
            initView: function(){
				this.bindClickEvt();
				this.initLeftMenu();
				this.dealSecondMenuData();
			},
			//绑定点击事件
			bindClickEvt: function(){
				var _this = this;
				
				var $menuRight = _this.$box.find('.th-menu-right');
				//显示隐藏
				_this.$box.on('click', '.ctrl-sidebar', function(){
					var $curBtn = $(this);
					
					if($menuRight.hasClass('open')){
						$menuRight.removeClass('open');
						_this.$box.css({
							'width': '190px'
						});
						$('.main-content').css({
							'margin-left': '190px'
						});
					}else{
						$menuRight.addClass('open');
						_this.$box.css({
							'width': '380px'
						});
						$('.main-content').css({
							'margin-left': '380px'
						});
					}

					if($curBtn.hasClass('open')){
						$curBtn.removeClass('open');
					}else{
						$curBtn.addClass('open');
					}
				});

				//二级菜单点击
				_this.$box.on('click', 'a[function-id]', function(){
					var $curBtn = $(this);
					var curFnId = $curBtn.attr('function-id');

					_this.initRightMenu(curFnId);
					if(!$menuRight.hasClass('open')){
						_this.$box.find('.ctrl-sidebar').click();
					}
				});

				//三级菜单点击
				_this.$box.on('click', '.third-menu-dropdown-toggle', function(){
					var $curBtn = $(this);
					var $curLi = $curBtn.parents('li');

					$curLi.toggleClass('open');
					$curLi.find('.submenu').stop().slideToggle('fast');
				});

				//左侧菜单 连接点击 关闭三四级菜单
				_this.$box.on('click', '.th-menu-left a.second-menu', function(){
					var $a = $(this);
					var curUrl = $a.attr('url');
					var $ctrlSidebar = _this.$box.find('.ctrl-sidebar');
					if(curUrl && curUrl != ''){
						if($ctrlSidebar.hasClass('open')){
							$ctrlSidebar.click();
						}
					}
				});
			},
			//处理二级菜单数据
			dealSecondMenuData: function(){
				var _this = this;

				$.each(_this.menuJson, function(firstIdx, firstMenuObj){
					$.each(firstMenuObj.subFunctionList, function(secondIdx, secondMenuObj){
						_this.menuSecondJson[secondMenuObj.id] = secondMenuObj;
					});
				});
			},
			//初始化菜单
			initLeftMenu: function(){
				var _this = this;

				var $menuLeft = _this.$box.find('.th-menu-left');  //左侧菜单容器

				var tempOne = '\
						<li class="ctrl-sidebar">\
							<div class="ctrl-sidebar-tag"></div>\
						</li>\
				';

				//一级菜单循环
				$.each(_this.menuJson, function(firstIdx, firstMenuObj){
					var tempTwo = '';

					//二级菜单循环
					$.each(firstMenuObj.subFunctionList, function(secondIdx, secondMenuObj){
						var imgStr = '';
						if(secondMenuObj.iconPath && secondMenuObj.iconPath != ''){
							imgStr = '<img class="second-menu-img" src="'+secondMenuObj.iconPath+'" alt="">';
						}
						if(secondMenuObj.functionUrl && secondMenuObj.functionUrl != ''){   //如果菜单url不为空
							tempTwo += '\
								<li class="hover">\
									<a class="second-menu" href="javascript:addTabs({id:\''+secondMenuObj.id+'\',title:\''+secondMenuObj.functionName+'\',close: true,url:\''+secondMenuObj.functionUrl+'&amp;clickFunctionId='+secondMenuObj.id+'&amp;funTp=1\'})" title="'+secondMenuObj.functionName+'" url="'+secondMenuObj.functionUrl+'">\
										'+imgStr+'\
										'+secondMenuObj.functionName+'\
									</a>\
								</li>\
							';
						}else{
							tempTwo += '\
								<li class="hover">\
									<a class="second-menu" function-id="'+secondMenuObj.id+'" href="javascript:;" title="'+secondMenuObj.functionName+'" url="'+secondMenuObj.functionUrl+'">\
										'+imgStr+'\
										'+secondMenuObj.functionName+'\
									</a>\
								</li>\
							';
						}
						
					});
					tempOne += '\
						<li class="hover open">\
							<a href="#" class="dropdown-toggle th-dropdown-toggle">\
								<b class="arrow icon-angle-down"></b>\
								<span class="menu-text">'+firstMenuObj.functionName+'</span>\
							</a>\
							<ul class="submenu" style="display: none;">\
								'+tempTwo+'\
							</ul>\
						</li>\
					';
				});

				$menuLeft.append(tempOne);
			},
			initRightMenu: function(functionId){
				var _this = this;

				var $menuRight = _this.$box.find('.th-menu-right');  //右侧菜单容器
				var curSecondMenuJson = _this.menuSecondJson[functionId];
				
				var tempThird = '';

				//三级菜单循环
				$.each(curSecondMenuJson.subFunctionList, function(thirdIdx, thirdMenuObj){
					var tempFourth = '';

					//四级菜单循环
					$.each(thirdMenuObj.subFunctionList, function(fourthIdx, fourthMenuObj){
						tempFourth += '\
							<li class="hover">\
								<a href="javascript:addTabs({id:\''+fourthMenuObj.id+'\',title:\''+fourthMenuObj.functionName+'\',close: true,url:\''+fourthMenuObj.functionUrl+'&amp;clickFunctionId='+fourthMenuObj.id+'&amp;funTp=1\'})" title="'+fourthMenuObj.functionName+'" url="'+fourthMenuObj.functionUrl+'">\
									'+fourthMenuObj.functionName+'\
								</a>\
							</li>\
						';
					});

					if(thirdMenuObj.functionUrl && thirdMenuObj.functionUrl != ''){
						tempThird += '\
							<li class="hover">\
								<a href="javascript:addTabs({id:\''+thirdMenuObj.id+'\',title:\''+thirdMenuObj.functionName+'\',close: true,url:\''+thirdMenuObj.functionUrl+'&amp;clickFunctionId='+thirdMenuObj.id+'&amp;funTp=1\'})" title="'+thirdMenuObj.functionName+'" url="'+thirdMenuObj.functionUrl+'">\
									'+thirdMenuObj.functionName+'\
								</a>\
							</li>\
						';
					}else{
						tempThird += '\
							<li class="hover open">\
								<a href="#" class="third-menu-dropdown-toggle">\
									<b class="arrow icon-angle-down"></b>\
									<span class="menu-text">'+thirdMenuObj.functionName+'</span>\
								</a>\
								<ul class="submenu" style="display: block;">\
									'+tempFourth+'\
								</ul>\
							</li>\
						';
					}
				});

				$menuRight.children().remove();
				$menuRight.append(tempThird);
			},

			
        };

        return SidebarVo;
    })();
	/** SidebarVo end */
	var sidebarVo = new SidebarVo('.th-sidebar', menuObj);
</script>

</body>
</html>
