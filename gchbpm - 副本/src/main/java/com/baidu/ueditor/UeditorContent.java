package com.baidu.ueditor;

import java.util.HashMap;
/**   
 * @author liuxing
 * @date 2016-06-03 16:00:50
 *
 */
public class UeditorContent {
	
	/**去掉字符串前面的空的p标签和只带<br/>的p标签
	 * @param s
	 * @return HashMap<String,String> map
	 * map 中isLast=Y时返回需要的字符串map.get("result")
	 */
	public static  HashMap<String,String> clearEmpty(String s){
		//<p>						</p><p><br/></p><p><br/></p><p><span style=\"line-height: 1.6em;\">没有速度阿萨德阿萨德是</span><br/></p>
		 HashMap<String,String> map = new HashMap<String,String>();
		 map.put("isLast", "N");
		 if(s!=null){
			 int n = s.indexOf("</p>");
			 String s1 = null; 
			 if(n!=-1){//有</p>
				 s1 = s.substring(3, n); 
				 String text = s1.trim();
				 if(("").equals(text)||("&nbsp;").equals(text)||("<br/>").equals(text)){
					 s = s.substring(n+4); 
					 map.put("result", s);
				 }else{
//					 int m = text.indexOf("<br/>");
//					 if(m!=-1&&text.length()<15){
//						 s = s.substring(n+4); 
//						 map.put("result", s);
//					 }else{
						 map.put("isLast", "Y");
						 map.put("result", s);
//					 }
				 }
			 }else{
				 map.put("isLast", "Y");
				 map.put("result", s);
			 }
		 }else{
			 map.put("isLast", "Y");
			 map.put("result", s);
		 }
			return map;
	 }
	
	 public static void main(String[] args) {
		 String s = "<p>						</p><p><br/></p><p>22<br/></p><p><span style=\"line-height: 1.6em;\">没有速度阿萨德阿萨德是</span><br/></p>";
//		 String s = "<p><br/></p><p><span style=\"line-height: 1.6em;\">没有速度阿萨德阿萨德是</span><br/></p>";
//		 String s = "<p>   </p><p><span sty</p>";
		 HashMap<String,String> map = new HashMap<String,String>();
		 map.put("result", s);
		 do {
			 map =clearEmpty(map.get("result"));
		} while ("N".equals(map.get("isLast")));
		 System.out.println(map.get("result"));
	    }
}
