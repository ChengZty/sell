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

    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
    <link rel="stylesheet" href="webpage/daggerSrc/editContent/css/editContent.css" />  
  
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //不能删除正文H5内容
  var contextHtml = '${tPosterPage.contextHtml}';
  var title = '${tPosterPage.title}';
  var coverPic = '${tPosterPage.coverPic}';
  
  //编写自定义JS代码
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
	<input type="hidden" id="id" value="${tPosterPage.id }"/> 
	
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
    </div>
    <!--右侧菜单-->
    <div class="th_menu">
        <div class="left_bar">
            <button class="left_btn btn_text active">文本</button>
            <button temp_tag="tempPic" class="template left_btn btn_pic">图片</button>
            <button temp_tag="tempGoods" class="template left_btn btn_goods">商品</button>
        </div>
        <div class="btn_item right_content">
            <div class="menu_info">
                <h2 class="menu_title">资讯编辑</h2>
                <div class="template_box">
                    <div temp_tag="tempTitle" class="template temp_item temp_title active">标题</div>
                    <div temp_tag="tempAuthor" class="template temp_item temp_author">作者</div>
                    <div temp_tag="tempContent" class="template temp_item temp_content">正文内容</div>
                </div>
            </div>
        </div>
    </div>
     <!-- 此处影响到jquery弹框 -->
    <!-- <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script> -->
    <!-- 配置文件 -->
    <script type="text/javascript" src="webpage/daggerSrc/Formdesign/js/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="webpage/daggerSrc/Formdesign/js/ueditor/ueditor.all.min.js"></script>
    <!-- 上下拖动 -->
    <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script>
    <!--<script src="webpage/daggerSrc/editShop/js/dragula.min.js"></script>-->
    <script src="webpage/daggerSrc/editContent/js/editContent.js"></script>
</body>
</html>
