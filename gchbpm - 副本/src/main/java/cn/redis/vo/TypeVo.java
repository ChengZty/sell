package cn.redis.vo;

@SuppressWarnings("serial")
public class TypeVo implements java.io.Serializable{
	private String id;
	private String typecode;
	private String typename;
	private String typegroupid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypegroupid() {
		return typegroupid;
	}
	public void setTypegroupid(String typegroupid) {
		this.typegroupid = typegroupid;
	}
	
}
