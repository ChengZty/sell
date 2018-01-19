package com.buss.newGoods.service;
import com.buss.newGoods.entity.TGuideRecommendInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TGuideRecommendInfoServiceI extends CommonService{

	public void saveRecommendInfo(TGuideRecommendInfoEntity tGuideRecommendInfo);

	public void deleteRecommendInfo(TGuideRecommendInfoEntity tGuideRecommendInfo);

	public void updateRecommendInfo(TGuideRecommendInfoEntity t);
}
