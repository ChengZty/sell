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
	</script>
	</head>

	<body>
		<div class="navbar navbar-default" id="navbar">
			<div id="addShortcutMSG" style="width：100%; height:30px; line-height:30px; text-align:center; font-size:16px;display:none" >
				
			</div>
			<script type="text/javascript">
				jQuery(function($) {
					if(${accountStatus }== "0"){
						jQuery('#addShortcutMSG').text('您的账户余额不足，请尽快充值。');
						jQuery('#addShortcutMSG').css({'display':'block','background':'#ffb752'});
						jQuery('#addShortcutMSG').click(function (){
							jQuery('#addShortcutMSG').css({'display':'none'});
						});
						/* setTimeout("jQuery('#addShortcutMSG').css('display','none')",3000); */
					}else if(${accountStatus }== "2"){
							jQuery('#addShortcutMSG').text('您的资金账户不存在，请与平台联系。');
							jQuery('#addShortcutMSG').css({'display':'block','background':'#ffb752'});
							jQuery('#addShortcutMSG').click(function (){
								jQuery('#addShortcutMSG').css({'display':'none'});
							});
							/* setTimeout("jQuery('#addShortcutMSG').css('display','none')",3000); */
					}
				});
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}

				//按CTRL + Q 实现快捷方式的下载
				$(document).ready(function(){
					document.onkeyup=function(event){
						if (event.ctrlKey && event.keyCode === 81){ 
							//显示提示信息3s
							jQuery('#addShortcutMSG').text('创建快捷方式');
							jQuery('#addShortcutMSG').css({'display':'block','background':'#79B471'});
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
						<li class="guide_help" >
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

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success">
								<i class="icon-signal"></i>
							</button>

							<button class="btn btn-info">
								<i class="icon-pencil"></i>
							</button>

							<button class="btn btn-warning">
								<i class="icon-group"></i>
							</button>

							<button class="btn btn-danger">
								<i class="icon-cogs"></i>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->

					<ul class="nav nav-list">
						<li class="active">
							<a  href="javascript:addTabs({id:'home',title:'首页',close: false,url: 'loginController.do?acehomeNew'});">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> 首页 </span>
							</a>
						</li>
						<t:menu style="ace" menuFun="${menuMap}" funTp="${funTp }"></t:menu>
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12" style="width: 99%;padding-left:2px;padding-right: 2px;" id="tabs">
	                            <ul class="nav nav-tabs" role="tablist">
	                                <!-- <li class="active"><a href="#Index" role="tab" data-toggle="tab">首页</a></li> -->
	                            </ul>
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
		<script type="text/javascript" src="plug-in/ace/assets/js/bootstrap-tab.js"></script>
		<script src="plug-in/jquery/jquery.contextmenu.js"></script>
		<script src="plug-in/layer/layer.js"></script>
	    <script src="plug-in/ace/js/bootbox.js"></script>
	    <link rel="stylesheet" href="plug-in/ace/css/poptip.css" />
	  
		<!-- inline scripts related to this page -->
		<script>
		jQuery(function($) {
			addTabs({id:'home',title:'首页',close: false,url: 'loginController.do?acehomeNew'});
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

	//点击短信制作，显示短信制作按钮
	$('body').on('click', '.btn_make_message', function(){
		addTabs({id:'add_message',title:'短信',close: true,url:'tSmsSendInfoController.do?goAdd&source='});
	});
	$('body').on('mousedown', '.btn_make_message', function(){
		$(this).css({
			'background-color': '#487593',
			'color': '#fff'
		});
	});
	$('body').on('mouseup', '.btn_make_message', function(){
		$(this).css({
			'background-color': '#6fb3e0',
			'color': '#000'
		});
	});
</script>
</body>
</html>
