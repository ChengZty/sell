package cn.redis.service;

import java.util.Map;
import java.util.Set;



public interface RedisService {
	/**
	 * 设置值 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value);
	 
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public String get(final String key);
	 /**
     * 指定DEL操作
     * 
     * @param key
     * @return
     */
    public Long del(final String key);
    
    
    /**
     * 设置生存时间，单位为秒
     * 
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds);
    
    /**
     * 执行SET操作，并且设置生存时间，单位为秒
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(final String key, final String value, final Integer seconds);

	/**
	 * 初始化字典表
	 */
	public void initAllTypeGroups();
	/**
	 * 获取自增长
	 * @param key
	 * @return
	 */
	public Long incr(final String key);
	/**
	 * 获取自减
	 * @param key
	 * @return
	 */
	public Long decr(final String key);
	
	/**
	 * 批量删除
	 * @param key
	 * @return
	 */
	public void batchDel(final String key);
	
	 /**
     * 执行批量SET操作
     * @param Map<String,String> map
     * @param nxxx  NX 不存在时set key,XX 存在时 set key
     * @param expx 时间单位 EX 秒,PX 毫秒
     * @param time 生存时间
     * @return
     */
	public String batchSet(final Map<String,String> map,final String nxxx,final String expx,final int time);
	
	/**
     * 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
     * @param key
     * @return
     */
	Long getEffectiveTime(String key);
    /**
     * 系统参数写入缓存
     */
	void initAllSysParamters();

	 /**
     * 执行hmset操作，并且设置生存时间(大于0则设置生存时间)，单位为秒
     * @param key
     * @param hash
     * @param seconds
     * @return
     */
	String hmset(String key, Map<String, String> hash, int seconds);

	 /**
     * 执行hget操作，并且设置生存时间(大于0则设置生存时间)，单位为秒
     * @param hashMapkey hashMap的key
     * @param mapkey hashMap的value里的key
     * @return hashMap的value
     */
	String hget(String hashMapkey, String mapkey);

	/***
	 * 通过key模糊获取keys
	 * key  
	 */
	Set<String> hkeys(String key);

}
