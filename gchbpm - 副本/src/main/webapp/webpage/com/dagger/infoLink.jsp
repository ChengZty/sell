<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>海报列表</title>
    <t:base type="jquery,easyui"></t:base>
    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
    <link rel="stylesheet" href="webpage/daggerSrc/infoLink/css/infoLink.css" />
   <script type="text/javascript">
    var urlPre = "${url}";
   </script>
</head>
<body>
<form id="form" action="">
<div class="xj_div_box">
    <div class="head_box">
        <p class="title">资讯链接</p>
<!--         <div class="info_close"> -->
<!--             <img class="info_close_img" src="webpage/daggerSrc/infoLink/img/info_close.png" alt=""> -->
<!--         </div> -->
    </div>
    <div class="context_box">
        <div class="search_box">
            <input class="keyword" type="text" name="keywords" placeholder="请输入关键字" >
            <button type="button" class="btn_search">搜索</button>
        </div>
        <p class="goods_number">资讯（共<span id="total">0</span>条）</p>
        <div class="goods_box">
            <ul id="dataBody">
            <!-- 
                <li class="goods_item">
                    <div class="goods_item_box">
                        <img class="goods_img" src="../../daggerSrc/infoLink/img/test_info_pic.png" alt="">
                        <p class="goods_name">2016时尚大衣</p>
                        <p class="info_url">www.baidu.com</p>
                        <button class="btn_goods_link">选择链接</button>
                    </div>
                </li>
                <li class="goods_item">
                    <div class="goods_item_box">
                        <img class="goods_img" src="../../daggerSrc/infoLink/img/test_info_pic.png" alt="">
                        <p class="goods_name">2016时尚大衣</p>
                        <p class="info_url">www.baidu.com</p>
                        <button class="btn_goods_link">选择链接</button>
                    </div>
                </li>
                <li class="goods_item">
                    <div class="goods_item_box">
                        <img class="goods_img" src="../../daggerSrc/infoLink/img/test_info_pic.png" alt="">
                        <p class="goods_name">2016时尚大衣</p>
                        <p class="info_url">www.baidu.com</p>
                        <button class="btn_goods_link">选择链接</button>
                    </div>
                </li>
                <li class="goods_item">
                    <div class="goods_item_box">
                        <img class="goods_img" src="../../daggerSrc/infoLink/img/test_info_pic.png" alt="">
                        <p class="goods_name">2016时尚大衣</p>
                        <p class="info_url">www.baidu.com</p>
                        <button class="btn_goods_link">选择链接</button>
                    </div>
                </li>
                <li class="goods_item">
                    <div class="goods_item_box">
                        <img class="goods_img" src="../../daggerSrc/infoLink/img/test_info_pic.png" alt="">
                        <p class="goods_name">2016时尚大衣</p>
                        <p class="info_url">www.baidu.com</p>
                        <button class="btn_goods_link">选择链接</button>
                    </div>
                </li>
                 -->
            </ul>
            <div style="position: relative;right: 500px;"><input id="urlText" type="text"/></div>
        </div>
    </div>
    <div class="page_box">
        <input type="hidden" id="rows" name="rows" value="5"/>
        <input type="hidden" id="page" name="page" value="1"/>
        <div class="btn_up_box">
            <img class="page_up_img" src="webpage/daggerSrc/choosePicture/img/btn_up.png" alt="">
        </div>
        <div class="btn_box">
            <div class="page_number_dive">
                <p class="page"><span id="curr_page">1</span>/<span id="totalPage">1</span></p>
            </div>
            <div class="btn_next_box">
                <img class="page_next_img" src="webpage/daggerSrc/choosePicture/img/btn_next.png" alt="">
            </div>
            <div class="page_input_box">
                <input class="page_input" id="page_input" type="number"  min="1">
            </div>
            <div class="page_skip_box">
                <p class="page_skip" >跳转</p>
            </div>
        </div>
    </div>
</div>
</form>
<script src="webpage/daggerSrc/infoLink/js/infoLink.js?v=1.2"></script>
</body>
</html>
</html>