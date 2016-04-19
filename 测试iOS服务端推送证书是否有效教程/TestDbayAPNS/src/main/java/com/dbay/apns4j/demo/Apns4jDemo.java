package com.dbay.apns4j.demo;


import java.io.InputStream;
import java.util.List;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;


/**
 * @author RamosLi
 *注意： 
 *开发模式：
 *1、push.p12 为开发的推送证书
 *2、token="开发的token"
 *3、config.setDevEnv(true)
 *
 */
public class Apns4jDemo {
	private static IApnsService apnsService;
	
	private static IApnsService getApnsService() {
		if (apnsService == null) {
			ApnsConfig config = new ApnsConfig();
			
			//pushDistri.p12 证书名，要放在src目录下
			InputStream is = Apns4jDemo.class.getClassLoader().getResourceAsStream("push.p12");
			config.setKeyStore(is);
			config.setDevEnv(true);//设置是否为开发模式，false为发布模式，true为调试模式
			config.setPassword("ahw123456");//证书密码
			config.setPoolSize(3);
			apnsService = ApnsServiceImpl.createInstance(config);
		}
		return apnsService;
	}
	
	 //main方法
	public static void main(String[] args) {
		IApnsService service = getApnsService();
		
		// send notification 
		
		//token，从iOS APP注册推送之后可以获取
//		String token = "c5f5e2a2d937fc6c9f34c236caf4177899a13269348d7c19d5e4c8c3d357ccf1";
//		String token = "b405c0f27564ebff7fc358575e3d02aa3ece19addb620cea26cd3e21046cdbe6";
		String token = "868c7fd3f09badd06fe161374b3004682e61a0704cbadfa1f8edb8e5a850e16b";
		
		
		//推送的消息体
		Payload payload = new Payload();
		payload.setAlert("How are you?");
		// If this property is absent, the badge is not changed. To remove the badge, set the value of this property to 0
		payload.setBadge(1);
		// set sound null, the music won't be played
//		payload.setSound(null);
		payload.setSound("msg.mp3");
		payload.addParam("uid", 123456);
		payload.addParam("type", 12);
		service.sendNotification(token, payload);
		
		// payload, use loc string
		Payload payload2 = new Payload();
		payload2.setBadge(1);
		payload2.setAlertLocKey("GAME_PLAY_REQUEST_FORMAT");
		payload2.setAlertLocArgs(new String[]{"Jenna", "Frank"});
		
		//发出推送
		service.sendNotification(token, payload2);
		
		// get feedback 推送发出后从苹果服务器获得的反馈
		List<Feedback> list = service.getFeedbacks();
		if (list != null && list.size() > 0) {
			for (Feedback feedback : list) {
				System.out.println(feedback.getDate() + " " + feedback.getToken());
			}
		}
		
		try {
			// sleep 5s.
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// It's a good habit to shutdown what you never use
		service.shutdown();
		
//		System.exit(0);
	}
}
