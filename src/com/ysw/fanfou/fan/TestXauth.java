package com.ysw.fanfou.fan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;
import com.ysw.fanfou.fan.Fanfou;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestXauth {
	public static void main(String[] args) {
		xauth("my username", "my password");
		//getFriends();
		//getPublicTimeline();//这条为随便看看
		// UpdateStatus("test_让大槐树下长出金子来");//测试发消息
		// System.out.println(notification());//获取未读新提醒数，包括被@，私信，关注请求
		// getMentions();
	}

	private static String access_token = Fanfou.access_token;// 诗文的access_token
	private static String access_token_secret = Fanfou.access_token_secret;// 诗文的access_token_secret

	/**
	 * xauth
	 * 
	 * @param username,password
	 *            账号和密码
	 */
	public static void xauth(String username,String password) {
		String url = "http://fanfou.com/oauth/access_token";// xauth url
		String method = "GET";// 发消息采用GET方法
		// String access_token=Fanfou.access_token;//当前的access_token
		// String
		// access_token_secret=Fanfou.access_token_secret;//当前的access_token_secret
		Oauth oauth = new Oauth();
		String timestamp = oauth.oauth_timestamp;
		String nonce = oauth.oauth_nonce;
		System.out.println("随机串：" + nonce);// 输出随机字符串
		System.out.println("时间戳：" + timestamp);// 输出时间戳
		try {
			@SuppressWarnings("deprecation")
			TreeMap<String, String> tmp = new TreeMap<String, String>();
			tmp.put("oauth_consumer_key", oauth.oauth_consumer_key);
			tmp.put("oauth_nonce", nonce);
			tmp.put("oauth_signature_method", "HMAC-SHA1");
			tmp.put("oauth_timestamp", timestamp);
			tmp.put("x_auth_username", username);
			tmp.put("x_auth_password", password);
			tmp.put("x_auth_mode", "client_auth");
			// 至此是两个tmp的共有参数
			//怀疑可能某些参数需要encode再存入不同的dict，待会儿试试。因此，创建另一个tmp
			TreeMap<String, String> tmp2 = tmp;// 另外创建一个tmp，一个用来签名，一个用来写在POST请求的参数里。暂时这样写，应该有更简洁的写法。
//			tmp.put("status", status);
//			tmp2.put("status", URLEncoder.encode(status, "utf-8"));
			String dict = Oauth.getDict(tmp);
			String dict2 = Oauth.getDict(tmp2);
			System.out.println("dict:" + dict);
			System.out.println("dict2:" + dict2);
			String basestring = oauth.BaseString(method, url, dict2);
			System.out.println("basestring:" + basestring);// 打印到控制台以便调试
			String oauth_signature = oauth.hmacsha1(basestring,
					Fanfou.oauth_consumer_secret + "&" /*+ access_token_secret*/);
			System.out.println("转码前签名：" + oauth_signature);// 打印到控制台以便调试
			oauth_signature = URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名：" + oauth_signature);// 打印到控制台以便调试
			String params = dict + "&oauth_signature=" + oauth_signature;
			System.out.println("params：" + params);// 打印到控制台以便调试
			System.out.println("下面正式发送请求");
			String g = Oauth.sendGet(url, params);
			System.out.println(g);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}

	}

}
