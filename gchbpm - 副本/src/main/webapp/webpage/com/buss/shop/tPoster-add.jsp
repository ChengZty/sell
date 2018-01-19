<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>海报</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="观潮汇数据科技">
    <meta name="description" content="观潮汇管家系统，短信制作，商品SHOW" />
    <meta name="Keywords" content="观潮汇管家系统，短信制作，商品资讯，图文制作，内容排版，大数据处理" />

	<t:base type="jquery,easyui,tools,layer"></t:base>
  
    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
	<link rel="stylesheet" href="plug-in/slick/slick.css" />
    <link rel="stylesheet" href="plug-in/slick/slick-theme.css" />
    <!--<link rel="stylesheet" href="webpage/daggerSrc/editContent/css/editContent.css" />  -->
    <!--<link rel="stylesheet" href="webpage/daggerSrc/wangEditor/css/wangEditor.css" />  -->
    <link rel="stylesheet" href="webpage/daggerSrc/colorpicker/css/colpick.css" />  
    <link rel="stylesheet" href="webpage/daggerSrc/editContent/css/editContent.css?v=1.0.4" />
  
	<script type="text/javascript">
		//编写自定义JS代码
		var domain = '${domain}';//七牛domain
	</script>
 </head>
 <body>
	
 <!--顶部按钮-->
    <div class="th_top">
        <button class="btn_menu btn_save">保存到草稿箱</button>
        <button class="btn_menu btn_check">预览</button>
        <div class="done_box">
            <button class="btn_menu btn_done">完成</button>
            <div class="alert_box hide">
                <p class="alert_info">需要上传了资讯封面和标题才能完成哦</p>
                <div class="btn_box">
                    <button class="btn_cancel">取消</button><button class="btn_confirm active">确定</button>
                </div>
            </div>
        </div>
        <div class="inform_box" style="display: none;">
            <p class="inform_num">11</p>
        </div>
    </div>
    <!-- 海报id -->
	<input type="hidden" id="id" /> 
    <div class="th_content">
        <!--编辑图文封面和标题-->
        <div class="th_cover">
            <div class="bg_top">
                <div class="bg_bottom">
                    <div class="cover_content">
                        <div class="img_box">
                            <img class="cover_pic" id="coverPic" src="webpage/daggerSrc/common/img/cover_pic.png" alt="" onclick="" >
                        </div>
						<p style="color: #808080; font-size: 14px; padding-left: 14px; text-align: center;">封面图片建议宽高比为:750*446</p>
                        <div class="text_box">
                            <input class="cover_title" id="title" type="text" name="infoTitle" value="" placeholder="请输入资讯的标题，点击上面图片改资讯封面">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--编辑图文内容-->
        <div class="edit_content">
            <div class="bg_top">
                <div class="bg_bottom">
                    <div class="mobile_content" id="mobile_content">
						
                    </div>
                </div>
            </div> 
        </div>

		<!--uEditor 用于编辑文本，位置随着点击位置变化而变化-->
		<div class="ueditor_box" id="ueditor_box" style="display: none;"></div>
    </div>
    <!--右侧菜单-->
    <div class="th_menu">
        <div class="left_bar">
            <button temp_tag="tempText" class="left_btn btn_text active">文本</button>
            <button temp_tag="tempPic" class="left_btn btn_pic">图片</button>
            <button temp_tag="tempGoods" class="template left_btn btn_goods">商品</button>
            <button temp_tag="tempTemplate" class="left_btn btn_template">模板</button>
        </div>
        <div class="right_content">
            <div class="menu_info">
				<div class="tempText" style="display: block;">
					<!--<h2 class="tempText_title">资讯编辑</h2>-->
					<div class="template_box">
						<div temp_tag="tempTitle" class="template temp_item temp_title">标题</div>
						<!--<div temp_tag="tempAuthor" class="template temp_item temp_author">作者</div>-->
						<div temp_tag="tempContent" class="template temp_item temp_content">正文内容</div>
					</div>
					<!--文字菜单-->
					<div class="text_spec">
						<div class="spec_mask"></div>
						<div class="spec_row">
							<!--颜色选择器-->
							<div class="colorpicker"></div>
							<!--字体-->
							<div class="fontsize">
								<div class="btn-group dropdown">
									<input type="text" name="fontsize" class="input_value font_size" value="16">
									<span class="caret"></span>
									<ul class="dropdown-menu" tabindex="69" style="overflow-y: auto; outline: none;"><li><li><a href="javascript:void(0)">16</a></li><a href="javascript:void(0)">18</a></li><li><a href="javascript:void(0)">20</a></li><li><a href="javascript:void(0)">22</a></li><li><a href="javascript:void(0)">24</a></li><li><a href="javascript:void(0)">26</a></li><li><a href="javascript:void(0)">28</a></li><li><a href="javascript:void(0)">30</a></li><li><a href="javascript:void(0)">34</a></li><li><a href="javascript:void(0)">36</a></li><li><a href="javascript:void(0)">40</a></li><li><a href="javascript:void(0)">44</a></li><li><a href="javascript:void(0)">46</a></li><li><a href="javascript:void(0)">50</a></li><li><a href="javascript:void(0)">60</a></li><li><a href="javascript:void(0)">72</a></li><li><a href="javascript:void(0)">96</a></li><li><a href="javascript:void(0)">106</a></li><li><a href="javascript:void(0)">120</a></li><li><a href="javascript:void(0)">144</a></li><li><a href="javascript:void(0)">288</a></li><li><a href="javascript:void(0)">366</a></li><li><a href="javascript:void(0)">477</a></li><li><a href="javascript:void(0)">566</a></li></ul>
								</div>
							</div>
							<!--行高-->
							<div class="lineheight">
								<!-- .label 行距  -->
								<div class="btn-group dropdown">
								<input type="text" name="lineheight" class="input_value line_height" value="1倍">
								<span class="caret"></span>
								<ul slot="dropdown-menu" class="dropdown-menu dropdown-pos" tabindex="6" style="overflow-y: auto; outline: none;"><li><a href="javascript:void(0)">1倍</a></li><li><a href="javascript:void(0)">1.2倍</a></li><li><a href="javascript:void(0)">1.35倍</a></li><li><a href="javascript:void(0)">1.5倍</a></li><li><a href="javascript:void(0)">2倍</a></li><li><a href="javascript:void(0)">2.5倍</a></li></ul>
								</div>
							</div>
						</div>
						<div class="spec_row">
							<div icon_tag="font-weight" default_value="normal" active_value="bold" class="icon icon_weight"></div><!--
							--><div icon_tag="font-style" default_value="normal" active_value="italic" class="icon icon_italic"></div><!--
							--><div icon_tag="text-decoration" default_value="none" active_value="underline" class="icon icon_underline"></div><!--
							--><div icon_tag="text-align" active_value="left" class="icon icon_paragraph_left"></div><!--
							--><div icon_tag="text-align" active_value="center" class="icon icon_paragraph_center"></div><!--
							--><div icon_tag="text-align" active_value="right" class="icon icon_paragraph_right"></div>
						</div>
					</div>
				</div>
				<div class="tempPic" style="display: none;">
					<!--图片菜单-->
					<div class="pic_select">
						<div class="pic_tab_bar">
							<div pic_tag="my_pic_content" class="tab_pic tab_my_pic active">我  的</div>
							<div pic_tag="template_pic_content" class="tab_pic tab_template_pic">模  板</div>
						</div>
						<!--我的-->
						<div class="pic_content my_pic_content">
							<ul class="sort_list clearfix"></ul>
							<ul class="pic_list clearfix" style="display: none;"></ul>
							<div class="uploading" style="display: none;">
								<form method="post" action="" id="myform" enctype="multipart/form-data">
									<input type="file" id="files" name="files" required="required" multiple accept="image/png,image/gif,image/jpeg"/>
								</form>			
							</div>
							
						</div>
						<!--模版-->
						<div class="pic_content template_pic_content"  style="display: none;">
							<ul class="sort_list clearfix"></ul>
							<ul class="pic_list clearfix" style="display: none;"> </ul>
						</div>
					</div>
				</div>
				<div class="tempGoods" style="display: none;"></div>
				<div class="tempTemplate" style="display: none;">
					<!--布局模板菜单-->
					<div class="template_select">
						<div class="template_tab_bar">
							<div temp_tag="cur_template_content" class="tab_template tab_cur_template active">当前模板</div>
							<div temp_tag="all_template_content" class="tab_template tab_all_template">全部模板</div>
						</div>
						<div class="template_content cur_template_content">
							<div class="btns">
								<button type="button" class="change_background">更换背景</button>
								<button type="button" class="remove_background">移&nbsp;&nbsp;&nbsp;&nbsp;除</button>
							</div>
							<div class="cur_template_bg">
								<img class="cur_bg" src="webpage/daggerSrc/common/img/cover_pic.png" alt="">
							</div>
						</div>
						<div class="template_content all_template_content" style="display: none;">
							<!--布局模版分类-->
							<!--<ul class="sort_list clearfix"  style="display: none;">
								<li class="sort_item">
									<div class="sort_title">全部</div>
								</li>
								<li class="sort_item">
									<div class="sort_title">春天</div>
								</li>
								<li class="sort_item">
									<div class="sort_title">夏天</div>
								</li>
							</ul>-->
							<!--布局模版列表-->
							<ul class="template_list clearfix">
								<!--<li class="layout_item btn_back">
									<div class="sort_title">返回</div>
								</li>-->
								<!--茶-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_tea" >
											<img class="img_bg" src="webpage/daggerSrc/editContent/img/temp_tea/example_one_bg.jpg" alt="" >
											<div th_editable th_draggable key_usable class="title_box">这是标题</div>
											<div th_editable th_draggable key_usable class="para_box">这是内容</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
								<!--优惠活动-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_onsale" >
											<img class="img_bg" src="webpage/daggerSrc/editContent/img/temp_on_sale/onsale_bg.jpg" alt="" >
											<div key_usable th_draggable class="title_box">
												<div th_editable class="title_content">优惠活动</div>
											</div>
											<div key_usable th_draggable class="acti_item">
												<div th_editable class="acti_title">活动一:</div>
												<div class="acti_content_box">
													<div th_editable class="acti_content">满500送价值￥300礼品一份</div>
												</div>
											</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
								<!--特惠购-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_tetui" >
											<img class="img_bg" src="webpage/daggerSrc/editContent/img/temp_tehui/tehui_bg.jpg" alt="" >
											<div th_editable th_draggable key_usable class="desc_font desc_00">全场5折起</div>
											<div th_editable th_draggable key_usable class="desc_font desc_01">活动时间</div>
											<div th_editable th_draggable key_usable class="desc_font desc_04">2017.10.1-10.10</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
								<!--时尚时装-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_fashion_clothes" >
											<img class="img_bg" src="webpage/daggerSrc/editContent/img/temp_fashion_clothes/fashion_clothes.jpg" alt="" >
											<div th_editable th_draggable key_usable class="desc_font desc_01">UNIQLO</div>
											<div th_editable th_draggable key_usable class="desc_font desc_02">AND</div>
											<div th_editable th_draggable key_usable class="desc_font desc_03">LEMAIRE</div>
											<div th_draggable key_usable class="desc_line"></div>
											<div th_editable th_draggable key_usable class="desc_font desc_04">2017   SPRING&SUMMER</div>
											<div th_editable th_draggable key_usable class="desc_font desc_05">— 6月18日 —</div>
											<div th_editable th_draggable key_usable class="desc_font desc_06">全网指定专营店摩登上市</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
								<!--邀请涵-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_invitation" >
											<img class="img_bg" src="webpage/daggerSrc/editContent/img/temp_invitation/invitation_bg.jpg" alt="" >
											<div th_editable th_draggable key_usable class="desc_font desc_01">欢迎来店</div>
											<div th_draggable key_usable class="desc_line"></div>
											<div th_editable th_draggable key_usable class="desc_font desc_04">观潮汇数据科技</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
								<!--圆图-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_circle_img" >
											<div class="circle_img_box">
												<div th_draggable key_usable class="circle_img_item change_img_item cirle_01">
													<img draggable="false" class="circle_img change_img" src="webpage/daggerSrc/editContent/img/temp_circle_img/001.jpg" alt="">
													<div class="change_img_mask"></div>
												</div>
												<div th_draggable key_usable class="circle_img_item change_img_item cirle_02">
													<img draggable="false" class="circle_img change_img" src="webpage/daggerSrc/editContent/img/temp_circle_img/002.jpg" alt="">
													<div class="change_img_mask"></div>
												</div>
												<div th_draggable key_usable class="circle_img_item change_img_item cirle_03">
													<img draggable="false" class="circle_img change_img" src="webpage/daggerSrc/editContent/img/temp_circle_img/003.jpg" alt="">
													<div class="change_img_mask"></div>
												</div>
											</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--轮播图-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_carousel" >
											<div carousel_id="" class="carousel_box">
												<div class="carousel_item change_img_item">
													<img class="carousel_img change_img" src="webpage/daggerSrc/common/img/cover_pic_hint.png" alt="">
													<div class="change_img_mask"></div>
												</div>
												<div class="carousel_item change_img_item">
													<img class="carousel_img change_img" src="webpage/daggerSrc/common/img/cover_pic_hint.png" alt="">
													<div class="change_img_mask"></div>
												</div>
												<div class="carousel_item change_img_item">
													<img class="carousel_img change_img" src="webpage/daggerSrc/common/img/cover_pic_hint.png" alt="">
													<div class="change_img_mask"></div>
												</div>
											</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 01-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_01" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_001.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 02-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_02" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_002.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 03-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_03" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_003.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 04-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_04" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_004.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 05-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_05" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_005.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 06-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_06" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_006.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>

								<!--国庆 中秋 07-->
								<li temp_tag="tempLayout" class="layout_item template">
									<div template_type="tempLayout" class="template_item">
										<div class="layout_content temp_national_autumn_07" >
											<img class="img_bg" src="http://img7.guanjiaapp.net/image/template/platform/national_autumn_007.jpg" alt="" >
											<div th_editable th_draggable key_usable class="para_box">输入内容...</div>
										</div>
										<div class="menu_bar">
											<img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>
											<img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>
											<img  class="save_pic" src="webpage/daggerSrc/editContent/img/bg_save_pic.png"/>
											<div class="click_move">
												<img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">
												<img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">
												<img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">
												<img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
            </div>
			<div class="pop_window">
				<!--添加分类-->
				<div id="id_add_sort" style="display: none;">
					<div class="pop_window_mask"></div>
					<div class="add_sort_window">
						<p class="add_sort_title">请填写分类名称:</p>
						<input class="input_sort_name" type="text" name="sort_name">
						<div class="btns">
							<button type="button" class="btn_sort_cancle">取消</button>
							<button type="button" class="btn_sort_confirm">确认</button>
						</div>
					</div>
				</div>
			</div>
        </div>
    </div>
	<!--手机预览图-->
	<div class="phone_preview_box">
		<div class="phone_preview">
			<span class="close_preview"></span>
			<iframe class="preview_content" src="" name="contentWin"></iframe>
		</div>
		<div class="preview_mask"></div>
	</div>
	


	<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<!-- 七牛上传 end -->

	<script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
	
     <!-- 此处影响到jquery弹框 -->
    <!-- <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script> -->
    <!-- 配置文件 -->
    <!--<script type="text/javascript" src="webpage/daggerSrc/Formdesign/js/ueditor/ueditor.config.js"></script>-->
    <!-- 编辑器源码文件 -->
    <!--<script type="text/javascript" src="webpage/daggerSrc/Formdesign/js/ueditor/ueditor.all.min.js"></script>-->
    <!-- 上下拖动 -->
    <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script>
	<!--自由拖动-->
    <script src="webpage/daggerSrc/draggabilly/js/draggabilly.pkgd.min.js"></script>
	<!--轮播-->
	<script src="plug-in/slick/slick.js"></script>
    <!--<script src="webpage/daggerSrc/editShop/js/dragula.min.js"></script>-->
    <!--<script src="webpage/daggerSrc/editContent/js/editContent.js"></script>-->
    <!--<script src="webpage/daggerSrc/wangEditor/js/wangEditor.js"></script>-->
	<!--颜色选择-->
    <script src="webpage/daggerSrc/colorpicker/js/colpick.js"></script>
	<!--将dom保存为图片-->
    <script src="webpage/daggerSrc/common/js/dom-to-image.min.js"></script>
    <script src="webpage/daggerSrc/editContent/js/editContent.js?v=1.0.4"></script>
</body>
</html>
<%-- 
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPosterController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPosterPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPosterPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPosterPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPosterPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPosterPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPosterPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPosterPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tPosterPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							作者:
						</label>
					</td>
					<td class="value">
					     	 <input id="author" name="author" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">作者</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							封面图:
						</label>
					</td>
					<td class="value">
					     	 <input id="coverPic" name="coverPic" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">封面图</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							正文:
						</label>
					</td>
					<td class="value">
					     	 <input id="context" name="context" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">正文</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							正文等h5:
						</label>
					</td>
					<td class="value">
					     	 <input id="contextHtml" name="contextHtml" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">正文等h5</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							长链接:
						</label>
					</td>
					<td class="value">
					     	 <input id="url" name="url" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">长链接</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							零售商id:
						</label>
					</td>
					<td class="value">
					     	 <input id="retailerId" name="retailerId" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商id</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							海报状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="postStatus" name="postStatus" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">海报状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body> --%>
