/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */
CKEDITOR.editorConfig = function( config ) {

	config.enterMode = CKEDITOR.ENTER_BR;// CKEDITOR.ENTER_P;CKEDITOR.ENTER_DIV
	config.fullPage= true;
	config.allowedContent= true;
	config.font_defaultLabel = '宋体';
	config.fontSize_defaultLabel = '12px';
	config.tabSpaces = 2;

	config.language = "zh-cn";
	config.image_previewText = ' '; // 预览区域显示内容
	config.font_names = '宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'
			+ config.font_names;
	config.skin = 'moonocolor';
	config.toolbarGroups = [
	        { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
	        { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
            { name: 'insert' },{name:'font'},{ name: 'tools' },
            { name: 'others' },{ name: 'editing',     groups: [ 'find', 'selection' ] },
            { name: 'links' },{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup', 'font'] },
            { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
            { name: 'forms' },{ name: 'styles' },{ name: 'colors' }];
	
	// 下面是编辑器工具栏配置，"/"代表换行  
	config.toolbar_Full = [  
	                      ['Source','-','NewPage','Preview','-','Templates'],  
	                      ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],  
	                      ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],  
	                      ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],  
	                      '/',  
	                      ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],  
	                      ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],  
	                      ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],  
	                      ['Link','Unlink','Anchor'],  
	                      ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],  
	                      '/',  
	                      ['Styles','Format','Font','FontSize'],  
	                      ['TextColor','BGColor']  
	                     ];  
	//以下是后添加的验证非法数据  
	config.protectedSource.push(/<\s*iframe[\s\S]*?>/gi);  //<iframe> tags.                
	config.protectedSource.push(/<\s*frameset[\s\S]*?>/gi) ; // <frameset> tags.  
	config.protectedSource.push(/<\s*frame[\s\S]*?>/gi) ; // <frame> tags.  
	config.protectedSource.push(/<\s*script[\s\S]*?\/script\s*>/gi) ; // <SCRIPT> tags.  
	config.removePlugins = 'save';
};
