package com.mine.product.msgboard.webservice;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IMsgboardWebservice {
	@WebMethod
	public String getReplyFromPsp(Map<String, String> map);
}
