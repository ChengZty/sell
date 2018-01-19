package org.jeecgframework.core.util;

import java.util.Collection;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BaseResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


/**
 * 极光推送工具类
 * @author liuxing
 * 
 */
public class JPushUtil {
	
	/**通过别名发送通知
	 * @param jpush
	 * @param list 别名列表
	 * @param title 通知栏标题
	 * @param alert 通知栏内容
	 * @return
	 */
	public static int sendJPushNotificationByAlias(JPushClient jpush,Collection<String> list,String title,String alert){
		int errCount=0;
		PushPayload pushPayload = PushPayload.newBuilder()
		        .setPlatform(Platform.all())
		        .setAudience(Audience.alias(list))
//		        .setAudience(Audience.tag(tStoreSNotice.getStoreId()))//一次推送最多 20 个
		        .setNotification(Notification.newBuilder()
		        		.addPlatformNotification(IosNotification.newBuilder()
		        		.setAlert(title)//内容为空则不展示到通知栏
		        		.setBadge(1)
//		                .setSound("happy")
		                .build())
		                .addPlatformNotification(AndroidNotification.newBuilder()
		                .setTitle(title)//通知栏标题
		                .setAlert(alert)//内容为空则不展示到通知栏
//		                .addExtra("count", count.intValue())
		                .build())
		        .build())
//		        .setMessage(Message.newBuilder()
//		        		.setTitle("jobRedCountMessage")
//		        		.setMsgContent("待完成任务数更新")
//		        		.addExtra("count", count.intValue())
//		        		.addExtra("from", "JPush")
//		        		.build()
//		        		)
		        .setOptions(Options.newBuilder().setApnsProduction(true).build())
		        .build();
				PushResult res;
				try {
					LogUtil.debug(pushPayload.toString());
					res = jpush.sendPush(pushPayload);
					int code = res.statusCode;
					if (BaseResult.ERROR_CODE_OK != code) {// 发送不成功
						errCount = 1;
						LogUtil.info(title+"发送失败， 错误代码=" + res.statusCode);
					} else {
						System.out.println(title+"发送成功");
					}
				} catch (APIConnectionException e) {
					errCount = 1;
					LogUtil.info(title+"发送失败， 错误信息=" + e.getMessage());
					e.printStackTrace();
				} catch (APIRequestException e) {
					errCount = 1;
					LogUtil.info(title+"发送失败， 错误代码=" + e.getErrorCode() + ",错误信息="+ e.getErrorMessage());
					e.printStackTrace();
				}
		return errCount;
	}

	/**
	 * @param jpush
	 * @param title 通知栏标题
	 * @param alert 通知栏内容
	 * @param tag 标签，多个用英文逗号隔开
	 * @return
	 */
	public static int sendJPushNotificationByTags(JPushClient jpush, String title, String alert, String... tag) {
		int errCount=0;
		PushPayload pushPayload = PushPayload.newBuilder()
		        .setPlatform(Platform.all())
		        .setAudience(Audience.tag(tag))
//		        .setAudience(Audience.tag(tStoreSNotice.getStoreId()))//一次推送最多 20 个
		        .setNotification(Notification.newBuilder()
		        		.addPlatformNotification(IosNotification.newBuilder()
		        		.setAlert(title)//内容为空则不展示到通知栏
		        		.setBadge(1)
//		                .setSound("happy")
		                .build())
		                .addPlatformNotification(AndroidNotification.newBuilder()
		                .setTitle(title)//通知栏标题
		                .setAlert(alert)//内容为空则不展示到通知栏
//		                .addExtra("count", count.intValue())
		                .build())
		        .build())
//		        .setMessage(Message.newBuilder()
//		        		.setTitle("jobRedCountMessage")
//		        		.setMsgContent("待完成任务数更新")
//		        		.addExtra("count", count.intValue())
//		        		.addExtra("from", "JPush")
//		        		.build()
//		        		)
		        .setOptions(Options.newBuilder().setApnsProduction(true).build())
		        .build();
				PushResult res;
				try {
					LogUtil.debug(pushPayload.toString());
					res = jpush.sendPush(pushPayload);
					int code = res.statusCode;
					if (BaseResult.ERROR_CODE_OK != code) {// 发送不成功
						errCount = 1;
						LogUtil.info(title+"发送失败， 错误代码=" + res.statusCode);
					} else {
						System.out.println(title+"发送成功");
					}
				} catch (APIConnectionException e) {
					errCount = 1;
					LogUtil.info(title+"发送失败， 错误信息=" + e.getMessage());
					e.printStackTrace();
				} catch (APIRequestException e) {
					errCount = 1;
					LogUtil.info(title+"发送失败， 错误代码=" + e.getErrorCode() + ",错误信息="+ e.getErrorMessage());
					e.printStackTrace();
				}
		return errCount;
	};

}
