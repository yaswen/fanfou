package com.ysw.fanfou.fan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;
import com.ysw.fanfou.fan.Fanfou;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Status {
	public static void main(String[] args) {
		getFriends();
		//getPublicTimeline();//这条为随便看看
		// UpdateStatus("test_让大槐树下长出金子来");//测试发消息
		// System.out.println(notification());//获取未读新提醒数，包括被@，私信，关注请求
		// getMentions();
	}

	private static String access_token = Fanfou.access_token;// 诗文的access_token
	private static String access_token_secret = Fanfou.access_token_secret;// 诗文的access_token_secret

	/**
	 * 发消息方法
	 * 
	 * @param status
	 *            要发送的消息
	 */
	public static void UpdateStatus(String status) {
		String url = "http://api.fanfou.com/statuses/update.json";// 发消息API
		String method = "POST";// 发消息采用POST方法
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
			tmp.put("oauth_token", access_token);
			// 至此是每次都需要put的五个参数
			TreeMap<String, String> tmp2 = tmp;// 另外创建一个tmp，一个用来签名，一个用来写在POST请求的参数里。暂时这样写，应该有更简洁的写法。
			tmp.put("status", status);
			tmp2.put("status", URLEncoder.encode(status, "utf-8"));
			String dict = Oauth.getDict(tmp);
			String dict2 = Oauth.getDict(tmp2);
			System.out.println("dict:" + dict);
			System.out.println("dict2:" + dict2);
			String basestring = oauth.BaseString(method, url, dict2);
			System.out.println("basestring:" + basestring);// 打印到控制台以便调试
			String oauth_signature = oauth.hmacsha1(basestring,
					Fanfou.oauth_consumer_secret + "&" + access_token_secret);
			System.out.println("转码前签名：" + oauth_signature);// 打印到控制台以便调试
			oauth_signature = URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名：" + oauth_signature);// 打印到控制台以便调试
			String params = dict + "&oauth_signature=" + oauth_signature;
			System.out.println("params：" + params);// 打印到控制台以便调试
			System.out.println("下面正式发送请求");
			String g = Oauth.sendPost(url, params);
			System.out.println(g);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}

	}

	/**
	 * 获取未读新提醒数，包括被@，私信，关注请求
	 */
	public static String notification() {
		String url = "http://api.fanfou.com/account/notification.json";// 未读提醒数API
		String method = "GET";// 发消息采用POST方法
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
			tmp.put("oauth_token", Fanfou.access_token);
			// 至此是每次都需要put的五个参数
			TreeMap<String, String> tmp2 = tmp;// 另外创建一个tmp，一个用来签名，一个用来写在POST请求的参数里。暂时这样写，应该有更简洁的写法。
			// tmp.put("status", status);
			// tmp2.put("status",URLEncoder.encode(status,"utf-8"));
			String dict = Oauth.getDict(tmp);
			String dict2 = Oauth.getDict(tmp2);
			String basestring = oauth.BaseString(method, url, dict2);
			System.out.println("basestring:" + basestring);// 打印到控制台以便调试
			String oauth_signature = oauth.hmacsha1(basestring,
					Fanfou.oauth_consumer_secret + "&"
							+ Fanfou.access_token_secret);
			System.out.println("转码前签名：" + oauth_signature);// 打印到控制台以便调试
			oauth_signature = URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名：" + oauth_signature);// 打印到控制台以便调试
			String params = dict + "&oauth_signature=" + oauth_signature;
			System.out.println("params：" + params);// 打印到控制台以便调试
			System.out.println("下面正式发送请求");
			String g = oauth.sendGet(url, params);
			System.out.println(g);
			return g;
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			return null;
		}

	}

	public static void getMentions() {
		String url = "http://api.fanfou.com/statuses/mentions.json";// 获取@的API
		String method = "GET";// 发消息采用POST方法
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
			tmp.put("oauth_token", Fanfou.access_token);
			// 至此是每次都需要put的五个参数
			TreeMap<String, String> tmp2 = tmp;// 另外创建一个tmp，一个用来签名，一个用来写在POST请求的参数里。暂时这样写，应该有更简洁的写法。
			// tmp.put("status", status);
			// tmp2.put("status",URLEncoder.encode(status,"utf-8"));
			String dict = Oauth.getDict(tmp);
			String dict2 = Oauth.getDict(tmp2);
			String basestring = oauth.BaseString(method, url, dict2);
			System.out.println("basestring:" + basestring);// 打印到控制台以便调试
			String oauth_signature = oauth.hmacsha1(basestring,
					Fanfou.oauth_consumer_secret + "&"
							+ Fanfou.access_token_secret);
			System.out.println("转码前签名：" + oauth_signature);// 打印到控制台以便调试
			oauth_signature = URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名：" + oauth_signature);// 打印到控制台以便调试
			String params = dict + "&oauth_signature=" + oauth_signature;
			System.out.println("params：" + params);// 打印到控制台以便调试
			System.out.println("下面正式发送请求");
			String g = oauth.sendGet(url, params);
			System.out.println(g);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
	}
	public static void getFriends() {
		String url="http://api.fanfou.com/users/friends.json";
		String method = "GET";
		String friends = baseFanfou("", url, method);
		JSONArray arr = JSONArray.fromObject(friends);
		for (int i = 0; i < arr.size(); i++) {
			JSONObject user = JSONObject.fromObject(arr.get(i));
			String username=user.getString("name");
			System.out.println(i+1+"."+username);//输出序号和昵称
		}
		//System.out.println(friends);//输出所有，测试用，用于看格式。
	}
	public static void getPublicTimeline() {
		String url = "http://api.fanfou.com/statuses/public_timeline.json";
		String method = "GET";
		String statuses = baseFanfou("", url, method);
		JSONArray arr = JSONArray.fromObject(statuses);
		// System.out.println("第一条消息如下：");
		// System.out.println(arr);
		// arr.get(0)
		System.out.println(arr.get(0));
		String[] txt = new String[arr.size()];
		System.out.println("size" + arr.size());
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj = JSONObject.fromObject(arr.get(i));
			txt[i] = obj.getString("text");
			JSONObject user=JSONObject.fromObject(obj.getString("user"));//试了一下竟然成功了，按理说getString的结果是String，fromObject，按理说应该从JSON对象里面取，看来兼容了。
			String username=user.getString("name");
			System.out.println(username+"\t\t"+txt[i]);//输出昵称和消息内容
		}
		// JSONObject obj = JSONObject.fromObject(statuses);
		// obj.put("name", "John");
		// System.out.println(obj.toString());
	}

	public static String baseFanfou(String status, String url, String method) {
		// String url="http://api.fanfou.com/statuses/update.json";//发消息API
		// String method="POST";//发消息采用POST方法
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
			tmp.put("oauth_token", Fanfou.access_token);
			// 至此是每次都需要put的五个参数
			TreeMap<String, String> tmp2 = tmp;// 另外创建一个tmp，一个用来签名，一个用来写在POST请求的参数里。暂时这样写，应该有更简洁的写法。
			if (!status.equals("")) {

				tmp.put("status", status);
				tmp2.put("status", URLEncoder.encode(status, "utf-8"));
			}
			String dict = Oauth.getDict(tmp);
			String dict2 = Oauth.getDict(tmp2);
			String basestring = oauth.BaseString(method, url, dict2);
			System.out.println("basestring:" + basestring);// 打印到控制台以便调试
			String oauth_signature = oauth.hmacsha1(basestring,
					Fanfou.oauth_consumer_secret + "&"
							+ Fanfou.access_token_secret);
			System.out.println("转码前签名：" + oauth_signature);// 打印到控制台以便调试
			oauth_signature = URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println("转码后签名：" + oauth_signature);// 打印到控制台以便调试
			String params = dict + "&oauth_signature=" + oauth_signature;
			System.out.println("params：" + params);// 打印到控制台以便调试
			System.out.println("下面正式发送请求");
			String g = null;
			if (method.equals("POST")) {
				g = oauth.sendPost(url, params);
			} else if (method.equals("GET")) {
				g = oauth.sendGet(url, params);
			}
			System.out.println(g);
			return g;

		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			return null;
		}

	}
}
