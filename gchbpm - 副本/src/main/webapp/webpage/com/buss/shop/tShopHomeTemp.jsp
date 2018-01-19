<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>首页</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="观潮汇数据科技">
    <meta name="description" content="观潮汇管家系统，短信制作，商品SHOW" />
    <meta name="Keywords" content="观潮汇管家系统，短信制作，商品资讯，图文制作，内容排版，大数据处理" />

    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
    <link rel="stylesheet" href="plug-in/slick/slick.css" />
    <link rel="stylesheet" href="plug-in/slick/slick-theme.css" />
    <link rel="stylesheet" href="webpage/daggerSrc/editShopHome/css/editShopHome.css" />
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
<body>
    <!--顶部按钮-->
    <div class="th_top">
        <button class="btn_menu btn_save">保存</button>
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
	<input type="hidden" id="id" value="${tShopHomeTemp.id }" />
	
    <div class="th_content">
        <!--编辑图文内容-->
        <div class="edit_content">
            <div class="bg_top">
                <div class="bg_bottom">
                    <div class="mobile_content" id="mobile_content">
                    	<c:if test="${not empty tShopHomeTemp.content }">
                    		${tShopHomeTemp.content}
                    	</c:if>
                    	<c:if test="${empty tShopHomeTemp.content }">
	                        <div temp_type="singleInfo" class="content_item info_item single selected default_temp">
	                            <div class="handle_bar"></div>
	                            <div class="single_box">
	                                <img class="link_info" temp_link="linklinklink"  src="webpage/daggerSrc/common/img/cover_pic.png" alt="">
	                            </div>
	                            <div class="btn_check_all check_all_info">查看全部资讯</div>
	                        </div>
	                        <div temp_type="scrollGoods" class="content_item goods_item horizontal default_temp">
	                            <div class="handle_bar"></div>
	                            <h2 class="h_title">当红人气王</h2>
	                            <div obj_id="" class="scroll_box">
	                                    <ul class="scroll_list clearfix">
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                        <li class="scroll_item fl"><img class="link_info" temp_link="linklinklink" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>
	                                    </ul>
	                            </div>
	                            <div class="btn_check_all check_all_goods">查看全部商品</div>
	                        </div>
	                       </c:if> 
                    </div>
                </div>
            </div> 
        </div>
    </div>
    <!--右侧菜单-->
    <div class="th_menu">
        <div class="left_bar">
            <button class="left_btn btn_info active">资讯</button>
            <button class="left_btn btn_goods">商品</button>
        </div>
        <div class="right_content">
            <!--资讯模块-->
            <div class="info_menu_box">
                <h2 class="module_title">资讯模块</h2>
                <div class="layout">
                    <h3 class="layout_style">资讯排列图</h3>
                    <div style_name="singleInfo" class="style_item single_style active">单篇（默认）</div>
                    <div style_name="carouselInfo" class="style_item carrousel_style">轮播图</div>
                </div>
                <div class="split_line"></div>
                
                <ul class="link_list">
                    
                </ul>
            </div>

            <!--商品模块-->
            <div class="goods_menu_box hide">
                <h2 class="module_title">商品模块</h2>
                <div class="layout">
                    <h3 class="layout_style">商品排列图</h3>
                    <div style_name="twoGoods" class="style_item two_style active">双列商品（默认）</div>
                    <div style_name="scrollGoods" class="style_item scroll_style">左右滑动商品</div>
                </div>
                <div class="split_line"></div>
                <div class="title_box">
                    <h3 class="goods_title">标题</h3>
                    <input class="input_title" type="text" name="goodsModuleTitle" value="">
                </div>
                <ul class="link_list">
                    <li class="info_link_item">
                        <div class="btn_move_box">
                            <div class="link_title">链接</div>
                            <div class="btn_move">
                                <button class="btn_up"></button>
                                <button class="btn_down"></button>
                                <button class="btn_close"></button>
                            </div>
                        </div>
                        <div class="link_url">
                            <p class="url_content">//h5.m.taobaotaobaotaobaotaobao.com/awp/core</p>
                            <button class="btn_changelink"></button>
                        </div>
                        <div class="info_pic_box">
                            <img class="info_pic" src="webpage/daggerSrc/common/img/cover_pic.png" alt="">
                            <p class="pic_note">建议选择340x340的图片，类型：jpg.png</p>
                        </div>
                    </li>
                </ul>
            </div>

        </div>
    </div>

	<!--是 否  弹窗-->
	<div class="simple_popwindow">
		<div class="mask_simple_popwindow"></div>
		<div class="simple_popwindow_content">
			<p class="window_title">确认删除此模块？</p>
			<div class="btn_popwindow_box">
				<button type="button" class="simple_popwindow_no" id="simple_popwindow_no">否</button>
				<button type="button" class="simple_popwindow_yes" id="simple_popwindow_yes">是</button>
			</div>
		</div>
	</div>
    
    <script src="plug-in/slick/slick.js"></script>
    <script src="plug-in/iscroll/iscroll.min.js"></script>
    <!-- 上下拖动 -->
    <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script>
    <script src="webpage/daggerSrc/editShopHome/js/editShopHome.js"></script>
</body>
</html>