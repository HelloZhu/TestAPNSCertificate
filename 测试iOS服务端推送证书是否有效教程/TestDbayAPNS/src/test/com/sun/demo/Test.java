package com.sun.demo;

import java.util.List;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;
import com.dbay.apns4j.model.PushNotification;

public class Test {

	private static IApnsService apnsService;

	private static IApnsService getApnsService() {
		if (apnsService == null) {
			ApnsConfig config = new ApnsConfig();
			config.setKeyStore("/Users/apple2/Desktop/TestDbayAPNS/src/test/com/sun/demo/push.p12");
			config.setDevEnv(false);
			config.setPassword("ahw123456");
			config.setPoolSize(5);
			apnsService = ApnsServiceImpl.createInstance(config);
		}
		return apnsService;
	}

	public static void main(String[] args) {
		// pushMsg();
		getFeedbacks();
	}

	/**
	 * 获取反馈
	 */
	public static void getFeedbacks() {

		IApnsService service = getApnsService();

		List<Feedback> list = service.getFeedbacks();
		if (list != null && list.size() > 0) {
			for (Feedback feedback : list) {
				System.out.println(feedback.getDate() + " "
						+ feedback.getToken());
			}
		}
	}

	/**
	 * 发送消息
	 */
	public static void pushMsg() {

		IApnsService service = getApnsService();

		Payload payload = new Payload();
		payload.setAlert("test");
		payload.setSound("default");
		payload.setBadge(3);
//		payload.addParam("type", "IM");

		PushNotification notification = new PushNotification();
		notification
				.setToken("c5f5e2a2d937fc6c9f34c236caf4177899a13269348d7c19d5e4c8c3d357ccf1");
		notification.setPayload(payload);
		notification.setId(324325);

		// con.sendNotification(notification);

		service.sendNotification(
				"c5f5e2a2d937fc6c9f34c236caf4177899a13269348d7c19d5e4c8c3d357ccf1",
				payload);

	}

}
