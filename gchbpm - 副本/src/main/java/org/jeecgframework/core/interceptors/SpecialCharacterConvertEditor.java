package org.jeecgframework.core.interceptors;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;

/**
 * 特殊字符替换
 * 20170511添加
 * @author  
 *
 */
public class SpecialCharacterConvertEditor extends PropertyEditorSupport {
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			text = text
//			.replaceAll("\"", "&quot;")
						.replaceAll("'", "&#39;");
			setValue(text);
		} else {
			setValue(null);
		}
	}
}
