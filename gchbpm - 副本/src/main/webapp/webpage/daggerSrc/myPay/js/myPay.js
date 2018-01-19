/// <reference path="F:/tianhu/typings/typings/index.d.ts" />

$(document).ready(function(){
    $('.my_package').parents('.content_item').css({
        'height': $('.my_package').outerHeight(true)
    });
});