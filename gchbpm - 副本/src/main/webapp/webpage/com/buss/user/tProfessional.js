
 function setTopCategoryName(){
	  var name = $("#topCategoryId option:selected").text();
	  $("#topCategoryName").val(name);
  }
 
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}