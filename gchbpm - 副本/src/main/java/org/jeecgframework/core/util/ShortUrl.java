package org.jeecgframework.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 生成短链接
 * @author lenovo
 *
 */
public class ShortUrl {   
//    public static void main(String[] args) {   
//        String url = "http://www.sunchis.com";   
//        for (String string : ShortText(url)) {   
//            print(string);   
//            break;
//        }   
//    }   
       
    public static String[] ShortText(String longUrl){   
        String key = "XuLiang";                 //自定义生成MD5加密字符串前的混合KEY   
        String[] chars = new String[]{          //要使用生成URL的字符   
            "a","b","c","d","e","f","g","h",   
            "i","j","k","l","m","n","o","p",   
            "q","r","s","t","u","v","w","x",   
            "y","z","0","1","2","3","4","5",   
            "6","7","8","9","A","B","C","D",   
            "E","F","G","H","I","J","K","L",   
            "M","N","O","P","Q","R","S","T",   
            "U","V","W","X","Y","Z"   
        };   
           
        String hex = Encript.md5(key + longUrl);   
        int hexLen = hex.length();   
        int subHexLen = hexLen / 8;   
        String[] ShortStr = new String[4];   
           
        for (int i = 0; i < subHexLen; i++) {   
            String outChars = "";   
            int j = i + 1;   
            String subHex = hex.substring(i * 8, j * 8);   
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);   
               
            for (int k = 0; k < 6; k++) {   
                int index = (int) (Long.valueOf("0000003D", 16) & idx);   
                outChars += chars[index];   
                idx = idx >> 5;   
            }   
            ShortStr[i] = outChars;   
        }   
           
        return ShortStr;   
    }   
       
    /**
     * 单个生成短链接
     * @param longUrl
     * @return
     */
    public static String SingleShortText(String longUrl){   
        String key = "XuLiang";                 //自定义生成MD5加密字符串前的混合KEY   
        String[] chars = new String[]{          //要使用生成URL的字符   
            "a","b","c","d","e","f","g","h",   
            "i","j","k","l","m","n","o","p",   
            "q","r","s","t","u","v","w","x",   
            "y","z","0","1","2","3","4","5",   
            "6","7","8","9","A","B","C","D",   
            "E","F","G","H","I","J","K","L",   
            "M","N","O","P","Q","R","S","T",   
            "U","V","W","X","Y","Z"   
        };   
           
        String hex = Encript.md5(key + longUrl);   
        int hexLen = hex.length();   
        int subHexLen = hexLen / 8;   
        String[] ShortStr = new String[4];   
           
        for (int i = 0; i < subHexLen; i++) {   
            String outChars = "";   
            int j = i + 1;   
            String subHex = hex.substring(i * 8, j * 8);   
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);   
               
            for (int k = 0; k < 6; k++) {   
                int index = (int) (Long.valueOf("0000003D", 16) & idx);   
                outChars += chars[index];   
                idx = idx >> 5;   
            }   
            ShortStr[i] = outChars;   
        }   
           
        return ShortStr[0];   
    }  
    
    /**
     * 批量生成短链接
     * @param longUrls
     * @return
     */
    public static Map<String,String> DoubleShortText(List<String> longUrls){
    	Map<String,String> map = new HashMap<String, String>();
    	for (String longUrl : longUrls) {
			String[] strs = ShortText(longUrl);
			for (String shortParam : strs) {
				if(Utility.isEmpty(map.get(shortParam))){
					map.put(shortParam, longUrl);//<短链接参数，长连接>
					map.put(longUrl, shortParam);//<长连接,短链接参数>
					break;
				}
			}
		}
    	return map;
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}   