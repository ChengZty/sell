package com.buss.goods.service;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.buss.goods.entity.TGoodsDescEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.entity.TGoodsPicEntity;
import com.buss.goods.entity.TGoodsStoreEntity;
import com.buss.goods.entity.TSpecHeadersEntity;
import com.buss.goods.vo.GoodsStockImportVo;
import com.buss.goods.vo.GoodsWordsImportVo;
import com.buss.goods.vo.LowestPriceImportVo;
import com.buss.goods.vo.TGoodsGroupVo;
import com.buss.goods.vo.TGoodsSellVo;
import com.buss.goods.vo.TNewGoodsImportVo;

public interface TGoodsServiceI extends CommonService{

	List<TGoodsStoreEntity> getGoodsStores(String goodsId);

	List<TGoodsPicEntity> getGoodsPics(String goodsId);

	TGoodsDescEntity getGoodsDesc(String goodsId);

	public void deleteGoods(String goodsId);


	/**批量导入组合基础属性及明细商品
	 * @param listTGoodsEntitys
	 * @return
	 */
	public String saveBatchOfGroup(List<TGoodsGroupVo> listTGoodsEntitys);

	/**更新商品库存
	 * @param tGoods
	 */
	public void updateGoodsStore(TGoodsEntity tGoods);

	/**上架
	 * @param tGoods
	 */
	public void doUp(TGoodsEntity tGoods);

	/**下架并删除活动中的相关商品，取消相关未付款的订单，如果录入了库存则要还库存
	 * @param tGoods
	 */
	public void doDown(TGoodsEntity tGoods);

	/**查询在售商品列表
	 * @param request
	 * @return
	 */
	public List<TGoodsSellVo> getSellGoodsBySql(HttpServletRequest request);

	public void updateRecommondPics(TGoodsEntity tGoods);

	/**上新 保存商品（包括d+商品）和组合
	 * @param tGoods
	 * @param tSpecHeader
	 */
	public void saveNewGoods(TGoodsEntity tGoods,TSpecHeadersEntity tSpecHeader);

	/**上新 更新商品
	 * @param tGoods
	 * @param tSpecHeader
	 */
	public void updateNewGoods(TGoodsEntity t);

	public void doChangeActivityPrice(TGoodsEntity tGoods,BigDecimal activityPrice);

	/**修改价格（原价，现价，最低价）
	 * @param request
	 */
	public void doChangePrice(HttpServletRequest request);

	/**修改属性
	 * @param request
	 */
	public void doChangeProperty(HttpServletRequest request);

	/**排序
	 * @param id
	 * @param type
	 * @param sortNum
	 * @return
	 */
	public void doChangeSort(String id, String type, String sortNum);

	/**更新零售商商品详情(4个真)
	 * @param tGoods
	 */
	public void updateNewGoodsDetail(TGoodsEntity tGoods);

	/**校验并批量保存商品相关信息
	 * @param map
	 * @return
	 */
	String batchSaveGoods(Map<String, Object> map) throws SQLException;

	
	/**校验并批量修改最低价
	 * @param lowestPriceList
	 * @return 
	 */
	String batchChangePirce(List<LowestPriceImportVo> lowestPriceList);

	/**批量保存商品话术
	 * @param wordsList
	 * @return
	 */
	String batchSaveGoodsWords(List<GoodsWordsImportVo> wordsList) throws Exception;

	/**批量校验并保存导入的商品基础数据
	 * @param importList
	 * @param headers 
	 * @return
	 */
	Map<String, String> batchSaveNewGoods(List<TNewGoodsImportVo> importList, TSpecHeadersEntity headers) throws Exception;

	/**上传excel文件到七牛
	 * @param pojoClass	实体类
	 * @param dataSet	数据集合
	 * @param retailer	零售商
	 * @param model 上传模块
	 * @return 文件路径
	 */
	String uploadExcelFileToQN(Class<?> pojoClass,  Collection<?> dataSet,TSUser retailer,String model,String title) throws IOException;

	/**批量更新商品库存
	 * @param importList
	 * @return
	 */
	Map<String, String> batchUpdateGoodsStock(List<GoodsStockImportVo> importList) throws Exception;

}
