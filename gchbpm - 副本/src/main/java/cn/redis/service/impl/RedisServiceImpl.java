package cn.redis.service.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import cn.redis.service.Function;
import cn.redis.service.RedisService;
import cn.redis.vo.TypeVo;
import cn.redis.vo.TypegroupVo;

import com.buss.param.entity.TSysParameterEntity;
import com.buss.param.entity.vo.TSysParameterVO;

/**
 * 
 * 分片式集群的Redis操作
 *
 */
@Service("redisService")
@Transactional
public class RedisServiceImpl implements RedisService{

    @Autowired(required = false)//如果运行环境（Spring）中有就注入，没有就忽略
    private ShardedJedisPool shardedJedisPool;
    @Autowired
    private ICommonDao commonDao;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private <T> T execute(Function<T, ShardedJedis> fun) {
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            //shardedJedis = shardedJedisPool.getResource();
        	shardedJedis = getShardedJedis();
            return fun.callback(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        return null;
    }

    
    private  ShardedJedis getShardedJedis()
	{
		int timeoutCount = 0;
		while (true) // 如果是网络超时则多试几次
		{
			try
			{
				ShardedJedis shardedJedis = shardedJedisPool.getResource();
				return shardedJedis;
			} catch (Exception e)
			{
				if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException)
				{
					timeoutCount++;
					LogUtil.warning("getJedis timeoutCount="+timeoutCount);
					if (timeoutCount > 3)
					{
						break;
					}
				}else
				{
					LogUtil.error("getJedis error", e);
					break;
				}
			}
		}
		return null;
	}
    
    
    /**
     * 执行SET操作
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                return e.set(key, value);
            }
        });
    }

    /**
     * 指定GET操作
     * 
     * @param key
     * @return
     */
    public String get(final String key) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                return e.get(key);
            }
        });
    }

    /**
     * 指定DEL操作
     * 
     * @param key
     * @return
     */
    public Long del(final String key) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.del(key);
            }
        });
    }

    /**
     * 设置生存时间，单位为秒
     * 
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, seconds);
            }
        });
    }

    /**
     * 执行SET操作，并且设置生存时间，单位为秒
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(final String key, final String value, final Integer seconds) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                String str = e.set(key, value);
                //设置生存时间
                e.expire(key, seconds);
                return str;
            }
        });
    }
    /**
     * 数据字典写入缓存
     */
    public void initAllTypeGroups() {
    	 ShardedJedis shardedJedis = null;
         try {
        	 String initDic = ResourceUtil.getConfigByName("initDic");//是否初始化字典
        	 if("true".equals(initDic)){
        		 // 从连接池中获取到jedis分片对象
        		 shardedJedis = this.getShardedJedis();
        		 String groupSql = "select id,typegroupname,typegroupcode from t_s_typegroup";
        		 List<TypegroupVo> typeGroups = this.commonDao.findObjForJdbc(groupSql, TypegroupVo.class);
        		 String typeSql = "select id,typecode,typename,typegroupid from t_s_type";
        		 List<TypeVo> typeList = this.commonDao.findObjForJdbc(typeSql, TypeVo.class);
        		 //把字典组和字典内容组装起来
        		 this.fillTypeGroups(typeGroups,typeList);
//        		 List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
        		 for (TypegroupVo typeGroup : typeGroups) {
        			 shardedJedis.set(typeGroup.getTypegroupcode(), MAPPER.writeValueAsString(typeGroup));
//        			 TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
//        			 List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
//        			 TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
        		 }
        		 LogUtil.info("初始化数据字典写入redis");
        	 }
         } catch (Exception e) {
        	 LogUtil.error("数据字典写入缓存异常",e);
             //e.printStackTrace();
         } finally {
             if (null != shardedJedis) {
                 // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                 shardedJedis.close();
             }
         }
	}
    
    /**把字典组和字典内容组装起来*/
    private void fillTypeGroups(List<TypegroupVo> typeGroups, List<TypeVo> typeList) {
    	Map<String,List<TypeVo>> typeGroupMap = new HashMap<String,List<TypeVo>>();
    	for(TypeVo vo : typeList){
			if(typeGroupMap.containsKey(vo.getTypegroupid())){
				List<TypeVo> typeVoList = typeGroupMap.get(vo.getTypegroupid());
				typeVoList.add(vo);
			}else{
				List<TypeVo> typeVoList = new ArrayList<TypeVo>();
				typeVoList.add(vo);
				typeGroupMap.put(vo.getTypegroupid(), typeVoList);
			}
		}
		for(TypegroupVo typeGroup : typeGroups){
			if(typeGroupMap.containsKey(typeGroup.getId())){
				typeGroup.setTypeList(typeGroupMap.get(typeGroup.getId()));
			}
		}
		
	}


	/**
     * 系统参数写入缓存
     */
    @Override
    public void initAllSysParamters() {
    	 ShardedJedis shardedJedis = null;
         try {
             // 从连接池中获取到jedis分片对象
//             shardedJedis = shardedJedisPool.getResource();
        	 shardedJedis = this.getShardedJedis();
         	 List<TSysParameterEntity> sysParams = this.commonDao.loadAll(TSysParameterEntity.class);
         	TSysParameterVO vo = null;
         	 if(Utility.isNotEmpty(sysParams)){
         		 Set<String> retailerIds = new HashSet<String>();//零售商ID set集合
         		 for(TSysParameterEntity entity : sysParams){
         			retailerIds.add(entity.getRetailerId());
         		 }
         		 if(retailerIds.size()>0){
         			 for(String retId : retailerIds){
         				 List<TSysParameterVO> list = new ArrayList<TSysParameterVO>();
         				 for (TSysParameterEntity tSysParameterEntity : sysParams) {
         					 if(retId.equals(tSysParameterEntity.getRetailerId())){
         						 vo = new TSysParameterVO();
         						 vo.setId(tSysParameterEntity.getId());
         						 vo.setParaCode(tSysParameterEntity.getParaCode());
         						 vo.setParaValue(tSysParameterEntity.getParaValue());
         						 list.add(vo);
         					 }
         				}
         				 //系统参数key格式：sysParams_retailerId
         				shardedJedis.set(TSysParameterEntity.SYS_PARAMS+"_"+retId, MAPPER.writeValueAsString(list));
         			 }
         		 }
         		 
         	 }
         } catch (Exception e) {
        	 LogUtil.error("异常",e);
             //e.printStackTrace();
         } finally {
             if (null != shardedJedis) {
                 // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                 shardedJedis.close();
             }
         }
	}
    
    /**
     * 自动增长
     */
	@Override
	public Long incr(final String key) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.incr(key);
            }
        });
	}
    /***
     * 自减
     */
	@Override
	public Long decr(final String key) {
		 return this.execute(new Function<Long, ShardedJedis>() {
	            @Override
	            public Long callback(ShardedJedis e) {
	                return e.decr(key);
	            }
	     });
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void batchDel(final String key) {
		 this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
     	        Collection<Jedis> shards = e.getAllShards();
     	        for (Jedis shard : shards) {
//     	        	System.out.println(key);
//     	        	System.out.println(shard.keys(key));
     	        	Set<String> set = shard.keys(key);
     	        	Iterator<String> it = set.iterator();  
	     	          while(it.hasNext()){  
 	     	              String keyStr = it.next();  
	     	              /*System.out.println(keyStr); */ 
	     	             shard.del(keyStr);
	     	          }  
     	        }
                return 1L;
            }
		});
	       
	}
	
	 /**
     * 执行批量SET操作
     * @param Map<String,String> map
     * @param nxxx  NX 不存在时set key,XX 存在时 set key
     * @param expx 时间单位 EX 秒,PX 毫秒
     * @param time 生存时间
     * @return
     */
	@Override
    public String batchSet(final Map<String,String> map,final String nxxx,final String expx,final int time) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
            	ShardedJedisPipeline p = e.pipelined();
            	for (Entry<String,String> entry :map.entrySet()) {
//                    p.set(entry.getKey(), entry.getValue());.
            		p.set(entry.getKey(),  entry.getValue(), Utility.isEmpty(nxxx)?"NX":nxxx, Utility.isEmpty(expx)?"EX":expx, time);//p.set(entry.getKey(),  entry.getValue(), "NX", "EX", 3600*24*7);
                }
            	p.sync();
                return null;
            }
        });
    }

	
	/**
     * 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
     * @param key
     * @return
     */
	@Override
    public Long getEffectiveTime(final String key) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.ttl(key);
            }
        });
    }
	
	
    /**
     * 执行hmset操作，并且设置生存时间(大于0则设置生存时间)，单位为秒
     * @param key
     * @param hash
     * @param seconds
     * @return
     */
	@Override
    public String hmset(final String key, final Map<String, String> hash, final int seconds) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
            	String str = e.hmset(key, hash);
            	if(seconds>0){
            		//设置生存时间
            		e.expire(key, seconds);
            	}
                return str;
            }
        });
    }
	
	 /**
     * 执行hget操作，并且设置生存时间(大于0则设置生存时间)，单位为秒
     * @param hashMapkey hashMap的key
     * @param mapkey hashMap的value里的key
     * @return
     */
	@Override
    public String hget(final String hashMapkey, final String mapkey) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
            	return e.hget(hashMapkey, mapkey); 
            }
        });
    }
	
	/***
	 * 通过key模糊获取keys
	 * key  
	 */
	@Override
	public Set<String> hkeys(final String key) {
		return this.execute(new Function<Set<String>, ShardedJedis>() {
            @Override
            public Set<String> callback(ShardedJedis e) {
            	Set<String> keys = new HashSet<String>();
     	        Collection<Jedis> shards = e.getAllShards();//获取所有的缓存实例
     	        for (Jedis shard : shards) {
     	        	Set<String> set = shard.keys(key);//获取匹配prefix的所有的key
     	        	Iterator<String> it = set.iterator();  
	     	          while(it.hasNext()){  
	     	              String keyStr = it.next();  
	     	              keys.add(keyStr);
	     	          }  
     	        }
                return keys;
            }
        });
	}
}
