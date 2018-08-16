package com.ysw.fanfou.fan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.ysw.fanfou.fan.Oauth;

public class DoToken {
	String oauth_token;

	public String getOauth_token() {
		return oauth_token;
	}

	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}
	
	public String execute(){
		System.out.println("注意！！！！前方高能预警！！！！\noauth_token如下："+oauth_token);
		//获得了oauth_token
		Oauth oauth=new Oauth();
		String timestamp=oauth.oauth_timestamp;
		String nonce = oauth.oauth_nonce;
		System.out.println("随机串："+nonce);// 输出随机字符串
		System.out.println("时间戳："+timestamp);// 输出时间戳
		String method="GET";
		String url="http://fanfou.com/oauth/access_token";
		// System.out.println(sendGet("http://www.fanfou.com/",""));//测试get请求
		// 发送get请求成功

		try {
//			String ceshijiami = hmacsha1("abc", "123");
//			ceshijiami=URLEncoder.encode(ceshijiami, "utf-8");
//			System.out.println("测试加密算法：" + ceshijiami);
			String dict1=oauth.dict(oauth.oauth_consumer_key,"HMAC-SHA1",oauth_token,nonce,timestamp);
			String basestring = oauth.BaseString(method,url,dict1);
			System.out.println("basestring:"+basestring);
			String oauth_signature = oauth.hmacsha1(basestring, Fanfou.oauth_consumer_secret+"&"+Fanfou.request_token_secret);
			System.out.println("转码前签名："+oauth_signature);
			oauth_signature=URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名："+oauth_signature);
			String params = dict1+"&oauth_signature=" + oauth_signature;
					/*"oauth_consumer_key=" + oauth.oauth_consumer_key + 
					"&oauth_nonce=" + nonce + 
					"&oauth_signature=" + oauth_signature+
					"&oauth_signature_method=HMAC-SHA1" + 
					"&oauth_timestamp=" + timestamp;*/
			// params = URLEncoder.encode(params, "utf-8");
			System.out.println("下面正式进行请求");
			String g = oauth.sendGet(url,params);
			System.out.println(g);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
			
		
		
		
		
		return "success";
	}
	
//	oauth_consumer_key	饭否应用的API Key
//	oauth_token	通过以上步骤获得的Request Token
//	oauth_signature_method	签名方法，目前只支持HMAC-SHA1
//	oauth_signature	签名值，签名方法见OAuthSignature
//	oauth_timestamp	时间戳，取当前时间
//	oauth_verifier	[可选]授权码
//	oauth_nonce	单次值，随机的字符串，防止重复请求
	
}
