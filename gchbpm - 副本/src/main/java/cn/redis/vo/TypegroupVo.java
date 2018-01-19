package cn.redis.vo;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.buss.goods.entity.TGoodsPicEntity;
import com.buss.goods.entity.TGoodsStoreEntity;

@SuppressWarnings("serial")
public class TypegroupVo implements java.io.Serializable{
	private String id;
	private String typegroupname;
	private String typegroupcode;
	List<TypeVo> typeList = new ArrayList<TypeVo>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypegroupname() {
		return typegroupname;
	}
	public void setTypegroupname(String typegroupname) {
		this.typegroupname = typegroupname;
	}
	public String getTypegroupcode() {
		return typegroupcode;
	}
	public void setTypegroupcode(String typegroupcode) {
		this.typegroupcode = typegroupcode;
	}
	public List<TypeVo> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<TypeVo> typeList) {
		this.typeList = typeList;
	}
	
}
