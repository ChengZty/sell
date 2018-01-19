<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>选择图片</title>
      <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="观潮汇数据科技">
    <meta name="description" content="观潮汇管家系统，短信制作，商品SHOW" />
    <meta name="Keywords" content="观潮汇管家系统，短信制作，商品资讯，图文制作，内容排版，大数据处理" />

    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
    <link rel="stylesheet" href="webpage/daggerSrc/choosePicture/css/choosePicture.css" />
    
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <%-- <t:base type="jquery,easyui,tools,DatePicker,layer"></t:base> --%>
  <style type="text/css">
  
  </style>
 
 </head>
 <body>
 <form id="form" action="">
<div class="xj_div_box">
    <div class="div_head">
        <p class="title">选择图片</p>
    </div>
    <div class="div_content">
        <div class="classify">
            <ul id="ImgClass">
            </ul>
            <ul>
					<li><div class="classify_item_box classify_item_addClass">
							<p class="classify_item">+新建分组</p>
						</div>
						<div class="new_classify">
							<p class="new_classify_title">创建分组</p>
							<form method="post" action="" id="classform">
								<input class="new_classify_input" id="input_class"
									name="classname" type="text">
							</form>
							<div class="new_classify_button">
								<button class="btn_cancel" onclick="return false;">取消</button>
								<button class="btn_affirm" id="btn_affirm_addclass" onclick="javascript:addPicClass(); return false;">确认</button>
							</div>

						</div>
					</li>
				</ul>
        </div>
        <div class="uploading">
            <form method="post" action="" id="myform" enctype="multipart/form-data">
					<button class="btn_uploading" id="btn_uploading">本地上传</button>
					<input type="file" id="files" name="files" onchange="doChange()"
						required="required" multiple style="opacity: 0" />
			</form>			
        </div>
        <div class="pic_box">
            <div class="pic_list">
                <ul class="pic_ul" id="dataBody">
                </ul>
            </div>
            <div class="page_box">
            	<!-- form一起提交的参数 -->
            	<input type="hidden" id="rows" name="rows" value="10"/>
        		<input type="hidden" id="page" name="page" value="1"/>
        		<input type="hidden" id="classId" name="classId" value="0"/><!--默认为全部 分类 -->
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
	                    <input class="page_input" id="page_input" type="number" min="1">
	                </div>
	                <div class="page_skip_box">
	                    <p class="page_skip" >跳转</p>
	                </div>
                </div>
            </div>
        </div>
    </div>
    <div class="div_button">
        <div class="choose_number_box">
            	<p class="choose_number">
					已选<span id="chooseNumber">0</span>个，可选1个
				</p>
        </div>
        <div class="button_box">
            <button class="btn_cancel btn_cancel_close">取消</button>
            <button class="btn_affirm btn_affirm_upPic" id="upPicBtn">确认</button>
        </div>
    </div>
</div>
</form>
<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="webpage/daggerSrc/choosePicture/js/choosePicture.js"></script>
</body>
</html>