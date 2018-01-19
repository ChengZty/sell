<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>管家点评</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<!-- 七牛上传 end -->
  <style type="text/css">
  .uploadify-button{
      background-image: -webkit-linear-gradient(bottom, #505050 0%, #707070 100%);
      border-radius: 30px;
      border: 2px solid #808080;
      color: #FFF;
      font: bold 12px Arial, Helvetica, sans-serif;
      text-align: center;
      }
  .pic_div{
  	width: 100px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
    float: left;
  }
  .video_div{
  	width: 240px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
    float: left;
  }
  .delete {
    display: block;
    color: white;
    background: rgba(255, 255, 255, 0.2);
    width: 20px;
    height: 20px;
    line-height: 20px;
    top: 0;
    right: 0;
    position: absolute;
    text-align: center;
    z-index: 100;
}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGuideRecommendInfoController.do?doAdd" beforeSubmit="initPicsAndVideos" tiptype="4">
					<input name="goodsId" type="hidden" value="${goods_Id }">
			     	<input name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailer_Id }">
			     	 <input id="guideId" name="guideId" type="hidden" >
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							导购:
						</label>
					</td>
					<td class="value">
					     	 <input id="guideName"  type="text" style="width: 150px" readonly="readonly" />
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findGuides()" >
										<span class="icon-search l-btn-icon-left">选择导购</span></a>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购ID</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							文字描述:
						</label>
					</td>
					<td class="value">
							<textarea rows="5" style="width: 100%" name="description"></textarea>
							<label class="Validform_label" style="display: none;">文字描述</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							阅读量:
						</label>
					</td>
					<td class="value">
					     	 <input id="readNum" name="readNum" type="text" style="width: 150px" size="5" class="inputxt" datatype="n">
							<span class="Validform_checktip">大于0的整数</span>
							<label class="Validform_label" style="display: none;">阅读量</label>
					</td>
				</tr>
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								正面图:
							</label>
						</td>
						<td>
              					<div id="container_1" >
		                            <a  id="pickfiles_1" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
		                            </a>
		                            <div id="container_1_div" style="min-height: 100px;">
		                            
		                            </div>
		                      </div>
						</td>
					</tr>
					<!-- <tr>
						<td align="right">
							<label class="Validform_label">
								搭配图:
							</label>
						</td>
						<td>
							<div id="container_2" >
		                            <a  id="pickfiles_2" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
		                            </a>
		                            <div id="container_2_div" style="min-height: 100px;">
		                            
		                            </div>
		                      </div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								细节图:
							</label>
						</td>
						<td>
							<div id="container_3" >
		                            <a  id="pickfiles_3" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
		                            </a>
		                            <div id="container_3_div" style="min-height: 100px;">
		                            
		                            </div>
		                      </div>
						</td>
					</tr> -->
					<tr>
						<td align="right">
							<label class="Validform_label">
								视频:
							</label>
						</td>
						<td>
							<div id="container_4" >
		                            <a  id="pickfiles_4" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择视频</div>
		                            </a>
		                            <div id="container_4_div" style="min-height: 120px;">
		                            
		                            </div>
		                      </div>
		                      <span class="Validform_checktip">视频为mp4格式,大小不超过20M</span>&nbsp;&nbsp;
		                      <span id="percent"></span>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/buss/newGoods/tGuideRecommendInfo.js?v=1.11"></script>
 <script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.3"></script>
 