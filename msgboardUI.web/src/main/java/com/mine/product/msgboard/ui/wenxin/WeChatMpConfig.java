package com.mine.product.msgboard.ui.wenxin;


import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WeChatMpConfig {
	  /**
     * 配置WxMpService所需信息
     * @return
     */
    @Bean  // 此注解指定在Spring容器启动时，就执行该方法并将该方法返回的对象交由Spring容器管理
	public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        // 设置配置信息的存储位置
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

        return wxMpService;
    }

    
    /**
     * 配置appID和appsecret
     * @return
     */
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
    	Resource res1 = new ClassPathResource("config/weixin.properties");
		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 	   
		//获得路径
 		String mpAppId=p.getProperty("mpAppId").replaceAll("\\s*", "");
 		String mpAppSecret=p.getProperty("mpAppSecret").replaceAll("\\s*", "");
   
    	
        // 使用这个实现类则表示将配置信息存储在内存中
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(mpAppId);
        wxMpInMemoryConfigStorage.setSecret(mpAppSecret);

        return wxMpInMemoryConfigStorage;
    }

}
