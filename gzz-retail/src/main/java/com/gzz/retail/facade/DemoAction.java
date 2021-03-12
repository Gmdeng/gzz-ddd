package com.gzz.retail.facade;

import com.gzz.boot.payment.alipay.AliPayProcessor;
import com.gzz.boot.payment.alipay.AliPayRequest;
import com.gzz.boot.payment.wxpay.WxPayRequest;
import com.gzz.boot.payment.wxpay.WxPayProcessor;
import com.gzz.boot.sms.SmsTemplate;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.IdGenerator;
import com.gzz.retail.application.system.SystemApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoAction {
	@Autowired
	private SmsTemplate smsTemplate;
	@Autowired
	private WxPayProcessor wxPayProcessor;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private AliPayProcessor aliPayProcessor;
	@Autowired
	private SystemApp systemApp;

	/**
	 * 测试一下
	 * @param name
	 * @return
	 */
	@RequestMapping("/testSystemApp")
	public Object testSystemApp(String name) {
		System.out.println("请求M: " + name);

		if(name== null){
			name = UUID.randomUUID().toString().replace("-","");
		}
		systemApp.saveModule(name);
		return "hello";
	}

	/**
	 * 测试一下
	 * @param id
	 * @return
	 */
	@RequestMapping("/testGetApp")
	public Object testGetSystemApp(Long id) {
		//TRACE < DEBUG < INFO < WARN < ERROR < FATAL

		log.debug("我是DEBUG信息");
		log.info("我是INFO信息");
		log.warn("我是WARN信息");
		log.error("我是ERROR信息");
		System.out.println("请求P: " + id);
		return systemApp.getModule(id);
	}

	/**
	 * 测试一下
	 * @return
	 */
	@RequestMapping("/testGetAppList")
	public Object testGetList() {
		System.out.println("请求P: List");
		return systemApp.getModuleList(new ParamMap());
	}
	@RequestMapping("/getTicket")
	public Object getTicket(Long id) {
		System.out.println("" + id);
		smsTemplate.sendSMS("hello sending");
		WxPayRequest wxPayRequest = WxPayRequest.buildH5Pay()
				.amount("9834")
				.tradeNo(idGenerator.generateNo("wx-"))
				.notifyUrl("http://www.g-zz.com");
		wxPayProcessor.unifiedOrder(wxPayRequest);

		return "hello";
	}


	@RequestMapping("/getAliPay")
	public Object getAliPay(Long id) {
		System.out.println("" + id);

		AliPayRequest payRequest = AliPayRequest.buildH5Pay()
				.amount("9834")
				.tradeNo(idGenerator.generateNo("ali-"))
				.notifyUrl("http://www.g-zz.com");
		aliPayProcessor.unifiedOrder(payRequest);

		return "hello";
	}

}
