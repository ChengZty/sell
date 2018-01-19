package org.jeecgframework.core.common.model.json;

import java.util.List;

/**
 * $.ajax 查询列表grid
 * @author liuxing 
 * 20170305
 */
public class DataGridJson {

	private boolean success = true;// 是否成功
	private List results;// 结果集
	private int total;//总记录数
	private int totalPage = 1;//总页数
	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List getResults() {
		return results;
	}
	public void setResults(List results) {
		this.results = results;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**设置列表参数
	 * @param grid
	 * @param list
	 */
	public static void setDataGrid(DataGridJson grid, List list,int total) {
		// TODO Auto-generated method stub
		grid.setResults(list);
		grid.setTotal(total);//总记录数
		int pages = grid.getTotal()/grid.getRows()+1;//总页数
		if(grid.getTotal()%grid.getRows()==0&&grid.getTotal()>0){
			pages  = grid.getTotal()/grid.getRows() ;
		}
		grid.setTotalPage(pages);
	}
	
}
