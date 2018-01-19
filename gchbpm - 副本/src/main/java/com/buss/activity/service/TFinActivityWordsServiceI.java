package com.buss.activity.service;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

public interface TFinActivityWordsServiceI extends CommonService{

 	public void getWordsGoodsList(HttpServletRequest request, DataGrid dataGrid);

	public void doDelActWords(String ids,String wordsId);
}
