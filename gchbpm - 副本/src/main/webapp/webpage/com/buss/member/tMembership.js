
  $(function () {
      $('#templatePic_u').uploadify({buttonText:'浏览图片',
          progressData:'speed',
          multi:false,
          height:25,
          queueID:'progress_bar_m',
          overrideEvents:['onDialogClose'],
          fileTypeDesc:'文件格式:',
          fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
          fileSizeLimit:'200KB',
          swf:'plug-in/uploadify/uploadify.swf',
          uploader:'tMembershipController.do?uploadPic&sessionId=${pageContext.session.id}',
          auto:true,
          onUploadSuccess : function(file, data, response) {
        	  var d=$.parseJSON(data);
              if(d.success){
                  var d=$.parseJSON(data);
                  $("#prePic").attr("src",d.msg);
                  $("#headPic").val(d.msg);
              }

          }
      });
  });